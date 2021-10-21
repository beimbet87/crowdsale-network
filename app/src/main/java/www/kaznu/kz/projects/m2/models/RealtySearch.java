package www.kaznu.kz.projects.m2.models;

public class RealtySearch {

    private int refSearch;
    private int refRealty;
    private int refUser;
    private Filter filter;
    private int status;
    private String name;
    private String surname;
    private String address;
    private String userImageUrl;

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

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

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public int getRefSearch() {
        return refSearch;
    }

    public void setRefSearch(int refSearch) {
        this.refSearch = refSearch;
    }

    public int getRefRealty() {
        return refRealty;
    }

    public void setRefRealty(int refRealty) {
        this.refRealty = refRealty;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
