package www.kaznu.kz.projects.m2.models;

import android.content.Context;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class Properties implements Constants {

    private ArrayList<Directory> realtyType;
    private ArrayList<Directory> requestOffers;
    private ArrayList<Directory> realtyProperties;
    private ArrayList<Directory> rentPeriod;
    private ArrayList<Directory> dealType;
    private ArrayList<Directory> countries;
    private ArrayList<Directory> currencies;

    public Properties(Context context) {

        Logger Log = new Logger(context, TAG);

        TinyDB data = new TinyDB(context);

        realtyType = data.getListDirectory(SHARED_REALTY_TYPE, Directory.class);
        requestOffers = data.getListDirectory(SHARED_REQUEST_OFFERS, Directory.class);
        realtyProperties = data.getListDirectory(SHARED_REALTY_PROPERTIES, Directory.class);
        rentPeriod = data.getListDirectory(SHARED_RENT_PERIOD, Directory.class);
        dealType = data.getListDirectory(SHARED_DEAL_TYPE, Directory.class);
        countries = data.getListDirectory(SHARED_COUNTRIES, Directory.class);
        currencies = data.getListDirectory(SHARED_CURRENCIES, Directory.class);
    }

    public ArrayList<Directory> getRealtyProperties() {
        return realtyProperties;
    }

    public void setRealtyProperties(ArrayList<Directory> realtyProperties) {
        this.realtyProperties = realtyProperties;
    }

    public ArrayList<Directory> getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(ArrayList<Directory> rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public ArrayList<Directory> getDealType() {
        return dealType;
    }

    public void setDealType(ArrayList<Directory> dealType) {
        this.dealType = dealType;
    }

    public ArrayList<Directory> getRealtyType() {
        return realtyType;
    }

    public void setRealtyType(ArrayList<Directory> realtyType) {
        this.realtyType = realtyType;
    }

    public ArrayList<Directory> getRequestOffers() {
        return requestOffers;
    }

    public void setRequestOffers(ArrayList<Directory> requestOffers) {
        this.requestOffers = requestOffers;
    }

    public ArrayList<Directory> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Directory> countries) {
        this.countries = countries;
    }

    public ArrayList<Directory> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(ArrayList<Directory> currencies) {
        this.currencies = currencies;
    }
}
