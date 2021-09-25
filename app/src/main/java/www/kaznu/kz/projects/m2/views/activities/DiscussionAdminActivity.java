package www.kaznu.kz.projects.m2.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import www.kaznu.kz.projects.m2.MainActivity;
import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.DiscussionAdminListAdapter;
import www.kaznu.kz.projects.m2.api.pusher.Conversations;
import www.kaznu.kz.projects.m2.api.pusher.RequestMessage;
import www.kaznu.kz.projects.m2.api.pusher.SendMessage;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.interfaces.ILoadDiscussion;
import www.kaznu.kz.projects.m2.models.Message;
import www.kaznu.kz.projects.m2.models.Tokens;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.customviews.DatePickerView;

public class DiscussionAdminActivity extends AppCompatActivity implements Constants {

    LinearLayout linearLayout;
    Button btnBookingRequest, btnSendBookingRequest;
    Button btnCloseBooking;
    ImageView btnSendMessage;
    Button backButton;

    DatePickerView calendar;
    Conversations conversations;

    SendMessage sendMessage;
    RequestMessage requestMessage;

    EditText etMessage, etPrice;
    TextView tvTotalPrice;

    Tokens tokens;
    boolean isOwner;

    private RecyclerView mMessageRecycler;
    private DiscussionAdminListAdapter mMessageAdapter;

    SimpleDateFormat sdf;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_admin);

        tokens = new Tokens(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        backButton = findViewById(R.id.toolbar_back);
        tvTotalPrice = findViewById(R.id.tv_total);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("fragment", "message_admin_list");
                startActivity(intent);
            }
        });

        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        Intent intent = getIntent();

        int contact = intent.getIntExtra("contact", 0);
        int refRealty = intent.getIntExtra("ref_realty", 0);
        isOwner = intent.getBooleanExtra("owner", false);

        conversations = new Conversations(this, contact, refRealty, tokens.getAccessToken());

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);

        conversations.setOnLoadListener((data, message, messages) -> {
            mMessageAdapter = new DiscussionAdminListAdapter(getApplicationContext(), messages);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setReverseLayout(true);
            mMessageRecycler.setLayoutManager(linearLayoutManager);
            mMessageRecycler.setAdapter(mMessageAdapter);
            mMessageAdapter.setDiscussion(new ILoadDiscussion() {
                @Override
                public void onLoadDiscussions() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMessageAdapter.notifyDataSetChanged();
//                            mMessageAdapter.setLoaded();
                        }
                    }, 1000);
                }
            });
        });

        linearLayout = findViewById(R.id.booking_request);
        btnBookingRequest = findViewById(R.id.btn_booking_request);
        btnSendBookingRequest = findViewById(R.id.btn_send_booking_request);
        btnCloseBooking = findViewById(R.id.btn_close);

        btnSendMessage = findViewById(R.id.btn_send_message);
        etMessage = findViewById(R.id.et_message);

        etPrice = findViewById(R.id.tv_price_per_day);
        calendar = findViewById(R.id.calendar_view);

        etPrice.setText("");
        tvTotalPrice.setText("");

        btnSendMessage.setOnClickListener(v -> {

//            String currentDate = sdf.format(new Date());

            Message message = new Message();

            message.setRefReceiver(contact);
            message.setRefRealty(refRealty);
            message.setMessage(etMessage.getText().toString());

            conversations = new Conversations(getApplicationContext(), contact, refRealty, tokens.getAccessToken());

            etMessage.setText("");
            sendMessage = new SendMessage(getApplicationContext(), message, tokens.getAccessToken());
            sendMessage.setOnLoadListener(new SendMessage.CustomOnLoadListener() {
                @Override
                public void onComplete(int code, String message) {
                    conversations = new Conversations(getApplicationContext(), contact, refRealty, tokens.getAccessToken());

                    conversations.setOnLoadListener(new Conversations.CustomOnLoadListener() {
                        @Override
                        public void onComplete(int data, String message, ArrayList<Message> messages) {
                            Logger.d(messages.get(0).getMessage());
                            mMessageAdapter = new DiscussionAdminListAdapter(getApplicationContext(), messages);
                            mMessageAdapter.notifyDataSetChanged();
                            mMessageRecycler.setAdapter(mMessageAdapter);

                            mMessageAdapter.setDiscussion(new ILoadDiscussion() {
                                @Override
                                public void onLoadDiscussions() {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mMessageAdapter.notifyDataSetChanged();
//                                    mMessageAdapter.setLoaded();
                                        }
                                    }, 1000);
                                }
                            });
                        }
                    });
                }
            });
        });

        final BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setPeekHeight(0);
        }

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setPeekHeight(0);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        btnBookingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        etPrice.setEnabled(true);

        btnCloseBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                long diff = 1;
                if(calendar.getStartDate() != null && calendar.getEndDate() != null) {
                    diff = Utils.dateDiff(Utils.parseDateDefault(calendar.getStartDate()),
                            Utils.parseDateDefault(calendar.getEndDate()));
                }

                Double price = Double.parseDouble(s.toString());
                tvTotalPrice.setText(Utils.parsePrice(Utils.totalPrice(diff, price)));

                Logger.d(calendar.getStartDate());
                Logger.d(calendar.getEndDate());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnSendBookingRequest.setOnClickListener(v -> {

            if (!etPrice.getText().toString().equals("")) {
                Message message = new Message();
                message.setGuest(true);
                message.setRefReceiver(contact);
                message.setRefRealty(refRealty);
                message.setDateFrom(Utils.parseDateDefault(calendar.getStartDate()));
                message.setDateTo(Utils.parseDateDefault(calendar.getEndDate()));
                message.setPrice(Double.parseDouble(etPrice.getText().toString()));

                requestMessage = new RequestMessage(getApplicationContext(), message, tokens.getAccessToken());

                requestMessage.setOnLoadListener((code, message1) -> {

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    conversations = new Conversations(getApplicationContext(), contact, refRealty, tokens.getAccessToken());

                    conversations.setOnLoadListener((data, message11, messages) -> {
                        mMessageAdapter = new DiscussionAdminListAdapter(getApplicationContext(), messages);
                        mMessageAdapter.notifyDataSetChanged();
                        mMessageRecycler.setAdapter(mMessageAdapter);

                        mMessageAdapter.setDiscussion(new ILoadDiscussion() {
                            @Override
                            public void onLoadDiscussions() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMessageAdapter.notifyDataSetChanged();
//                                        mMessageAdapter.setLoaded();
                                    }
                                }, 1000);
                            }
                        });
                    });
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "Введите цену!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}