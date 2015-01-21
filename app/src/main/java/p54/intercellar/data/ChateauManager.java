package p54.intercellar.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import p54.intercellar.model.Chateau;

/**
 * Created by sreiss on 17/01/15.
 */
public class ChateauManager extends InterCellarManager {
    public ChateauManager(InterCellarDatabase databaseHelper) {
        setDatabaseHelper(databaseHelper);
    }

    public Chateau create(Chateau chateau) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(databaseHelper.CHATEAU_KEY_DOMAIN, chateau.getDomain());
        values.put(databaseHelper.CHATEAU_KEY_REGION, chateau.getRegion());

        long id = database.insert(getDatabaseHelper().TABLE_CHATEAU, null, values);
        chateau.setId(id);

        return chateau;
    }

    public Chateau update(Chateau chateau) {
        SQLiteDatabase database = getDatabaseHelper().getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(getDatabaseHelper().CHATEAU_KEY_DOMAIN, chateau.getDomain());
        values.put(getDatabaseHelper().CHATEAU_KEY_REGION, chateau.getRegion());

        database.update(getDatabaseHelper().TABLE_CHATEAU, values, "id=?", new String[]{"" + chateau.getId()});

        return chateau;
    }

    public Chateau findById(long id) {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String[] selectionArgs = new String[] {
                Long.toString(id)
        };

        Cursor cursor = database.query(databaseHelper.TABLE_CHATEAU, null, databaseHelper.COMMON_KEY_ID + " = ?", selectionArgs, null, null, null);

        Chateau chateau;
        if (cursor.moveToFirst()) {
            chateau = new Chateau();
            chateau.setId(cursor.getLong(cursor.getColumnIndex(databaseHelper.COMMON_KEY_ID)));
            chateau.setDomain(cursor.getString(cursor.getColumnIndex(databaseHelper.CHATEAU_KEY_DOMAIN)));
            chateau.setRegion(cursor.getString(cursor.getColumnIndex(databaseHelper.CHATEAU_KEY_REGION)));
        } else {
            chateau = null;
        }

        cursor.close();

        return chateau;
    }
}