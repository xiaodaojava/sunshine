package red.lixiang.tools.jdk.trace;

/**
 * @author lixiang
 * @date 2019/12/28
 **/
public class TraceMap {
    private String traceId;
    private String ip;
    private String userName;
    private String hospitalCode;


    public String getHospitalCode() {
        return hospitalCode;
    }

    public TraceMap setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
        return this;
    }

    public String getTraceId() {
        return traceId;
    }

    public TraceMap setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public TraceMap setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public TraceMap setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
