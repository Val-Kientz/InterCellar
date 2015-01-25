package p54.intercellar.screen;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.controller.InterCellarController;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;
import p54.intercellar.model.Shelf;
import p54.intercellar.view.BottleDetailsFragment;

public class AddBottleActivity extends InterCellarActivity<BottleController> {
    private static final int PICTURE_TAKEN = 1;
    private static final int CHATEAU_CREATED = 2;
    private static final int BOTTLE_ADDED = 10;
    private static final int BOTTLE_EDITED = 11;
    private String pictureFileName = "";
    private Bottle currentBottle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bottle);

        List<Chateau> chateauList = getController().getChateauList();
        Spinner selectChateau = (Spinner) findViewById(R.id.spinner_select_chateau);
        if (!chateauList.isEmpty()) {
            selectChateau.setAdapter(new ArrayAdapter<Chateau>(this,
                    R.layout.support_simple_spinner_dropdown_item, chateauList));
        } else {
            selectChateau.setVisibility(View.GONE);
        }

        String action = getIntent().getStringExtra("action");
        if (action.equals("edit")) {
            long id = getIntent().getLongExtra("bottleId", -1);
            currentBottle = getController().getBottle(id);
        }
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

    public void onSelectImagePressed(View v) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, PICTURE_TAKEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICTURE_TAKEN:
                pictureFileName = getController().saveTakenPicture(this, data);
                Bitmap image = getController().getPicture(this, pictureFileName);
                ImageView imageView = (ImageView) findViewById(R.id.image_bottle_piture);
                imageView.setImageBitmap(image);
                break;
            case CHATEAU_CREATED:
                if (resultCode == AddChateauActivity.CHATEAU_CREATED) {
                    List<Chateau> chateauList = getController().getChateauList();

                    ((Spinner) findViewById(R.id.spinner_select_chateau)).setAdapter(new ArrayAdapter<Chateau>(this,
                            R.layout.support_simple_spinner_dropdown_item, chateauList));
                } else {
                    Toast.makeText(this, R.string.an_error_occured_while_creating_chateau, Toast.LENGTH_LONG);
                }
                break;
        }
    }

    public void onAddChateauButtonPressed(View v) {
        Intent addChateauActivity = new Intent(this, AddChateauActivity.class);
        startActivityForResult(addChateauActivity, CHATEAU_CREATED);
    }

    public void onAddBottleButtonPressed(View v) {
        Bottle bottle = extractBottleData(new Bottle());

        getController().createBottle(bottle);
        setResult(BOTTLE_ADDED);
        finish();
    }

    public void onEditBottleButtonPressed(View v) {
        Bottle bottle = extractBottleData(currentBottle);

        getController().updateBottle(bottle);
        setResult(BOTTLE_EDITED);
        finish();
    }

    private Bottle extractBottleData(Bottle bottle) {
        String name = ((EditText) findViewById(R.id.edit_text_bottle_name)).getText().toString();
        String year = ((EditText) findViewById(R.id.edit_text_bottle_year)).getText().toString();
        String priceString = ((EditText) findViewById(R.id.edit_text_bottle_price)).getText().toString();
        double price;
        if (!priceString.equals("")) {
            price = Double.parseDouble(priceString);
        } else {
            price = 0;
        }
        String description = ((EditText) findViewById(R.id.edit_text_bottle_description)).getText().toString();
        String type = ((EditText) findViewById(R.id.edit_text_bottle_type)).getText().toString();
        String market = ((EditText) findViewById(R.id.edit_text_bottle_market)).getText().toString();

        if (!pictureFileName.equals("")) {
            bottle.setPicture(pictureFileName);
        } else {
            bottle.setPicture("");
        }

        bottle.setName(name);
        bottle.setYear(year);
        bottle.setPrice(price);
        bottle.setDescription(description);
        bottle.setType(type);
        bottle.setMarket(market);
        bottle.setPicture(pictureFileName);

        Chateau chateau = (Chateau) ((Spinner) findViewById(R.id.spinner_select_chateau)).getSelectedItem();
        bottle.setChateau(chateau);

        List<Rating> ratingList = new ArrayList<>();
        bottle.setRatingList(ratingList);

        return bottle;
    }
}
