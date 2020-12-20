package www.kaznu.kz.projects.m2.utils;

import android.content.Context;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.views.customviews.FlowLayout;

public class ViewData {

    private static boolean isView = false;

    public static boolean isView() {
        return isView;
    }

    public static void setView(boolean view) {
        isView = view;
    }

    public void addTextData(String data, FlowLayout flowLayout, Context context,
                                   int padding0, int padding1) {

        Logger Log = new Logger(context, Constants.TAG);
        Log.d("ViewData --> " + isView);
        if(isView) {
            TextView textView = new TextView(context);
            textView.setTextSize(12);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.view_profile_button_background));
            textView.setTextColor(ContextCompat.getColor(context, R.color.color_primary_dark));
            textView.setMaxLines(1);
            textView.setPadding(padding1, padding0, padding1, padding0);
            textView.setText(data);
            flowLayout.addView(textView);
        }
    }
}
