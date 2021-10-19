package www.kaznu.kz.projects.m2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.MessageList;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Constants {

    private final ArrayList<MessageList> messageList;
    private static ClickListener clickListener;

    public MessageListAdapter(ArrayList<MessageList> messageList){
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        return new MessageListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageList chat = messageList.get(position);
        ((MessageListAdapter.ItemViewHolder) holder).bind(chat);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return messageList == null ? 0 : messageList.size();
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

            userName = itemView.findViewById(R.id.tv_address);
            messages = itemView.findViewById(R.id.tv_date);
            messageCounts = itemView.findViewById(R.id.tv_message_count);
            icon = itemView.findViewById(R.id.iv_avatar);
        }

        void bind(MessageList chat) {
            if(chat.getMessageCount() == 0) {
                messageCounts.setBackgroundResource(R.drawable.message_count_background_zero);
            }
            else {
                messageCounts.setBackgroundResource(R.drawable.message_count_background);
            }

            String header = chat.getTitle();
            if(header.equals("") || header == null || header.equals("null")) {
                header = "Заголовок";
            }

            String body = chat.getLastMessage();

            userName.setText(header);
            messages.setText(body);
            messageCounts.setText(String.valueOf(chat.getMessageCount()));

            if(!(chat.getImage().equals("") || chat.getImage().equals(BASE_URL))) {
                Picasso.get().load(chat.getImage()).into(icon);
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
        MessageListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}