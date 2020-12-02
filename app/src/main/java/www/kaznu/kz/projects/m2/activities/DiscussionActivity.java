package www.kaznu.kz.projects.m2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.DiscussionListAdapter;
import www.kaznu.kz.projects.m2.api.pusher.Conversations;
import www.kaznu.kz.projects.m2.api.pusher.SendMessage;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Message;
import www.kaznu.kz.projects.m2.utils.Logger;

public class DiscussionActivity extends AppCompatActivity implements Constants {

    LinearLayout linearLayout;
    Button btnBookingRequest;
    ImageView btnCloseBooking;
    ImageView btnSendMessage;
    Button backButton;
    Conversations conversations;

    SendMessage sendMessage;

    EditText etMessage;

    SharedPreferences spToken, spPusher, spUser;
    String token;

    private RecyclerView mMessageRecycler;
    private DiscussionListAdapter mMessageAdapter;

    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        backButton = findViewById(R.id.toolbar_back);

        backButton.setOnClickListener(v -> finish());

        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        Logger Log = new Logger(this, TAG);

        Intent intent = getIntent();

        int contact = intent.getIntExtra("contact", 0);
        int refRealty = intent.getIntExtra("ref_realty", 0);

        spToken = getSharedPreferences("M2_TOKEN", 0);
        spPusher = getSharedPreferences("M2_PUSHER_INFO", 0);
        spUser = getSharedPreferences("M2_USER_INFO", 0);

        token = spToken.getString("access_token", "");

        conversations = new Conversations(this, contact, refRealty, token);

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);

        conversations.setOnLoadListener(new Conversations.CustomOnLoadListener() {
            @Override
            public void onComplete(int data, String message, ArrayList<Message> messages) {
                mMessageAdapter = new DiscussionListAdapter(getApplicationContext(), messages);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setReverseLayout(true);
                mMessageRecycler.setLayoutManager(linearLayoutManager);
                mMessageRecycler.setAdapter(mMessageAdapter);
            }
        });

        linearLayout = findViewById(R.id.booking_request);
        btnBookingRequest = findViewById(R.id.btn_booking_request);
        btnCloseBooking = findViewById(R.id.btn_close);

        btnSendMessage = findViewById(R.id.btn_send_message);
        etMessage = findViewById(R.id.et_message);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentDate = sdf.format(new Date());

                Message message = new Message();

                message.setRefReceiver(contact);
                message.setRefRealty(refRealty);
                message.setMessage(etMessage.getText().toString());

                conversations = new Conversations(getApplicationContext(), contact, refRealty, token);

                etMessage.setText("");
                sendMessage = new SendMessage(getApplicationContext(), message, token);
                sendMessage.setOnLoadListener(new SendMessage.CustomOnLoadListener() {
                    @Override
                    public void onComplete(int code, String message) {
                        conversations = new Conversations(getApplicationContext(), contact, refRealty, token);

                        conversations.setOnLoadListener(new Conversations.CustomOnLoadListener() {
                            @Override
                            public void onComplete(int data, String message, ArrayList<Message> messages) {
                                mMessageAdapter = new DiscussionListAdapter(getApplicationContext(), messages);
                                mMessageAdapter.notifyDataSetChanged();
                                mMessageRecycler.setAdapter(mMessageAdapter);
                            }
                        });
                    }
                });
            }
        });

        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(1);
        btnBookingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        btnCloseBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

    }
}