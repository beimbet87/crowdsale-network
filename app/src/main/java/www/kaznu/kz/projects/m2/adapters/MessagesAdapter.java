package www.kaznu.kz.projects.m2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Chat;
import www.kaznu.kz.projects.m2.utils.Logger;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Constants {

    Context context;
    private ArrayList<Chat> chats;
    private static ClickListener clickListener;

    public MessagesAdapter(Context context, ArrayList<Chat> chats){
        this.context = context;
        this.chats = chats;
    }

    public MessagesAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        return new MessagesAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        ((MessagesAdapter.ItemViewHolder) holder).bind(chat, this.context);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return chats == null ? 0 : chats.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView userName;
        TextView messages;
        ImageView icon;
        TextView messageCounts;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            userName = itemView.findViewById(R.id.tv_message_title);
            messages = itemView.findViewById(R.id.tv_last_message);
            messageCounts = itemView.findViewById(R.id.tv_message_count);
            icon = itemView.findViewById(R.id.iv_icon);
        }

        void bind(Chat chat, Context context) {
            Logger Log = new Logger(context, Constants.TAG);

            Log.d(chat.getLastMessage());

            if(chat.getCount() == 0) {
                messageCounts.setBackgroundResource(R.drawable.message_count_background_zero);
            }
            else {
                messageCounts.setBackgroundResource(R.drawable.message_count_background);
            }

            String header = chat.getCompanyName();
            if(header.equals("") || header == null) {
                header = "Имя и Фамилия";
            }
            String body = chat.getLastMessage();

            userName.setText(header);
            messages.setText(body);
            messageCounts.setText(String.valueOf(chat.getCount()));

            if(chat.getImageLink().equals("")) {
                icon.setImageResource(R.drawable.default_appartment);
            } else {
                String url = BASE_URL + chat.getImageLink();
                Picasso.get().load(url).into(icon);
            }
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MessagesAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}