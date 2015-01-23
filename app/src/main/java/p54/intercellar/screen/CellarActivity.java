package p54.intercellar.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import p54.intercellar.R;
import p54.intercellar.controller.CellarController;
import p54.intercellar.view.CellarDetailsFragment;

public class CellarActivity extends InterCellarActivity<CellarController> implements CellarFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cellar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cellar, menu);
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
    public void onFragmentInteraction(long id)
    {
        CellarDetailsFragment cellarDetailsFragment = (CellarDetailsFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_cellar_land);

        if (cellarDetailsFragment == null) {
            Intent bottleDetailsActivity = new Intent(this, BottleDetailsActivity.class);
            bottleDetailsActivity.putExtra("id", id);
            startActivity(bottleDetailsActivity);
        } else {
            //cellarDetailsFragment.showBottleDetails(id);
        }
    }
}
