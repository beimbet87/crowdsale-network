package www.kaznu.kz.projects.m2.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private String image;
    private int refRealty;
    private int messageType;
    private boolean mine;
    private boolean isGuest;
    private int refSender;
    private int refReceiver;
    private int idBook;
    private int accept;
    private String created_at;
    private String message;
    private String dateFrom;
    private String dateTo;
    private Double price;
    private String comment;
    private int stars;

    public String getBody() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("to", this.getRefReceiver());
            jsonBody.put("refRealty", this.getRefRealty());
            jsonBody.put("body", this.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Message Error: " + e);
        }

        return jsonBody.toString();
    }

    public String getRequestBody() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("isGuest", this.isGuest());
            jsonBody.put("to", this.getRefReceiver());
            jsonBody.put("refRealty", this.getRefRealty());
            jsonBody.put("dateFrom", this.getDateFrom());
            jsonBody.put("dateTo", this.getDateTo());
            jsonBody.put("price", this.getPrice());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Message Error: " + e);
        }

        return jsonBody.toString();
    }

    public String getResponseBody() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("isGuest", this.isGuest());
            jsonBody.put("bookId", this.getIdBook());
            jsonBody.put("accept", this.getAccept());
            jsonBody.put("to", this.getRefReceiver());
            jsonBody.put("refRealty", this.getRefRealty());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Message Error: " + e);
        }

        return jsonBody.toString();
    }

    public String getRateBookBody() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("isGuest", this.isGuest());
            jsonBody.put("stars", this.getStars());
            jsonBody.put("comment", this.getComment());
            jsonBody.put("to", this.getRefReceiver());
            jsonBody.put("bookId", this.getIdBook());
            jsonBody.put("refRealty", this.getRefRealty());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Message Error: " + e);
        }

        return jsonBody.toString();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getRefSender() {
        return refSender;
    }

    public void setRefSender(int refSender) {
        this.refSender = refSender;
    }

    public int getRefReceiver() {
        return refReceiver;
    }

    public void setRefReceiver(int refReceiver) {
        this.refReceiver = refReceiver;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getRefRealty() {
        return refRealty;
    }

    public void setRefRealty(int refRealty) {
        this.refRealty = refRealty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }
}
