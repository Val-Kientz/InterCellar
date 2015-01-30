package p54.intercellar.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import p54.intercellar.R;
import p54.intercellar.controller.CellarController;
import p54.intercellar.model.Cellar;
import p54.intercellar.view.CellarDetailsFragment;
import p54.intercellar.view.CellarFragment;

public class CellarActivity extends InterCellarActivity<CellarController> implements CellarFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellar);

        if (findViewById(R.id.fragment_cellar_details_land) != null)
        {
            List<Cellar> cellarList = getController().getCellarList();
            CellarDetailsFragment cellarDetailsFragment = (CellarDetailsFragment) getFragmentManager()
                    .findFragmentById(R.id.fragment_cellar_details_land);

            if (!cellarList.isEmpty())
            {
                getController().setCurrentCellarId(cellarList.get(0).getId());
                cellarDetailsFragment.showCellarDetails(getController().getCurrentCellarId());
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cellar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(long id)
    {
        CellarDetailsFragment cellarDetailsFragment = (CellarDetailsFragment) getFragmentManager()
                .findFragmentById(R.id.cellar_details);

        if (cellarDetailsFragment == null) {
            Intent cellarDetailsActivity = new Intent(this, CellarDetailsActivity.class);
            cellarDetailsActivity.putExtra("cellarId", id);
            startActivity(cellarDetailsActivity);
        } else
        {
            //cellarDetailsFragment.showCellarDetails(id);
        }
    }
}
