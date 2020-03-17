package red.lixiang.tools.base;

import red.lixiang.tools.base.annotation.EnhanceTool;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 只提供一些方法
 *
 * @author lixiang
 * @date 2020/3/16
 **/
public interface CommonModel {

    /**
     * 对当前实体类的处理
     * @return
     */
    default public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            EnhanceTool enhanceTool = field.getAnnotation(EnhanceTool.class);
            if(enhanceTool !=null && enhanceTool.skipToMap()){
                continue;
            }
            Object obj;
            try {
                obj = field.get(this);
                if(obj == null){
                    continue;
                }
                if(obj.getClass()== String.class){
                    map.put((field.getName()), String.valueOf(obj));
                }else {
                    map.put((field.getName()), obj);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
