package red.lixiang.tools.admin;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import red.lixiang.tools.admin.config.XdReturnValueHandler;
import red.lixiang.tools.admin.controller.ManualController;
import red.lixiang.tools.jdk.ListTools;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SunshineAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunshineAdminApplication.class, args);
    }

    @Resource
    private List<HandlerMapping> handlerMappingList;

    @Bean
    public ApplicationRunner init() {
        return args -> {

			RequestMappingInfo mappingInfo = RequestMappingInfo
					.paths("manualUrl")
					.methods(RequestMethod.GET)
					.produces(MediaType.APPLICATION_JSON_VALUE)
					.build();
            for (HandlerMapping handlerMapping : handlerMappingList) {
                if(handlerMapping instanceof AbstractHandlerMethodMapping){

                    AbstractHandlerMethodMapping<RequestMappingInfo> handlerMethodMapping = (AbstractHandlerMethodMapping) handlerMapping;
                    Method manualHandler = ManualController.class.getDeclaredMethod("manualHandler");
                    handlerMethodMapping.registerMapping(mappingInfo,"manualController",manualHandler);
                }
            }

		};
    }
}
