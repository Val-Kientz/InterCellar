package p54.intercellar.screen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.view.BottleDetailsFragment;
import p54.intercellar.view.InterCellarFormFragment;
import p54.intercellar.view.RatingFragment;
import p54.intercellar.view.YesCancelAlert;

public class BottleDetailsActivity extends InterCellarActivity<BottleController> implements InterCellarFormFragment.OnFormReady, InterCellarFormFragment.OnFormDestroy {
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

    public void onDeleteClick(MenuItem menuItem) {
        YesCancelAlert alert = new YesCancelAlert(this,
                getString(R.string.are_you_sure),
                getString(R.string.delete_bottle),
                getString(R.string.yes),
                getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getController().deleteBottle(getController().getCurrentBottleId());

                        Intent bottleList = new Intent(BottleDetailsActivity.this, BottleActivity.class);
                        bottleList.putExtra("bottleDeleted", true);
                        startActivity(bottleList);
                    }
                }
        );

        alert.show();
    }

    @Override
    public void onFormReady(String fragmentClass) {
        Toast.makeText(this, fragmentClass, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFormDestroy(String fragmentClass) {
        Toast.makeText(this, fragmentClass, Toast.LENGTH_LONG).show();
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
