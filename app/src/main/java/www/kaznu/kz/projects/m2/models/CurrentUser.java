package www.kaznu.kz.projects.m2.models;

import android.content.Context;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.TinyDB;
import www.kaznu.kz.projects.m2.utils.Utils;

public class CurrentUser implements Constants {

    private TinyDB data;
    private final int stars;
    private final int id;
    private final int sex;
    private boolean owner;
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
    private final ArrayList<BookingApplication> clientBooks;
    private final ArrayList<BookingApplication> ownersBooks;

    private final ArrayList<BookingApplication> clientBooksHistory;
    private final ArrayList<BookingApplication> ownersBooksHistory;

    private final ArrayList<Chat> clientMessageList;
    private final ArrayList<Chat> ownerMessageList;

    private final int rateCount;
    private final double rateAverage;
    private boolean complete;

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

        data = new TinyDB(context);
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
        clientBooks = data.getListBookingModel(SHARED_USER_BOOKING, BookingApplication.class);
        ownersBooks = data.getListBookingModel(SHARED_OWNER_BOOKING, BookingApplication.class);

        clientBooksHistory = data.getListBookingModel(SHARED_USER_BOOKING_HISTORY, BookingApplication.class);
        ownersBooksHistory = data.getListBookingModel(SHARED_OWNER_BOOKING_HISTORY, BookingApplication.class);

        clientMessageList = data.getListMessageModel(SHARED_USER_MESSAGE_LIST, Chat.class);
        ownerMessageList = data.getListMessageModel(SHARED_OWNER_MESSAGE_LIST, Chat.class);

        rateCountOwner = data.getInt(SHARED_OWNER_RATE_COUNT);
        rateAverageOwner = data.getDouble(SHARED_OWNER_RATE_AVERAGE);
        ratesOwner = data.getListRateModel(SHARED_OWNER_RATE, RateModel.class);
        owner = data.getBoolean(SHARED_IS_OWNER);
        complete = data.getBoolean(SHARED_USER_COMPLETE);
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

    public boolean isOwner() {
        return this.owner;
    }

    public void setOwner(boolean owner) {
        data.putBoolean(SHARED_IS_OWNER, owner);
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public ArrayList<BookingApplication> getClientBooks() {
        return clientBooks;
    }

    public ArrayList<BookingApplication> getOwnersBooks() {
        return ownersBooks;
    }

    public ArrayList<BookingApplication> getClientBooksHistory() {
        return clientBooksHistory;
    }

    public ArrayList<BookingApplication> getOwnersBooksHistory() {
        return ownersBooksHistory;
    }

    public ArrayList<ScheduleSection> getScheduleSection() {
        ArrayList<ScheduleSection> result = new ArrayList<>();
        ArrayList<BookingApplication> items = new ArrayList<>();

        for (int i = 1; i < getOwnersBooksHistory().size(); i++) {

            String preDate = Utils.parseDateWithDot(getOwnersBooksHistory().get(i - 1).getTimeStart());
            String curDate = Utils.parseDateWithDot(getOwnersBooksHistory().get(i).getTimeStart());

            if (preDate.equals(curDate)) {
                items.add(getOwnersBooksHistory().get(i - 1));

                if (i == getOwnersBooksHistory().size() - 1) {
                    String sectionTitle = Utils.parseDateFullText(getOwnersBooksHistory().get(i - 1).getTimeStart());
                    items.add(getOwnersBooksHistory().get(i));
                    ScheduleSection data = new ScheduleSection(sectionTitle, items);
                    result.add(data);
                    items = new ArrayList<>();
                }

            } else {
                String sectionTitle = Utils.parseDateFullText(getOwnersBooksHistory().get(i - 1).getTimeStart());
                items.add(getOwnersBooksHistory().get(i - 1));
                ScheduleSection data = new ScheduleSection(sectionTitle, items);
                result.add(data);
                items = new ArrayList<>();
            }
        }

        return result;
    }

    public ArrayList<Chat> getClientMessageList() {
        return clientMessageList;
    }

    public ArrayList<Chat> getOwnerMessageList() {
        return ownerMessageList;
    }
}