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

    protected static final String TABLE_BOTTLE = "bottle";
    protected static final String TABLE_CELLAR = "cellar";
    protected static final String TABLE_CHATEAU = "chateau";
    protected static final String TABLE_RATING = "rating";
    protected static final String TABLE_SHELF = "shelf";
    protected static final String TABLE_BOTTLE_CHATEAU = "bottle_chateau";
    protected static final String TABLE_BOTTLE_RATING = "bottle_rating";
    protected static final String TABLE_CELLAR_SHELF = "cellar_shelf";
    protected static final String TABLE_SHELF_BOTTLE = "shelf_bottle";

    // endregion

    // region Fields

    // id field for all
    protected static final String COMMON_KEY_ID = "id";

    // bottle fields
    protected static final String BOTTLE_KEY_YEAR = "year";
    protected static final String BOTTLE_KEY_NAME = "name";
    protected static final String BOTTLE_KEY_PRICE = "price";
    protected static final String BOTTLE_KEY_PICTURE = "picture";
    protected static final String BOTTLE_KEY_DESCRIPTION = "description";
    protected static final String BOTTLE_KEY_TYPE = "type";
    protected static final String BOTTLE_KEY_MARKET = "market";
    protected static final String BOTTLE_KEY_COORDINATES = "coordinates";
    protected static final String BOTTLE_KEY_SCAN_FORMAT = "scan_format";
    protected static final String BOTTLE_KEY_SCAN_CONTENT = "scan_content";
    protected static final String BOTTLE_KEY_CHATEAU_ID = "chateau_id";
    protected static final String BOTTLE_KEY_SHELF_ID = "shelf_id";

    // cellar fields
    protected static final String CELLAR_KEY_NAME = "name";

    // chateau fields
    protected static final String CHATEAU_KEY_DOMAIN = "domain";
    protected static final String CHATEAU_KEY_REGION = "region";

    // rating fields
    protected static final String RATING_KEY_COMMENT = "comment";
    protected static final String RATING_KEY_DATE = "date";
    protected static final String RATING_KEY_RATE = "rate";

    // shelf fields
    protected static final String SHELF_KEY_CAPACITY = "capacity";
    protected static final String SHELF_KEY_WIDTH = "width";
    protected static final String SHELF_KEY_HEIGHT = "height";

    // bottle_chateau fields
    protected static final String BOTTLE_CHATEAU_KEY_BOTTLE_ID = "bottle_id";
    protected static final String BOTTLE_CHATEAU_KEY_CHATEAU_ID = "chateau_id";

    // bottle_rating fields
    protected static final String BOTTLE_RATING_KEY_BOTTLE_ID = "bottle_id";
    protected static final String BOTTLE_RATING_KEY_RATING_ID = "rating_id";

    // cellar_shelf fields
    protected static final String CELLAR_SHELF_KEY_CELLAR_ID = "cellar_id";
    protected static final String CELLAR_SHELF_KEY_SHELF_ID = "shelf_id";

    // shelf_bottle fields
    protected static final String SHELF_BOTTLE_KEY_SHELF_ID = "shelf_id";
    protected static final String SHELF_BOTTLE_KEY_BOTTLE_ID = "bottle_id";

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
            + BOTTLE_KEY_SCAN_FORMAT + " TEXT,"
            + BOTTLE_KEY_SCAN_CONTENT + " TEXT,"
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
