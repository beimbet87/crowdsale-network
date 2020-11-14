package www.kaznu.kz.projects.m2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.models.Directory;

public class RentPeriodAdapter extends BaseAdapter {
    Context context;
    ArrayList<Directory> data;
    LayoutInflater inflter;

    public RentPeriodAdapter(Context applicationContext, ArrayList<Directory> data) {
        this.context = applicationContext;
        this.data = data;
        this.inflter = (LayoutInflater.from(this.context));
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_rent_period, null);
        TextView names = (TextView) view.findViewById(R.id.tv_rent_period);
        names.setText(data.get(i).getValue());
        return view;
    }
}