package www.kaznu.kz.projects.m2.utils;

import android.content.Context;
import android.util.Log;

public class Logger {
    private Context context;
    private String tag;
    public Logger(Context context, String tag) {
        this.context = context;
        this.tag = tag;
    }

    public void d(String message) {
        Log.d(this.tag, "Debug (" + context.getClass().getSimpleName() + "): " + message);
    }
}
