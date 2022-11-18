package red.lixiang.tools.spring.event;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LocalEvent {
    String taskCode();
}
