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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.BookingApplication;
import www.kaznu.kz.projects.m2.utils.Utils;

public class BookingAdapter extends BaseAdapter {

    Context context;
    private ArrayList<BookingApplication> booking;

    public BookingAdapter(Context context, ArrayList<BookingApplication> booking){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.booking = booking;
    }

    public BookingAdapter() {
    }

    @Override
    public int getCount() {
        return booking.size();
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
            viewHolder.address = convertView.findViewById(R.id.tv_message_title);
            viewHolder.date = convertView.findViewById(R.id.tv_last_message);
            viewHolder.icon = convertView.findViewById(R.id.iv_icon);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        StringBuilder date = new StringBuilder();
        date.append(Utils.parseDateWithDot(booking.get(position).getTimeStart())).append(" - ");
        date.append(Utils.parseDateWithDot(booking.get(position).getTimeEnd()));

        viewHolder.address.setText(booking.get(position).getAddress());
        viewHolder.date.setText(date);
        Picasso.with(context).load(Constants.BASE_URL.concat(booking.get(position).getLinkImage())).into(viewHolder.icon);

        return convertView;
    }

    private static class ViewHolder {

        TextView address;
        TextView date;
        ImageView icon;

    }

}