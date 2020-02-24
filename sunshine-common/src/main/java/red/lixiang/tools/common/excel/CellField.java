package red.lixiang.tools.common.excel;

/**
 *
 * @Author lixiang
 * @CreateTime 2019/11/30
 **/
public class CellField {

    /** cell 值,字符串 */
    public static final int FIELD_STRING = 1;

    /** cell类型, 普通的 */
    public static final int CELL_NORMAL = 1;

    /** cell类型, 下拉框
     * 下拉框的中的java字段一定要和实体类的值对应,不然不好set进去,而且最好是和String类型的对应
     */
    public static final int CELL_DROP = 2;


    /** java实体类名字 */
    private String javaName;

    /** excel表头的东西 */
    private String cellName;

    /** cell值的类型,有字符串,日期,等等,默认是字符串的 */
    private Integer fieldType = 1;
    /** cell 的类型, 有普通的,下拉框,等等,默认是普通的 */
    private Integer cellType = 1;

    /** 排序值,默认是0 */
    private Integer order = 0;

    /** 默认值 */
    private Object defaultValue;

    public CellField() {
    }

    public CellField(String javaName, String cellName) {
        this.javaName = javaName;
        this.cellName = cellName;
    }

    public CellField(String javaName, String cellName, Integer cellType) {
        this.javaName = javaName;
        this.cellName = cellName;
        this.cellType = cellType;
    }

    public String getJavaName() {
        return javaName;
    }

    public CellField setJavaName(String javaName) {
        this.javaName = javaName;
        return this;
    }

    public String getCellName() {
        return cellName;
    }

    public CellField setCellName(String cellName) {
        this.cellName = cellName;
        return this;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public CellField setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public Integer getCellType() {
        return cellType;
    }

    public CellField setCellType(Integer cellType) {
        this.cellType = cellType;
        return this;
    }

    public Integer getOrder() {
        return order;
    }

    public CellField setOrder(Integer order) {
        this.order = order;
        return this;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public CellField setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

}
