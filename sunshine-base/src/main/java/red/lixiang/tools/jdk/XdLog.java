package red.lixiang.tools.jdk;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class XdLog {
    private static final Logger defaultLogger = LoggerFactory.getLogger("xd");

    private Map<String,Object> paramsMap;

    private Object request;

    private Object result;

    private String message;

    private Throwable throwable;

    public static XdLog build(){
        return new XdLog();
    }

    public XdLog params(String key,Object value){
        if(paramsMap == null){
            this.paramsMap = new LinkedHashMap<>();
        }
        this.paramsMap.put(key, value);
        return this;
    }

    public XdLog request(Object request){
        this.request = request;
        return this;
    }

    public XdLog result(Object result){
        this.result = result;
        return this;
    }

    public XdLog message(String message){
        this.message = message;
        return this;
    }

    public XdLog throwable(Throwable throwable){
        this.throwable = throwable;
        return this;
    }

    public XdLog info(){
        defaultLogger.info(getLogString());
        return this;
    }

    public XdLog error(){
        defaultLogger.error(getLogString());
        return this;
    }

    private String getLogString() {
        StringBuilder logMessage  = new StringBuilder();
        logMessage.append(message).append("|");
        logMessage.append("request:").append(JSON.toJSONString(request)).append("|");
        logMessage.append("params:").append(JSON.toJSONString(paramsMap)).append("|");
        logMessage.append("result:").append(JSON.toJSONString(result)).append("|");
        if(throwable != null){
           logMessage.append("exception:").append(ExceptionTools.exceptionMsg(throwable));
        }
        return logMessage.toString();
    }


}
