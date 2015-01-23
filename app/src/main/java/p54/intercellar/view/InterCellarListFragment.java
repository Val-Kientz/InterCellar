package p54.intercellar.view;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = (T) ((InterCellarActivity) getActivity()).getController();
    }

    protected T getController() {
        return controller;
    }
}
