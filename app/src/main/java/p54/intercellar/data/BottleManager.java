package p54.intercellar.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import p54.intercellar.model.Bottle;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;

/**
 * Created by sreiss on 17/01/15.
 */
public class BottleManager extends InterCellarManager<Bottle> {
    private ChateauManager chateauManager;
    private RatingManager ratingManager;

    public BottleManager (InterCellarDatabase databaseHelper) {
        super(databaseHelper);
        this.chateauManager = new ChateauManager(databaseHelper);
        this.ratingManager = new RatingManager(databaseHelper);
    }

    public Bottle update(Bottle bottle) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = buildBottleContentValues(databaseHelper, bottle);

        String whereClause = databaseHelper.COMMON_KEY_ID + " = ?";
        String[] whereArgs = { String.valueOf(bottle.getId()) };
        database.update(databaseHelper.TABLE_BOTTLE, values, whereClause, whereArgs);

        bottle = saveBottleRatings(databaseHelper, database, bottle);

        return bottle;
    }

    public Bottle create(Bottle bottle) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = buildBottleContentValues(databaseHelper, bottle);

        long id = database.insert(databaseHelper.TABLE_BOTTLE, null, values);
        bottle.setId(id);

        bottle = saveBottleRatings(databaseHelper, database, bottle);

        return bottle;
    }

    private Bottle saveBottleRatings(InterCellarDatabase databaseHelper, SQLiteDatabase database, Bottle bottle) {
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

    public List<Bottle> findByShelfId(long shelfId)
    {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();


        Cursor cursor = database.query(databaseHelper.TABLE_BOTTLE, null, null, null, null, null, null);

       /* String sql = "SELECT b.* FROM "
                + databaseHelper.TABLE_BOTTLE + " AS b, "
                + databaseHelper.TABLE_SHELF_BOTTLE + " AS sb "
                + "WHERE sb." + databaseHelper.SHELF_BOTTLE_KEY_SHELF_ID + " = ? "
                + "AND sb." + databaseHelper.BOTTLE_SHELF_KEY_SHELF_ID+ " = b." + databaseHelper.COMMON_KEY_ID;

        String[] selectionArgs = new String[] {
                Long.toString(shelfId)
        };
        Cursor cursor = database.rawQuery(sql, selectionArgs);
*/
        List<Bottle> bottleList = new ArrayList<Bottle>();
        while(cursor.moveToNext()) {

            Bottle shelf = buildBottle(databaseHelper, cursor);
            bottleList.add(shelf);
        }

        cursor.close();

        return bottleList;
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
            bottle = buildBottle(databaseHelper, cursor);
            cursor.close();
        } else {
            bottle = null;
        }

        return bottle;
    }

    public List<Bottle> findAll() {
        List<Bottle> bottleList = new ArrayList<Bottle>();
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.query(databaseHelper.TABLE_BOTTLE, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Bottle bottle = buildBottle(databaseHelper, cursor);
            bottleList.add(bottle);
        }

        cursor.close();

        return bottleList;
        // TODO : Find all with ratings and chateau
    }

    public List<Bottle> getBottlesLike(String search) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<Bottle> bottleList = new ArrayList<Bottle>();

        String[] selectionArgs = new String[1];
        for (int i = 0; i < 1; i += 1) {
            selectionArgs[i] = "%" + search + "%";
        }

        // TODO : make search work
        String sql = "SELECT * FROM " + databaseHelper.TABLE_BOTTLE + " "
                + "WHERE " + databaseHelper.BOTTLE_KEY_NAME + " LIKE ?";
        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            bottleList.add(buildBottle(databaseHelper, cursor));
        }

        cursor.close();

        return bottleList;
    }

    public Bottle findBottleByBarCode(String barCode) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String[] selectionArgs = new String[]{barCode};
        Cursor cursor = database.query(databaseHelper.TABLE_BOTTLE, null, databaseHelper.BOTTLE_KEY_SCAN_CONTENT + " = ?", selectionArgs, null, null, null);

        Bottle bottle = null;
        if (cursor.moveToFirst()) {
            bottle = buildBottle(databaseHelper, cursor);
        }

        cursor.close();

        return bottle;
    }

    public int count() {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String sql = "SELECT COUNT(*) FROM " + databaseHelper.TABLE_BOTTLE;
        Cursor cursor = database.rawQuery(sql, null);

        int count;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        } else {
            count = 0;
        }

        cursor.close();

        return count;
    }

    public int countByBarCode(String barCode) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String[] selectionArgs = new String[] {barCode};
        String sql = "SELECT COUNT(*) FROM " + databaseHelper.TABLE_BOTTLE
                + " WHERE " + databaseHelper.BOTTLE_KEY_SCAN_CONTENT + " = ?";
        Cursor cursor = database.rawQuery(sql, selectionArgs);

        int count;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        } else {
            count = 0;
        }

        return count;
    }

    public void delete (long id) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String[] whereArgs = new String[] {String.valueOf(id)};
        database.delete(databaseHelper.TABLE_BOTTLE_RATING, databaseHelper.BOTTLE_RATING_KEY_BOTTLE_ID + " = ?", whereArgs);
        database.delete(databaseHelper.TABLE_BOTTLE, databaseHelper.COMMON_KEY_ID + " = ?", whereArgs);
    }

    // region builders

    private Bottle buildBottle(InterCellarDatabase databaseHelper, Cursor cursor) {
        Bottle bottle = new Bottle();
        bottle.setId(cursor.getLong(cursor.getColumnIndex(databaseHelper.COMMON_KEY_ID)));
        bottle.setYear(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_YEAR)));
        bottle.setName(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_NAME)));
        bottle.setPrice(cursor.getFloat(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_PRICE)));
        bottle.setPicture(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_PICTURE)));
        bottle.setDescription(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_DESCRIPTION)));
        bottle.setType(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_TYPE)));
        bottle.setMarket(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_MARKET)));
        bottle.setCoordinates(cursor.getInt(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_COORDINATES)));
        bottle.setScanFormat(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_SCAN_FORMAT)));
        bottle.setScanContent(cursor.getString(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_SCAN_CONTENT)));

        Long chateauId = cursor.getLong(cursor.getColumnIndex(databaseHelper.BOTTLE_KEY_CHATEAU_ID));

        Chateau chateau = chateauManager.findById(chateauId);
        bottle.setChateau(chateau);

        List<Rating> ratingList = ratingManager.listRatingsByBottleId(bottle.getId());
        bottle.setRatingList(ratingList);

        return bottle;
    }

    private ContentValues buildBottleContentValues(InterCellarDatabase databaseHelper, Bottle bottle) {
        ContentValues values = new ContentValues();

        values.put(databaseHelper.BOTTLE_KEY_YEAR, bottle.getYear());
        values.put(databaseHelper.BOTTLE_KEY_NAME, bottle.getName());
        values.put(databaseHelper.BOTTLE_KEY_PRICE, bottle.getPrice());
        values.put(databaseHelper.BOTTLE_KEY_PICTURE, bottle.getPicture());
        values.put(databaseHelper.BOTTLE_KEY_DESCRIPTION, bottle.getDescription());
        values.put(databaseHelper.BOTTLE_KEY_TYPE, bottle.getType());
        values.put(databaseHelper.BOTTLE_KEY_MARKET, bottle.getMarket());
        values.put(databaseHelper.BOTTLE_KEY_COORDINATES, bottle.getCoordinates());
        values.put(databaseHelper.BOTTLE_KEY_SCAN_FORMAT, bottle.getScanFormat());
        values.put(databaseHelper.BOTTLE_KEY_SCAN_CONTENT, bottle.getScanContent());
        values.put(databaseHelper.BOTTLE_KEY_CHATEAU_ID, bottle.getChateau().getId());

        return values;
    }

    // end region
}
