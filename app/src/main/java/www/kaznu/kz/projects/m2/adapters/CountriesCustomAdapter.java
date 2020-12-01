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

public class CountriesCustomAdapter extends BaseAdapter {
    Context context;
    String [] data;
    LayoutInflater inflter;

    public CountriesCustomAdapter(Context applicationContext, String [] data) {
        this.context = applicationContext;
        this.data = data;
        inflter = (LayoutInflater.from(applicationContext));
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_country, null);
        TextView names = view.findViewById(R.id.tvCountry);
        names.setText(data[i]);
        return view;
    }
}