package www.kaznu.kz.projects.m2.models;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class CurrentUser implements Constants {
    private final int stars;
    private final int id;
    private final int sex;
    private final String name;
    private final String surname;
    private final String birth;
    private final String email;
    private final String phone;
    private final String imageLink;
    private final String description;
    private final int currency;
    private final String countryCode;
    private final String countryName;
    private final ArrayList<RateModel> rates;
    private final int rateCount;
    private final double rateAverage;

    private final ArrayList<RateModel> ratesOwner;
    private final int rateCountOwner;

    public ArrayList<RateModel> getRatesOwner() {
        return ratesOwner;
    }

    public int getRateCountOwner() {
        return rateCountOwner;
    }

    public double getRateAverageOwner() {
        return rateAverageOwner;
    }

    private final double rateAverageOwner;

    public CurrentUser(Context context) {

        TinyDB data = new TinyDB(context);
        id = data.getInt(SHARED_USER_ID);
        sex = data.getInt(SHARED_USER_SEX);
        name = data.getString(SHARED_USER_NAME);
        surname = data.getString(SHARED_USER_SURNAME);
        birth = data.getString(SHARED_USER_BIRTH);
        email = data.getString(SHARED_USER_EMAIL);
        phone = data.getString(SHARED_USER_PHONE);
        imageLink = data.getString(SHARED_USER_IMAGE_LINK);
        description = data.getString(SHARED_USER_DESCRIPTION);
        currency = data.getInt(SHARED_USER_CURRENCY);
        stars = data.getInt(SHARED_USER_STARS);
        countryCode = data.getString(SHARED_USER_COUNTRY_CODE);
        countryName = data.getString(SHARED_USER_COUNTRY_NAME);
        rateCount = data.getInt(SHARED_USER_RATE_COUNT);
        rateAverage = data.getDouble(SHARED_USER_RATE_AVERAGE);
        rates = data.getListRateModel(SHARED_USER_RATE, RateModel.class);

        rateCountOwner = data.getInt(SHARED_OWNER_RATE_COUNT);
        rateAverageOwner = data.getDouble(SHARED_OWNER_RATE_AVERAGE);
        ratesOwner = data.getListRateModel(SHARED_OWNER_RATE, RateModel.class);

    }

    public String getImageLink() {
        return imageLink;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getGender() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirth() {
        return birth;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getStars() {
        return stars;
    }

    public int getCurrency() {
        return currency;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getRateCount() {
        return rateCount;
    }

    public double getRateAverage() {
        return rateAverage;
    }

    public ArrayList<RateModel> getRates() {
        return rates;
    }
}