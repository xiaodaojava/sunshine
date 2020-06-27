package red.lixiang.tools.common.convertor;

import java.util.List;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/6/25
 **/
public class BaseConvertor implements Convertor {

    @Override
    public Object convertSingle(Object obj, String identity) {
        return null;
    }

    @Override
    public Map<Object, Object> convertList(List<Object> list, String identity) {
        return null;
    }
}
