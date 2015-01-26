package p54.intercellar.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.view.BottleDetailsFragment;
import p54.intercellar.view.RatingsFragment;

public class BottleDetailsActivity extends InterCellarActivity<BottleController> {
    private static final int EDIT_BOTTLE = 2;
    private static final int ADD_RATING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = getIntent().getLongExtra("bottleId", -1);

        setContentView(R.layout.activity_bottle_details);

        BottleDetailsFragment bottleDetailsFragment = (BottleDetailsFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle_details);
        bottleDetailsFragment.showBottleDetails(id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bottle_details, menu);
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

    public void onEditClick(View v) {
        Intent editBottleActivity = new Intent(this, BottleFormActivity.class);
        editBottleActivity.putExtra("bottleId", getController().getCurrentBottleId());
        startActivityForResult(editBottleActivity, EDIT_BOTTLE);
    }

    public void onAddRatingClick(View v) {
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
                RatingsFragment ratingsFragment = (RatingsFragment) getFragmentManager().findFragmentById(R.id.fragment_rating_list);
                //TODO: REFRESH RATINGS
                ratingsFragment.refreshRatingList(getController().getCurrentBottleId());
                break;
        }
    }
}
