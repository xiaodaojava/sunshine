package red.lixiang.tools.common.convertor;

import java.util.List;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/6/25
 **/
public interface Convertor {
    Object convertSingle(Object obj,String identity);

    Map<Object, Object> convertList(List<Object> list,String identity);
}
