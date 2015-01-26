package p54.intercellar.screen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;

public class BottleFormActivity extends InterCellarActivity<BottleController> {
    // region requestCodes
    private static final int TAKE_PICTURE = 1;
    private static final int CREATE_CHATEAU = 2;
    // endregion

    // region resultCodes
    private static final int BOTTLE_ADDED = 10;
    private static final int BOTTLE_EDITED = 11;
    // endregion

    private String pictureFileName = "";
    private Bottle currentBottle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long id = getIntent().getLongExtra("bottleId", -1);

        if (id != -1) {
            currentBottle = getController().getBottle(id);
            setContentView(R.layout.activity_bottle_form_edit);
            setTitle(R.string.edit_bottle);
        } else {
            setContentView(R.layout.activity_bottle_form_add);
            setTitle(R.string.add_a_bottle);
        }

        fillChateauSpinner();
    }

    private void fillChateauSpinner() {
        List<Chateau> chateauList = getController().getChateauList();
        Spinner selectChateau = (Spinner) findViewById(R.id.spinner_select_chateau);
        if (!chateauList.isEmpty()) {
            selectChateau.setAdapter(new ArrayAdapter<Chateau>(this,
                    R.layout.support_simple_spinner_dropdown_item, chateauList));
        } else {
            selectChateau.setVisibility(View.GONE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PICTURE:
                pictureFileName = getController().saveTakenPicture(this, data);
                Bitmap image = getController().getPicture(this, pictureFileName);
                ImageView imageView = (ImageView) findViewById(R.id.image_bottle_piture);
                imageView.setImageBitmap(image);
                break;
            case CREATE_CHATEAU:
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

    // region eventHandlers

    public void onAddClick(View v) {
        Bottle bottle = extractBottleData(new Bottle());

        getController().createBottle(bottle);
        setResult(BOTTLE_ADDED);
        finish();
    }

    public void onEditClick(View v) {
        Bottle bottle = extractBottleData(currentBottle);

        getController().updateBottle(bottle);
        setResult(BOTTLE_EDITED);
        finish();
    }

    public void onAddChateauClick(View v) {
        Intent addChateauActivity = new Intent(this, AddChateauActivity.class);
        startActivityForResult(addChateauActivity, CREATE_CHATEAU);
    }

    public void onPictureClick(View v) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, TAKE_PICTURE);
    }

    // endregion
}
