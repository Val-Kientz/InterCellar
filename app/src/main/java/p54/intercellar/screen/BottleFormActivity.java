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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;
import p54.intercellar.view.BottleFormFragment;

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
            fillChateauSpinner();
            setBottleData(currentBottle);
        } else {
            setContentView(R.layout.activity_bottle_form_add);
            setTitle(R.string.add_a_bottle);
            fillChateauSpinner();
        }
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

    private void setBottleData(Bottle bottle) {
        Map<String, String> values = new HashMap<String, String>();
        values.put("name", bottle.getName());
        values.put("year", bottle.getYear());
        values.put("price", String.valueOf(bottle.getPrice()));
        values.put("description", bottle.getDescription());
        values.put("market", bottle.getMarket());
        values.put("type", bottle.getType());

        BottleFormFragment bottleFormFragment = (BottleFormFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle_form);
        bottleFormFragment.setValues(values);

        Spinner chateauSpinner = ((Spinner) findViewById(R.id.spinner_select_chateau));
        int chateauPosition = ((ArrayAdapter<Chateau>) chateauSpinner.getAdapter()).getPosition(bottle.getChateau());
        chateauSpinner.setSelection(chateauPosition);
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

                    Spinner chateauSpinner = ((Spinner) findViewById(R.id.spinner_select_chateau));
                    chateauSpinner.setAdapter(new ArrayAdapter<Chateau>(this,
                            R.layout.support_simple_spinner_dropdown_item, chateauList));
                    chateauSpinner.setSelection(chateauSpinner.getAdapter().getCount() - 1);
                    chateauSpinner.invalidate();

                } else {
                    Toast.makeText(this, R.string.an_error_occured_while_creating_chateau, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private Bottle extractBottleData(Bottle bottle, BottleFormFragment bottleFormFragment) {
        Map<String, String> values = bottleFormFragment.getValues();

        double price;
        if (!values.get("price").equals("")) {
            price = Double.parseDouble(values.get("price"));
        } else {
            price = 0;
        }

        if (!pictureFileName.equals("")) {
            bottle.setPicture(pictureFileName);
        } else {
            bottle.setPicture("");
        }

        bottle.setName(values.get("name"));
        bottle.setYear(values.get("year"));
        bottle.setPrice(price);
        bottle.setDescription(values.get("description"));
        bottle.setType(values.get("type"));
        bottle.setMarket(values.get("market"));
        bottle.setPicture(pictureFileName);

        Chateau chateau = (Chateau) ((Spinner) findViewById(R.id.spinner_select_chateau)).getSelectedItem();
        bottle.setChateau(chateau);

        List<Rating> ratingList = new ArrayList<>();
        bottle.setRatingList(ratingList);

        return bottle;
    }

    // region eventHandlers

    public void onAddClick(View v) {
        BottleFormFragment bottleFormFragment = (BottleFormFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle_form);

        if (bottleFormFragment.checkRequiredFields()) {
            Bottle bottle = extractBottleData(new Bottle(), bottleFormFragment);
            getController().createBottle(bottle);
            setResult(BOTTLE_ADDED);
            finish();
        }
    }

    public void onEditClick(View v) {
        BottleFormFragment bottleFormFragment = (BottleFormFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle_form);

        if (bottleFormFragment.checkRequiredFields()) {
            Bottle bottle = extractBottleData(currentBottle, bottleFormFragment);
            getController().updateBottle(bottle);
            setResult(BOTTLE_EDITED);
            finish();
        }
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
