package red.lixiang.tools.spring.controller;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import red.lixiang.tools.base.KV;
import red.lixiang.tools.common.convertor.Convertor;
import red.lixiang.tools.common.mybatis.BaseMapper;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.spring.ContextHolder;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/6/26
 **/
public class SysDicConvertor implements Convertor {

    @Override
    public Object convertSingle(Object obj, String identity) {

        SQL sql = new SQL() {
            {
                SELECT("name", "value");
                FROM("`sys_dic`");
                WHERE("name = '"+identity+"'");
            }
        };
        //组装sql
        BaseMapper baseMapper = ContextHolder.getBean("sysDicMapper", BaseMapper.class);
        Object dbValue = baseMapper.selectOne(sql.toString());
        if(null != dbValue){
            try {
                Field value = dbValue.getClass().getDeclaredField("value");
                value.setAccessible(true);
                String valueStr = value.get(dbValue).toString();
                Map<String, String> map = ContextHolder.convertMap(valueStr);
                return map.get(String.valueOf(obj));
            } catch (NoSuchFieldException | IllegalAccessException e) {

            }
        }

        return null;
    }

    public List<KV> getList(String identity){
        SQL sql = new SQL() {
            {
                SELECT("name", "value");
                FROM("`sys_dic`");
                WHERE("name = '"+identity+"'");
            }
        };
        //组装sql
        BaseMapper baseMapper = ContextHolder.getBean("sysDicMapper", BaseMapper.class);
        Object dbValue = baseMapper.selectOne(sql.toString());
        if(null != dbValue){
            try {
                Field value = dbValue.getClass().getDeclaredField("value");
                value.setAccessible(true);
                String valueStr = value.get(dbValue).toString();
                List<KV> kvList = ContextHolder.convert(valueStr);
                return kvList;
            } catch (NoSuchFieldException | IllegalAccessException e) {

            }
        }
        return null;
    }

    @Override
    public Map<Object, Object> convertList(List<Object> list, String identity) {
        SQL sql = new SQL() {
            {
                SELECT("name", "value");
                FROM("`sys_dic`");
                WHERE("name = '"+identity+"'");
            }
        };
        //组装sql
        BaseMapper baseMapper = ContextHolder.getBean("sysDicMapper", BaseMapper.class);
        Object dbValue = baseMapper.selectOne(sql.toString());
        if(null != dbValue){
            try {
                Field value = dbValue.getClass().getDeclaredField("value");
                value.setAccessible(true);
                String valueStr = value.get(dbValue).toString();
                Map<String, String> map = ContextHolder.convertMap(valueStr);
                return new HashMap<>(map);
            } catch (NoSuchFieldException | IllegalAccessException e) {

            }
        }
        return null;
    }
}
