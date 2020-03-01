package red.lixiang.tools.common.ding;

import java.util.Arrays;
import java.util.List;

/**
 * @author lixiang
 * @date 2020/2/29
 **/
public class DingMessage {

    public static final String TYPE_TEXT="text";

    public static final String TYPE_ACTION_CARD = "actionCard";

    private String  msgtype;

    private TextMessage text;

    private ActionCardMessage actionCard;

    static class TextMessage{
        private String content;

        public String getContent() {
            return content;
        }

    }

    static class ActionCardMessage{
        private String title;
        private String text;
        private String hideAvatar;
        private String btnOrientation;
        private List<DButton> btns;

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

        public String getHideAvatar() {
            return hideAvatar;
        }

        public String getBtnOrientation() {
            return btnOrientation;
        }

        public List<DButton> getBtns() {
            return btns;
        }
    }
    static class DButton{
        private String title;
        private String actionURL;

        public String getTitle() {
            return title;
        }

        public String getActionURL() {
            return actionURL;
        }
    }

    public static DingMessage initTextMessage(String content){
        TextMessage textMessage = new TextMessage();
        textMessage.content = content;
        DingMessage dingMessage = new DingMessage();
        dingMessage.text = textMessage;
        dingMessage.msgtype = TYPE_TEXT;
        return dingMessage;
    }

    public static DingMessage initActionCardMessage(String title,String content,String trueUrl, String falseUrl){
        DButton confirmBtn = new DButton();
        confirmBtn.title="可以";confirmBtn.actionURL=trueUrl;
        DButton falseBtn = new DButton();
        falseBtn.title="不可以";falseBtn.actionURL=falseUrl;
        List<DButton> dButtons = Arrays.asList(confirmBtn, falseBtn);
        ActionCardMessage actionCardMessage = new ActionCardMessage();
        actionCardMessage.title = title;
        actionCardMessage.text = content;
        actionCardMessage.btns = dButtons;
        actionCardMessage.btnOrientation="1";
        DingMessage dingMessage = new DingMessage();
        dingMessage.msgtype=TYPE_ACTION_CARD;
        dingMessage.actionCard = actionCardMessage;
        return dingMessage;
    }

    public String getMsgtype() {
        return msgtype;
    }



    public TextMessage getText() {
        return text;
    }

    public ActionCardMessage getActionCard() {
        return actionCard;
    }

    public static String getTypeText() {
        return TYPE_TEXT;
    }
}
