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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.api.RealtyProperties;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.Directory;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.RateModel;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.utils.Logger;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> implements Constants {

    Context context;
    private ArrayList<RateModel> comments;
    Logger Log;

    OnCardClickListener onCardClickListener;

    public interface OnCardClickListener {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener) {
        this.onCardClickListener = onCardClickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titles;
        TextView comment;
        ImageView icon;
        RatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            titles = itemView.findViewById(R.id.tv_name_title);
            comment = itemView.findViewById(R.id.tv_comment);
            ratingBar = itemView.findViewById(R.id.rating_stars);
            icon = itemView.findViewById(R.id.iv_icon);
        }
    }

    public RatingAdapter(Context context, ArrayList<RateModel> comments){
        this.context = context;
        this.comments = comments;
        Log = new Logger(context, TAG);
    }

    public RatingAdapter() {
    }

    @NonNull
    @Override
    public RatingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.MyViewHolder holder, int position) {
        RateModel comment = this.comments.get(position);

        String header = comment.getName().concat(" ").concat(comment.getSurname());
        String commentText = comment.getComment();

        if(header.isEmpty() || header.equals("null")) {
            header = "Заголовок по умолчанию";
        }

        Log.d(header);

        holder.titles.setText(header);

        holder.ratingBar.setRating(comment.getStars());
        Log.d(String.valueOf(comment.getStars()));

        holder.comment.setText(commentText);

        if(comment.getImage().equals("null")) {
            String url = BASE_URL.concat(comment.getImage());
            Picasso.with(this.context).load(url).into(holder.icon);
        } else {
            holder.icon.setImageResource(R.drawable.ic_default_avatar);
        }

        holder.itemView.setOnClickListener(v -> onCardClickListener.OnCardClicked(v, position));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if(comments != null)
            return comments.size();
        return 0;
    }

    public void removeItem(int position) {
        comments.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Realty item, int position) {
        notifyItemInserted(position);
    }

    public ArrayList<RateModel> getData() {
        return comments;
    }

}