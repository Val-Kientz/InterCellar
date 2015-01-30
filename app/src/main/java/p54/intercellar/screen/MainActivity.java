package p54.intercellar.screen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.controller.CellarController;
import p54.intercellar.controller.ChateauController;
import p54.intercellar.controller.InterCellarController;
import p54.intercellar.controller.ShelfController;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Cellar;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;
import p54.intercellar.model.Shelf;


public class MainActivity extends InterCellarActivity<BottleController> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void launchBottleActivity(View v) {
        Intent bottleActivity = new Intent(this, BottleActivity.class);
        startActivity(bottleActivity);
    }

    public void launchCellarActivity(View v) {
        Intent cellarActivity = new Intent(this, CellarActivity.class);
        startActivity(cellarActivity);
    }

    public void createDummies()
    {
        ShelfController sc = new ShelfController(this);
        CellarController cc = new CellarController(this);
        ChateauController chc = new ChateauController(this);


        List<Bottle> bottleList = new ArrayList<Bottle>();
        List<Shelf> shelfList = new ArrayList<Shelf>();

        Chateau ch = new Chateau();
        ch.setDomain("Chateau Bonvin");
        ch.setRegion("D'Alsace");

        ch.setId(chc.createChateau(ch).getId());


        for(int cpt=1 ; cpt<9; cpt++)
        {
            Bottle b = new Bottle();
            b.setName("Bouteille " + cpt);
            b.setDescription("Description de la bouteille " + cpt);
            b.setMarket("Lidle " + cpt);
            b.setPrice(cpt);
            b.setChateau(ch);
            b.setCoordinates(cpt);
            b.setYear("199" + cpt);
            b.setType("Type" + cpt);
            b.setRatingList(new ArrayList<Rating>());

            b.setId(getController().createBottle(b).getId());

            bottleList.add(b);
        }


        Shelf s1 = new Shelf();
        s1.setCapacity(10);
        s1.setWidth(10);
        s1.setHeight(10);
        s1.setBottleList(bottleList);
        s1.setId(sc.createShelf(s1).getId());
        shelfList.add(s1);

        Shelf s2 = new Shelf();
        s2.setCapacity(10);
        s2.setWidth(10);
        s2.setHeight(10);
        s2.setBottleList(bottleList);
        s2.setId(sc.createShelf(s2).getId());
        shelfList.add(s2);

        Shelf s3 = new Shelf();
        s3.setCapacity(10);
        s3.setWidth(10);
        s3.setHeight(10);
        s3.setBottleList(bottleList);
        s3.setId(sc.createShelf(s3).getId());
        shelfList.add(s3);


        Cellar c1 = new Cellar();
        c1.setName("My Epic Cellar");
        c1.setShelfList(shelfList);
        c1.setId(cc.createCellar(c1).getId());
        
        Cellar c2 = new Cellar();
        c2.setName("My Epic Cellar");
        c2.setShelfList(shelfList);
        c2.setId(cc.createCellar(c1).getId());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult barCode = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

                if (barCode != null) {
                    Bottle bottle = getController().getBottleByBarCode(barCode.getContents());
                    if (bottle != null) {
                        Intent bottleDetails = new Intent(this, BottleDetailsActivity.class);
                        bottleDetails.putExtra("bottleId", bottle.getId());
                        startActivity(bottleDetails);
                    } else {
                        Toast.makeText(this, R.string.no_bottle_found_with_that_barcode, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.scan_error), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public void onSearchBarCodeClick(View v) {
        IntentIntegrator scanIntent = new IntentIntegrator(this);
        scanIntent.initiateScan();
    }

    public void onDummiesClick(View v) {
        createDummies();
        Toast.makeText(this, R.string.dummies_generated, Toast.LENGTH_SHORT).show();
    }
}
