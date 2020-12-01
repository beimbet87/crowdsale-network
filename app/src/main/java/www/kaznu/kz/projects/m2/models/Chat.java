package www.kaznu.kz.projects.m2.models;

public class Chat {

    private String companyName;
    private String imageLink;
    private int me;
    private int company;
    private int refRealty;
    private int count;
    private int countNew;
    private int meOwner;
    private String lastMessage;
    private boolean haveRequest;
    private String socket_id;

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
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

    public String getSocket_id() {
        return socket_id;
    }

    public void setSocket_id(String socket_id) {
        this.socket_id = socket_id;
    }

    public int getMe() {
        return me;
    }

    public void setMe(int me) {
        this.me = me;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean isHaveRequest() {
        return haveRequest;
    }

    public void setHaveRequest(boolean haveRequest) {
        this.haveRequest = haveRequest;
    }

    public int getRefRealty() {
        return refRealty;
    }

    public void setRefRealty(int refRealty) {
        this.refRealty = refRealty;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getMeOwner() {
        return meOwner;
    }

    public void setMeOwner(int meOwner) {
        this.meOwner = meOwner;
    }
}
