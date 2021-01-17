package red.lixiang.tools.base.third.wechat.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import red.lixiang.tools.base.CommonModel;
import red.lixiang.tools.jdk.StringTools;

import java.util.Map;

/**
 * @author lixiang
 * @date 2020/3/19
 **/
public class TempQRImageReq  implements CommonModel {
    //"{\"expire_seconds\": "+time+",\"action_name\":\"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":" + sceneId + "}}}";

    public static final int SCENE_LOGIN = 10;

    public TempQRImageReq(String expireSeconds,String sceneId) {
        this.expireSeconds = expireSeconds;
        Scene scene = new Scene();
        scene.sceneId = sceneId;
        ActionInfo actionInfo = new ActionInfo();
        actionInfo.scene = scene;
        this.actionInfo = actionInfo;
    }

    private String expireSeconds;
    private String actionName = "QR_SCENE";

    private ActionInfo actionInfo;



    static class ActionInfo{
        Scene scene;

        public Scene getScene() {
            return scene;
        }
    }

    static class Scene{
        @JSONField(name = "scene_id")
        String sceneId;

        public String getSceneId() {
            return sceneId;
        }

        public Scene setSceneId(String sceneId) {
            this.sceneId = sceneId;
            return this;
        }
    }

    public static void main(String[] args) {
        TempQRImageReq req = new TempQRImageReq("300","998");
        Map<String, Object> stringObjectMap = req.toMap();
        System.out.println(JSON.toJSONString(stringObjectMap));
    }
    /**
     * 对key需不需要特殊处理
     *
     * @param s
     * @return
     */
    @Override
    public String mapKey(String s) {
        return StringTools.camel2UnderScope(s);
    }
}
