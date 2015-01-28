package p54.intercellar.view;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottleFormFragment extends InterCellarFormFragment<BottleController> {


    public BottleFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottle_form, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, Integer> fields = new HashMap<String, Integer>();
        fields.put("name", R.id.edit_text_bottle_name);
        fields.put("year", R.id.edit_text_bottle_year);
        fields.put("price", R.id.edit_text_bottle_price);
        fields.put("description", R.id.edit_text_bottle_description);
        fields.put("market", R.id.edit_text_bottle_market);
        fields.put("type", R.id.edit_text_bottle_type);
        setFields(fields);

        Map<String, Integer> requiredFields = new HashMap<String, Integer>();
        requiredFields.put("name", R.string.name_is_required);
        requiredFields.put("price", R.string.price_is_required);
        requiredFields.put("year", R.string.year_is_required);
        requiredFields.put("type", R.string.type_is_required);
        setRequiredFields(requiredFields);
    }
}
