package p54.intercellar.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import p54.intercellar.model.Bottle;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;

/**
 * Created by sreiss on 17/01/15.
 */
public class BottleManager extends InterCellarManager {
    private ChateauManager chateauManager;
    private RatingManager ratingManager;

    public BottleManager (InterCellarDatabase databaseHelper) {
        setDatabaseHelper(databaseHelper);
        this.chateauManager = new ChateauManager(databaseHelper);
        this.ratingManager = new RatingManager(databaseHelper);
    }

    public Bottle create(Bottle bottle) {
        SQLiteDatabase database = getDatabaseHelper().getWritableDatabase();

        String coordinates = bottle.getCoordinates()[0] + ";" + bottle.getCoordinates()[1];

        ContentValues values = new ContentValues();
        values.put(getDatabaseHelper().BOTTLE_KEY_YEAR, bottle.getYear());
        values.put(getDatabaseHelper().BOTTLE_KEY_NAME, bottle.getName());
        values.put(getDatabaseHelper().BOTTLE_KEY_PRICE, bottle.getPrice());
        values.put(getDatabaseHelper().BOTTLE_KEY_PICTURE, bottle.getPicture());
        values.put(getDatabaseHelper().BOTTLE_KEY_DESCRIPTION, bottle.getDescription());
        values.put(getDatabaseHelper().BOTTLE_KEY_TYPE, bottle.getType());
        values.put(getDatabaseHelper().BOTTLE_KEY_MARKET, bottle.getMarket());
        values.put(getDatabaseHelper().BOTTLE_KEY_COORDINATES, coordinates);

        long id = database.insert(getDatabaseHelper().TABLE_BOTTLE, null, values);
        bottle.setId(id);

        if (bottle.getChateau().getId() > 0) {
            chateauManager.update(bottle.getChateau());
        } else {
            chateauManager.create(bottle.getChateau());
        }

        for (int i = 0; i < bottle.getRatingList().size(); i += 1) {
            Rating rating = bottle.getRatingList().get(i);
            if (rating.getId() > 0) {
                rating = ratingManager.update(rating);
            } else {
                rating = ratingManager.create(rating);
            }
            bottle.getRatingList().set(i, rating);
        }

        return bottle;
    }
}
