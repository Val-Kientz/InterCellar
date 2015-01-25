package p54.intercellar.screen;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import p54.intercellar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoBottlesFoundFragment extends Fragment {


    public NoBottlesFoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_bottles_found, container, false);
    }


}
