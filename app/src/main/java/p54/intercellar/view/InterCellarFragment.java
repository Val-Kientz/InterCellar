package p54.intercellar.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import p54.intercellar.controller.InterCellarController;
import p54.intercellar.data.InterCellarDatabase;
import p54.intercellar.screen.InterCellarActivity;

/**
 * Created by sreiss on 23/01/15.
 */

/**
 * @param <T> The activity class (e.g.: BottleActivity)
 */
public abstract class InterCellarFragment<T> extends Fragment {
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
