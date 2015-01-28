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
import p54.intercellar.controller.RatingController;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFormFragment extends InterCellarFormFragment<RatingController> {


    public RatingFormFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rating_form, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, Integer> fields = new HashMap<String, Integer>();
        fields.put("rate", R.id.edit_text_rating_rate);
        fields.put("comment", R.id.edit_text_rating_comment);
        setFields(fields);

        Map<String, Integer> requiredFields = new HashMap<String, Integer>();
        requiredFields.put("rate", R.string.rate_is_required);
        requiredFields.put("comment", R.string.comment_is_required);
        setRequiredFields(requiredFields);
    }
}
