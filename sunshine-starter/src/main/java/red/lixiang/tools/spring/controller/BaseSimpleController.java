package red.lixiang.tools.spring.controller;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import red.lixiang.tools.base.BaseResponse;
import red.lixiang.tools.base.KV;
import red.lixiang.tools.base.PageData;
import red.lixiang.tools.base.annotation.TableField;
import red.lixiang.tools.common.mybatis.BaseMapper;
import red.lixiang.tools.common.mybatis.model.BaseQC;
import red.lixiang.tools.common.mybatis.model.Page;
import red.lixiang.tools.common.mybatis.model.Sort;
import red.lixiang.tools.jdk.ListTools;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.spring.ContextHolder;
import red.lixiang.tools.spring.convert.ConvertorTools;
import red.lixiang.tools.spring.processor.ProcessorCache;
import red.lixiang.tools.spring.processor.SimpleProcessor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static red.lixiang.tools.common.mybatis.MybatisToolCache.DOMAIN_DO_CACHE;
import static red.lixiang.tools.common.mybatis.MybatisToolCache.DOMAIN_QC_CACHE;

/**
 * 这个类,可以直接走mapper
 *
 * @author lixiang
 * @date 2020/6/23
 **/
@RestController
public class BaseSimpleController extends CommonController {

    @Resource
    private SysDicConvertor sysDicConvertor;

    /**
     * 查询列表的方法
     *
     * @param domain
     * @param page
     * @param sort
     * @return
     */
    @GetMapping("/simple/{domain}/query")
    public BaseResponse<PageData> simpleQuery(@PathVariable String domain, Page page, Sort sort) {
        HttpServletRequest request = getRequest();
        //拿到入参
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = new HashMap<>();
        parameterMap.forEach((key, value) -> {
            if (value != null) {
                map.put(key, value[0]);
            }
        });
        // 通过domain去找mapper的类, 这里的domain实际上就是表名
        BaseMapper proxy = ContextHolder.getBean(domain + "Mapper", BaseMapper.class);
        //找到对应的QC
        Class<?> qcClass = DOMAIN_QC_CACHE.get(domain);
        try {
            BaseQC query = (BaseQC) qcClass.getDeclaredConstructor().newInstance();
            query.setSort(sort);

            // 把入参注入到反射生成的类中
            richObject(query, map);
            // 实际上是mapper中的方法
            Class<?> aClass = DOMAIN_DO_CACHE.get(domain);
            List<SimpleProcessor> processorList = ProcessorCache.processorFromDomain(aClass);
            if (ListTools.isNotBlank(processorList)) {
                for (SimpleProcessor simpleProcessor : processorList) {
                    simpleProcessor.beforeSimpleQuery(query);
                }
            }
            Long count = proxy.countByQuery(query);
            query.setPage(page);
            List queryList = proxy.findByQuery(query);
            ConvertorTools.convertList(queryList, DOMAIN_DO_CACHE.get(domain));
            if (ListTools.isNotBlank(processorList)) {
                for (SimpleProcessor simpleProcessor : processorList) {
                    simpleProcessor.afterSimpleQuery(query);
                }
            }
            return BaseResponse.assemblePageResponse(queryList, count, page.getPageIndex(), page.getPageSize());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }


    @GetMapping("/simple/{domain}/get")
    public BaseResponse simpleGet(@PathVariable String domain, Long id) {
        // 通过domain去找mapper
        BaseMapper proxy = ContextHolder.getBean(domain + "Mapper", BaseMapper.class);
        Object dbEntity = proxy.findById(id);
        ConvertorTools.convertSingle(dbEntity);
        return BaseResponse.success(dbEntity);
    }


    @PostMapping("/simple/{domain}/save")
    public BaseResponse simpleSave(@PathVariable String domain) {
        String postData = postData();
        Map<String, Object> map = JSON.parseObject(postData, Map.class);
        Class<?> aClass = DOMAIN_DO_CACHE.get(domain);
        List<SimpleProcessor> processorList = ProcessorCache.processorFromDomain(aClass);
        Object entity = richObject(aClass, map);
        BaseMapper proxy = ContextHolder.getBean(domain + "Mapper", BaseMapper.class);
        // 添加前置处理
        if (ListTools.isNotBlank(processorList)) {
            for (SimpleProcessor simpleProcessor : processorList) {
                simpleProcessor.beforeSimpleSave(entity);
            }
        }
        if (map.get("id") == null) {

            proxy.insert(entity);
        } else {
            proxy.update(entity);
        }
        // 添加后置处理
        if (ListTools.isNotBlank(processorList)) {
            for (SimpleProcessor simpleProcessor : processorList) {
                simpleProcessor.afterSimpleSave(entity);
            }
        }
        return BaseResponse.success(entity);
    }

    @GetMapping("/simple/{domain}/remove")
    public BaseResponse simpleRemove(@PathVariable String domain, Long id) {
        Class<?> aClass = DOMAIN_DO_CACHE.get(domain);
        List<SimpleProcessor> processorList = ProcessorCache.processorFromDomain(aClass);
        // 通过domain去找mapper
        BaseMapper proxy = ContextHolder.getBean(domain + "Mapper", BaseMapper.class);
        proxy.removeById(id);
        if (ListTools.isNotBlank(processorList)) {
            for (SimpleProcessor simpleProcessor : processorList) {
                simpleProcessor.afterSimpleRemove(id);
            }
        }
        return BaseResponse.success("ok");
    }

    @PostMapping("/simple/{domain}/removeByQuery")
    public BaseResponse simpleRemove(@PathVariable String domain) {
        String postData = postData();
        Map<String, Object> map = JSON.parseObject(postData, Map.class);
        Class<?> aClass = DOMAIN_QC_CACHE.get(domain);
        List<SimpleProcessor> processorList = ProcessorCache.processorFromDomain(aClass);
        BaseQC entity = (BaseQC) richObject(aClass, map);
        // 通过domain去找mapper
        BaseMapper proxy = ContextHolder.getBean(domain + "Mapper", BaseMapper.class);
        proxy.removeByQuery(entity);
        if (ListTools.isNotBlank(processorList)) {
            for (SimpleProcessor simpleProcessor : processorList) {
                simpleProcessor.afterSimpleRemoveByQuery(entity);
            }
        }
        return BaseResponse.success("ok");
    }

    @GetMapping("/tableField/{domain}")
    public List<TableColumn> getTableField(@PathVariable String domain) {
        // 先从domain获取到DO
        Class<?> doClass = DOMAIN_DO_CACHE.get(domain);
        // 从DOClass 中获取到字段注解
        List<TableColumn> fieldList = new ArrayList<>();
        for (Field field : doClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(TableField.class)) {
                continue;
            }
            TableField tableField = field.getAnnotation(TableField.class);
            if (StringTools.isBlank(tableField.title())) {
                continue;
            }

            TableColumn tableColumn = new TableColumn();
            tableColumn.setField(field.getName())
                    .setTitle(tableField.title())
                    .setEditable(tableField.editable());
            if(StringTools.isNotBlank(tableField.cellStyle())){
                tableColumn.setCellStyle(tableField.cellStyle());
            }

            fieldList.add(tableColumn);
        }
        return fieldList;
    }

    /**
     * 获取前端展示的新增的框和搜索的框框
     *
     * @param type   1:新增的, 2:搜索的
     * @param domain 领域名称
     * @return
     */
    @GetMapping("/tableInsertField/{type}/{domain}")
    public List<FrontInsertField> getTableInsertField(@PathVariable Integer type, @PathVariable String domain) {
        // 先从domain获取到DO
        Class<?> cls = null;
        if (1 == type) {
            cls = DOMAIN_DO_CACHE.get(domain);
        } else if (2 == type) {
            cls = DOMAIN_QC_CACHE.get(domain);
        }
        // 从DOClass 中获取到字段注解
        List<FrontInsertField> fieldList = new ArrayList<>();
        for (Field field : cls.getDeclaredFields()) {
            if (!field.isAnnotationPresent(TableField.class)) {
                continue;
            }
            TableField tableField = field.getAnnotation(TableField.class);
            if (!tableField.addField() || (StringTools.isBlank(tableField.label()) && StringTools.isBlank(tableField.title()))) {
                // 设置为不新增的
                continue;
            }
            FrontInsertField insertField = new FrontInsertField();
            // 先看label有没有,没有的话,就取title
            String label = StringTools.isBlank(tableField.label()) ? tableField.title() : tableField.label();
            insertField.setLabel(label)
                    .setFieldType(tableField.fieldType())
                    .setProperty(tableField.property())
                    .setFieldName(field.getName());
            if (tableField.fieldType() == TableField.TYPE_SELECT) {
                //如果是下拉框的话,则获取下拉框的值
                if(Object.class == tableField.convertor()){
                    List<KV> propertyList = ContextHolder.getPropertyList(tableField.property(), "");
                    insertField.setSelectList(propertyList);
                }
                if(SysDicConvertor.class == tableField.convertor()){
                    List<KV> list = sysDicConvertor.getList(tableField.property());
                    insertField.setSelectList(list);
                }

            }
            fieldList.add(insertField);
        }
        return fieldList;
    }

    /**
     * 获取配置信息
     * key就是想展示出来的,比如hospitalName
     * 1.从配置中取的传参： type:1,key:xxxx
     * 2.从sql中取的传参： type:2,domain:hospital,key:hospitalName, idField: id , s:人民医院
     * 3.从sys_dic中取： type:3,domain:需要就传,key:refer_type
     * @param type   1.从配置中获取, 2.通过sql去表中获取 ，或者通过sysDic表去取
     * @param key    属性的key值, 配置中的key , sql的话则为表中的字段名
     * @param domain 如果是使用sql的话,则是表名
     * @param s      用户现在输入的值
     * @return
     */
    @GetMapping("/property/{type}/{key}/query")
    public List<KV> queryProperty(@PathVariable String type,
                                  @PathVariable String key,
                                  String domain,
                                  @RequestParam(required = false, defaultValue = "id") String idField,
                                  String s) {

        if ("1".equals(type)) {
            return ContextHolder.getPropertyList(key, "");
        } else if ("2".equals(type)) {
            try {
                // 先去获取QC
                Class<?> qcClass = DOMAIN_QC_CACHE.get(domain);
                SQL sql = new SQL() {
                    {
                        SELECT(key, idField);
                        FROM("`" + domain + "`");
                        if (StringTools.isNotBlank(s)) {
                            String hexS = HtmlUtils.htmlEscapeHex(s);
                            WHERE(key + " like concat('%'," + hexS + ",'%') ");
                        }
                    }
                };
                //组装sql
                BaseMapper baseMapper = ContextHolder.getBean(domain + "Mapper", BaseMapper.class);
                List list = baseMapper.selectList(sql.toString());
                // 把查出来的list,赋值给返回值
                Class<?> domainClass = DOMAIN_DO_CACHE.get(domain);
                Field keyField = domainClass.getDeclaredField(key);
                keyField.setAccessible(true);
                Field idxField = domainClass.getDeclaredField(idField);
                idxField.setAccessible(true);
                Object collect = list.stream().map(x -> {
                    try {
                        return new KV(String.valueOf(keyField.get(x)), String.valueOf(idxField.get(x)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
                return (List<KV>) collect;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }else if("3".equals(type)){
            SQL sql = new SQL() {
                {
                    SELECT("name", "value");
                    FROM("`sys_dic`");
                    WHERE("name = '"+key+"'");
                    if(StringTools.isNotBlank(domain)){
                        WHERE("domain = '"+domain+"'");
                    }
                }
            };
            //组装sql
            BaseMapper baseMapper = ContextHolder.getBean("sysDicMapper", BaseMapper.class);
            Object obj = baseMapper.selectOne(sql.toString());
            if(null != obj){
                try {
                    Field value = obj.getClass().getDeclaredField("value");
                    value.setAccessible(true);
                    String valueStr = value.get(obj).toString();
                    return ContextHolder.convert(valueStr);
                } catch (NoSuchFieldException | IllegalAccessException e) {

                }
            }
        }
        return null;
    }


    /**
     * 通过反射,向一个类中注入map中对应字段的值
     *
     * @param clazz
     * @param map
     * @return
     */
    public Object richObject(Class<?> clazz, Map<String, Object> map) {
        try {
            Object o = clazz.getDeclaredConstructor().newInstance();
            richObject(o, map);
            return o;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void richObject(Object obj, Map<String, Object> map) {
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String fileName = field.getName();
                Object object = map.get(fileName);
                if (object == null) {
                    continue;
                }
                String value = String.valueOf(object);
                if (StringTools.isBlank(value)) {
                    continue;
                }
                Class<?> type = field.getType();
                if (type == Long.class) {
                    field.set(obj, Long.parseLong(value));
                } else if (type == Integer.class) {
                    field.set(obj, Integer.valueOf(value));
                }else if(type==Double.class){
                    field.set(obj,Double.valueOf(value));
                } else if (type == Date.class) {
                    //时间类型的先跳过
                    continue;
                } else {
                    field.set(obj, value);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }




}
