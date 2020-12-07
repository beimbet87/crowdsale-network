package www.kaznu.kz.projects.m2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.activities.RealtyActivity;
import www.kaznu.kz.projects.m2.adapters.OfferAdapter;
import www.kaznu.kz.projects.m2.callbacks.SwipeToDeleteCallback;
import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.models.OfferDialog;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;
import www.kaznu.kz.projects.m2.utils.Logger;
import www.kaznu.kz.projects.m2.utils.Utils;

public class OfferFragment extends Fragment {

    RecyclerView lView;
    OfferAdapter lAdapter;
    SharedPreferences shDialogs;

    ConstraintLayout constraintLayout;
    ArrayList<Offers> offers;

    public OfferFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_offer, container, false);

        lView = root.findViewById(R.id.lv_ads);
        constraintLayout = root.findViewById(R.id.constraint_layout);


        shDialogs = requireActivity().getSharedPreferences("M2DIALOGS", 0);

        if (!shDialogs.getBoolean("offer_dialog", false)) {
            OfferDialog offerDialog = new OfferDialog(requireActivity());
            offerDialog.show();

            SharedPreferences.Editor editor = shDialogs.edit();

            editor.putBoolean("offer_dialog", true);
            editor.apply();
        }

        offers = getArguments().getParcelableArrayList("offers");

        lAdapter = new OfferAdapter(requireContext(), offers, getArguments().getString("price"),
                getResources().getDimensionPixelSize(R.dimen.padding_top_bottom),
                getResources().getDimensionPixelSize(R.dimen.padding_left_right));

        lView.setAdapter(lAdapter);

        lAdapter.setOnCardClickListner((view, position) -> {
            Intent realtyIntent = new Intent(requireContext(), RealtyActivity.class);
            realtyIntent.putExtra("images", offers.get(position).getImagesLink());
            realtyIntent.putExtra("title", offers.get(position).getRealty().getHeader());
            realtyIntent.putExtra("address", offers.get(position).getRealty().getAddress());
            realtyIntent.putExtra("price", offers.get(position).getRealty().getCost());
            realtyIntent.putExtra("owner", offers.get(position).getOwner().getName());
            realtyIntent.putExtra("stars", offers.get(position).getOwner().getStars());
            realtyIntent.putExtra("avatar", offers.get(position).getOwner().getImageLink());
            if (offers.get(position).getRealty().getDescription() != null) {
                realtyIntent.putExtra("body", offers.get(position).getRealty().getDescription());
            } else {
                realtyIntent.putExtra("body", "null");
            }
            realtyIntent.putExtra("floor", offers.get(position).getRealty().getFloor());
            realtyIntent.putExtra("floorbuild", offers.get(position).getRealty().getFloorBuild());
            realtyIntent.putExtra("area", offers.get(position).getRealty().getArea());
            realtyIntent.putExtra("livingspace", offers.get(position).getRealty().getLivingSpace());
            realtyIntent.putExtra("ref_realty", offers.get(position).getRealty().getId());
            realtyIntent.putExtra("contact", offers.get(position).getOwner().getId());
            realtyIntent.putIntegerArrayListExtra("properties", offers.get(position).getProperties());
            Logger Log = new Logger(requireContext(), Constants.TAG);
            Log.d(offers.get(position).getOwner().getId() + "" + offers.get(position).getRealty().getId());

            startActivity(realtyIntent);
        });

        enableSwipeToDeleteAndUndo();

        return root;
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(requireContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                Realty item;
                item = lAdapter.getData().get(position).getRealty();

                lAdapter.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(constraintLayout, "Предложние будет удалено.", Snackbar.LENGTH_LONG);
                snackbar.setAction("Отмена", view -> {
                    lAdapter.restoreItem(item, position);
                    lView.scrollToPosition(position);
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
                snackbar.setBackgroundTint(getResources().getColor(R.color.color_primary_error));

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(lView);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
