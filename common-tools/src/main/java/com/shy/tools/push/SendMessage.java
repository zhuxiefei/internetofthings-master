package com.shy.tools.push;

/**
 * 发送消息模型
 */
public class SendMessage {

    /**
     * 发送类型
     */
    private String sendType;

    /**
     * 发送序号
     */
    private String sendNo;

    /**
     * 发送标题
     */
    private String sendTitle;

    /**
     * 发送内容
     */
    private String sendContent;

    /**
     * 发送所属ID
     */
    private String sendId;

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType == null ? null : sendType.trim();
    }

    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo == null ? null : sendNo.trim();
    }

    public String getSendTitle() {
        return sendTitle;
    }

    public void setSendTitle(String sendTitle) {
        this.sendTitle = sendTitle == null ? null : sendTitle.trim();
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent == null ? null : sendContent.trim();
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId == null ? null : sendId.trim();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SendMessage{");
        sb.append("sendType='").append(sendType).append('\'');
        sb.append(", sendNo='").append(sendNo).append('\'');
        sb.append(", sendTitle='").append(sendTitle).append('\'');
        sb.append(", sendContent='").append(sendContent).append('\'');
        sb.append(", sendId='").append(sendId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
