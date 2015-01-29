package p54.intercellar.screen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.controller.CellarController;
import p54.intercellar.controller.ShelfController;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Cellar;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;
import p54.intercellar.model.Shelf;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDummies();

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
        BottleController bc = new BottleController(this);
        ShelfController sc = new ShelfController(this);
        CellarController cc = new CellarController(this);

        List<Bottle> bottleList = new ArrayList<Bottle>();
        List<Shelf> shelfList = new ArrayList<Shelf>();


        for(int cpt=1 ; cpt<9; cpt++)
        {
            Bottle b = new Bottle();
            b.setName("Bouteille " + cpt);
            b.setDescription("Description de la bouteille " + cpt);
            b.setMarket("Lidle " + cpt);
            b.setPrice(cpt);
            b.setChateau(new Chateau());
            b.setCoordinates(cpt);
            b.setYear("199" + cpt);
            b.setType("Type" + cpt);
            b.setRatingList(new ArrayList<Rating>());

            b.setId(bc.createBottle(b).getId());

            bottleList.add(b);
        }
        int prout=0;
        for(int cpt=1; cpt<4; cpt++)
        {
            Shelf s = new Shelf();
            s.setCapacity(10);
            s.setWidth(10);
            s.setHeight(10);
            if(cpt == 1 || cpt==3)
            {
                s.setBottleList(bottleList);
            }

            s.setId(sc.createShelf(s).getId());
            shelfList.add(s);
        }

        Cellar c = new Cellar();
        c.setName("My Epic Cellar");
        c.setShelfList(shelfList);
        c.setId(cc.createCellar(c).getId());

    }
}
