package p54.intercellar.controller;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import p54.intercellar.data.BottleManager;
import p54.intercellar.data.InterCellarDatabase;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;

/**
 * Created by Simon on 09/01/2015.
 */
public class BottleController extends InterCellarController<BottleManager> {
    private ChateauController chateauController;

    public BottleController(Context context) {
        super(context);
        chateauController = new ChateauController(context);

        /*
        Chateau chateau = new Chateau();
        chateau.setDomain("TestDomain");
        chateau.setRegion("TestRegion");

        List<Rating> ratingList = new ArrayList<Rating>();
        Rating rating1 = new Rating();
        rating1.setRate(34);
        rating1.setDate(new Date());
        rating1.setComment("Test");
        Rating rating2 = new Rating();
        rating2.setRate(176);
        rating2.setDate(new Date());
        rating2.setComment("Re test");
        ratingList.add(rating1);
        ratingList.add(rating2);

        Bottle bottle = new Bottle();
        bottle.setYear("1998");
        bottle.setName("Test");
        bottle.setPrice((float) 10.0);
        bottle.setPicture("");
        bottle.setDescription("");
        bottle.setType("Rouge");
        bottle.setMarket("St Jean");
        bottle.setCoordinates(1);
        bottle.setChateau(chateau);
        bottle.setRatingList(ratingList);

        getManager().create(bottle);
        */
    }

    public Bottle getBottle(long id) {
        return getManager().findById(id);
    }

    public List<Bottle> getBottleList() {
        return getManager().findAll();
    }

    public Bottle createBottle(Bottle bottle) {
        return getManager().create(bottle);
    }

    public List<Chateau> getChateauList() {
        return chateauController.getChateauList();
    }
}
