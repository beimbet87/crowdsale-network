package www.kaznu.kz.projects.m2.models;

import java.util.ArrayList;

public class Offers {

    Realty realty;
    User owner;
    ArrayList<Search> searches;
    ArrayList<Integer> offersOptionsId;
    ArrayList<Integer> properties;
    ArrayList<String> imagesLink;

    public Offers() {
        searches = new ArrayList<>();
        offersOptionsId = new ArrayList<>();
        properties = new ArrayList<>();
        imagesLink = new ArrayList<>();
    }

    public Realty getRealty() {
        return realty;
    }

    public void setRealty(Realty realty) {
        this.realty = realty;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArrayList<Search> getSearches() {
        return searches;
    }

    public void setSearches(ArrayList<Search> searches) {
        this.searches = searches;
    }

    public ArrayList<Integer> getOffersOptionsId() {
        return offersOptionsId;
    }

    public void setOffersOptionsId(ArrayList<Integer> offersOptionsId) {
        this.offersOptionsId = offersOptionsId;
    }

    public ArrayList<Integer> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Integer> properties) {
        this.properties = properties;
    }

    public ArrayList<String> getImagesLink() {
        return imagesLink;
    }

    public void setImagesLink(ArrayList<String> imagesLink) {
        this.imagesLink = imagesLink;
    }
}
