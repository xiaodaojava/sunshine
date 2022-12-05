package red.lixiang.tools.admin.config;

import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import red.lixiang.tools.base.BaseResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class XdReturnValueHandler implements HandlerMethodReturnValueHandler {

    private HandlerMethodReturnValueHandler delegation ;

    public XdReturnValueHandler setDelegation(HandlerMethodReturnValueHandler delegation) {
        this.delegation = delegation;
        return this;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return BaseResponse.class == returnType.getParameterType();
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter outWriter = response.getWriter();
        outWriter.write(JSON.toJSONString(returnValue));
        outWriter.flush();
        outWriter.close();
    }
}
