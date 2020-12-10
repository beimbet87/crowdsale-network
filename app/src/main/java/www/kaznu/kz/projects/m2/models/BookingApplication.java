package www.kaznu.kz.projects.m2.models;

import www.kaznu.kz.projects.m2.interfaces.Constants;

public class BookingApplication implements Constants {
    private String linkImage;
    private int refRealtyOwner;
    private int appid;
    private int ownerAccept;
    private int clientAccept;
    private String address;
    private int refRealty;
    private double pricePerDay;
    private String timeStart;
    private String timeEnd;
    private int accept;
    private int refRealtyGuest;

    public int getRefRealtyOwner() {
        return refRealtyOwner;
    }

    public void setRefRealtyOwner(int refRealtyOwner) {
        this.refRealtyOwner = refRealtyOwner;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getOwnerAccept() {
        return ownerAccept;
    }

    public void setOwnerAccept(int ownerAccept) {
        this.ownerAccept = ownerAccept;
    }

    public int getClientAccept() {
        return clientAccept;
    }

    public void setClientAccept(int clientAccept) {
        this.clientAccept = clientAccept;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRefRealty() {
        return refRealty;
    }

    public void setRefRealty(int refRealty) {
        this.refRealty = refRealty;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public int getRefRealtyGuest() {
        return refRealtyGuest;
    }

    public void setRefRealtyGuest(int refRealtyGuest) {
        this.refRealtyGuest = refRealtyGuest;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
