package p54.intercellar.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

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
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = getDatabaseHelper().getWritableDatabase();

        String coordinates = bottle.getCoordinates()[0] + ";" + bottle.getCoordinates()[1];

        ContentValues values = new ContentValues();
        values.put(databaseHelper.BOTTLE_KEY_YEAR, bottle.getYear());
        values.put(databaseHelper.BOTTLE_KEY_NAME, bottle.getName());
        values.put(databaseHelper.BOTTLE_KEY_PRICE, bottle.getPrice());
        values.put(databaseHelper.BOTTLE_KEY_PICTURE, bottle.getPicture());
        values.put(databaseHelper.BOTTLE_KEY_DESCRIPTION, bottle.getDescription());
        values.put(databaseHelper.BOTTLE_KEY_TYPE, bottle.getType());
        values.put(databaseHelper.BOTTLE_KEY_MARKET, bottle.getMarket());
        values.put(databaseHelper.BOTTLE_KEY_COORDINATES, coordinates);
        if (bottle.getChateau().getId() > 0) {
            chateauManager.update(bottle.getChateau());
        } else {
            chateauManager.create(bottle.getChateau());
        }
        values.put(databaseHelper.BOTTLE_KEY_CHATEAU_ID, bottle.getChateau().getId());

        long id = database.insert(databaseHelper.TABLE_BOTTLE, null, values);
        bottle.setId(id);

        for (int i = 0; i < bottle.getRatingList().size(); i += 1) {
            Rating rating = bottle.getRatingList().get(i);
            if (rating.getId() > 0) {
                rating = ratingManager.update(rating);
            } else {
                rating = ratingManager.create(rating);
            }

            ContentValues bottleRatingContent = new ContentValues();
            bottleRatingContent.put(databaseHelper.BOTTLE_RATING_KEY_BOTTLE_ID, bottle.getId());
            bottleRatingContent.put(databaseHelper.BOTTLE_RATING_KEY_RATING_ID, rating.getId());
            database.insert(databaseHelper.TABLE_BOTTLE_RATING, null, bottleRatingContent);

            bottle.getRatingList().set(i, rating);
        }

        return bottle;
    }

    public Bottle findById(long id) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = getDatabaseHelper().getWritableDatabase();

        String[] selectionArgs = new String[] {
          Long.toString(id)
        };
        Cursor cursor = database.query(databaseHelper.TABLE_BOTTLE, null, databaseHelper.COMMON_KEY_ID + " = ?", selectionArgs, null, null, null);

        Bottle bottle;
        if (cursor.moveToFirst()) {
            bottle = new Bottle();
            bottle.setId(cursor.getLong(cursor.getColumnIndex(databaseHelper.COMMON_KEY_ID)));
            bottle.setYear(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_YEAR)));
            bottle.setName(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_NAME)));
            bottle.setPrice(cursor.getFloat(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_PRICE)));
            bottle.setPicture(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_PICTURE)));
            bottle.setDescription(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_DESCRIPTION)));
            bottle.setType(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_TYPE)));
            bottle.setMarket(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_MARKET)));
            String[] coordStrings = cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_COORDINATES)).split(";");
            bottle.setCoordinates(new int[]{Integer.parseInt(coordStrings[0]), Integer.parseInt(coordStrings[1])});

            Long chateauId = cursor.getLong(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_CHATEAU_ID));

            cursor.close();

            Chateau chateau = chateauManager.findById(chateauId);
            bottle.setChateau(chateau);

            List<Rating> ratingList = ratingManager.listRatingsByBottleId(bottle.getId());
            bottle.setRatingList(ratingList);
        } else {
            bottle = null;
        }

        return bottle;
    }
}