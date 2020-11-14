package www.kaznu.kz.projects.m2.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class Utils {
    public static void replaceFragment(FragmentActivity activity, Fragment fragment, int resource) {
        activity.getSupportFragmentManager().beginTransaction().replace(resource, fragment).commit();
    }
}
