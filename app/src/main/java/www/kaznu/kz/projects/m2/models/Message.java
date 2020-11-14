package www.kaznu.kz.projects.m2.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private String socket_id;
    private int id;
    private int sender_id;
    private int receiver_id;
    private String message;
    private String created_at;
    private int refRealty;
    private Double price;
    private String timeStart;
    private String timeEnd;
    private boolean mine;
    private int type;

    public String getBody() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("id", this.getId());
            jsonBody.put("sender_id", this.getSender_id());
            jsonBody.put("receiver_id", this.getReceiver_id());
            jsonBody.put("message", this.getMessage());
            jsonBody.put("created_at", this.getCreated_at());
            jsonBody.put("refRealty", this.getRefRealty());
            jsonBody.put("price", this.getPrice());
            jsonBody.put("timeStart", this.getTimeStart());
            jsonBody.put("timeEnd", this.getTimeEnd());
            jsonBody.put("mine", this.isMine());
            jsonBody.put("type", this.getType());
            jsonBody.put("socket_id", this.getSocket_id());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("M2TAG", "Message Error: " + e);
        }

        return jsonBody.toString();
    }

    public String getSocket_id() {
        return socket_id;
    }

    public void setSocket_id(String socket_id) {
        this.socket_id = socket_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
