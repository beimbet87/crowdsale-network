package www.kaznu.kz.projects.m2.adapters;

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
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.BookingApplication;
import www.kaznu.kz.projects.m2.utils.Utils;

public class BookingItemAdapterAdmin extends RecyclerView.Adapter<BookingItemAdapterAdmin.ViewHolder> implements Constants {

    ArrayList<BookingApplication> items;
    private static BookingItemAdapterAdmin.ClickListener clickListener;

    public BookingItemAdapterAdmin(ArrayList<BookingApplication> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.booking_list_admin_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingApplication data = items.get(position);
        String header = data.getAddress();
        if(header.equals("") || header == null || header.equals("null")) {
            header = "Адрес";
        }

        StringBuilder date = new StringBuilder();
        date.append(Utils.parseDateWithoutYear(data.getTimeStart())).append(" - ");
        date.append(Utils.parseDateWithoutYear(data.getTimeEnd()));

        holder.tvTitle.setText(header);
        holder.tvDate.setText(date);

        if(data.getLinkImage().equals("")) {
            holder.ivIcon.setImageResource(R.drawable.default_appartment);
        } else {
            String url = BASE_URL + data.getLinkImage();
            Picasso.get().load(url).into(holder.ivIcon);
        }
        
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView tvTitle;
        TextView tvDate;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvTitle = itemView.findViewById(R.id.tv_message_title);
            tvDate = itemView.findViewById(R.id.tv_date_interval);
            ivIcon = itemView.findViewById(R.id.iv_icon);
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

    public void setOnItemClickListener(BookingItemAdapterAdmin.ClickListener clickListener) {
        BookingItemAdapterAdmin.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}