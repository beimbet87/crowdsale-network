package www.kaznu.kz.projects.m2.adapters;

import static com.yandex.runtime.Runtime.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.pusher.RequestMessage;
import www.kaznu.kz.projects.m2.api.pusher.ResponseMessage;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.interfaces.ILoadDiscussion;
import www.kaznu.kz.projects.m2.models.Message;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.Utils;

public class DiscussionAdminListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1; // Send message from admin to guest
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_1 = 3;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_2 = 4;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_3 = 5;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_4 = 6;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_5 = 7;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_6 = 8;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_7 = 9;
    private static final int VIEW_TYPE_MESSAGE_BOOKING_8 = 10;

    private Context mContext;
    private ArrayList<Message> mMessageList;
    ILoadDiscussion loadDiscussion;
    public int refRealty;

    public DiscussionAdminListAdapter(Context context, ArrayList<Message> messageList, int refRealty) {
        mContext = context;
        mMessageList = messageList;
        this.refRealty = refRealty;
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

        if (message.getMessageType() == 1 && message.isMine()) {
            return VIEW_TYPE_MESSAGE_SENT;
        }
        else if (message.getMessageType() == 1 && !message.isMine()) {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
        else if (message.getMessageType() == 21 && !message.isMine()) {
            return VIEW_TYPE_MESSAGE_BOOKING_1;
        }
        else if (message.getMessageType() == 31 && message.isMine() ||
                message.getMessageType() == 32 && !message.isMine()) {
            return VIEW_TYPE_MESSAGE_BOOKING_2;
        } else if (message.getMessageType() == 42 && !message.isMine()) {
            return VIEW_TYPE_MESSAGE_BOOKING_3;
        } else if (message.getMessageType() == 41 && message.isMine()) {
            return VIEW_TYPE_MESSAGE_BOOKING_5;
        }
        else if (message.getMessageType() == 22 && message.isMine()) {
            return VIEW_TYPE_MESSAGE_BOOKING_4;
        } else if (message.getMessageType() == 51 && !message.isMine()) {
            return VIEW_TYPE_MESSAGE_BOOKING_5;
        } else if (message.getMessageType() == 71 && !message.isMine()) {
            return VIEW_TYPE_MESSAGE_BOOKING_6;
        } else if (message.getMessageType() == 72 && message.isMine()) {
            return VIEW_TYPE_MESSAGE_BOOKING_7;
        } else if (message.getMessageType() == 52 && !message.isMine()) {
            return VIEW_TYPE_MESSAGE_BOOKING_8;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_to_admin, parent, false);
            return new SentMessageHolder(view);
        }
        else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_from_admin, parent, false);
            return new ReceivedMessageHolder(view);
        }
        else if (viewType == VIEW_TYPE_MESSAGE_BOOKING_1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_offer_from_admin, parent, false);
            return new BookingMessageHolder(view);
        }
        else if (viewType == VIEW_TYPE_MESSAGE_BOOKING_2) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_offer_from_admin_accept, parent, false);
            return new BookingMessageHolder02(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_BOOKING_3) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_offer_from_admin_03, parent, false);
            return new BookingMessageHolder03(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_BOOKING_4) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_booking_to_admin, parent, false);
            return new BookingMessageHolder04(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_BOOKING_5) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_booking_to_admin, parent, false);
            return new BookingMessageHolder05(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_BOOKING_6) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_offer_from_admin_05, parent, false);
            return new BookingMessageHolder06(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_BOOKING_7) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_offer_from_admin_06, parent, false);
            return new BookingMessageHolder07(view);
        }
        else if (viewType == VIEW_TYPE_MESSAGE_BOOKING_8) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_offer_from_admin_07, parent, false);
            return new BookingMessageHolder08(view);
        }
        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
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
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message, messagePrev, position, getItemCount(), 1);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message, messagePrev, position, getItemCount(), 1);
                break;
            case VIEW_TYPE_MESSAGE_BOOKING_1:
                ((BookingMessageHolder) holder).bind(message, messagePrev, position, getItemCount(), 21);
                break;
            case VIEW_TYPE_MESSAGE_BOOKING_2:
                ((BookingMessageHolder02) holder).bind(message, messagePrev, position, getItemCount(), 31);
                break;
            case VIEW_TYPE_MESSAGE_BOOKING_3:
                ((BookingMessageHolder03) holder).bind(message, messagePrev, position, getItemCount(), 42);
                break;
            case VIEW_TYPE_MESSAGE_BOOKING_4:
                ((BookingMessageHolder04) holder).bind(message, messagePrev, position, getItemCount(), 22, refRealty);
                break;
            case VIEW_TYPE_MESSAGE_BOOKING_5:
                ((BookingMessageHolder05) holder).bind(message, messagePrev, position, getItemCount(), 41);
                break;
            case VIEW_TYPE_MESSAGE_BOOKING_6:
                ((BookingMessageHolder06) holder).bind(message, messagePrev, position, getItemCount(), 71);
                break;
            case VIEW_TYPE_MESSAGE_BOOKING_7:
                ((BookingMessageHolder07) holder).bind(message, messagePrev, position, getItemCount(), 72);
                break;
            case VIEW_TYPE_MESSAGE_BOOKING_8:
                ((BookingMessageHolder08) holder).bind(message, messagePrev, position, getItemCount(), 52);
                break;
        }
    }

    private static class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, tvMessageDate;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_body);
            timeText = (TextView) itemView.findViewById(R.id.tv_time);
            tvMessageDate = (TextView) itemView.findViewById(R.id.tv_message_date);
        }

        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());
            Log.d(Constants.TAG, "SentMessageHolder date: " + date0 + " " + date1);

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

            messageText.setText(message.getMessage());

            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                Date date = format.parse(message.getCreated_at());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                assert date != null;
                timeText.setText(dateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, tvMessageDate;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_body);
            timeText = (TextView) itemView.findViewById(R.id.tv_time);
            profileImage = itemView.findViewById(R.id.message_avatar);
            tvMessageDate = (TextView) itemView.findViewById(R.id.tv_message_date);
        }

        void bind(Message message, Message messagePrev, int id, int count, int type) {

            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());
            Log.d(Constants.TAG, "SentMessageHolder date: " + date0 + " " + date1);

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

            messageText.setText(message.getMessage());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                Date date = format.parse(message.getCreated_at());
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                timeText.setText(dateFormat.format(date));
                if(!message.getImage().matches("null")) {
                    Picasso.get().load(Constants.BASE_URL.concat("Images/").concat(message.getImage())).into(profileImage);
                    Log.d(Constants.TAG, Constants.BASE_URL.concat("Images/").concat(message.getImage()));
                } else {
                    profileImage.setImageResource(R.drawable.ic_default_avatar);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private static class BookingMessageHolder extends RecyclerView.ViewHolder {
        TextView tvDateFrom, tvDateTo, tvPrice, tvTotalPrice, timeText;
        TextView tvAlert, tvMessageDate;
        Button btnCancel;
        ImageView ivAvatar;

        BookingMessageHolder(View itemView) {
            super(itemView);

            tvDateFrom = itemView.findViewById(R.id.tv_date_from);
            tvDateTo = itemView.findViewById(R.id.tv_date_to);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            timeText = itemView.findViewById(R.id.tv_time);
            tvAlert = itemView.findViewById(R.id.tv_alert);
            tvMessageDate = itemView.findViewById(R.id.tv_message_date);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
            ivAvatar = itemView.findViewById(R.id.message_avatar);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Log.d(Constants.TAG, "BookingMessageHolder: " + id + " " + count);

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
            if (type == 21) {
                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
                tvPrice.setText(Utils.parsePrice(message.getPrice()));
                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
                timeText.setText(Utils.parseTime(message.getCreated_at()));
                if(!message.getImage().matches("null")) {
                    Picasso.get().load(Constants.BASE_URL.concat("Images/").concat(message.getImage())).into(ivAvatar);
                    Log.d(Constants.TAG, Constants.BASE_URL.concat("Images/").concat(message.getImage()));
                } else {
                    ivAvatar.setImageResource(R.drawable.ic_default_avatar);
                }
            }
        }
    }

    private static class BookingMessageHolder02 extends RecyclerView.ViewHolder {
//        TextView tvDateFrom, tvDateTo, tvPrice, tvTotalPrice, timeText;
        TextView tvAlert, tvMessageDate, timeText;
//        Button btnCancel;
//        ImageView ivAvatar;

        BookingMessageHolder02(View itemView) {
            super(itemView);

//            tvDateFrom = itemView.findViewById(R.id.tv_date_from);
//            tvDateTo = itemView.findViewById(R.id.tv_date_to);
//            tvPrice = itemView.findViewById(R.id.tv_price);
//            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            timeText = itemView.findViewById(R.id.tv_time);
//            tvAlert = itemView.findViewById(R.id.tv_alert);
            tvMessageDate = itemView.findViewById(R.id.tv_message_date);
//            btnCancel = itemView.findViewById(R.id.btn_cancel);
//            ivAvatar = itemView.findViewById(R.id.message_avatar);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Log.d(Constants.TAG, "BookingMessageHolder: " + id + " " + count);

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
            if (type == 31) {
//                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
//                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
//                tvPrice.setText(Utils.parsePrice(message.getPrice()));
//                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
                timeText.setText(Utils.parseTime(message.getCreated_at()));
//                if(!message.getImage().matches("null")) {
//                    Picasso.get().load(Constants.BASE_URL.concat("Images/").concat(message.getImage())).into(ivAvatar);
//                    Log.d(Constants.TAG, Constants.BASE_URL.concat("Images/").concat(message.getImage()));
//                } else {
//                    ivAvatar.setImageResource(R.drawable.ic_default_avatar);
//                }
            } else if (type == 32) {
//                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
//                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
//                tvPrice.setText(Utils.parsePrice(message.getPrice()));
//                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
                    timeText.setText(Utils.parseTime(message.getCreated_at()));
//                if(!message.getImage().matches("null")) {
//                    Picasso.get().load(Constants.BASE_URL.concat("Images/").concat(message.getImage())).into(ivAvatar);
//                    Log.d(Constants.TAG, Constants.BASE_URL.concat("Images/").concat(message.getImage()));
//                } else {
//                    ivAvatar.setImageResource(R.drawable.ic_default_avatar);
//                }
                }
        }
    }

    private static class BookingMessageHolder03 extends RecyclerView.ViewHolder {
        TextView tvDateFrom, tvDateTo, tvPrice, tvTotalPrice, timeText;
        TextView tvAlert, tvMessageDate;
        Button btnCancel;
        ImageView ivAvatar;

        BookingMessageHolder03(View itemView) {
            super(itemView);

            tvDateFrom = itemView.findViewById(R.id.tv_date_from);
            tvDateTo = itemView.findViewById(R.id.tv_date_to);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            timeText = itemView.findViewById(R.id.tv_time);
            tvAlert = itemView.findViewById(R.id.tv_alert);
            tvMessageDate = itemView.findViewById(R.id.tv_message_date);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
            ivAvatar = itemView.findViewById(R.id.message_avatar);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Log.d(Constants.TAG, "BookingMessageHolder: " + id + " " + count);

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
            if (type == 42) {
                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
                tvPrice.setText(Utils.parsePrice(message.getPrice()));
                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
                timeText.setText(Utils.parseTime(message.getCreated_at()));
                if(!message.getImage().matches("null")) {
                    Picasso.get().load(Constants.BASE_URL.concat("Images/").concat(message.getImage())).into(ivAvatar);
                    Log.d(Constants.TAG, Constants.BASE_URL.concat("Images/").concat(message.getImage()));
                } else {
                    ivAvatar.setImageResource(R.drawable.ic_default_avatar);
                }
            }
        }
    }

    public static class BookingMessageHolder04 extends RecyclerView.ViewHolder {
        TextView tvDateFrom, tvDateTo, tvPrice, tvTotalPrice, timeText;
        TextView tvAlert, tvMessageDate;
        Button btnCancel;

        BookingMessageHolder04(View itemView) {

            super(itemView);
            tvDateFrom = itemView.findViewById(R.id.tv_date_from);
            tvDateTo = itemView.findViewById(R.id.tv_date_to);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            timeText = itemView.findViewById(R.id.tv_time);
            tvAlert = itemView.findViewById(R.id.tv_alert);
            tvMessageDate = itemView.findViewById(R.id.tv_message_date);
            btnCancel = itemView.findViewById(R.id.btn_cancel);

        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type, int refRealty) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Log.d(Constants.TAG, "BookingMessageHolder: " + id + " " + count);

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
            if (type == 22) {
                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
                tvPrice.setText(Utils.parsePrice(message.getPrice()));
                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
                timeText.setText(Utils.parseTime(message.getCreated_at()));

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d(Constants.TAG, refRealty + "");
                        Message data = new Message();
                        data.setGuest(false);
                        data.setAccept(0);
                        data.setIdBook(message.getIdBook());
                        data.setRefReceiver(message.getRefReceiver());
                        data.setRefRealty(refRealty);

                        ResponseMessage responseMessage = new ResponseMessage(v.getContext(), data, new Tokens(v.getContext()).getAccessToken());

                        responseMessage.setOnLoadListener(new ResponseMessage.CustomOnLoadListener() {
                            @Override
                            public void onComplete(int code, String message1) {
                                Toast.makeText(v.getContext(), "Удачно отменено", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            } else if (type == 41) {
                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
                tvPrice.setText(Utils.parsePrice(message.getPrice()));
                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
                timeText.setText(Utils.parseTime(message.getCreated_at()));
                btnCancel.setVisibility(View.GONE);
                tvAlert.setVisibility(View.VISIBLE);
                tvAlert.setText("Вы отменили запрос");
            }
        }
    }

    private static class BookingMessageHolder05 extends RecyclerView.ViewHolder {
        TextView tvDateFrom, tvDateTo, tvPrice, tvTotalPrice, timeText;
        TextView tvAlert, tvMessageDate;
        Button btnCancel;

        BookingMessageHolder05(View itemView) {
            super(itemView);

            tvDateFrom = itemView.findViewById(R.id.tv_date_from);
            tvDateTo = itemView.findViewById(R.id.tv_date_to);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            timeText = itemView.findViewById(R.id.tv_time);
            tvAlert = itemView.findViewById(R.id.tv_alert);
            tvMessageDate = itemView.findViewById(R.id.tv_message_date);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Log.d(Constants.TAG, "BookingMessageHolder: " + id + " " + count);

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
            if (type == 22) {
                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
                tvPrice.setText(Utils.parsePrice(message.getPrice()));
                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
                timeText.setText(Utils.parseTime(message.getCreated_at()));
            } else if (type == 41) {
                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
                tvPrice.setText(Utils.parsePrice(message.getPrice()));
                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
                timeText.setText(Utils.parseTime(message.getCreated_at()));
                btnCancel.setVisibility(View.GONE);
                tvAlert.setVisibility(View.VISIBLE);
                tvAlert.setTextColor(Color.parseColor("#BA0952"));
                tvAlert.setText("Вы отменили запрос");
            }
        }
    }

    private static class BookingMessageHolder06 extends RecyclerView.ViewHolder {
        Button btnComment;
        TextView timeText, tvMessageDate;
        ImageView ivAvatar;

        BookingMessageHolder06(View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.tv_time);
            btnComment = itemView.findViewById(R.id.btn_comment);
            tvMessageDate = itemView.findViewById(R.id.tv_message_date);
            ivAvatar = itemView.findViewById(R.id.message_avatar);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Log.d(Constants.TAG, "BookingMessageHolder: " + id + " " + count);
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
            if (type == 71) {
                timeText.setText(Utils.parseTime(message.getCreated_at()));
                if(!message.getImage().matches("null")) {
                    Picasso.get().load(Constants.BASE_URL.concat("Images/").concat(message.getImage())).into(ivAvatar);
                    Log.d(Constants.TAG, Constants.BASE_URL.concat("Images/").concat(message.getImage()));
                } else {
                    ivAvatar.setImageResource(R.drawable.ic_default_avatar);
                }
            }
        }
    }

    private static class BookingMessageHolder08 extends RecyclerView.ViewHolder {
        TextView tvDateFrom, tvDateTo, tvPrice, tvTotalPrice, timeText;
        TextView tvAlert, tvMessageDate;

        BookingMessageHolder08(View itemView) {
            super(itemView);
            tvDateFrom = itemView.findViewById(R.id.tv_date_from);
            tvDateTo = itemView.findViewById(R.id.tv_date_to);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            timeText = itemView.findViewById(R.id.tv_time);
            tvAlert = itemView.findViewById(R.id.tv_alert);
            tvMessageDate = itemView.findViewById(R.id.tv_message_date);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Log.d(Constants.TAG, "BookingMessageHolder: " + id + " " + count);

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
            if (type == 52) {
                tvDateFrom.setText(Utils.parseDateText(message.getDateFrom()));
                tvDateTo.setText(Utils.parseDateText(message.getDateTo()));
                tvPrice.setText(Utils.parsePrice(message.getPrice()));
                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(Utils.dateDiff(message.getDateFrom(), message.getDateTo()), message.getPrice())));
                timeText.setText(Utils.parseTime(message.getCreated_at()));
            }
        }
    }

    private static class BookingMessageHolder07 extends RecyclerView.ViewHolder {
        TextView timeText, tvMessageDate;
        TextView tvComment;
        RatingBar ratingBar;

        BookingMessageHolder07(View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.tv_time);
            tvMessageDate = itemView.findViewById(R.id.tv_message_date);
            tvComment = itemView.findViewById(R.id.tv_comment);
            ratingBar = itemView.findViewById(R.id.rb_stars);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message, Message messagePrev, int id, int count, int type) {
            String date0 = Utils.parseDateWithDot(message.getCreated_at());
            String date1 = Utils.parseDateWithDot(messagePrev.getCreated_at());

            Log.d(Constants.TAG, "BookingMessageHolder: " + id + " " + count);
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
            if (type == 72) {
                timeText.setText(Utils.parseTime(message.getCreated_at()));
                tvComment.setText(message.getComment());
                ratingBar.setRating(message.getStars());
                Log.d(Constants.TAG, "Comments: ".concat(message.getComment()));
            }
        }
    }

    private void setTimeTextVisibility(long ts1, long ts2, TextView timeText) {

        if (ts2 == 0) {
            timeText.setVisibility(View.VISIBLE);
            timeText.setText(ts1 + "");
        } else {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTimeInMillis(ts1);
            cal2.setTimeInMillis(ts2);

            boolean sameMonth = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);

            if (sameMonth) {
                timeText.setVisibility(View.GONE);
                timeText.setText("");
            } else {
                timeText.setVisibility(View.VISIBLE);
                timeText.setText(ts2 + "");
            }

        }
    }

    public interface OnItemClickListener {

        void onItemClick(Context context, Message message, String token);

    }
}