package red.lixiang.tools.spring.event;

import java.lang.reflect.Method;

public class LocalEventHandlerMethod {

    private LocalEventHandler localEventHandler;

    private Method method;

    public LocalEventHandlerMethod(LocalEventHandler localEventHandler, Method method) {
        this.localEventHandler = localEventHandler;
        this.method = method;
    }

    public LocalEventHandler getLocalEventHandler() {
        return localEventHandler;
    }

    public LocalEventHandlerMethod setLocalEventHandler(LocalEventHandler localEventHandler) {
        this.localEventHandler = localEventHandler;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public LocalEventHandlerMethod setMethod(Method method) {
        this.method = method;
        return this;
    }
}
