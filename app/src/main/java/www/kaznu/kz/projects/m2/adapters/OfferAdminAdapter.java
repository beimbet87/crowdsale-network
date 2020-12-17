package www.kaznu.kz.projects.m2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.FlowLayout;

public class OfferAdminAdapter extends RecyclerView.Adapter<OfferAdminAdapter.MyViewHolder> implements Constants {

    Context context;
    private ArrayList<CurrentUser> users;
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
        RatingBar ratingBar;
        FlowLayout flowLayout;
        Button btnOffer;
        LinearLayout offerPanel;

        public MyViewHolder(View itemView) {
            super(itemView);

            titles = itemView.findViewById(R.id.tv_profile_username1);
            btnOffer = itemView.findViewById(R.id.btn_offer_price);
            price = itemView.findViewById(R.id.tv_price_per_day);
            ratingBar = itemView.findViewById(R.id.rb_profile_rating1);
            icon = itemView.findViewById(R.id.iv_profile_image1);
            flowLayout = itemView.findViewById(R.id.offer_properties);
            offerPanel = itemView.findViewById(R.id.offer_panel);
        }
    }

    public OfferAdminAdapter(Context context, ArrayList<CurrentUser> users) {
        this.context = context;
        this.users = users;

        Log = new Logger(context, TAG);
    }

    @NonNull
    @Override
    public OfferAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_offer_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OfferAdminAdapter.MyViewHolder holder, int position) {

        CurrentUser user = this.users.get(position);
        holder.offerPanel.setVisibility(View.GONE);

        String header = user.getName();
//        String cost = Utils.parsePrice((double) Math.round(user.getCost()));

        if (header.isEmpty() || header.equals("null")) {
            header = "Заголовок по умолчанию";
        }

        holder.titles.setText(header);
//        addText(getRooms(roomCount), holder.flowLayout, context, padding0, padding1);

        holder.ratingBar.setRating(user.getStars());

        if (!user.getImageLink().isEmpty()) {
            String url = BASE_URL.concat(user.getImageLink());
            Picasso.get().load(url).into(holder.icon);
        } else {
            holder.icon.setImageResource(R.drawable.button_background_gray);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, position);
                if(isOffer) {
                    holder.offerPanel.setVisibility(View.VISIBLE);
                    isOffer = false;
                } else {
                    holder.offerPanel.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if (users != null)
            return users.size();
        return 0;
    }

    public void removeItem(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Realty item, int position) {
//        offers.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<CurrentUser> getData() {
        return users;
    }

    public String getRooms(int rooms) {
        return rooms + " комн.";
    }

    public String addText(String data, FlowLayout flowLayout, Context context, int padding0, int padding1) {

        TextView textView = new TextView(context);
        textView.setTextSize(12);
        textView.setBackground(ContextCompat.getDrawable(context, R.drawable.view_profile_button_background));
        textView.setTextColor(ContextCompat.getColor(context, R.color.color_primary_dark));
        textView.setMaxLines(1);
        textView.setPadding(padding1, padding0, padding1, padding0);
        textView.setText(data);
        flowLayout.addView(textView);
        return data;
    }

}