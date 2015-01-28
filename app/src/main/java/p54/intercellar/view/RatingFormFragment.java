package p54.intercellar.view;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import p54.intercellar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFormFragment extends Fragment {


    public RatingFormFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rating_form, container, false);
    }

    public boolean checkRequiredFields() {
        boolean checked = true;

        // Resource - Error Message String pair
        Map<Integer, Integer> requiredFields = new HashMap<Integer, Integer>();
        requiredFields.put(R.id.edit_text_rating_rate, R.string.rate_is_required);
        requiredFields.put(R.id.edit_text_rating_comment, R.string.comment_is_required);

        for (int res: requiredFields.keySet()) {
            TextView textView = (TextView) getView().findViewById(res);
            if (textView.getText().toString().trim().equals("")) {
                checked = false;
                Toast.makeText(getActivity(), getString(requiredFields.get(res)), Toast.LENGTH_SHORT).show();
            }
        }

        return checked;
    }

    public Map<String, String> getValues() {
        Map<String, String> values = new HashMap<String, String>();
        Map<String, Integer> fields = new HashMap<String, Integer>();
        fields.put("rate", R.id.edit_text_rating_rate);
        fields.put("comment", R.id.edit_text_rating_comment);

        for (String field: fields.keySet()) {
            TextView textView = (TextView) getView().findViewById(fields.get(field));
            values.put(field, textView.getText().toString());
        }

        return values;
    }
}
