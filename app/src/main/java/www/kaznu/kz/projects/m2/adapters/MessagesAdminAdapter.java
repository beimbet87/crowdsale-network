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

public class MessagesAdminAdapter extends BaseAdapter {

    Context context;
    private String [] userNames;
    private String [] messages;
    private int [] images;
    private int [] messageCounts;

    public MessagesAdminAdapter(Context context, String [] values, String [] numbers, int [] images, int [] counts){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.userNames = values;
        this.messages = numbers;
        this.images = images;
        this.messageCounts = counts;
    }

    public MessagesAdminAdapter() {
    }

    @Override
    public int getCount() {
        return userNames.length;
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
            convertView = inflater.inflate(R.layout.message_list_admin_item, parent, false);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.tv_message_title);
            viewHolder.messages = (TextView) convertView.findViewById(R.id.tv_last_message);
            viewHolder.messageCounts = (TextView) convertView.findViewById(R.id.tv_message_count);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        if(messageCounts[position] == 0) {
            viewHolder.messageCounts.setBackgroundResource(R.drawable.message_count_background_zero);
        }
        else {
            viewHolder.messageCounts.setBackgroundResource(R.drawable.message_count_background);
        }
        viewHolder.userName.setText(userNames[position]);
        viewHolder.messages.setText(messages[position]);
        viewHolder.messageCounts.setText(String.valueOf(messageCounts[position]));
        viewHolder.icon.setImageResource(images[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView userName;
        TextView messages;
        ImageView icon;
        TextView messageCounts;

    }

}