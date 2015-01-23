package p54.intercellar.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sreiss on 17/01/15.
 */
public class InterCellarDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "interCellarDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // region Table Names

    public static final String TABLE_BOTTLE = "bottle";
    public static final String TABLE_CELLAR = "cellar";
    public static final String TABLE_CHATEAU = "chateau";
    public static final String TABLE_RATING = "rating";
    public static final String TABLE_SHELF = "shelf";
    public static final String TABLE_BOTTLE_CHATEAU = "bottle_chateau";
    public static final String TABLE_BOTTLE_RATING = "bottle_rating";
    public static final String TABLE_CELLAR_SHELF = "cellar_shelf";
    public static final String TABLE_SHELF_BOTTLE = "shelf_bottle";

    // endregion

    // region Fields

    // id field for all
    public static final String COMMON_KEY_ID = "id";

    // bottle fields
    public static final String BOTTLE_KEY_YEAR = "year";
    public static final String BOTTLE_KEY_NAME = "name";
    public static final String BOTTLE_KEY_PRICE = "price";
    public static final String BOTTLE_KEY_PICTURE = "picture";
    public static final String BOTTLE_KEY_DESCRIPTION = "description";
    public static final String BOTTLE_KEY_TYPE = "type";
    public static final String BOTTLE_KEY_MARKET = "market";
    public static final String BOTTLE_KEY_COORDINATES = "coordinates";
    public static final String BOTTLE_KEY_CHATEAU_ID = "chateau_id";
    public static final String BOTTLE_KEY_SHELF_ID = "shelf_id";

    // cellar fields
    public static final String CELLAR_KEY_NAME = "name";

    // chateau fields
    public static final String CHATEAU_KEY_DOMAIN = "domain";
    public static final String CHATEAU_KEY_REGION = "region";

    // rating fields
    public static final String RATING_KEY_COMMENT = "comment";
    public static final String RATING_KEY_DATE = "date";
    public static final String RATING_KEY_RATE = "rate";

    // shelf fields
    public static final String SHELF_KEY_CAPACITY = "capacity";
    public static final String SHELF_KEY_WIDTH = "width";
    public static final String SHELF_KEY_HEIGHT = "height";

    // bottle_chateau fields
    public static final String BOTTLE_CHATEAU_KEY_BOTTLE_ID = "bottle_id";
    public static final String BOTTLE_CHATEAU_KEY_CHATEAU_ID = "chateau_id";

    // bottle_rating fields
    public static final String BOTTLE_RATING_KEY_BOTTLE_ID = "bottle_id";
    public static final String BOTTLE_RATING_KEY_RATING_ID = "rating_id";

    // cellar_shelf fields
    public static final String CELLAR_SHELF_KEY_CELLAR_ID = "cellar_id";
    public static final String CELLAR_SHELF_KEY_SHELF_ID = "shelf_id";

    // shelf_bottle fields
    public static final String SHELF_BOTTLE_KEY_SHELF_ID = "shelf_id";
    public static final String SHELF_BOTTLE_KEY_BOTTLE_ID = "bottle_id";

    // endregion

    // region CREATE statements

    private static final String CREATE_BOTTLE = "CREATE TABLE "
            + TABLE_BOTTLE + "(" + COMMON_KEY_ID + " INTEGER PRIMARY KEY,"
            + BOTTLE_KEY_YEAR + " TEXT,"
            + BOTTLE_KEY_NAME + " TEXT,"
            + BOTTLE_KEY_PRICE + " REAL,"
            + BOTTLE_KEY_PICTURE + " TEXT,"
            + BOTTLE_KEY_DESCRIPTION + " TEXT,"
            + BOTTLE_KEY_TYPE + " TEXT,"
            + BOTTLE_KEY_MARKET + " TEXT,"
            + BOTTLE_KEY_COORDINATES + " TEXT,"
            + BOTTLE_KEY_CHATEAU_ID + " INTEGER,"
            + BOTTLE_KEY_SHELF_ID + " INTEGER,"
            + "FOREIGN KEY (" + BOTTLE_KEY_SHELF_ID + ") REFERENCES " + TABLE_SHELF + "(" + COMMON_KEY_ID + "),"
            + "FOREIGN KEY (" + BOTTLE_KEY_CHATEAU_ID + ") REFERENCES " + TABLE_CHATEAU + "(" + COMMON_KEY_ID + "))";

    private static final String CREATE_CELLAR = "CREATE TABLE "
            + TABLE_CELLAR + "(" + COMMON_KEY_ID + " INTEGER PRIMARY KEY,"
            + CELLAR_KEY_NAME + " TEXT)";

    private static final String CREATE_CHATEAU = "CREATE TABLE "
            + TABLE_CHATEAU + "(" + COMMON_KEY_ID + " INTEGER PRIMARY KEY,"
            + CHATEAU_KEY_DOMAIN + " TEXT,"
            + CHATEAU_KEY_REGION + " TEXT)";

    private static final String CREATE_RATING = "CREATE TABLE "
            + TABLE_RATING + "(" + COMMON_KEY_ID + " INTEGER PRIMARY KEY,"
            + RATING_KEY_COMMENT + " TEXT,"
            + RATING_KEY_DATE + " TEXT,"
            + RATING_KEY_RATE + " REAL)";

    private static final String CREATE_SHELF = "CREATE TABLE "
            + TABLE_SHELF + "(" + COMMON_KEY_ID + " INTEGER PRIMARY KEY,"
            + SHELF_KEY_CAPACITY + " INTEGER,"
            + SHELF_KEY_HEIGHT + " INTEGER,"
            + SHELF_KEY_WIDTH + " INTEGER)";

    private static final String CREATE_BOTTLE_CHATEAU = "CREATE TABLE "
            + TABLE_BOTTLE_CHATEAU + "(" + COMMON_KEY_ID + " INTEGER PRIMARY KEY,"
            + BOTTLE_CHATEAU_KEY_BOTTLE_ID + " INTEGER,"
            + BOTTLE_CHATEAU_KEY_CHATEAU_ID + " INTEGER)";

    private static final String CREATE_BOTTLE_RATING = "CREATE TABLE "
            + TABLE_BOTTLE_RATING + "(" + COMMON_KEY_ID + " INTEGER PRIMARY KEY,"
            + BOTTLE_RATING_KEY_BOTTLE_ID + " INTEGER,"
            + BOTTLE_RATING_KEY_RATING_ID + " INTEGER)";

    private static final String CREATE_CELLAR_SHELF = "CREATE TABLE "
            + TABLE_CELLAR_SHELF + "(" + COMMON_KEY_ID + " INTEGER PRIMARY KEY,"
            + CELLAR_SHELF_KEY_CELLAR_ID + " INTEGER,"
            + CELLAR_SHELF_KEY_SHELF_ID + " INTEGER)";

    private static final String CREATE_SHELF_BOTTLE = "CREATE TABLE "
            + TABLE_SHELF_BOTTLE + "(" + COMMON_KEY_ID + " INTEGER PRIMARY KEY,"
            + SHELF_BOTTLE_KEY_SHELF_ID + " INTEGER,"
            + SHELF_BOTTLE_KEY_BOTTLE_ID + " INTEGER)";

    // endregion

    public InterCellarDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOTTLE);
        db.execSQL(CREATE_CELLAR);
        db.execSQL(CREATE_CHATEAU);
        db.execSQL(CREATE_RATING);
        db.execSQL(CREATE_SHELF);
        db.execSQL(CREATE_BOTTLE_CHATEAU);
        db.execSQL(CREATE_BOTTLE_RATING);
        db.execSQL(CREATE_CELLAR_SHELF);
        db.execSQL(CREATE_SHELF_BOTTLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOTTLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CELLAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATEAU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHELF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOTTLE_CHATEAU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOTTLE_RATING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CELLAR_SHELF);

        onCreate(db);
    }
}
