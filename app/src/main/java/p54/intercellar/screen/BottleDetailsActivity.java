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

public class BottleDetailsActivity extends InterCellarActivity<BottleController> {
    private static final int EDIT_BOTTLE = 2;

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

    public void onEditButtonPressed(View v) {
        Intent editBottleActivity = new Intent(this, BottleFormActivity.class);
        editBottleActivity.putExtra("action", "edit");
        editBottleActivity.putExtra("bottleId", getController().getCurrentBottleId());
        startActivityForResult(editBottleActivity, EDIT_BOTTLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case EDIT_BOTTLE:
                Toast.makeText(this, R.string.bottle_successfully_edited, Toast.LENGTH_LONG);
                break;
        }
    }
}
