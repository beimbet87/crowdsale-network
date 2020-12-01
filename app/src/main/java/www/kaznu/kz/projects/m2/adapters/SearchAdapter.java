package www.kaznu.kz.projects.m2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.RentPeriod;
import www.kaznu.kz.projects.m2.models.Directory;
import www.kaznu.kz.projects.m2.models.Filter;
import www.kaznu.kz.projects.m2.models.Search;

public class SearchAdapter extends RecyclerView.Adapter {

    Context context;
    public ArrayList<Search> searchs;

    public SearchAdapter(Context context, ArrayList<Search> searchs) {
        this.context = context;
        this.searchs = searchs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_item, parent, false);
        return new SearchAdapter.SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Search search = searchs.get(position);
        ((SearchHolder) holder).bind(search, this.context);
    }

    private static class SearchHolder extends RecyclerView.ViewHolder {
        TextView tvAddress;
        TextView tvRoomCount;
        TextView tvRentPeriod;
        TextView tvProperty;
        TextView tvCost;
        TextView tvCount;

        SearchHolder(View itemView) {
            super(itemView);

            tvAddress = itemView.findViewById(R.id.tv_address);
            tvRoomCount = itemView.findViewById(R.id.tv_room_count);
            tvRentPeriod = itemView.findViewById(R.id.tv_search_rent_period);
            tvProperty = itemView.findViewById(R.id.tv_property);
            tvCost = itemView.findViewById(R.id.tv_cost_interval);
            tvCount = itemView.findViewById(R.id.tv_count);
        }

        void bind(Search search, Context context) {
            RentPeriod rentPeriod = new RentPeriod(context);
            rentPeriod.setOnLoadListener(new RentPeriod.CustomOnLoadListener() {
                @Override
                public void onComplete(ArrayList<Directory> data) {
                    int rentPeriod = search.getFilter().getRentPeriod();
                    if (rentPeriod == -1) {
                        rentPeriod = 0;
                    }

                    Log.d("M2TAG", search.getFilter().getPropertiesId() + "");

                    tvAddress.setText("Адрес по умолчанию");
                    tvRoomCount.setText(getRoomCount(search.getFilter()));
                    tvRentPeriod.setText(data.get(rentPeriod).getValue());
                    tvProperty.setText("Без мебели");
                    tvCost.setText(getCost(search.getFilter()));
                    tvCount.setText(String.valueOf(search.getCount()));
                }
            });

        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return searchs.size();
    }

    private static String getRoomCount(Filter filter) {

        StringBuilder rooms = new StringBuilder("");
        if (filter.getRoomCount().size() > 0) {
            for (int i = 0; i < filter.getRoomCount().size(); i++) {
                if (i == filter.getRoomCount().size() - 1) {
                    rooms.append(filter.getRoomCount().get(i));
                } else {
                    rooms.append(filter.getRoomCount().get(i)).append(", ");
                }
            }
            rooms.append(" комнатная");
        } else {
            rooms.append("0 - комнатная");
        }

        return rooms.toString();
    }

    private static String getCost(Filter filter) {
        return "От " + filter.getCostLowerLimit() + " ₸ до " + filter.getCostUpperLimit() + " ₸";
    }

}
