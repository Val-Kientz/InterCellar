package p54.intercellar.view;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChateauFormFragment extends InterCellarFormFragment<BottleController> {


    public ChateauFormFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chateau_form, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, Integer> fields = new HashMap<String, Integer>();
        fields.put("domain", R.id.text_edit_chateau_domain);
        fields.put("region", R.id.text_edit_chateau_region);
        setFields(fields);

        Map<String, Integer> requiredFields = new HashMap<String, Integer>();
        requiredFields.put("domain", R.string.domain_is_required);
        requiredFields.put("region", R.string.region_is_required);
        setRequiredFields(requiredFields);
    }
}
