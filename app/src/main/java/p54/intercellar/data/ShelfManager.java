package p54.intercellar.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import p54.intercellar.model.Bottle;
import p54.intercellar.model.Shelf;

/**
 * Created by val on 23/01/2015.
 */
public class ShelfManager extends InterCellarManager
{
    //CellarManager cellarManager;
    BottleManager bottleManager;

    public ShelfManager(InterCellarDatabase databaseHelper)
    {
        super(databaseHelper);

        //this.cellarManager = new CellarManager(databaseHelper);
        this.bottleManager = new BottleManager(databaseHelper);
    }

    public Shelf create(Shelf shelf)
    {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(databaseHelper.SHELF_KEY_CAPACITY, shelf.getCapacity());
        values.put(databaseHelper.SHELF_KEY_HEIGHT, shelf.getHeight());
        values.put(databaseHelper.SHELF_KEY_WIDTH, shelf.getWidth());

        long id = database.insert(databaseHelper.TABLE_SHELF, null, values);
        shelf.setId(id);

        return shelf;
    }

    public Shelf update(Shelf shelf)
    {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(databaseHelper.SHELF_KEY_CAPACITY, shelf.getCapacity());
        values.put(databaseHelper.SHELF_KEY_HEIGHT, shelf.getHeight());
        values.put(databaseHelper.SHELF_KEY_WIDTH, shelf.getWidth());

        database.update(databaseHelper.TABLE_SHELF, values, "id=?", new String[]{"" + shelf.getId()});

        return shelf;
    }

    public Shelf findById(long id)
    {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = getDatabaseHelper().getWritableDatabase();

        String[] selectionArgs = new String[] {
                Long.toString(id)
        };
        Cursor cursor = database.query(databaseHelper.TABLE_CELLAR, null, databaseHelper.COMMON_KEY_ID + " = ?", selectionArgs, null, null, null);

        Shelf shelf;
        if (cursor.moveToFirst()) {
            shelf = buildShelf(databaseHelper, cursor);
            cursor.close();
        } else {
            shelf = null;
        }

        return shelf;
    }

    public List<Shelf> findAll()
    {
        List<Shelf> shelfList = new ArrayList<Shelf>();
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.query(databaseHelper.TABLE_SHELF, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Shelf shelf = buildShelf(databaseHelper, cursor);
            shelfList.add(shelf);
        }

        cursor.close();

        return shelfList;
    }

    public List<Shelf> shelvesByCellarId(long cellarId)
    {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();


        //Cursor cursor = database.query(databaseHelper.TABLE_SHELF, null, null, null, null, null, null);

        String sql = "SELECT s.* FROM "
                + databaseHelper.TABLE_SHELF + " AS s "
                + " WHERE s." + databaseHelper.SHELF_KEY_CELLAR_ID+ " = ?";

        String[] selectionArgs = new String[] {
                Long.toString(cellarId)
        };
        Cursor cursor = database.rawQuery(sql, selectionArgs);

        List<Shelf> shelfList = new ArrayList<Shelf>();
        while(cursor.moveToNext()) {

            Shelf shelf = buildShelf(databaseHelper, cursor);
            shelfList.add(shelf);
        }

        cursor.close();

        return shelfList;

    }

    public Shelf updateShelf(Shelf shelf)
    {
        InterCellarDatabase databaseHelper = getDatabaseHelper();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = buildShelfContentValues(databaseHelper, shelf);

        String whereClause = databaseHelper.COMMON_KEY_ID + " = ?";
        String[] whereArgs = { String.valueOf(shelf.getId()) };
        database.update(databaseHelper.TABLE_SHELF, values, whereClause, whereArgs);

        shelf = saveShelfBottle(databaseHelper, database, shelf);

        return shelf;
    }
    private Shelf saveShelfBottle(InterCellarDatabase databaseHelper, SQLiteDatabase database, Shelf shelf)
    {
        for (int i = 0; i < shelf.getBottleList().size(); i += 1) {
            Bottle bottle = shelf.getBottleList().get(i);
            if (bottle.getId() > 0) {
                bottle = bottleManager.update(bottle);
            } else {
                bottle = bottleManager.create(bottle);
            }

            ContentValues shelfBottleContent = new ContentValues();
            shelfBottleContent.put(databaseHelper.SHELF_BOTTLE_KEY_BOTTLE_ID, shelf.getId());
            shelfBottleContent.put(databaseHelper.BOTTLE_KEY_SHELF_ID, bottle.getId());
            database.insert(databaseHelper.TABLE_SHELF_BOTTLE, null, shelfBottleContent);

            shelf.getBottleList().set(i, bottle);
        }

        return shelf;
    }

    private Shelf buildShelf(InterCellarDatabase databaseHelper, Cursor cursor)
    {
        Shelf shelf = new Shelf();
        shelf.setId(cursor.getLong(cursor.getColumnIndex(databaseHelper.COMMON_KEY_ID)));
        shelf.setCapacity(cursor.getInt(cursor.getColumnIndex(databaseHelper.SHELF_KEY_CAPACITY)));
        shelf.setWidth(cursor.getInt(cursor.getColumnIndex(databaseHelper.SHELF_KEY_WIDTH)));
        shelf.setHeight(cursor.getInt(cursor.getColumnIndex(databaseHelper.SHELF_KEY_HEIGHT)));
        shelf.setCellar_id(cursor.getLong(cursor.getColumnIndex(databaseHelper.SHELF_KEY_CELLAR_ID)));
        List<Bottle> bottleList = bottleManager.findByShelfId(shelf.getId());
        shelf.setBottleList(bottleList);

        return shelf;
    }

    private ContentValues buildShelfContentValues(InterCellarDatabase databaseHelper, Shelf shelf) {
        ContentValues values = new ContentValues();

        values.put(databaseHelper.SHELF_KEY_HEIGHT, shelf.getHeight());
        values.put(databaseHelper.SHELF_KEY_CAPACITY, shelf.getCapacity());
        values.put(databaseHelper.SHELF_KEY_WIDTH, shelf.getWidth());
        values.put(databaseHelper.SHELF_KEY_CELLAR_ID, shelf.getCellar_id());

        return values;
    }
}
