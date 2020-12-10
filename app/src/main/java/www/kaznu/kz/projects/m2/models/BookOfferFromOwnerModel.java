package www.kaznu.kz.projects.m2.models;

import www.kaznu.kz.projects.m2.interfaces.Constants;

public class BookOfferFromOwnerModel implements Constants {
    private boolean hasOffers;
    private double price;
    private int refBooking;
    private int refFrom;
    private int refTo;
    private int refRealty;
    private String time;

    public boolean isHasOffers() {
        return hasOffers;
    }

    public void setHasOffers(boolean hasOffers) {
        this.hasOffers = hasOffers;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRefBooking() {
        return refBooking;
    }

    public void setRefBooking(int refBooking) {
        this.refBooking = refBooking;
    }

    public int getRefFrom() {
        return refFrom;
    }

    public void setRefFrom(int refFrom) {
        this.refFrom = refFrom;
    }

    public int getRefTo() {
        return refTo;
    }

    public void setRefTo(int refTo) {
        this.refTo = refTo;
    }

    public int getRefRealty() {
        return refRealty;
    }

    public void setRefRealty(int refRealty) {
        this.refRealty = refRealty;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
