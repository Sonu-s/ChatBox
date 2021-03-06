package com.example.user.chatbox.Class;

public class ChatsMsg {

    private String message;
    private String user;
    private String msgDate;
    private String msgTime;
    private boolean isSeenMsg;
    private boolean isTyping;
    private String image_message;


    public ChatsMsg() {

    }

    public ChatsMsg(String message, String user) {

        this.message = message;
        this.user = user;

        //this.msgTime = msgTime;
    }

    public String getImage_message() {
        return image_message;
    }

    public void setImage_message(String image_message) {
        this.image_message = image_message;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getUser() {

        return user;
    }

    public void setUser(String user) {

        this.user = user;
    }

    public String getMsgTime() {

        return msgTime;
    }

    public void setMsgTime(String msgTime) {

        this.msgTime = msgTime;
    }

    public String getMsgDate() {

        return msgDate;
    }

    public void setMsgDate(String msgDate) {

        this.msgDate = msgDate;
    }

    public boolean getIsSeenMsg() {
        return isSeenMsg;
    }

    public void setIsSeenMsg(boolean isSeenMsg) {
        this.isSeenMsg = isSeenMsg;
    }

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean typing) {
        isTyping = typing;
    }
}
