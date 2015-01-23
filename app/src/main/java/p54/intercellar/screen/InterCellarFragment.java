package p54.intercellar.screen;

import android.app.Fragment;
import android.os.Bundle;

import p54.intercellar.controller.InterCellarController;
import p54.intercellar.data.InterCellarDatabase;

/**
 * Created by sreiss on 23/01/15.
 */

/**
 * @param <T> The activity class (e.g.: BottleActivity)
 */
public abstract class InterCellarFragment<T> extends Fragment {
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
