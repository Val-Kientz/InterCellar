package p54.intercellar.screen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Bottle;

public class BottleActivity extends InterCellarActivity<BottleController> implements BottleFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottle);
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
            bottleDetailsActivity.putExtra("id", id);
            startActivity(bottleDetailsActivity);
        } else {
            bottleDetailsFragment.showBottleDetails(id);
        }
    }
}
