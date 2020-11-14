package www.kaznu.kz.projects.m2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.R;
import www.kaznu.kz.projects.m2.adapters.OfferAdapter;
import www.kaznu.kz.projects.m2.api.realty.FilterOffers;
import www.kaznu.kz.projects.m2.callbacks.SwipeToDeleteCallback;
import www.kaznu.kz.projects.m2.models.Filter;
import www.kaznu.kz.projects.m2.models.OfferDialog;
import www.kaznu.kz.projects.m2.models.Offers;
import www.kaznu.kz.projects.m2.models.Realty;

public class OfferActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;

    RecyclerView lView;
    OfferAdapter lAdapter;
    SharedPreferences token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        lView = findViewById(R.id.lv_ads);
        constraintLayout = findViewById(R.id.constraint_layout);

        OfferDialog offerDialog = new OfferDialog(this);
        offerDialog.show();
        Filter filter = new Filter();
//        filter.setRealtyType(6);
//        ArrayList<Integer> roomCount = new ArrayList<>();
//        roomCount.add(1);
//        roomCount.add(2);
//        roomCount.add(3);
//        roomCount.add(4);
//        roomCount.add(5);
//        filter.setRoomCount(roomCount);
//        filter.setCostLowerLimit(0.0);
//        filter.setCostUpperLimit(250000.0);
        filter.setOffset(0);
        filter.setLimit(10);

        token = getSharedPreferences("M2_TOKEN", 0);

        FilterOffers filterOffers = new FilterOffers(this, filter,  token.getString("access_token", ""));

        filterOffers.setOnLoadListener(new FilterOffers.CustomOnLoadListener() {
            @Override
            public void onComplete(ArrayList<Offers> offers) {
                
                lAdapter = new OfferAdapter(OfferActivity.this, offers, 4, 5);

                lView.setAdapter(lAdapter);

                lAdapter.setOnCardClickListner(new OfferAdapter.OnCardClickListner() {
                    @Override
                    public void OnCardClicked(View view, int position) {
                        Intent realtyIntent = new Intent(OfferActivity.this, RealtyActivity.class);
                        realtyIntent.putExtra("images", offers.get(position).getImagesLink());
                        realtyIntent.putExtra("title", offers.get(position).getRealty().getHeader());
                        realtyIntent.putExtra("address", offers.get(position).getRealty().getAddress());
                        realtyIntent.putExtra("price", offers.get(position).getRealty().getCost());
                        realtyIntent.putExtra("owner", offers.get(position).getOwner().getName());
                        realtyIntent.putExtra("avatar", offers.get(position).getOwner().getImageLink());
                        if(offers.get(position).getRealty().getDescription() != null) {
                            realtyIntent.putExtra("body", offers.get(position).getRealty().getDescription());
                        }
                        else {
                            realtyIntent.putExtra("body", "null");
                        }
                        realtyIntent.putExtra("floor", offers.get(position).getRealty().getFloor());
                        realtyIntent.putExtra("floorbuild", offers.get(position).getRealty().getFloorBuild());
                        realtyIntent.putExtra("area", offers.get(position).getRealty().getArea());
                        realtyIntent.putExtra("livingspace", offers.get(position).getRealty().getLivingSpace());

                        startActivity(realtyIntent);
                        finish();
                    }
                });

                enableSwipeToDeleteAndUndo();
            }
        });
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                Realty item;
                item = lAdapter.getData().get(position).getRealty();

                lAdapter.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(constraintLayout, "Предложние будет удалено.", Snackbar.LENGTH_LONG);
                snackbar.setAction("Отмена", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        lAdapter.restoreItem(item, position);
                        lView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
                snackbar.setBackgroundTint(getResources().getColor(R.color.color_primary_error));

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(lView);
    }
}