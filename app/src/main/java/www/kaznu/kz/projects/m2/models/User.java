package www.kaznu.kz.projects.m2.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String imageLink;
    private String descrition;
    private int id;
    private int sex;
    private String name;
    private String surname;
    private String birth;
    private String email;
    private String phone;
    private String password;
    private int profileType;

    public String getBody() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("description", this.getDescrition());
            jsonBody.put("phone", this.getPhone());
            jsonBody.put("name", this.getName());
            jsonBody.put("surname", this.getSurname());
            jsonBody.put("birth", this.getBirth());
            jsonBody.put("password", this.getPassword());
            jsonBody.put("profileType", this.getProfileType());
            jsonBody.put("sex", this.getSex());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonBody.toString();
    }

    public int getProfileType() {
        return profileType;
    }

    public void setProfileType(int profileType) {
        this.profileType = profileType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
