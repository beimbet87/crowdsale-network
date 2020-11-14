package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import www.kaznu.kz.projects.m2.MainActivity;
import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.DiscussionActivity;
import www.kaznu.kz.projects.m2.activities.OfferActivity;
import www.kaznu.kz.projects.m2.activities.RealtyActivity;
import www.kaznu.kz.projects.m2.adapters.MessagesAdapter;
import www.kaznu.kz.projects.m2.adapters.OfferAdapter;
import www.kaznu.kz.projects.m2.api.pusher.MyChats;
import www.kaznu.kz.projects.m2.models.Chat;

import static com.yandex.runtime.Runtime.getApplicationContext;

public class MessagesFragment extends Fragment {

    final public String URL_GET_MY_CHATS = "http://someproject-001-site1.itempurl.com/api/Chat/getMyChats";

    JSONArray chats;

    ListView lView;

    ListAdapter lAdapter;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_messages, container, false);

        lView = rootView.findViewById(R.id.lv_messages);

        SharedPreferences token = requireActivity().getSharedPreferences("M2_TOKEN", 0);

        MyChats myChats = new MyChats(requireContext(), token.getString("access_token", ""));

        myChats.setOnLoadListener(new MyChats.CustomOnLoadListener() {
            @Override
            public void onComplete(int code, String message, ArrayList<Chat> chats) {

                lView.setAdapter(new MessagesAdapter(requireContext(), chats));

                lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getContext(), DiscussionActivity.class);
                        intent.putExtra("contact", chats.get(i).getUserId());
                        intent.putExtra("ref_realty", chats.get(i).getRealty());
                        startActivity(intent);
                    }
                });
            }
        });

        return rootView;
    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
