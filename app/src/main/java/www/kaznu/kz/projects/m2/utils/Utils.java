package www.kaznu.kz.projects.m2.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import www.kaznu.kz.projects.m2.R;

public class Utils {

    public static String toUpper(String data) {
        return data.substring(0, 1).toUpperCase() + data.substring(1).toLowerCase();
    }
    public static void replaceFragment(FragmentActivity activity, Fragment fragment, int resource) {
        activity.getSupportFragmentManager().beginTransaction().replace(resource, fragment).commit();
    }

    public static String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd.MM.yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        String str = null;

        try {
            Date date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateWithDot(String date) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "dd.MM.yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        String str = null;

        String today = outputFormat.format(Calendar.getInstance().getTime());

        try {
            Date data = inputFormat.parse(date);
            str = outputFormat.format(data);

            if (str.compareToIgnoreCase(today) == 0) {
                str = "Сегодня";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateText(String date) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        String str = null;

        try {
            Date data = inputFormat.parse(date);
            str = outputFormat.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateDefault(String date) {
        String outputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String inputPattern = "dd MMM yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        String str = null;

        try {
            Date data = inputFormat.parse(date);
            str = outputFormat.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getCurrentDate() {
        String outputPattern = "dd MMM yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        return outputFormat.format(new Date());
    }

    public static String getCurrentDateToDatabase() {
        String outputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        return outputFormat.format(new Date());
    }

    public static String parsePrice(Double price) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance();
        format.applyPattern("#,###");

        return format.format(price) + " ₸";
    }

    public static String parseTime(String time) {
        String result = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = format.parse(time);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            assert date != null;
            result = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static long dateDiff(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays + 1;
    }

    public static Double totalPrice(long days, Double price) {
        return days * price;
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    public static void exitApp(Context context, Activity activity) {
        new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Dialog_Style_Alert))
                .setMessage("Вы действительно хотите выйти?")
                .setCancelable(false)

                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.finish();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
    }
}
