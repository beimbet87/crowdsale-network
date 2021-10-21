package www.kaznu.kz.projects.m2.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.BookingApplication;
import www.kaznu.kz.projects.m2.models.RealtySearch;
import www.kaznu.kz.projects.m2.utils.Utils;
import www.kaznu.kz.projects.m2.views.activities.DiscussionAdminActivity;

public class RealtySearchesAdapterAdmin extends RecyclerView.Adapter<RealtySearchesAdapterAdmin.ViewHolder> implements Constants {

    private ArrayList<RealtySearch> sectionList;
    private static ClickListener clickListener;

    public RealtySearchesAdapterAdmin(ArrayList<RealtySearch> sectionList) {
        this.sectionList = sectionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.realty_searches_admin_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RealtySearch search = sectionList.get(position);
        String fullName = search.getName().concat(" ").concat(search.getSurname());

        String address = search.getAddress();
        if(address.equals("") || address == null || address.equals("null")) {
            address = "г.Алматы, мкр. Алатау, дом 124";
        }

        holder.tvFullName.setText(fullName);
        holder.tvAddress.setText(address);

        if(search.getUserImageUrl().equals("")) {
            holder.ivAvatar.setImageResource(R.drawable.default_appartment);
        } else {
            String url = BASE_URL + search.getUserImageUrl();
            Picasso.get().load(url).into(holder.ivAvatar);
        }

    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvFullName;
        TextView tvAddress;
        ImageView ivAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvFullName = itemView.findViewById(R.id.tv_fullname);
            tvAddress = itemView.findViewById(R.id.tv_address);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAbsoluteAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAbsoluteAdapterPosition(), view);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RealtySearchesAdapterAdmin.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}