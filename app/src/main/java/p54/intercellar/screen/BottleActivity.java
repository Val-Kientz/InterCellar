package p54.intercellar.screen;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.List;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Bottle;
import p54.intercellar.view.BottleDetailsFragment;
import p54.intercellar.view.BottleFragment;
import p54.intercellar.view.RatingFragment;

public class BottleActivity extends InterCellarActivity<BottleController> implements BottleFragment.OnBottleClick {
    private static final int ADD_BOTTLE = 1;
    private static final int ADD_RATING = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottle);

        if (findViewById(R.id.fragment_bottle_details_land) != null) {
            List<Bottle> bottleList = getController().getBottleList();
            BottleDetailsFragment bottleDetailsFragment = (BottleDetailsFragment) getFragmentManager()
                    .findFragmentById(R.id.fragment_bottle_details_land);

            if (!bottleList.isEmpty()) {
                getController().setCurrentBottleId(bottleList.get(0).getId());
                bottleDetailsFragment.showBottleDetails(getController().getCurrentBottleId());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottle, menu);

        return true;
    }

    @Override
    public void onBottleClick(long id) {
        getController().setCurrentBottleId(id);

        if (findViewById(R.id.fragment_bottle_details_land) == null) {
            Intent bottleDetailsActivity = new Intent(this, BottleDetailsActivity.class);
            bottleDetailsActivity.putExtra("bottleId", id);
            startActivity(bottleDetailsActivity);
        } else {
            BottleDetailsFragment bottleDetailsFragment = (BottleDetailsFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle_details_land);
            RatingFragment ratingFragment = (RatingFragment) getFragmentManager().findFragmentById(R.id.fragment_rating_list);

            if (bottleDetailsFragment != null) {
                bottleDetailsFragment.showBottleDetails(id);
            }

            if (ratingFragment != null) {
                ratingFragment.refreshRatingList(id);
            }
            ((ScrollView) findViewById(R.id.bottle_scroll_view_land)).fullScroll(View.FOCUS_UP);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ADD_BOTTLE:
                BottleFragment bottleFragment = (BottleFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle);
                if (bottleFragment != null) {
                    bottleFragment.refreshList();
                }
                break;
            case ADD_RATING:
                RatingFragment ratingFragment = (RatingFragment) getFragmentManager().findFragmentById(R.id.fragment_rating_list);
                if (ratingFragment != null) {
                    long bottleId = getController().getCurrentBottleId();
                    ratingFragment.refreshRatingList(bottleId);
                }
                break;
        }
    }

    public void onAddClick(MenuItem menuItem) {
        Intent addBottleActivity = new Intent(this, BottleFormActivity.class);
        startActivityForResult(addBottleActivity, ADD_BOTTLE);
    }

    public void onAddRatingClick(MenuItem menuItem) {
        long bottleId = getController().getCurrentBottleId();
        Intent addRatingActivity = new Intent(this, AddRatingActivity.class);
        addRatingActivity.putExtra("bottleId", bottleId);
        startActivityForResult(addRatingActivity, ADD_RATING);
    }
}
