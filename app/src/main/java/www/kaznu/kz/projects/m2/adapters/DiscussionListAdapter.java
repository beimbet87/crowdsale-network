package www.kaznu.kz.projects.m2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import www.kaznu.kz.projects.m2.MainActivity;
import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.models.Message;
import www.kaznu.kz.projects.m2.utils.Utils;

public class DiscussionListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_1 = 3;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_2 = 4;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_3 = 5;
    private static final int VIEW_TYPE_MESSAGE_OFFER = 6;

    private Context mContext;
    private ArrayList<Message> mMessageList;

    public DiscussionListAdapter(Context context, ArrayList<Message> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message message = mMessageList.get(getItemCount() - position - 1);

        if (message.getMessageType() == 1) { // Must change
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else if (message.getMessageType() == 21) { // Must change
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_BOOKING_1;
        } else if (message.getMessageType() == 42) { // Must change
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_BOOKING_2;
        } else{
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_to, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_from, parent, false);
            return new ReceivedMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_BOOKING_1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_booking_to, parent, false);
            return new BookingMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_BOOKING_2) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_booking_to, parent, false);
            return new BookingMessageHolder(view);
        }
        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(getItemCount() - position - 1);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_BOOKING_1:
                ((BookingMessageHolder) holder).bind(message, 21);
                break;
            case VIEW_TYPE_MESSAGE_BOOKING_2:
                ((BookingMessageHolder) holder).bind(message, 42);
                break;
        }
    }

    private static class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_body);
            timeText = (TextView) itemView.findViewById(R.id.tv_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                Date date = format.parse(message.getCreated_at());
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                timeText.setText(dateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_body);
            timeText = (TextView) itemView.findViewById(R.id.tv_time);
            profileImage = itemView.findViewById(R.id.message_avatar);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                Date date = format.parse(message.getCreated_at());
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                timeText.setText(dateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private static class BookingMessageHolder extends RecyclerView.ViewHolder {
        TextView tvDateFrom, tvDateTo, tvPrice, tvTotalPrice, timeText;
        TextView tvAlert;
        Button btnCancel;

        BookingMessageHolder(View itemView) {
            super(itemView);

            tvDateFrom = itemView.findViewById(R.id.tv_date_from);
            tvDateTo = itemView.findViewById(R.id.tv_date_to);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            timeText = itemView.findViewById(R.id.tv_time);
            tvAlert = itemView.findViewById(R.id.tv_alert);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, int type) {
            if(type == 21) {
                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
                tvPrice.setText(Utils.parsePrice(message.getPrice(), "KAZ"));
                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice()), "KAZ"));
                tvAlert.setText("Ожидает ответа хозяйна");
                timeText.setText(Utils.parseTime(message.getCreated_at()));
            }
            else if(type == 42) {
                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
                tvPrice.setText(Utils.parsePrice(message.getPrice(), "KAZ"));
                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice()), "KAZ"));
                tvAlert.setText("Отменен Вами");
                tvAlert.setTextColor(Color.parseColor("#BA0952"));
                btnCancel.setVisibility(View.GONE);
                timeText.setText(Utils.parseTime(message.getCreated_at()));
            }

        }
    }
}