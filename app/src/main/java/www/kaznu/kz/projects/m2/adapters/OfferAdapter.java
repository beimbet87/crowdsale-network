package www.kaznu.kz.projects.m2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.RealtyProperties;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Directory;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.FlowLayout;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> implements Constants {

    Context context;
    private ArrayList<Offers> offers;
    private String price;
    private int padding0, padding1;
    Logger Log;
    int i;

    OnCardClickListner onCardClickListner;

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titles;
        TextView address;
        TextView price;
        ImageView icon;
        RatingBar ratingBar;
        FlowLayout flowLayout;


        public MyViewHolder(View itemView) {
            super(itemView);

            titles = itemView.findViewById(R.id.tv_title);
            address = itemView.findViewById(R.id.tv_message_title);
            price = itemView.findViewById(R.id.tv_price);
            ratingBar = itemView.findViewById(R.id.rating_stars);
            icon = itemView.findViewById(R.id.iv_icon);
            flowLayout = itemView.findViewById(R.id.offer_properties);
        }
    }

    public OfferAdapter(Context context, ArrayList<Offers> offers, String price, int padding0, int padding1){
        this.context = context;
        this.offers = offers;
        this.price = price;
        this.padding0 = padding0;
        this.padding1 = padding1;

        Log = new Logger(context, TAG);
    }

    public OfferAdapter() {
    }

    @NonNull
    @Override
    public OfferAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.MyViewHolder holder, int position) {
        Realty realty = this.offers.get(position).getRealty();

        String header = realty.getHeader();
        String address = realty.getAddress();
        String cost = Utils.parsePrice((double)Math.round(realty.getCost()), "");
        int roomCount = realty.getRoomCount();

        if(header.isEmpty() || header.equals("null")) {
            header = "Заголовок по умолчанию";
        }
        if(address.isEmpty() || address.equals("null")) {
            address = "Адрес по умолчанию";
        }

        holder.titles.setText(header);
        holder.address.setText(address);
        holder.price.setText(cost);
        addText(getRooms(roomCount), holder.flowLayout, context, padding0, padding1);

        if(this.offers.get(position).getProperties().size() > 0) {
            for (i = 0; i < this.offers.get(position).getProperties().size(); i++) {
                RealtyProperties realtyProperties = new RealtyProperties(context);
                final int temp = this.offers.get(position).getProperties().get(i);
                realtyProperties.setOnLoadListener(new RealtyProperties.CustomOnLoadListener() {
                    @Override
                    public void onComplete(ArrayList<Directory> data) {
                        for(int idx = 0; idx < data.size(); idx++) {
                            if(data.get(idx).getCodeId() == temp) {
                                String upperString = data.get(idx).getValue().substring(0, 1).toUpperCase() + data.get(idx).getValue().substring(1).toLowerCase();
                                addText(upperString, holder.flowLayout, context, padding0, padding1);
                            }
                        }
                    }
                });
            }
        }
        holder.ratingBar.setRating(this.offers.get(position).getOwner().getStars());
        Log.d(String.valueOf(this.offers.get(position).getOwner().getStars()));
        if(this.offers.get(position).getImagesLink().size() > 0) {
            String url = BASE_URL.concat(this.offers.get(position).getImagesLink().get(0));
            Glide.with(this.context).load(url).into(holder.icon);
        } else {
            holder.icon.setImageResource(R.drawable.button_background_gray);
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
        if(offers != null)
            return offers.size();
        return 0;
    }

    public void removeItem(int position) {
        offers.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Realty item, int position) {
//        offers.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Offers> getData() {
        return offers;
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