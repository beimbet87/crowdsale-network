package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.SearchActivity;
import www.kaznu.kz.projects.m2.adapters.SearchAdapter;
import www.kaznu.kz.projects.m2.api.searches.MySearches;
import www.kaznu.kz.projects.m2.models.Search;

public class SearchFlatsFragment extends Fragment {
    Button searchButton;
    MySearches mySearches;
    RecyclerView lView;

    SearchAdapter lAdapter;

    public SearchFlatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search_flats, container, false);
        lView = rootView.findViewById(R.id.lv_active_search);
        searchButton = rootView.findViewById(R.id.btn_new_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences token = requireActivity().getSharedPreferences("M2_TOKEN", 0);
        mySearches = new MySearches(requireContext(), token.getString("access_token", ""));

        mySearches.setOnLoadListener(new MySearches.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Search> searches) {
                Log.d("M2TAG", "Search ID: " + searches.get(0).getFilter().getLimit());
                lAdapter = new SearchAdapter(requireContext(), searches);
                lView.setLayoutManager(new LinearLayoutManager(requireContext()));
                lView.setAdapter(lAdapter);

//                lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                        Intent intent = new Intent(requireContext(), DiscussionActivity.class);
////                        startActivity(intent);
//                    }
//                });
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
