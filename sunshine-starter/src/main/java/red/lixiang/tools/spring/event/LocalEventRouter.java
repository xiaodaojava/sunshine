package red.lixiang.tools.spring.event;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocalEventRouter {

    @Resource
    private List<LocalEventHandler> localEventHandlerList;


    /**
     * key 是 taskCode
     * value 是可以处理的方法List
     */
    private Map<String,List<LocalEventHandlerMethod>> localEventMap = new HashMap<>();


    @PostConstruct
    public void initLocalEventHandler(){
        for (LocalEventHandler localEventHandler : localEventHandlerList) {
            Method[] declaredMethods = localEventHandler.getClass().getDeclaredMethods();
            for (Method method : declaredMethods) {
                Annotation annotation = AnnotationUtils.findAnnotation(method,LocalEvent.class);
                if(annotation == null){
                    return;
                }
                LocalEvent localEvent = (LocalEvent) annotation;
                String taskCode = localEvent.taskCode();
                List<LocalEventHandlerMethod> localEventHandlerMethods = localEventMap.computeIfAbsent(taskCode, k -> new ArrayList<>());
                localEventHandlerMethods.add(new LocalEventHandlerMethod(localEventHandler,method));
            }
        }
    }

    public boolean process(LocalEventModel eventModel){
        String taskCode  = eventModel.getTaskCode();
        List<LocalEventHandlerMethod> localEventHandlerMethods = localEventMap.get(taskCode);
        boolean result = true;
        for (LocalEventHandlerMethod method : localEventHandlerMethods) {
            try {
                method.getMethod().invoke(method.getLocalEventHandler(),eventModel);
            }catch (Exception e){
                result = false;
                e.printStackTrace();
            }
        }
        return result;
    }


}
