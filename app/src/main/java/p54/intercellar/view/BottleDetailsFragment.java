package p54.intercellar.view;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Bottle;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottleDetailsFragment extends InterCellarFormFragment<BottleController> {

    public BottleDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, Integer> fields = new HashMap<String, Integer>();
        fields.put("name", R.id.text_view_bottle_name);
        fields.put("year", R.id.text_view_bottle_year);
        fields.put("market", R.id.text_view_bottle_market);
        fields.put("price", R.id.text_view_bottle_price);
        fields.put("chateau", R.id.text_view_bottle_chateau);
        fields.put("description", R.id.text_view_bottle_description);
        fields.put("type", R.id.text_view_bottle_type);
        fields.put("scanContent", R.id.text_view_scan_content);
        fields.put("scanFormat", R.id.text_view_scan_format);
        setFields(fields);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottle_details, container, false);
    }

    public void showBottleDetails(long id) {
        BottleController controller = getController();
        Bottle bottle = controller.getBottle(id);
        if (bottle != null) {
            setBottleDetails(bottle);
        } else {
            setNoBottleMessage();
        }
    }

    private void setBottleDetails(Bottle bottle) {
        ((TextView) getView().findViewById(R.id.text_view_no_bottle)).setVisibility(View.GONE);

        BottleDetailsFragment bottleFormFragment = (BottleDetailsFragment) getFragmentManager().findFragmentById(R.id.fragment_bottle_details);

        Map<String, String> values = new HashMap<String, String>();
        values.put("name", bottle.getName());
        values.put("year", bottle.getYear());
        values.put("market", bottle.getMarket());
        values.put("price", String.valueOf(bottle.getPrice()));
        values.put("chateau", bottle.getChateau().toString());
        values.put("description", bottle.getDescription());
        values.put("type", bottle.getType());
        values.put("scanContent", bottle.getScanContent());
        values.put("scanFormat", bottle.getScanFormat());
        bottleFormFragment.setValues(values);

        String picturePath = bottle.getPicture();
        ImageView imageView = ((ImageView) getView().findViewById(R.id.image_bottle_piture));
        if (picturePath != null && !picturePath.equals("")) {
            Bitmap image = getController().getPicture(getActivity(), picturePath);
            imageView.setImageBitmap(image);
        } else {
            imageView.setImageBitmap(null);
        }
    }

    private void setNoBottleMessage() {
        Map<String, TextView> fieldViews = getFieldViews();

        for (TextView fieldView: fieldViews.values()) {
            fieldView.setVisibility(View.INVISIBLE);
        }
    }
}
