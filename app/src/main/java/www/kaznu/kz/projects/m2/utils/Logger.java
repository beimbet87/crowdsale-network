package www.kaznu.kz.projects.m2.utils;

import android.util.Log;

import www.kaznu.kz.projects.m2.interfaces.Constants;

public class Logger implements Constants {

    public Logger() {}

    public static void d(String message) {
        Log.d(TAG, message);
    }

    public static void e(String message) {
        Log.e(TAG, message);
    }

    public static void v(String message) {
        Log.v(TAG, message);
    }

    public static void i(String message) {
        Log.i(TAG, message);
    }

    public static void w(String message) {
        Log.w(TAG, message);
    }
}
