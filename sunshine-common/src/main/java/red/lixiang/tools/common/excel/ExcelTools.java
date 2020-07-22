package red.lixiang.tools.common.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import red.lixiang.tools.jdk.StringTools;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author lixiang
 * @CreateTime 2019/11/29
 **/
public class ExcelTools {

    public static <T> List<T> excelToList(InputStream inputStream, List<CellField> cellFieldList, Class<T> clazz) {
        return excelToList(inputStream, cellFieldList, clazz, true);
    }

    /**
     * 从文件中获取sheet
     *
     * @param inputStream
     * @param newFlag
     * @return
     */
    public static Sheet sheetFromFile(InputStream inputStream, boolean newFlag) {
        try {
            //fileInputStream就是一个book
            Workbook xssfWorkbook = newFlag ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);
            //从book中获取第一个sheet
            Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
            return xssfSheet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把Excel的Row转成实体类Bean
     *
     * @param row
     * @param cellFieldList
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T rowToBean(Sheet sheet, Row row, List<CellField> cellFieldList, Class<T> clazz) {

        try {
            // 把clazz进行实例化
            T t = clazz.getDeclaredConstructor().newInstance();
            // 把cellFieldList转成map<列名,java实体类名称>
            Map<String, String> cellMap = cellFieldList.stream().collect(Collectors.toMap(CellField::getCellName, CellField::getJavaName));
            // 先从sheet 获取第一行
            Row header = sheet.getRow(0);
            // 遍历传进来的row
            short cellNum = header.getLastCellNum();
            for (int i = 0; i < cellNum; i++) {
                // 获取excel列名
                String cellName = header.getCell(i).getStringCellValue();
                Field field = clazz.getDeclaredField(cellMap.get(cellName));
                field.setAccessible(true);
                // 获取excel中的值
                Cell cell = row.getCell(i);
                String value = cellValue(cell);
                if(StringTools.isBlank(value)){
                    continue;
                }
                // 把值注入到实体类中
                setEntityValue(field,value,t);
            }
            return t;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Excel中cell的值
     * @param cell
     * @return
     */
    private static String cellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        String value = "";
        switch (cell.getCellType()) {
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    // 是时间类型的
                    Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = format.format(date);
                    break;
                }
                DataFormatter formatter = new DataFormatter();
                value = formatter.formatCellValue(cell);
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;

            case BLANK:
                value = null;
                break;

            case BOOLEAN:
                value = "" + cell.getBooleanCellValue();
                break;

            case ERROR:
                value = "" + cell.getErrorCellValue();
                break;

            default:
                value = "" + cell.getStringCellValue();
        }
        return value;
    }

    public static void setEntityValue(Field field, String cellValue, Object t) {
        try {
            field.setAccessible(true);
            if (field.getType() == Integer.class) {
                //因为会有12.0这样的情况
                double d = Double.parseDouble(cellValue);
                field.set(t, (int) d);
            } else if (field.getType() == Double.class) {
                double d = Double.parseDouble(cellValue);
                field.set(t, d);
            } else if (field.getType() == BigDecimal.class) {
                BigDecimal b = new BigDecimal(cellValue);
                field.set(t, b);
            } else if (field.getType() == Long.class) {
                Long l = Long.parseLong(cellValue);
                field.set(t, l);
            } else if(field.getType() == Date.class){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date parse = format.parse(cellValue);
                    field.set(t,parse);
                } catch (ParseException e) {

                }

            } else  {
                field.set(t, cellValue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> excelToList(InputStream inputStream, List<CellField> cellFieldList, Class<T> clazz, boolean newFlag) {
        List<T> list = new ArrayList<>();
        //hashMap是以键值对(key and value)来存储的
        //<列名，单元格值>
        HashMap<String, String> indexMap = new HashMap<>();
        try {
            //fileInputStream就是一个book
            Workbook xssfWorkbook = newFlag ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);
            //从book中获取第一个sheet
            Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
            //获取sheet的总行数
            int rows = xssfSheet.getPhysicalNumberOfRows();
            //规定第1(引索是0)行是字段名行
            Row firstRow = xssfSheet.getRow(0);
            //遍历是从第2(引索是1)行开始
            for (int i = 1; i < rows; i++) {
                //从整个sheet里面获取第i行的数据
                Row xssfRow = xssfSheet.getRow(i);
                if (xssfRow == null) {
                    //如果某一行为空 就跳过读取下一行
                    continue;
                }
                //读取一行中的每一个单元格
                for (int j = 0; j < xssfRow.getLastCellNum(); j++) {
                    //获取该单元格的列名
                    //getStringCellValue从单元格取出类型为String的值
                    String columnCellName = firstRow.getCell(j).getStringCellValue();
                    //开始从第一列开始遍历单元格
                    Cell cell = xssfRow.getCell(j);
                    String value = cellValue(cell);
                    indexMap.put(columnCellName, value);
                }
                //一行循环之后，将值注入到新生成的实例中，然后放到list中
                try {

                    T t = clazz.getDeclaredConstructor().newInstance();

                    //获取到传入Class的全部变量名
                    // 对CellList进行循环, 然后赋值
                    for (CellField cellField : cellFieldList) {
                        String javaName = cellField.getJavaName();
                        String columnName = cellField.getCellName();
                        try {
                            String cellValue = indexMap.get(columnName);
                            if (StringTools.isBlank(cellValue)) {
                                continue;
                            }
                            Field field = clazz.getDeclaredField(javaName);
                            setEntityValue(field,cellValue,t);
                        } catch (NoSuchFieldException e) {
                            // 如果发生了这个异常,说明没有对应的java字段,可能是下拉框
                            // 对异常不做处理,也就是对这个不做处理
                        }
                    }

                    list.add(t);

                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


    /**
     * 这个是只生成表头文档
     *
     * @param cellFields
     * @param <T>
     * @return
     */
    public static <T> Workbook beanToExcel(List<CellField> cellFields) {
        return beanToExcel(null, cellFields, null, true);
    }

    /**
     * 将List对象转换为excel文档.
     *
     * @param list       POJO对象列表
     * @param cellFields 表头的描述项
     */
    public static <T> Workbook beanToExcel(List<T> list, List<CellField> cellFields, Class<T> clazz) {
        return beanToExcel(list, cellFields, clazz, true);
    }

    public static <T> Workbook beanToExcel(List<T> list, List<CellField> cellFields, Class<T> clazz, boolean newFlag) {
        cellFields.sort(Comparator.comparingInt(CellField::getOrder));
        Map<String, CellField> paramMap = cellFields.stream().collect(Collectors.toMap(CellField::getJavaName, x -> x, (x, y) -> x, LinkedHashMap::new));

        Workbook workbook = newFlag ? new XSSFWorkbook() : new HSSFWorkbook();

        Sheet sheet = workbook.createSheet();

        // 渲染第一行的表头
        Row firstRow = createExcelHeader(sheet, cellFields);

        if (list == null || clazz == null) {
            //只渲染一张空表,可以直接返回
            return workbook;
        }

        // 处理要反射的字段
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap<>(fields.length);
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            fieldMap.put(fieldName, field);
        }

        int beginNum = 1;
        for (T d : list) {

            Row row = accessRow(sheet, beginNum++);

            for (int i = 0; i < paramMap.size(); i++) {
                //开始为每行写入值
                try {
                    for (int i1 = 0; i1 < cellFields.size(); i1++) {
                        //获取表头对应的java名
                        CellField cellField = cellFields.get(i1);
                        String javaName = cellField.getJavaName();
                        String cellHeaderName = cellField.getCellName();

                        if (javaName != null && cellHeaderName.equals(firstRow.getCell(i).getStringCellValue())) {
                            Field field = fieldMap.get(javaName);
                            Cell cell = accessCell(row, i);
                            if (field == null) {
                                //对于CellFieldList里面有的字段,实体类中没有的,跳过这一列
                                cell.setBlank();
                                continue;
                            }
                            Object obj = field.get(d);
                            if (obj == null) {
                                continue;
                            } else if (String.class.isAssignableFrom(obj.getClass())) {
                                cell.setCellValue((String) obj);
                            } else if (Date.class.isAssignableFrom(obj.getClass())) {
                                // 日期存储为Number，显示需求依赖CellStyle
                                cell.setCellValue((Date) obj);
                            } else if (Number.class.isAssignableFrom(obj.getClass())) {
                                // 数字类型的也都按字符串方式来存
                                cell.setCellValue(String.valueOf(obj));
                            } else if (Boolean.class.isAssignableFrom(obj.getClass())) {
                                cell.setCellValue(((Boolean) obj));
                            } else {
                                cell.setCellValue(obj.toString());
                            }
                        }
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;

    }

    /**
     * 创建Excel的表头
     */
    public static Row createExcelHeader(List<CellField> cellFields) {
        Workbook wb = new XSSFWorkbook();
        return createExcelHeader(wb.createSheet(), cellFields);
    }

    public static Row createExcelHeader(Sheet sheet, List<CellField> cellFields) {
        cellFields.sort(Comparator.comparingInt(CellField::getOrder));
        Map<String, CellField> paramMap = cellFields.stream().collect(Collectors.toMap(CellField::getJavaName, x -> x, (x, y) -> x, LinkedHashMap::new));

        int colPointer = 0;
        // 渲染第一行的表头
        Row firstRow = accessRow(sheet, 0);
        for (CellField field : cellFields) {
            String fieldName = field.getJavaName();
            if (paramMap.get(fieldName) != null) {
                Cell cell = accessCell(firstRow, colPointer++);
                // System.out.println(fieldName + "!!!!" + paramMap.get(fieldName));
                cell.setCellValue(paramMap.get(fieldName).getCellName());
                if (CellField.CELL_DROP == field.getCellType()) {
                    createDropList((List<String>) field.getDefaultValue(), cell, sheet);
                }
            }
        }
        return firstRow;
    }

    /**
     * 对excel创建下拉框
     *
     * @param list
     * @param cell
     * @param sheet
     */
    public static void createDropList(List<String> list, Cell cell, Sheet sheet) {
        CellRangeAddressList addressList = new CellRangeAddressList(1, 2000, cell.getColumnIndex(), cell.getColumnIndex());
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        DataValidationConstraint dvConstraint = dvHelper.createExplicitListConstraint(list.toArray(new String[0]));
        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
        sheet.addValidationData(validation);
    }

    /**
     * 导出Excel文件
     *
     * @param wb
     */
    public static void exportExcelFile(Workbook wb, String partFileName) {
        if (wb != null) {
            String fileName = "Excel-" + partFileName + "-"
                    + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".xlsx";
            OutputStream out = null;
            try {
                out = new FileOutputStream(new File(fileName));
                wb.write(out);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /***
     * 创建一行
     * @param sheet
     * @param rowNum
     * @return
     */
    private static Row accessRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        return row;
    }

    /**
     * 创建一个单元格
     *
     * @param row
     * @param column
     * @return
     */
    private static Cell accessCell(Row row, int column) {
        Cell cell = row.getCell(column);
        if (cell == null) {
            cell = row.createCell(column);
        }
        return cell;
    }


    public static void main(String[] args) {
//        Workbook wb  = new XSSFWorkbook();
//        Sheet sheet = wb.createSheet();
//        List<CellField> list = new ArrayList<>();
//        CellField cellField = new CellField();
//        List<String> values = new ArrayList<>();
//        values.add("aaaa");
//        values.add("bbbb");
//        cellField.setDefaultValue(values);
//        cellField.setOrder(2);
//        cellField.setJavaName("javaName").setCellName("CellName").setCellType(CELL_DROP);
//
//        CellField c2 = new CellField();
//        c2.setCellName("second");
//        c2.setOrder(1);
//        list.add(cellField);
//        list.add(c2);
//        createExcelHeader(sheet,list);
//        exportExcelFile(wb,"test");

        String s = "123.0";
        System.out.println(s.replaceAll("\\.0", ""));
    }
}
