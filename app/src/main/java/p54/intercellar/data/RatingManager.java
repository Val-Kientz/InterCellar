package p54.intercellar.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import p54.intercellar.model.Rating;

/**
 * Created by sreiss on 17/01/15.
 */
public class RatingManager extends InterCellarManager {
    public RatingManager(InterCellarDatabase databaseHelper) {
        setDatabaseHelper(databaseHelper);
    }

    public Rating create(Rating rating) {
        SQLiteDatabase database = getDatabaseHelper().getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(getDatabaseHelper().RATING_KEY_COMMENT, rating.getComment());
        values.put(getDatabaseHelper().RATING_KEY_DATE, rating.getDate().toString());
        values.put(getDatabaseHelper().RATING_KEY_RATE, rating.getRate());

        long id = database.insert(getDatabaseHelper().TABLE_RATING, null, values);
        rating.setId(id);

        return rating;
    }

    public Rating update(Rating rating) {
        SQLiteDatabase database = getDatabaseHelper().getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(getDatabaseHelper().RATING_KEY_COMMENT, rating.getComment());
        values.put(getDatabaseHelper().RATING_KEY_RATE, rating.getRate());

        database.update(getDatabaseHelper().TABLE_RATING, values, "id=?", new String[]{"" + rating.getId()});

        return rating;
    }
}
