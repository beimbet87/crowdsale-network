package www.kaznu.kz.projects.m2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import www.kaznu.kz.projects.m2.R;

public class GenderTypeAdapter extends BaseAdapter {
    Context context;
    String[] data;
    LayoutInflater inflter;

    public GenderTypeAdapter(Context applicationContext) {
        this.context = applicationContext;
        this.data = applicationContext.getResources().getStringArray(R.array.gender);
        this.inflter = (LayoutInflater.from(this.context));
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_gender_type, null);
        TextView names = view.findViewById(R.id.tv_gender_type);
        names.setText(data[i]);
        return view;
    }
}