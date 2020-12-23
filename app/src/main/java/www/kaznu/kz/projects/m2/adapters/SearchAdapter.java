package www.kaznu.kz.projects.m2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.RealtyProperties;
import www.kaznu.kz.projects.m2.api.RealtyType;
import www.kaznu.kz.projects.m2.api.RentPeriod;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Filter;
import www.kaznu.kz.projects.m2.models.Search;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.customviews.FlowLayout;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    public ArrayList<Search> searches;
    public ArrayList<Boolean> isView;
    public ArrayList<FlowLayout> views;

    private static SearchAdapter.ClickListener clickListener;

    public int p0, p1;

    public SearchAdapter(Context context, ArrayList<Search> searches, int p0, int p1) {
        this.context = context;
        this.searches = searches;
        this.p0 = p0;
        this.p1 = p1;
        this.isView = new ArrayList<>();
        this.views = new ArrayList<>();

        for (int i = 0; i < searches.size(); i++) {
            this.isView.add(true);

            FlowLayout flowLayout = new FlowLayout(context);

            Search search = searches.get(i);

            if (getRoomCount(search.getFilter()) != null) {

                TextView tvRoomCount = new TextView(context);
                tvRoomCount.setTextSize(12);
                tvRoomCount.setBackground(ContextCompat.getDrawable(context, R.drawable.view_profile_button_background));
                tvRoomCount.setTextColor(ContextCompat.getColor(context, R.color.color_primary_dark));
                tvRoomCount.setMaxLines(1);
                tvRoomCount.setPadding(p1, p0, p1, p0);
                tvRoomCount.setText(getRoomCount(search.getFilter()));
                flowLayout.addView(tvRoomCount);
            }

            RealtyType realtyType = new RealtyType(context);
            realtyType.setOnLoadListener(data -> {
                String rent = null;
                for (int i1 = 0; i1 < data.size(); i1++) {
                    if (data.get(i1).getCodeId() == search.getFilter().getRealtyType())
                        rent = data.get(i1).getValue();
                }

                if (rent != null) {
                    TextView tvRealtyType = new TextView(context);
                    tvRealtyType.setTextSize(12);
                    tvRealtyType.setBackground(ContextCompat.getDrawable(context, R.drawable.view_profile_button_background));
                    tvRealtyType.setTextColor(ContextCompat.getColor(context, R.color.color_primary_dark));
                    tvRealtyType.setMaxLines(1);
                    tvRealtyType.setPadding(p1, p0, p1, p0);
                    tvRealtyType.setText(rent);

                    flowLayout.addView(tvRealtyType);
                }
            });

            if (getCost(search.getFilter()) != null) {
                TextView tvPrice = new TextView(context);
                tvPrice.setTextSize(12);
                tvPrice.setBackground(ContextCompat.getDrawable(context, R.drawable.view_profile_button_background));
                tvPrice.setTextColor(ContextCompat.getColor(context, R.color.color_primary_dark));
                tvPrice.setMaxLines(1);
                tvPrice.setPadding(p1, p0, p1, p0);
                tvPrice.setText(getCost(search.getFilter()));

                flowLayout.addView(tvPrice);
            }

            RentPeriod rentPeriod = new RentPeriod(context);
            rentPeriod.setOnLoadListener(data -> {
                String rent = null;
                for (int i1 = 0; i1 < data.size(); i1++) {
                    if (data.get(i1).getCodeId() == search.getFilter().getRentPeriod())
                        rent = data.get(i1).getValue();
                }

                if (rent != null) {
                    TextView tvRentPeriod = new TextView(context);
                    tvRentPeriod.setTextSize(12);
                    tvRentPeriod.setBackground(ContextCompat.getDrawable(context, R.drawable.view_profile_button_background));
                    tvRentPeriod.setTextColor(ContextCompat.getColor(context, R.color.color_primary_dark));
                    tvRentPeriod.setMaxLines(1);
                    tvRentPeriod.setPadding(p1, p0, p1, p0);
                    tvRentPeriod.setText(rent);

                    flowLayout.addView(tvRentPeriod);
                }
            });

            if (search.getFilter().getPropertiesId().size() > 0) {
                for (int ip = 0; ip < search.getFilter().getPropertiesId().size(); ip++) {
                    RealtyProperties realtyProperties = new RealtyProperties(context);
                    final int temp = search.getFilter().getPropertiesId().get(ip);
                    realtyProperties.setOnLoadListener(data -> {
                        for (int idx = 0; idx < data.size(); idx++) {
                            if (data.get(idx).getCodeId() == temp) {
                                String upperString = data.get(idx).getValue().substring(0, 1).toUpperCase() + data.get(idx).getValue().substring(1).toLowerCase();
                                TextView tvProperties = new TextView(context);
                                tvProperties.setTextSize(12);
                                tvProperties.setBackground(ContextCompat.getDrawable(context, R.drawable.view_profile_button_background));
                                tvProperties.setTextColor(ContextCompat.getColor(context, R.color.color_primary_dark));
                                tvProperties.setMaxLines(1);
                                tvProperties.setPadding(p1, p0, p1, p0);
                                tvProperties.setText(upperString);
                                flowLayout.addView(tvProperties);
                            }
                        }
                    });
                }
            }

            views.add(flowLayout);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_item, parent, false);
        return new SearchAdapter.SearchHolder(view);
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        ((SearchHolder) holder).flowLayout.removeAllViews();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Search search = searches.get(position);
        FlowLayout flow = views.get(position);

        ((SearchHolder) holder).bind(search, flow, this.context, position, this.isView.get(position), getItemCount() - position);
        this.isView.add(position, false);
    }

    private static class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvTitle;
        TextView tvCount;
        FlowLayout flowLayout;

        SearchHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvTitle = itemView.findViewById(R.id.tv_message_title);
            flowLayout = itemView.findViewById(R.id.list_search_properties);
            tvCount = itemView.findViewById(R.id.tv_count);
        }

        void bind(Search search, FlowLayout flow, Context context, int position, boolean isView, int num) {

            flowLayout.removeAllViews();
            flowLayout.addView(flow);

            tvTitle.setText(new StringBuilder().append("Поиск # ").append(search.getId()));
            tvCount.setText(String.valueOf(search.getCount()));

            Logger.d("ViewData --> " + position + " " + isView);

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

    public String getRoomCount(Filter filter) {
        StringBuilder rooms = new StringBuilder();
        if (filter.getRoomCount().size() > 0) {
            for (int i = 0; i < filter.getRoomCount().size(); i++) {
                if (i == filter.getRoomCount().size() - 1) {
                    rooms.append(filter.getRoomCount().get(i));
                } else {
                    rooms.append(filter.getRoomCount().get(i)).append(", ");
                }
            }
            rooms.append(" комнат.");
        } else {
            return null;
        }

        return rooms.toString();
    }

    public String getCost(Filter filter) {
        String price = null;

        if (filter.getCostLowerLimit() > 0.0 && filter.getCostUpperLimit() <= 0.0) {
            price = "От " + Utils.parsePrice(filter.getCostLowerLimit());
        }

        if (filter.getCostLowerLimit() <= 0.0 && filter.getCostUpperLimit() > 0.0) {
            price = "До " + Utils.parsePrice(filter.getCostUpperLimit());
        }

        if (filter.getCostLowerLimit() > 0.0 && filter.getCostUpperLimit() > 0.0) {
            price = "От " + Utils.parsePrice(filter.getCostLowerLimit())
                    + " до " + Utils.parsePrice(filter.getCostUpperLimit());
        }

        return price;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    public void setOnItemClickListener(SearchAdapter.ClickListener clickListener) {
        SearchAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }
}
