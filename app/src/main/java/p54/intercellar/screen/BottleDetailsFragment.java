package p54.intercellar.screen;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import p54.intercellar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottleDetailsFragment extends Fragment {

    public BottleDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottle_details, container, false);
    }

    public void updateBottleId(String id) {

    }
}