package red.lixiang.tools.jdk;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lixiang
 * @date 2020/5/4
 **/
public class MapTools {
    public String preSign(Map<String,Object> paramMap){
        String[] keys = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        String preSign = Arrays.stream(keys)
                .filter(x -> !x.equals("sign")  && (paramMap.get(x) != null))
                .map(x ->{
                    String s = String.valueOf(paramMap.get(x));
                    return x + "=" + s;
                } )
                .collect(Collectors.joining("&"));
        return preSign;
    }
}
