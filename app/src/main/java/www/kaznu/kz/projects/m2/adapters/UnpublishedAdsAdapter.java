package www.kaznu.kz.projects.m2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.RealtyProperties;
import www.kaznu.kz.projects.m2.api.RealtyType;
import www.kaznu.kz.projects.m2.api.RentPeriod;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Filter;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Properties;
import www.kaznu.kz.projects.m2.models.Search;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.FlowLayout;

public class UnpublishedAdsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    public ArrayList<Offers> offers;

    private static ClickListener clickListener;

    public UnpublishedAdsAdapter(Context context, ArrayList<Offers> offers) {
        this.context = context;
        this.offers = offers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ads_unpublished_list_item, parent, false);
        return new PublishedAdsHolder(view);
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Offers offer = offers.get(position);

        ((PublishedAdsHolder) holder).bind(offer, this.context, position, getItemCount() - position);
    }

    private static class PublishedAdsHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvTitle, tvAddress;
        TextView tvCount;
        TextView tvPrice;
        TextView tvRooms;
        ImageView ivIcon;

        PublishedAdsHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAddress = itemView.findViewById(R.id.tv_message_title);
            tvCount = itemView.findViewById(R.id.tv_message_count);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvRooms = itemView.findViewById(R.id.tv_rooms);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }

        void bind(Offers offers, Context context, int position, int num) {

            Logger Log = new Logger(context, Constants.TAG);

            tvTitle.setText(offers.getRealty().getHeader());
            tvCount.setText(String.valueOf(offers.getSearches().size()));
            tvAddress.setText(offers.getRealty().getAddress());
            tvPrice.setText(Utils.parsePrice(offers.getRealty().getCost()));
            tvRooms.setText(new Properties(context).getRentPeriodValue(offers.getRealty().getRentPeriod()));

            if(offers.getImagesLink().size() > 0) {
                Picasso.with(context).load(Constants.BASE_URL.concat(offers.getImagesLink().get(0))).into(ivIcon);
            }

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
        return offers.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        UnpublishedAdsAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }
}
