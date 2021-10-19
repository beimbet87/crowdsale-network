package www.kaznu.kz.projects.m2.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.ClickListener;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.BookingApplication;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> implements Constants {

    private final ArrayList<BookingApplication> booking;
    private static ClickListener listener;

    public BookingAdapter(ArrayList<BookingApplication> booking){
        this.booking = booking;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.booking_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingApplication data = booking.get(position);
        String address = data.getAddress();

        Logger.d("Address is: " + address);

        if(data.getAddress() == null || data.getAddress().equals("") || data.getAddress().equals("null")) {
            address = "Адрес по умолчанию";
        }

        StringBuilder date = new StringBuilder();
        date.append(Utils.parseDateWithoutYear(data.getTimeStart())).append(" - ");
        date.append(Utils.parseDateWithoutYear(data.getTimeEnd()));

        holder.tvAddress.setText(address);
        holder.tvDate.setText(date);

        if(data.getLinkImage().equals("")) {
            holder.ivAvatar.setImageResource(R.drawable.default_appartment);
        } else {
            String url = BASE_URL + data.getLinkImage();
            Picasso.get().load(url).into(holder.ivAvatar);
        }

        Logger.d("Booking list size: " + getItemCount());
    }

    @Override
    public int getItemCount() {
        return booking.size();
    }

    public void setOnClickListener(ClickListener listener) {
        BookingAdapter.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvAddress, tvDate;
        ImageView ivAvatar;

        public ViewHolder(@NonNull View root) {
            super(root);

            root.setOnClickListener(this);
            root.setOnLongClickListener(this);

            tvAddress = root.findViewById(R.id.tv_address);
            tvDate = root.findViewById(R.id.tv_date);
            ivAvatar = root.findViewById(R.id.iv_avatar);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAbsoluteAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onItemLongClick(getAbsoluteAdapterPosition(), view);
            return false;
        }
    }
}