package red.lixiang.tools.admin.config;

import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Component
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

        returnValueHandlers.add(new XdReturnValueHandler());
    }
}
