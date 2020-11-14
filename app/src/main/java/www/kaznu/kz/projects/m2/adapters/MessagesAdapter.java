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

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Chat;

public class MessagesAdapter extends BaseAdapter implements Constants {

    Context context;
    private ArrayList<Chat> chats;

    public MessagesAdapter(Context context, ArrayList<Chat> chats){
        this.context = context;
        this.chats = chats;
    }

    public MessagesAdapter() {
    }

    @Override
    public int getCount() {
        return chats.size();
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
            convertView = inflater.inflate(R.layout.message_list_item, parent, false);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.tv_address);
            viewHolder.messages = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.messageCounts = (TextView) convertView.findViewById(R.id.tv_message_count);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        if(this.chats.get(position).getCount() == 0) {
            viewHolder.messageCounts.setBackgroundResource(R.drawable.message_count_background_zero);
        }
        else {
            viewHolder.messageCounts.setBackgroundResource(R.drawable.message_count_background);
        }


        String header = chats.get(position).getUserName();
        if(header.equals("")) {
            header = "Имя и Фамилия";
        }
        String body = chats.get(position).getLastMessage();

        viewHolder.userName.setText(header);
        viewHolder.messages.setText(body);
        viewHolder.messageCounts.setText(String.valueOf(chats.get(position).getCount()));
        if(chats.get(position).getRealtyImageLink().equals("")) {
            viewHolder.icon.setImageResource(R.drawable.default_appartment);
        } else {
            String url = BASE_URL + chats.get(position).getRealtyImageLink();
            Glide.with(context).load(url).into(viewHolder.icon);
        }
        return convertView;
    }

    private static class ViewHolder {

        TextView userName;
        TextView messages;
        ImageView icon;
        TextView messageCounts;

    }

}