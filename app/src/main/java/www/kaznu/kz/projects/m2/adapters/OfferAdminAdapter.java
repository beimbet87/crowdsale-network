package www.kaznu.kz.projects.m2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.models.RealtySearch;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.views.customviews.FlowLayout;

public class OfferAdminAdapter extends RecyclerView.Adapter<OfferAdminAdapter.MyViewHolder> implements Constants {

    Context context;
    private ArrayList<RealtySearch> searches;
    boolean [] isSelected;
    int p0, p1;
    Logger Log;
    OnCardClickListner onCardClickListner;
    boolean isOffer = true;

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titles;
        TextView price;
        ImageView icon;
        ScaleRatingBar ratingBar;
        FlowLayout flowLayout;
        Button btnOffer;
        LinearLayout offerPanel;

        public MyViewHolder(View itemView) {
            super(itemView);

            titles = itemView.findViewById(R.id.tv_profile_username1);
            btnOffer = itemView.findViewById(R.id.btn_offer_price);
            price = itemView.findViewById(R.id.tv_price_per_day);
            ratingBar = (ScaleRatingBar) itemView.findViewById(R.id.rb_profile_rating1);
            icon = itemView.findViewById(R.id.iv_profile_image1);
            flowLayout = itemView.findViewById(R.id.fl_properties);
            offerPanel = itemView.findViewById(R.id.offer_panel);
        }
    }

    public OfferAdminAdapter(Context context, ArrayList<RealtySearch> users, int p0, int p1) {
        this.context = context;
        this.searches = users;
        this.p0 = p0;
        this.p1 = p1;

        isSelected = new boolean[getItemCount()];
    }

    @NonNull
    @Override
    public OfferAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_offer_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OfferAdminAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        RealtySearch search = this.searches.get(position);
        holder.offerPanel.setVisibility(View.GONE);

        String header = search.getName().concat(" ").concat(search.getSurname());
//        String cost = Utils.parsePrice((double) Math.round(user.getCost()));

        if (header.isEmpty() || header.equals("null")) {
            header = "Заголовок по умолчанию";
        }

        holder.titles.setText(header);

        StringBuilder rooms = new StringBuilder();

        for (int i = 0; i < search.getFilter().getRoomCount().size(); i++) {
            rooms.append(search.getFilter().getRoomCount().get(i));
            if(i != search.getFilter().getRoomCount().size() - 1) {
                rooms.append(", ");
            }
        }

        rooms.append(" - комнатная");

        String price = "От " + search.getFilter().getCostLowerLimit() + " ₸ до " +
                    search.getFilter().getCostUpperLimit() + " ₸";

        addText(price, holder.flowLayout, context, p0, p1);
        String rentPeriod = search.getFilter().getStartDate() + "-" +
                search.getFilter().getEndDate();
        addText(rentPeriod, holder.flowLayout, context, p0, p1);
        addText(rooms.toString(), holder.flowLayout, context, p0, p1);

        holder.ratingBar.setRating(5);

        if(search.getUserImageUrl().equals("")) {
            holder.icon.setImageResource(R.drawable.ic_default_avatar);
        } else {
            String url = BASE_URL + search.getUserImageUrl();
            Picasso.get().load(url).into(holder.icon);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, position);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if (searches != null)
            return searches.size();
        return 0;
    }

    public void removeItem(int position) {
        searches.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Realty item, int position) {
//        offers.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<RealtySearch> getData() {
        return searches;
    }

    public String getRooms(int rooms) {
        return rooms + " комн.";
    }

    public String addText(String data, FlowLayout flowLayout, Context context, int p0, int p1) {

        TextView textView = new TextView(context);
        textView.setTextSize(11);
        textView.setBackground(ContextCompat.getDrawable(context, R.drawable.view_profile_button_background));
        textView.setTextColor(ContextCompat.getColor(context, R.color.color_primary_dark));
        textView.setMaxLines(1);
        textView.setPadding(p1, p0, p1, p0);
        textView.setText(data);
        flowLayout.addView(textView);
        return data;
    }

}