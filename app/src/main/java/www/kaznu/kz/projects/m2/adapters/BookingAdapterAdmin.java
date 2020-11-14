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

import de.hdodenhof.circleimageview.CircleImageView;
import www.kaznu.kz.projects.m2.R;

public class BookingAdapterAdmin extends BaseAdapter {

    Context context;
    private String [] address;
    private String [] date;
    private int [] images;
    private int [] imagesAvatar;

    public BookingAdapterAdmin(Context context, String [] address, String [] date, int [] images, int [] avatar){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.address = address;
        this.date = date;
        this.images = images;
        this.imagesAvatar = avatar;
    }

    public BookingAdapterAdmin() {
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
            convertView = inflater.inflate(R.layout.booking_list_admin_item, parent, false);
            viewHolder.address = convertView.findViewById(R.id.tv_address);
            viewHolder.date = convertView.findViewById(R.id.tv_date);
            viewHolder.icon = convertView.findViewById(R.id.iv_icon);
            viewHolder.avatar = convertView.findViewById(R.id.account_avatar);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.address.setText(address[position]);
        viewHolder.date.setText(date[position]);
        viewHolder.icon.setImageResource(images[position]);
        viewHolder.avatar.setImageResource(imagesAvatar[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView address;
        TextView date;
        ImageView icon;
        CircleImageView avatar;

    }

}