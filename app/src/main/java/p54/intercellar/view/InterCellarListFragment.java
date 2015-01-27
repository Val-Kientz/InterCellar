package p54.intercellar.view;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;

import p54.intercellar.screen.InterCellarActivity;

/**
 * Created by sreiss on 23/01/15.
 */

/**
 * @param <T> The controller class (e.g.: BottleController).
 */
public abstract class InterCellarListFragment<T> extends ListFragment {
    private T controller;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        controller = (T) ((InterCellarActivity) activity).getController();
    }

    protected T getController() {
        return controller;
    }
}
