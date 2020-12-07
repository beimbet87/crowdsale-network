package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.DiscussionActivity;
import www.kaznu.kz.projects.m2.activities.OfferActivity;
import www.kaznu.kz.projects.m2.activities.SearchActivity;
import www.kaznu.kz.projects.m2.adapters.MessagesAdapter;
import www.kaznu.kz.projects.m2.adapters.SearchAdapter;
import www.kaznu.kz.projects.m2.api.RealtyType;
import www.kaznu.kz.projects.m2.api.RentPeriod;
import www.kaznu.kz.projects.m2.api.searches.MySearches;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Directory;
import www.kaznu.kz.projects.m2.models.Search;
import www.kaznu.kz.projects.m2.utils.Utils;

public class SearchFlatsFragment extends Fragment {
    Button searchButton;
    MySearches mySearches;
    RecyclerView lView;
    ProgressBar progressBar;

    SearchAdapter lAdapter;
    boolean isRent = true;

    public SearchFlatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search_flats, container, false);
        lView = rootView.findViewById(R.id.lv_active_search);
        searchButton = rootView.findViewById(R.id.btn_new_search);

        progressBar = rootView.findViewById(R.id.message_progress);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        lView.setItemAnimator(itemAnimator);

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

                lAdapter = new SearchAdapter(requireContext(), searches, getResources().getDimensionPixelSize(R.dimen.padding_top_bottom),
                        getResources().getDimensionPixelSize(R.dimen.padding_left_right));

                lView.setLayoutManager(new LinearLayoutManager(requireContext()));
                lView.setAdapter(lAdapter);
                lAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                lView.setItemViewCacheSize(16);

                lAdapter.setOnItemClickListener(new MessagesAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Intent offerIntent = new Intent(requireActivity(), OfferActivity.class);
                        Search search = searches.get(position);
                        Double loPrice = search.getFilter().getCostLowerLimit();
                        Double upPrice = search.getFilter().getCostUpperLimit();

                        if (loPrice > 0.0)
                            offerIntent.putExtra("lo_price", loPrice);

                        if (upPrice > 0.0)
                            offerIntent.putExtra("up_price", upPrice);

                        if (isRent) {
                            offerIntent.putExtra("is_rent", "Аренда");
                        } else {
                            offerIntent.putExtra("is_rent", "Покупка");
                        }

                        RealtyType realtyType = new RealtyType(requireContext());

                        realtyType.setOnLoadListener(new RealtyType.CustomOnLoadListener() {
                            @Override
                            public void onComplete(ArrayList<Directory> data) {
                                for (int i = 0; i < data.size(); i++) {
                                    if (data.get(i).getCodeId() == search.getFilter().getRealtyType()) {
                                        offerIntent.putExtra("realty_type", data.get(i).getValue());
                                        offerIntent.putExtra("realty_type_int", data.get(i).getCodeId());
                                    }
                                }
                            }
                        });

                        RentPeriod rentPeriod = new RentPeriod(requireContext());
                        rentPeriod.setOnLoadListener(new RentPeriod.CustomOnLoadListener() {
                            @Override
                            public void onComplete(ArrayList<Directory> data) {
                                for (int i = 0; i < data.size(); i++) {
                                    if(data.get(i).getCodeId() == search.getFilter().getRentPeriod()) {
                                        offerIntent.putExtra("rent_period", data.get(i).getValue());
                                        offerIntent.putExtra("rent_period_int", data.get(i).getCodeId());
                                    }
                                }
                            }
                        });


                        offerIntent.putExtra("date_from", search.getFilter().getStartDate());
                        offerIntent.putExtra("date_to", search.getFilter().getEndDate());

                        if(getRooms(search.getFilter().getRoomCount()) != null) {
                            offerIntent.putExtra("rooms", getRooms(search.getFilter().getRoomCount()));
                            offerIntent.putIntegerArrayListExtra("rooms_array", search.getFilter().getRoomCount());
                        }

                        if(search.getFilter().getPropertiesId().size() > 0) {
                            offerIntent.putIntegerArrayListExtra("properties", search.getFilter().getPropertiesId());
                        }

                        startActivity(offerIntent);
                    }

                    @Override
                    public void onItemLongClick(int position, View v) {

                    }
                });
            }
        });


        lView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        return rootView;
    }

    public String getRooms(ArrayList<Integer> data) {
        Collections.sort(data);

        if(data.isEmpty()) {
            return null;
        }
        else {
            StringBuilder result = new StringBuilder();

            for(int i = 0; i < data.size() - 1; i++) {
                result.append(data.get(i)).append(", ");
            }

            result.append(data.get(data.size()-1)).append(" - комнат.");

            return result.toString();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(lAdapter!=null)
            lAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
