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

public class PublishedAdsAdapter extends BaseAdapter {

    Context context;
    private String [] titles;
    private String [] address;
    private String [] prices;
    private int [] images;

    public PublishedAdsAdapter(Context context, String [] titles, String [] address, String [] prices, int [] images){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.titles = titles;
        this.context = context;
        this.address = address;
        this.prices = prices;
        this.images = images;
    }

    public PublishedAdsAdapter() {
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
            convertView = inflater.inflate(R.layout.ads_published_list_item, parent, false);
            viewHolder.title = convertView.findViewById(R.id.tv_title);
            viewHolder.address = convertView.findViewById(R.id.tv_address);
            viewHolder.price = convertView.findViewById(R.id.tv_price);
            viewHolder.icon = convertView.findViewById(R.id.iv_icon);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.title.setText(titles[position]);
        viewHolder.address.setText(address[position]);
        viewHolder.price.setText(prices[position]);
        viewHolder.icon.setImageResource(images[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView title;
        TextView address;
        TextView price;
        ImageView icon;

    }

}