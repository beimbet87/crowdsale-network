package www.kaznu.kz.projects.m2.models;

public class Chat {
    private String chanelReply;
    private int count;
    private int countNew;
    private String chatId;
    private int userId;
    private String userName;
    private String lastMessage;
    private boolean hasOffer;
    private int realty;
    private String realtyImageLink;

    public String getChanelReply() {
        return chanelReply;
    }

    public void setChanelReply(String chanelReply) {
        this.chanelReply = chanelReply;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCountNew() {
        return countNew;
    }

    public void setCountNew(int countNew) {
        this.countNew = countNew;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean isHasOffer() {
        return hasOffer;
    }

    public void setHasOffer(boolean hasOffer) {
        this.hasOffer = hasOffer;
    }

    public int getRealty() {
        return realty;
    }

    public void setRealty(int realty) {
        this.realty = realty;
    }

    public String getRealtyImageLink() {
        return realtyImageLink;
    }

    public void setRealtyImageLink(String realtyImageLink) {
        this.realtyImageLink = realtyImageLink;
    }
}
