package www.kaznu.kz.projects.m2.models;

import www.kaznu.kz.projects.m2.interfaces.Constants;

public class RateModel implements Constants {
    private int id;
    private String time;
    private int refFrom;
    private int refRealty;
    private int stars;
    private String comment;
    private String name;
    private String surname;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRefFrom() {
        return refFrom;
    }

    public void setRefFrom(int refFrom) {
        this.refFrom = refFrom;
    }

    public int getRefRealty() {
        return refRealty;
    }

    public void setRefRealty(int refRealty) {
        this.refRealty = refRealty;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
