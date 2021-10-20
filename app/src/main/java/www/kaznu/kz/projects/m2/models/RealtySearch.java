package www.kaznu.kz.projects.m2.models;

public class RealtySearch {
    private int refUser;
    private int status;
    private int count;
    private int id;
    private String city;
    private Filter filter;

    public int getRefUser() {
        return refUser;
    }

    public void setRefUser(int refUser) {
        this.refUser = refUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
