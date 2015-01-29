package p54.intercellar.screen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.PersistableBundle;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import p54.intercellar.view.BottleFragment;
import p54.intercellar.view.InterCellarFormFragment;

public class BottleFormActivity extends InterCellarActivity<BottleController> implements BottleFormFragment.OnFormReady, InterCellarFormFragment.OnFormDestroy {
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
    private SharedPreferences sharedPreferences;
    private BottleFormFragment bottleFormFragment;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottleFormFragment = (BottleFormFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle_form);
        sharedPreferences = getSharedPreferences("currentBottle", MODE_PRIVATE);
        intent = getIntent();

        long id = getIntent().getLongExtra("bottleId", -1);

        if (id != -1) {
            currentBottle = getController().getBottle(id);
            setContentView(R.layout.activity_bottle_form_edit);
            setTitle(R.string.edit_bottle);
            fillChateauSpinner();
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

    public void onFormReady(String fragmentClass) {
        if (fragmentClass.equals(BottleFormFragment.class.getName())) {
            if (bottleFormFragment == null) {
                bottleFormFragment = (BottleFormFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle_form);
            }

            if (sharedPreferences.getAll().size() > 0 && intent.getClass().getName().equals(BottleFormActivity.class.getName())) {
                List<String> fieldNames = bottleFormFragment.getFieldNames();
                Map<String, String> values = new HashMap<String, String>();
                for (String field : fieldNames) {
                    String value = sharedPreferences.getString(field, "");
                    values.put(field, value);
                }
                bottleFormFragment.setValues(values);
                int chateauIndex = sharedPreferences.getInt("chateauIndex", 0);
                bottleFormFragment.setChateauIndex(chateauIndex);
            } else {
                if (currentBottle != null) {
                    Map<String, String> values = new HashMap<String, String>();
                    values.put("name", currentBottle.getName());
                    values.put("year", currentBottle.getYear());
                    values.put("price", String.valueOf(currentBottle.getPrice()));
                    values.put("description", currentBottle.getDescription());
                    values.put("market", currentBottle.getMarket());
                    values.put("type", currentBottle.getType());
                    values.put("scanContent", currentBottle.getScanContent());
                    values.put("scanFormat", currentBottle.getScanFormat());
                    bottleFormFragment.setValues(values);
                    bottleFormFragment.setChateauIndex(currentBottle.getChateau());
                    Bitmap picture = getController().getPicture(this, currentBottle.getPicture());
                    bottleFormFragment.setPicture(picture);
                }
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
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
            case IntentIntegrator.REQUEST_CODE:
                IntentResult barCode = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

                if (barCode != null) {
                    Map<String, String> values = new HashMap<String, String>();
                    values.put("scanContent", barCode.getContents());
                    values.put("scanFormat", barCode.getFormatName());
                    bottleFormFragment.setValues(values);
                } else {
                    Toast.makeText(this, getString(R.string.scan_error), Toast.LENGTH_SHORT).show();
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
        bottle.setScanContent(values.get("scanContent"));
        bottle.setScanFormat(values.get("scanFormat"));
        bottle.setPicture(pictureFileName);

        Chateau chateau = (Chateau) ((Spinner) findViewById(R.id.spinner_select_chateau)).getSelectedItem();
        bottle.setChateau(chateau);

        List<Rating> ratingList = new ArrayList<>();
        bottle.setRatingList(ratingList);

        return bottle;
    }

    @Override
    public void onFormDestory(String fragmentClass) {
        if (fragmentClass.equals(BottleFormFragment.class.getName())) {
            /*SharedPreferences.Editor editor = sharedPreferences.edit();
            Map<String, String> values = bottleFormFragment.getValues();
            for (String field : values.keySet()) {
                editor.putString(field, values.get(field));
            }
            // editor.putString("picture", pictureFileName);
            editor.putInt("chateauIndex", bottleFormFragment.getChateauIndex());

            editor.commit();*/
        }
    }

    // region eventHandlers

    public void onAddClick(View v) {
        if (bottleFormFragment.checkRequiredFields()) {
            Bottle bottle = extractBottleData(new Bottle(), bottleFormFragment);
            getController().createBottle(bottle);
            setResult(BOTTLE_ADDED);
            finish();
        }
    }

    public void onEditClick(View v) {
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

    public void onScanClick(View v) {
        IntentIntegrator scanIntent = new IntentIntegrator(this);
        scanIntent.initiateScan();
    }

    // endregion
}
