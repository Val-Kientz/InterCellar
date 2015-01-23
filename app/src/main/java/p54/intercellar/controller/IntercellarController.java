package p54.intercellar.controller;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import p54.intercellar.data.BottleManager;
import p54.intercellar.data.InterCellarDatabase;
import p54.intercellar.data.InterCellarManager;

/**
 * Created by sreiss on 23/01/15.
 */

/**
 * @param <T> The type of manager the controller must use (e.g.: BottleManager).
 */
public abstract class InterCellarController<T> {
    private Context context;
    private T manager;

    protected InterCellarController(Context context) {
        InterCellarDatabase databaseHelper = new InterCellarDatabase(context);

        try {
            Class<T> managerClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Constructor managerConstructor = managerClass.getConstructor(InterCellarDatabase.class);
            manager = (T) managerConstructor.newInstance(databaseHelper);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public T getManager() {
        return (T) manager;
    }
}
