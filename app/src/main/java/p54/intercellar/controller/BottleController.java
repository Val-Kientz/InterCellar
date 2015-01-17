package p54.intercellar.controller;

import android.content.Context;

import java.util.ArrayList;

import p54.intercellar.data.BottleManager;
import p54.intercellar.data.InterCellarDatabase;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;

/**
 * Created by Simon on 09/01/2015.
 */
public class BottleController {
    private Context context;
    private BottleManager bottleManager;

    public BottleController(Context context) {
        this.context = context;
        InterCellarDatabase databaseHelper = new InterCellarDatabase(context);
        bottleManager = new BottleManager(databaseHelper);

        Chateau chateau = new Chateau();
        chateau.setDomain("TestDomain");
        chateau.setRegion("TestRegion");

        Bottle bottle = new Bottle();
        bottle.setYear("1998");
        bottle.setName("Test");
        bottle.setPrice((float) 10.0);
        bottle.setPicture("");
        bottle.setDescription("");
        bottle.setType("Rouge");
        bottle.setMarket("St Jean");
        bottle.setCoordinates(new int[]{1,1});
        bottle.setChateau(chateau);
        bottle.setRatingList(new ArrayList<Rating>());
    }
}
