package www.kaznu.kz.projects.m2.models;

import android.content.Context;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.utils.TinyDB;

public class Tokens implements Constants {

    private final String access_token;
    private final String token_type;
    private final Integer expires_in;

    public Tokens(Context context) {
        TinyDB data = new TinyDB(context);

        access_token = data.getString(SHARED_ACCESS_TOKEN);
        token_type = data.getString(SHARED_TOKEN_TYPE);
        expires_in = data.getInt(SHARED_EXPIRES_IN);
    }

    public String getAccessToken() {
        return access_token;
    }

    public String getTokenType() {
        return token_type;
    }

    public Integer getExpiresIn() {
        return expires_in;
    }
}
