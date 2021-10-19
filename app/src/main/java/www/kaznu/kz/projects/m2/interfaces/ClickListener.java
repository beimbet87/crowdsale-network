package www.kaznu.kz.projects.m2.interfaces;

import android.view.View;

public interface ClickListener {
    void onClick(int index, int buttonId);
    void onItemClick(int position, View v);
    void onItemLongClick(int position, View v);
}
