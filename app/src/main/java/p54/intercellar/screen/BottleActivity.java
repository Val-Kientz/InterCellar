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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bottle, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBottleClick(long id) {
        getController().setCurrentBottleId(id);

        BottleDetailsFragment bottleDetailsFragment = (BottleDetailsFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_bottle_details_land);

        if (bottleDetailsFragment == null) {
            Intent bottleDetailsActivity = new Intent(this, BottleDetailsActivity.class);
            bottleDetailsActivity.putExtra("bottleId", id);
            startActivity(bottleDetailsActivity);
        } else {
            bottleDetailsFragment.showBottleDetails(id);
            RatingFragment ratingFragment = (RatingFragment) getFragmentManager().findFragmentById(R.id.fragment_rating_list);
            if (ratingFragment != null) {
                ratingFragment.refreshRatingList();
            }
            ((ScrollView) findViewById(R.id.bottle_scroll_view_land)).fullScroll(View.FOCUS_UP);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ADD_BOTTLE:
                Toast.makeText(this, R.string.bottle_successfully_added, Toast.LENGTH_LONG);
                break;
            case ADD_RATING:

                break;
        }

        BottleFragment bottleFragment = (BottleFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle);
        if (bottleFragment != null) {
            bottleFragment.refreshList();
        }

        RatingFragment ratingFragment = (RatingFragment) getFragmentManager().findFragmentById(R.id.fragment_rating_list);
        if (ratingFragment != null) {
            ratingFragment.refreshRatingList();
        }
    }

    public void onAddClick(MenuItem menuItem) {
        Intent addBottleActivity = new Intent(this, BottleFormActivity.class);
        startActivityForResult(addBottleActivity, ADD_BOTTLE);
    }

    public void onAddRatingClick(MenuItem menuItem) {
        Intent addRatingActivity = new Intent(this, AddRatingActivity.class);
        addRatingActivity.putExtra("bottleId", getController().getCurrentBottleId());
        startActivityForResult(addRatingActivity, ADD_RATING);
    }
}
