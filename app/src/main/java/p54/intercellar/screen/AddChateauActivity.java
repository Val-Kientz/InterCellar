package p54.intercellar.screen;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import p54.intercellar.R;
import p54.intercellar.controller.ChateauController;
import p54.intercellar.model.Chateau;

public class AddChateauActivity extends InterCellarActivity<ChateauController> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chateau);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_chateau, menu);
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

    public void onAddChateauButtonPressed(View v) {
        Chateau chateau = new Chateau();

        String region = ((EditText) findViewById(R.id.text_edit_chateau_region)).getText().toString();
        String domain = ((EditText) findViewById(R.id.text_edit_chateau_domain)).getText().toString();

        chateau.setRegion(region);
        chateau.setDomain(domain);

        getController().createChateau(chateau);
    }
}
