package p54.intercellar.screen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;
import p54.intercellar.model.Shelf;

public class AddBottleActivity extends InterCellarActivity<BottleController> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bottle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_bottle, menu);
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

    public void onAddButtonBottlePressed(View v) {
        Bottle bottle = new Bottle();

        String name = ((EditText) findViewById(R.id.edit_text_bottle_name)).getText().toString();
        String year = ((EditText) findViewById(R.id.edit_text_bottle_year)).getText().toString();
        float price = 10.2F;
        String picture = "";
        String description = ((EditText) findViewById(R.id.edit_text_bottle_description)).getText().toString();
        String type = ((EditText) findViewById(R.id.edit_text_bottle_type)).getText().toString();
        String market = ((EditText) findViewById(R.id.edit_text_bottle_market)).getText().toString();

        bottle.setName(name);
        bottle.setYear(year);
        bottle.setPrice(price);
        bottle.setPicture(picture);
        bottle.setDescription(description);
        bottle.setType(type);
        bottle.setMarket(market);

        Chateau chateau = new Chateau();
        chateau.setRegion("Test");
        chateau.setDomain("Domaine Test");
        bottle.setChateau(chateau);

        List<Rating> ratingList = new ArrayList<>();
        bottle.setRatingList(ratingList);

        getController().createBottle(bottle);
        Toast.makeText(this, R.string.bottle_successfully_added, Toast.LENGTH_LONG);
        Intent bottleActivity = new Intent(this, BottleActivity.class);
        startActivity(bottleActivity);
    }
}
