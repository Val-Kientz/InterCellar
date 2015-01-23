package p54.intercellar.data;

import java.util.List;

/**
 * Created by sreiss on 17/01/15.
 */
public abstract class InterCellarManager<T> {
    private InterCellarDatabase databaseHelper;

    protected InterCellarManager(InterCellarDatabase databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    protected InterCellarDatabase getDatabaseHelper() {
        return databaseHelper;
    }
}
