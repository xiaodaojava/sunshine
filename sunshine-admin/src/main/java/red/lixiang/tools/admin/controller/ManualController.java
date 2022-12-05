package red.lixiang.tools.admin.controller;

import org.springframework.stereotype.Component;
import red.lixiang.tools.base.BaseResponse;

@Component
public class ManualController {

    public BaseResponse<String> manualHandler(){
        return BaseResponse.assembleResponse("MANUAL_HANDLER");
    }
}
