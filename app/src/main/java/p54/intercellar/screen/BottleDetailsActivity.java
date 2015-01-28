package p54.intercellar.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.view.BottleDetailsFragment;
import p54.intercellar.view.RatingFragment;

public class BottleDetailsActivity extends InterCellarActivity<BottleController> {
    private static final int EDIT_BOTTLE = 2;
    private static final int ADD_RATING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long bottleId = getIntent().getLongExtra("bottleId", -1);
        getController().setCurrentBottleId(bottleId);

        setContentView(R.layout.activity_bottle_details);

        BottleDetailsFragment bottleDetailsFragment = (BottleDetailsFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle_details);
        bottleDetailsFragment.showBottleDetails(getController().getCurrentBottleId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bottle_details, menu);
        return true;
    }

    public void onEditClick(MenuItem menuItem) {
        Intent editBottleActivity = new Intent(this, BottleFormActivity.class);
        editBottleActivity.putExtra("bottleId", getController().getCurrentBottleId());
        startActivityForResult(editBottleActivity, EDIT_BOTTLE);
    }

    public void onAddRatingClick(MenuItem menuItem) {
        Intent addRatingActivity = new Intent(this, AddRatingActivity.class);
        addRatingActivity.putExtra("bottleId", getController().getCurrentBottleId());
        startActivityForResult(addRatingActivity, ADD_RATING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case EDIT_BOTTLE:
                Toast.makeText(this, R.string.bottle_successfully_edited, Toast.LENGTH_LONG);
                BottleDetailsFragment bottleDetailsFragment = (BottleDetailsFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle_details);
                bottleDetailsFragment.showBottleDetails(getController().getCurrentBottleId());
                break;
            case ADD_RATING:
                RatingFragment ratingFragment = (RatingFragment) getFragmentManager().findFragmentById(R.id.fragment_rating_list);
                long bottleId = getController().getCurrentBottleId();
                ratingFragment.refreshRatingList(bottleId);
                break;
        }
    }
}
