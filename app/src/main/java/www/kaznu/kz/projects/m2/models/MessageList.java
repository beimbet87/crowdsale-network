package www.kaznu.kz.projects.m2.models;

public class MessageList {
    private String image;
    private String title;
    private String lastMessage;
    private Integer messageCount;
    private Integer company;
    private Integer refRealty;

    public MessageList(String image, String title, String lastMessage, Integer messageCount, Integer company, Integer refRealty) {
        this.image = image;
        this.title = title;
        this.lastMessage = lastMessage;
        this.messageCount = messageCount;
        this.company = company;
        this.refRealty = refRealty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public Integer getRefRealty() {
        return refRealty;
    }

    public void setRefRealty(Integer refRealty) {
        this.refRealty = refRealty;
    }

}
