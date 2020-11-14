package www.kaznu.kz.projects.m2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import www.kaznu.kz.projects.m2.R;

public class BookingAdapter extends BaseAdapter {

    Context context;
    private String [] address;
    private String [] date;
    private int [] images;

    public BookingAdapter(Context context, String [] address, String [] date, int [] images){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.address = address;
        this.date = date;
        this.images = images;
    }

    public BookingAdapter() {
    }

    @Override
    public int getCount() {
        return address.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.booking_list_item, parent, false);
            viewHolder.address = convertView.findViewById(R.id.tv_address);
            viewHolder.date = convertView.findViewById(R.id.tv_date);
            viewHolder.icon = convertView.findViewById(R.id.iv_icon);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.address.setText(address[position]);
        viewHolder.date.setText(date[position]);
        viewHolder.icon.setImageResource(images[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView address;
        TextView date;
        ImageView icon;

    }

}