package ro.cheiafermecata.smartlock.device.Data;

public class SendToUsersMessage {


    private String actionType;

    private String actionContent;


    public SendToUsersMessage(){

    }

    public SendToUsersMessage(String actionType, String actionContent) {
        this.actionType = actionType;
        this.actionContent = actionContent;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionContent() {
        return actionContent;
    }

    public void setActionContent(String actionContent) {
        this.actionContent = actionContent;
    }
}
