package p54.intercellar.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import p54.intercellar.model.Cellar;
import p54.intercellar.model.Shelf;

/**
 * Created by val on 23/01/2015.
 */
public class CellarManager extends InterCellarManager
{
    ShelfManager shelfManager;

    public CellarManager(InterCellarDatabase databaseHelper)
    {
        super(databaseHelper);
        shelfManager = new ShelfManager(databaseHelper);
    }

    public Cellar create(Cellar cellar)
    {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(databaseHelper.CELLAR_KEY_NAME, cellar.getName() );

        long id = database.insert(getDatabaseHelper().TABLE_CELLAR, null, values);
        cellar.setId(id);

        for (int i = 0; i < cellar.getShelfList().size(); i += 1) {
            Shelf shelf = cellar.getShelfList().get(i);
            if (shelf.getId() > 0) {
                shelf = shelfManager.update(shelf);
            } else {
                shelf = shelfManager.create(shelf);
            }
            ContentValues cellarShelvesContent = new ContentValues();
            cellarShelvesContent.put(databaseHelper.CELLAR_SHELF_KEY_CELLAR_ID, cellar.getId());
            cellarShelvesContent.put(databaseHelper.CELLAR_SHELF_KEY_SHELF_ID, shelf.getId());

            cellar.getShelfList().set(i, shelf);
        }

        return cellar;
    }
    public Cellar update(Cellar cellar)
    {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(databaseHelper.CELLAR_KEY_NAME, cellar.getName() );

        database.update(databaseHelper.TABLE_CELLAR, values, "id=?", new String[]{"" + cellar.getId()});

        return cellar;
    }
    public Cellar findById(long id)
    {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = getDatabaseHelper().getWritableDatabase();

        String[] selectionArgs = new String[] {
                Long.toString(id)
        };
        Cursor cursor = database.query(databaseHelper.TABLE_CELLAR, null, databaseHelper.COMMON_KEY_ID + " = ?", selectionArgs, null, null, null);

        Cellar cellar;
        if (cursor.moveToFirst()) {
            cellar = buildCellar(databaseHelper, cursor);
            cursor.close();
        } else {
            cellar = null;
        }

        return cellar;
    }
    public List<Cellar> findAll()
    {
        List<Cellar> cellarList = new ArrayList<Cellar>();
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.query(databaseHelper.TABLE_CELLAR, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Cellar cellar = buildCellar(databaseHelper, cursor);
            cellarList.add(cellar);
        }

        cursor.close();

        return cellarList;
    }
    private Cellar buildCellar(InterCellarDatabase databaseHelper, Cursor cursor)
    {
        Cellar cellar = new Cellar();
        cellar.setId(cursor.getLong(cursor.getColumnIndex(databaseHelper.COMMON_KEY_ID)));
        cellar.setName(cursor.getString(cursor.getColumnIndex(databaseHelper.CELLAR_KEY_NAME)));

        List<Shelf> shelfList = shelfManager.shelvesByCellarId(cellar.getId());

        return cellar;
    }
}
