package www.kaznu.kz.projects.m2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.model.AutocompletePrediction;

import java.util.ArrayList;
import java.util.List;

import www.kaznu.kz.projects.m2.R;

public class SearchAddressAdapter extends RecyclerView.Adapter<SearchAddressAdapter.SearchAddressViewHolder> implements Filterable {

    public final List<AutocompletePrediction> predictions = new ArrayList<>();

    public OnSearchAddressClickListener onSearchAddressClickListener;

    @NonNull
    @Override
    public SearchAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SearchAddressViewHolder(
                inflater.inflate(R.layout.address_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAddressViewHolder holder, int position) {
        final AutocompletePrediction prediction = predictions.get(position);
        holder.setPrediction(prediction);
        holder.itemView.setOnClickListener(v -> {
            if (onSearchAddressClickListener != null) {
                onSearchAddressClickListener.onPlaceClicked(prediction);
            }
        });
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }

    public void setPredictions(List<AutocompletePrediction> predictions) {
        this.predictions.clear();
        this.predictions.addAll(predictions);
        notifyDataSetChanged();
    }

    public void setPlaceClickListener(OnSearchAddressClickListener onSearchAddressClickListener) {
        this.onSearchAddressClickListener = onSearchAddressClickListener;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class SearchAddressViewHolder extends RecyclerView.ViewHolder {

        private final TextView location;
        private final TextView address;

        public SearchAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.latlng);
            address = itemView.findViewById(R.id.address);
        }

        public void setPrediction(AutocompletePrediction prediction) {
            location.setText(prediction.getSecondaryText(null));
            address.setText(prediction.getPrimaryText(null));
        }
    }

    public interface OnSearchAddressClickListener {
        void onPlaceClicked(AutocompletePrediction place);
    }
}
