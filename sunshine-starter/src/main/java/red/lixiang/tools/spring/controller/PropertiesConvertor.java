package red.lixiang.tools.spring.controller;

import red.lixiang.tools.common.convertor.Convertor;
import red.lixiang.tools.spring.ContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/6/26
 **/
public class PropertiesConvertor  implements Convertor {

    @Override
    public Object convertSingle(Object obj, String identity) {
        Map<String, String> propertyMap = ContextHolder.getPropertyMap(identity, "");

        return propertyMap.get(String.valueOf(obj));
    }

    @Override
    public Map<Object, Object> convertList(List<Object> list, String identity) {
        Map<String, String> propertyMap = ContextHolder.getPropertyMap(identity, "");
        Map<Object,Object> map  = new HashMap<>(propertyMap);
        return map;
    }
}
