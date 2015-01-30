package p54.intercellar.screen;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import p54.intercellar.R;
import p54.intercellar.controller.CellarController;
import p54.intercellar.view.CellarDetailsFragment;
import p54.intercellar.view.InterCellarFormFragment;

public class CellarDetailsActivity extends InterCellarActivity<CellarController>  implements InterCellarFormFragment.OnFormReady, InterCellarFormFragment.OnFormDestroy{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cellar_details);

        long cellarID = getIntent().getLongExtra("cellarId", -1);
        getController().setCurrentCellarId(cellarID);

        setContentView(R.layout.activity_cellar_details);

        CellarDetailsFragment cellarDetailsFragment = (CellarDetailsFragment) getFragmentManager().findFragmentById(R.id.cellar_details);
        cellarDetailsFragment.showCellarDetails(getController().getCurrentCellarId());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cellar_details, menu);
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
    public void onFormDestroy(String fragmentClass) {

    }

    @Override
    public void onFormReady(String fragmentClass) {

    }
}
