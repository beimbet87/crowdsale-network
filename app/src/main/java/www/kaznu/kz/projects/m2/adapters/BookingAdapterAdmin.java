package www.kaznu.kz.projects.m2.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.views.activities.DiscussionAdminActivity;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.BookingApplication;
import www.kaznu.kz.projects.m2.models.ScheduleSection;

public class BookingAdapterAdmin extends RecyclerView.Adapter<BookingAdapterAdmin.ViewHolder> implements Constants {

    private ArrayList<ScheduleSection> sectionList;

    public BookingAdapterAdmin(ArrayList<ScheduleSection> sectionList) {
        this.sectionList = sectionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.booking_list_admin_header, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleSection section = sectionList.get(position);
        String sectionTitle = section.getSectionTitle();
        ArrayList<BookingApplication> items = section.getSectionData();

        holder.tvSectionTitle.setText(sectionTitle);
        holder.rvSectionData.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

        BookingItemAdapterAdmin adapter = new BookingItemAdapterAdmin(items);

        holder.rvSectionData.setAdapter(adapter);

        adapter.setOnItemClickListener(new BookingItemAdapterAdmin.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DiscussionAdminActivity.class);
                intent.putExtra("contact", 52);
                intent.putExtra("ref_realty", section.getSectionData().get(position).getRefRealty());
                intent.putExtra("owner", true);

                holder.itemView.getContext().startActivity(intent);

                Log.d(Constants.TAG, "Click ---> " + section.getSectionData().get(position).getRefRealtyGuest());
                Log.d(Constants.TAG, "Click ---> " + section.getSectionData().get(position).getRefRealty());
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSectionTitle;
        RecyclerView rvSectionData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSectionTitle = itemView.findViewById(R.id.tv_header);
            rvSectionData = itemView.findViewById(R.id.rv_items);
        }
    }
}