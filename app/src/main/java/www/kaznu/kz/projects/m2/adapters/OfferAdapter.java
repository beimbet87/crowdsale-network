package www.kaznu.kz.projects.m2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> implements Constants {

    Context context;
    private ArrayList<Offers> offers;
    private int lower, upper;

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
        TextView rooms;
        TextView furnitures;
        TextView prices;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            titles = itemView.findViewById(R.id.tv_title);
            address = itemView.findViewById(R.id.tv_address);
            price = itemView.findViewById(R.id.tv_price);
            rooms = itemView.findViewById(R.id.tv_rooms);
            furnitures = itemView.findViewById(R.id.tv_mebels);
            prices = itemView.findViewById(R.id.tv_prices);
            icon = itemView.findViewById(R.id.iv_icon);
        }
    }

    public OfferAdapter(Context context, ArrayList<Offers> offers, int lower, int upper){
        this.context = context;
        this.offers = offers;
        this.lower = lower;
        this.upper = upper;
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
        String cost = String.valueOf(realty.getCost()).concat(" ₸");
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
        holder.rooms.setText(getRooms(roomCount));
        holder.furnitures.setText("Без мебели");
        holder.prices.setText("От " + lower + " ₸ до " + upper + " ₸");
        if(this.offers.get(position).getImagesLink().size() > 0) {
            String url = BASE_URL.concat(this.offers.get(position).getImagesLink().get(0));
            Glide.with(this.context).load(url).into(holder.icon);
        } else {
            holder.icon.setImageResource(R.drawable.message_icon0);
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
        if(rooms < 2) {
            return rooms + " комната";
        }
        else if(rooms < 5) {
            return rooms + " комнаты";
        }
        else {
            return rooms + " комнат";
        }
    }

}