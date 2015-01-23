package p54.intercellar.screen;

/**
 * Created by sreiss on 23/01/15.
 */

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import p54.intercellar.controller.InterCellarController;
import p54.intercellar.data.InterCellarDatabase;

/**
 * @param <T> The type of controller that must be implemented (e.g.: BottleController).
 */
public abstract class InterCellarActivity<T> extends ActionBarActivity {
    private T controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Class<T> controllerClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Constructor controllerConstructor = controllerClass.getConstructor(Context.class);
            controller = (T) controllerConstructor.newInstance(this);
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

    public T getController() {
        return controller;
    }
}
