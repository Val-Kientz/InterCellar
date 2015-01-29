package p54.intercellar.view;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class InterCellarFormFragment<T> extends InterCellarFragment<T> {
    // Name in fields - Error string id pair.
    private Map<String, Integer> requiredFields = new HashMap<String, Integer>();
    // Name - resource in view id pair
    private Map<String, Integer> fields = new HashMap<String, Integer>();

    public InterCellarFormFragment() {
        super();
    }

    protected void setFields(Map<String, Integer> fields) {
        this.fields = fields;
    }

    protected void setRequiredFields(Map<String, Integer> requiredFields) {
        this.requiredFields = requiredFields;
    }

    protected Map<String, TextView> getFieldViews() {
        Map<String, TextView> fieldViews = new HashMap<String, TextView>();

        for (String field: fields.keySet()) {
            TextView textView = (TextView) getView().findViewById(fields.get(field));
            fieldViews.put(field, textView);
        }

        return fieldViews;
    }

    public boolean checkRequiredFields() {
        boolean checked = true;
        Map<String, String> values = getValues();

        for (String field: requiredFields.keySet()) {
            if (values.get(field).trim().equals("")) {
                checked = false;
                Toast.makeText(getActivity(), getString(requiredFields.get(field)), Toast.LENGTH_SHORT).show();
            }
        }

        return checked;
    }

    public Map<String, String> getValues() {
        Map<String, String> values = new HashMap<String, String>();

        for (String field: fields.keySet()) {
            TextView textView = (TextView) getView().findViewById(fields.get(field));
            values.put(field, textView.getText().toString());
        }

        return values;
    }

    public void setValues(Map<String, String> values) {
        for (String field: values.keySet()) {
            TextView textView = (TextView) getView().findViewById(fields.get(field));
            textView.setText(values.get(field));
            textView.invalidate();
        }
    }
}
