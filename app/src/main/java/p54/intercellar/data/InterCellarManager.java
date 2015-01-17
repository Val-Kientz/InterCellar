package p54.intercellar.data;

/**
 * Created by sreiss on 17/01/15.
 */
public abstract class InterCellarManager {
    private InterCellarDatabase databaseHelper;

    protected InterCellarDatabase getDatabaseHelper() {
        return databaseHelper;
    }

    protected void setDatabaseHelper(InterCellarDatabase databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
}
