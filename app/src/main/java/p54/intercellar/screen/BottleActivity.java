package p54.intercellar.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Bottle;
import p54.intercellar.view.BottleDetailsFragment;
import p54.intercellar.view.BottleFragment;

public class BottleActivity extends InterCellarActivity<BottleController> implements BottleFragment.OnFragmentInteractionListener {
    private static final int ADD_BOTTLE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottle);

        BottleDetailsFragment bottleDetailsFragment = (BottleDetailsFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_bottle_details_land);


        if (bottleDetailsFragment != null) {
            List<Bottle> bottleList = getController().getBottleList();
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
    public void onFragmentInteraction(long id) {
        BottleDetailsFragment bottleDetailsFragment = (BottleDetailsFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_bottle_details_land);

        if (bottleDetailsFragment == null) {
            Intent bottleDetailsActivity = new Intent(this, BottleDetailsActivity.class);
            bottleDetailsActivity.putExtra("bottleId", id);
            startActivity(bottleDetailsActivity);
        } else {
            bottleDetailsFragment.showBottleDetails(id);
        }
    }

    public void onAddButtonPressed(View v) {
        Intent addBottleActivity = new Intent(this, BottleFormActivity.class);
        startActivityForResult(addBottleActivity, ADD_BOTTLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ADD_BOTTLE:
                Toast.makeText(this, R.string.bottle_successfully_added, Toast.LENGTH_LONG);
                break;
        }

        BottleFragment bottleFragment = (BottleFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle);
        bottleFragment.refreshList();
    }
}
