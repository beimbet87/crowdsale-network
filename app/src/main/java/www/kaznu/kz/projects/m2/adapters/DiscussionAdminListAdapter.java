package www.kaznu.kz.projects.m2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.ClickListener;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.interfaces.ILoadDiscussion;
import www.kaznu.kz.projects.m2.models.Message;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;

public class DiscussionAdminListAdapter extends RecyclerView.Adapter implements Constants{

    private Context mContext;
    private ArrayList<Message> mMessageList;
    ILoadDiscussion loadDiscussion;
    public int refRealty;
    ClickListener listener;

    public DiscussionAdminListAdapter(Context context, ArrayList<Message> messageList, int refRealty, ClickListener listener) {
        mContext = context;
        mMessageList = messageList;
        this.refRealty = refRealty;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(ArrayList<Message> data) {
        mMessageList.clear();
        mMessageList.addAll(data);
        notifyDataSetChanged();
    }

    public void setDiscussion(ILoadDiscussion loadDiscussion) {
        this.loadDiscussion = loadDiscussion;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessageList.get(position);

        if (message.getMessageType() == MESSAGE_TYPE_SENT && message.isMine()) {
            return MESSAGE_TYPE_SENT;
        } else if (message.getMessageType() == MESSAGE_TYPE_SENT && !message.isMine()) {
            return MESSAGE_TYPE_RECEIVED;
        } else if (message.getMessageType() == MESSAGE_TYPE_REQUEST_GUEST && !message.isMine()) {
            return MESSAGE_TYPE_REQUEST_GUEST;
        } else if (message.getMessageType() == MESSAGE_TYPE_REQUEST_OWNER && message.isMine()) {
            return MESSAGE_TYPE_REQUEST_OWNER;
        } else if (message.getMessageType() == MESSAGE_TYPE_ACCEPTED_GUEST && !message.isMine()) {
            return MESSAGE_TYPE_ACCEPTED_GUEST;
        } else if (message.getMessageType() == MESSAGE_TYPE_ACCEPTED_OWNER && message.isMine()) {
            return MESSAGE_TYPE_ACCEPTED_OWNER;
        } else if (message.getMessageType() == MESSAGE_TYPE_REJECTED_GUEST && !message.isMine()) {
            return MESSAGE_TYPE_REJECTED_GUEST;
        } else if (message.getMessageType() == MESSAGE_TYPE_REJECTED_OWNER && message.isMine()) {
            return MESSAGE_TYPE_REJECTED_OWNER;
        } else if (message.getMessageType() == MESSAGE_TYPE_PAYMENT && message.isMine()) {
            return MESSAGE_TYPE_PAYMENT;
        } else if (message.getMessageType() == MESSAGE_TYPE_PAYMENT_ACCEPTED && !message.isMine()) {
            return MESSAGE_TYPE_PAYMENT_ACCEPTED;
        } else if (message.getMessageType() == MESSAGE_TYPE_FINISH && message.isMine()) {
            return MESSAGE_TYPE_FINISH;
        } else if (message.getMessageType() == MESSAGE_TYPE_RATE_USER && message.isMine()) {
            return MESSAGE_TYPE_RATE_USER;
        } else if (message.getMessageType() == MESSAGE_TYPE_RATE_REALTY && message.isMine()) {
            return MESSAGE_TYPE_RATE_REALTY;
        } else if (message.getMessageType() == MESSAGE_TYPE_RATE_FINISHED && message.isMine()) {
            return MESSAGE_TYPE_RATE_FINISHED;
        } else {
            return MESSAGE_TYPE_DEPRECATED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == MESSAGE_TYPE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_to_guest, parent, false);
            return new MessageToHolder(view);
        } else if (viewType == MESSAGE_TYPE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_from_guest, parent, false);
            return new MessageFromHolder(view);
        } else if (viewType == MESSAGE_TYPE_REQUEST_GUEST) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_request_from_guest, parent, false);
            return new MessageRequestFromGuestHolder(view, this.listener);
        } else if (viewType == MESSAGE_TYPE_REQUEST_OWNER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_request_to_guest, parent, false);
            return new MessageRequestToGuestHolder(view, this.listener);
        } else if (viewType == MESSAGE_TYPE_ACCEPTED_GUEST || viewType == MESSAGE_TYPE_ACCEPTED_OWNER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_accepted_owner, parent, false);
            return new MessageAcceptedHolder(view, this.listener);
        } else if (viewType == MESSAGE_TYPE_REJECTED_GUEST) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_request_from_guest, parent, false);
            return new MessageRequestFromGuestHolder(view, this.listener);
        } else if (viewType == MESSAGE_TYPE_REJECTED_OWNER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_request_to_guest, parent, false);
            return new MessageRequestToGuestHolder(view, this.listener);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_from_guest, parent, false);
            return new MessageFromHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(position);
        Message messagePrev;

        if (position == getItemCount() - 1) {
            messagePrev = mMessageList.get(position);
        } else {
            messagePrev = mMessageList.get(position + 1);
        }

        switch (holder.getItemViewType()) {
            case MESSAGE_TYPE_SENT:
                ((MessageToHolder) holder).bind(message, messagePrev, position, getItemCount());
                break;
            case MESSAGE_TYPE_RECEIVED:
                ((MessageFromHolder) holder).bind(message, messagePrev, position, getItemCount());
                break;
            case MESSAGE_TYPE_REQUEST_GUEST:
                ((MessageRequestFromGuestHolder) holder).bind(message, messagePrev, position, getItemCount(), MESSAGE_TYPE_REQUEST_GUEST);
                break;
            case MESSAGE_TYPE_REQUEST_OWNER:
                ((MessageRequestToGuestHolder) holder).bind(message, messagePrev, position, getItemCount(), MESSAGE_TYPE_REQUEST_OWNER);
                break;
            case MESSAGE_TYPE_ACCEPTED_GUEST:
                ((MessageAcceptedHolder) holder).bind(message, messagePrev, position, getItemCount(), MESSAGE_TYPE_ACCEPTED_GUEST);
                break;
            case MESSAGE_TYPE_ACCEPTED_OWNER:
                ((MessageAcceptedHolder) holder).bind(message, messagePrev, position, getItemCount(), MESSAGE_TYPE_ACCEPTED_OWNER);
                break;
            case MESSAGE_TYPE_REJECTED_GUEST:
                ((MessageRequestFromGuestHolder) holder).bind(message, messagePrev, position, getItemCount(), MESSAGE_TYPE_REJECTED_GUEST);
                break;
            case MESSAGE_TYPE_REJECTED_OWNER:
                ((MessageRequestToGuestHolder) holder).bind(message, messagePrev, position, getItemCount(), MESSAGE_TYPE_REJECTED_OWNER);
                break;
            default:
                ((MessageFromHolder) holder).bind(message, messagePrev, position, getItemCount());
                break;
        }
    }

    private static class MessageToHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime, tvMessageDate;

        MessageToHolder(View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tv_message);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvMessageDate = itemView.findViewById(R.id.tv_message_date);
        }

        void bind(Message message, Message messagePrev, int id, int count) {

            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Logger.d("Message type: " + Constants.MESSAGE_TYPE_SENT + ", id: " + id + " and count: " + count);

            if (id < count - 1) {
                if (date0.compareToIgnoreCase(date1) == 0) {
                    tvMessageDate.setVisibility(View.GONE);
                } else {
                    tvMessageDate.setText(date0);
                }
            } else {
                tvMessageDate.setVisibility(View.VISIBLE);
                tvMessageDate.setText(date0);
            }

            tvMessage.setText(message.getMessage());

            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                Date date = format.parse(message.getCreated_at());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                assert date != null;
                tvTime.setText(dateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static class MessageFromHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime, tvMessageDate;
        ImageView ivProfileAvatar;

        MessageFromHolder(View itemView) {
            super(itemView);

            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            ivProfileAvatar = itemView.findViewById(R.id.iv_profile_avatar);
            tvMessageDate = (TextView) itemView.findViewById(R.id.tv_message_date);
        }

        void bind(Message message, Message messagePrev, int id, int count) {

            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Logger.d("Message type: " + Constants.MESSAGE_TYPE_RECEIVED+ ", id: " + id + " and count: " + count);

            if (id < count - 1) {
                if (date0.compareToIgnoreCase(date1) == 0) {
                    tvMessageDate.setVisibility(View.GONE);
                } else {
                    tvMessageDate.setText(date0);
                }
            } else {
                tvMessageDate.setVisibility(View.VISIBLE);
                tvMessageDate.setText(date0);
            }

            tvMessage.setText(message.getMessage());

            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                Date date = format.parse(message.getCreated_at());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                assert date != null;
                tvTime.setText(dateFormat.format(date));
                if(!message.getImage().matches("null")) {
                    Picasso.get().load(Constants.BASE_URL.concat("Images/").concat(message.getImage())).into(ivProfileAvatar);
                    Log.d(Constants.TAG, Constants.BASE_URL.concat("Images/").concat(message.getImage()));
                } else {
                    ivProfileAvatar.setImageResource(R.drawable.ic_default_avatar);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static class MessageRequestFromGuestHolder extends RecyclerView.ViewHolder {
        TextView tvDateFrom, tvDateTo, tvPrice, tvTotalPrice, tvTime;
        TextView tvAlert, tvMessageDate;
        Button btnCancel, btnAccept;
        ImageView ivProfileAvatar;
        ClickListener listener;

        MessageRequestFromGuestHolder(View itemView, ClickListener listener) {
            super(itemView);

            this.tvDateFrom = itemView.findViewById(R.id.tv_date_from);
            this.tvDateTo = itemView.findViewById(R.id.tv_date_to);
            this.tvPrice = itemView.findViewById(R.id.tv_price);
            this.tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            this.tvTime = itemView.findViewById(R.id.tv_time);
            this.tvAlert = itemView.findViewById(R.id.tv_alert);
            this.tvMessageDate = itemView.findViewById(R.id.tv_message_date);
            this.btnCancel = itemView.findViewById(R.id.btn_from_guest_cancel);
            this.btnAccept = itemView.findViewById(R.id.btn_from_guest_accept);
            this.ivProfileAvatar = itemView.findViewById(R.id.iv_profile_avatar);
            this.listener = listener;
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Logger.d("Message type: " + type + ", id: " + id + " and count: " + count);

            if (id < count - 1) {
                if (date0.compareToIgnoreCase(date1) == 0) {
                    tvMessageDate.setVisibility(View.GONE);
                } else {
                    tvMessageDate.setText(date0);
                }
            } else {
                tvMessageDate.setVisibility(View.VISIBLE);
                tvMessageDate.setText(date0);
            }

            tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
            tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
            tvPrice.setText(Utils.parsePrice(message.getPrice()));
            tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
            tvTime.setText(Utils.parseTime(message.getCreated_at()));
            if(!message.getImage().matches("null")) {
                Picasso.get().load(Constants.BASE_URL.concat("Images/").concat(message.getImage())).into(ivProfileAvatar);
                Log.d(Constants.TAG, Constants.BASE_URL.concat("Images/").concat(message.getImage()));
            } else {
                ivProfileAvatar.setImageResource(R.drawable.ic_default_avatar);
            }

            if (type == MESSAGE_TYPE_REQUEST_GUEST) {
                tvAlert.setVisibility(View.GONE);
                btnAccept.setOnClickListener(v -> listener.onClick(id, btnAccept.getId()));
                btnCancel.setOnClickListener(v -> listener.onClick(id, btnCancel.getId()));
            }
            else if(type == MESSAGE_TYPE_REJECTED_GUEST) {
                btnCancel.setVisibility(View.GONE);
                btnAccept.setVisibility(View.GONE);
                tvAlert.setVisibility(View.VISIBLE);
                tvAlert.setTextColor(Color.parseColor("#BA0952"));
                tvAlert.setText("Гость отменил запрос");
            }
        }
    }

    public static class MessageRequestToGuestHolder extends RecyclerView.ViewHolder {
        TextView tvDateFrom, tvDateTo, tvPrice, tvTotalPrice, tvTime;
        TextView tvAlert, tvMessageDate;
        Button btnCancel;
        ClickListener listener;

        MessageRequestToGuestHolder(View itemView, ClickListener listener) {

            super(itemView);
            tvDateFrom = itemView.findViewById(R.id.tv_date_from);
            tvDateTo = itemView.findViewById(R.id.tv_date_to);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvAlert = itemView.findViewById(R.id.tv_alert);
            tvMessageDate = itemView.findViewById(R.id.tv_message_date);
            btnCancel = itemView.findViewById(R.id.btn_to_guest_cancel);
            this.listener = listener;
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Logger.d("Message type: " + type + ", id: " + id + " and count: " + count);

            if (id < count - 1) {
                if (date0.compareToIgnoreCase(date1) == 0) {
                    tvMessageDate.setVisibility(View.GONE);
                } else {
                    tvMessageDate.setText(date0);
                }
            } else {
                tvMessageDate.setVisibility(View.VISIBLE);
                tvMessageDate.setText(date0);
            }

            tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
            tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
            tvPrice.setText(Utils.parsePrice(message.getPrice()));
            tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
            tvTime.setText(Utils.parseTime(message.getCreated_at()));

            if (type == MESSAGE_TYPE_REQUEST_OWNER) {
                btnCancel.setOnClickListener(v -> listener.onClick(id, btnCancel.getId()));
            }
            else if (type == MESSAGE_TYPE_REJECTED_OWNER) {
                btnCancel.setVisibility(View.GONE);
                tvAlert.setVisibility(View.VISIBLE);
                tvAlert.setTextColor(Color.parseColor("#BA0952"));
                tvAlert.setText("Вы отменили запрос");
            }
        }
    }

    private static class MessageAcceptedHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvMessageDate;
        Button btnSchedule;
        ClickListener listener;

        MessageAcceptedHolder(View itemView, ClickListener listener) {
            super(itemView);
            this.tvTime = itemView.findViewById(R.id.tv_time);
            this.tvMessageDate = itemView.findViewById(R.id.tv_message_date);
            this.btnSchedule = itemView.findViewById(R.id.btn_goto_schedule);
            this.listener = listener;
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Logger.d("Message type: " + type + ", id: " + id + " and count: " + count);

            if (id < count - 1) {
                if (date0.compareToIgnoreCase(date1) == 0) {
                    tvMessageDate.setVisibility(View.GONE);
                } else {
                    tvMessageDate.setText(date0);
                }
            } else {
                tvMessageDate.setVisibility(View.VISIBLE);
                tvMessageDate.setText(date0);
            }

            tvTime.setText(Utils.parseTime(message.getCreated_at()));
            btnSchedule.setOnClickListener(v -> listener.onClick(id, btnSchedule.getId()));
        }

    }

}