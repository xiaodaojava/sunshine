package red.lixiang.tools.jdk.trace;

/**
 * @author lixiang
 * @date 2019/12/28
 **/
public class TraceTools {

    private static final ThreadLocal<TraceMap> traceMapCache
            = new ThreadLocal<TraceMap>();

    public static TraceMap getTraceId() {
        return traceMapCache.get();
    }

    public static void setTraceId(TraceMap traceId) {
        traceMapCache.set(traceId);
    }

    public static void clear() {
        traceMapCache.remove();
    }
}
