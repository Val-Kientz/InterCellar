package p54.intercellar.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import p54.intercellar.model.Rating;

/**
 * Created by sreiss on 17/01/15.
 */
public class RatingManager extends InterCellarManager {
    public RatingManager(InterCellarDatabase databaseHelper) {
        super(databaseHelper);
    }

    public Rating create(Rating rating) {
        SQLiteDatabase database = getDatabaseHelper().getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(getDatabaseHelper().RATING_KEY_COMMENT, rating.getComment());
        values.put(getDatabaseHelper().RATING_KEY_DATE, Long.toString(rating.getDate().getTime()));
        values.put(getDatabaseHelper().RATING_KEY_RATE, rating.getRate());

        long id = database.insert(getDatabaseHelper().TABLE_RATING, null, values);
        rating.setId(id);

        return rating;
    }

    public Rating update(Rating rating) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(databaseHelper.RATING_KEY_COMMENT, rating.getComment());
        values.put(databaseHelper.RATING_KEY_RATE, rating.getRate());

        database.update(databaseHelper.TABLE_RATING, values, "id=?", new String[]{"" + rating.getId()});

        return rating;
    }

    public List<Rating> listRatingsByBottleId(long bottleId) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String sql = "SELECT r.* FROM "
            + databaseHelper.TABLE_RATING + " AS r, "
            + databaseHelper.TABLE_BOTTLE_RATING + " AS br "
            + "WHERE br." + databaseHelper.BOTTLE_RATING_KEY_BOTTLE_ID + " = ? "
            + "AND br." + databaseHelper.BOTTLE_RATING_KEY_RATING_ID + " = r." + databaseHelper.COMMON_KEY_ID + " "
            + "ORDER BY r." + databaseHelper.RATING_KEY_DATE;
        String[] selectionArgs = new String[] {
          Long.toString(bottleId)
        };
        Cursor cursor = database.rawQuery(sql, selectionArgs);

        List<Rating> ratingList = new ArrayList<Rating>();
        while(cursor.moveToNext()) {

            Rating rating = buildRating(databaseHelper, cursor);
            ratingList.add(rating);
        }

        cursor.close();

        return ratingList;
    }
    private Rating buildRating(InterCellarDatabase databaseHelper, Cursor cursor)
    {
        Rating rating = new Rating();
        rating.setId(cursor.getLong(cursor.getColumnIndex(databaseHelper.COMMON_KEY_ID)));
        rating.setComment(cursor.getString(cursor.getColumnIndex(databaseHelper.RATING_KEY_COMMENT)));
        Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex(databaseHelper.RATING_KEY_DATE))));
        rating.setDate(date);
        rating.setRate(cursor.getDouble(cursor.getColumnIndex(databaseHelper.RATING_KEY_RATE)));

        return rating;
    }
}
