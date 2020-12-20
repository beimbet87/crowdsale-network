package www.kaznu.kz.projects.m2.models;

public class BookingList {
    private String title;
    private String image;
    private String dates;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public BookingList(String title, String image, String dates) {
        this.title = title;
        this.image = image;
        this.dates = dates;
    }
}
