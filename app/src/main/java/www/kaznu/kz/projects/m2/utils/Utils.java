package www.kaznu.kz.projects.m2.utils;

import android.annotation.SuppressLint;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static void replaceFragment(FragmentActivity activity, Fragment fragment, int resource) {
        activity.getSupportFragmentManager().beginTransaction().replace(resource, fragment).commit();
    }

    public static String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd.MM.yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
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

        Date data = null;
        String str = null;

        String today = outputFormat.format(Calendar.getInstance().getTime());

        try {
            data = inputFormat.parse(date);
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

        Date data = null;
        String str = null;

        try {
            data = inputFormat.parse(date);
            str = outputFormat.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parsePrice(Double price, String currency) {
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
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays + 1;
    }

    public static Double totalPrice(long days, Double price) {
        return days * price;
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    public static class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private static class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence } public char charAt(int index) { return '*'; // This is the important part } public int length() { return mSource.length(); // Return default } public CharSequence subSequence(int start, int end) { return mSource.subSequence(start, end); // Return default } } };

            }

            @Override
            public int length() {
                return 0;
            }

            @Override
            public char charAt(int index) {
                return 0;
            }

            @NonNull
            @Override
            public CharSequence subSequence(int start, int end) {
                return null;
            }
        }
    }
}
