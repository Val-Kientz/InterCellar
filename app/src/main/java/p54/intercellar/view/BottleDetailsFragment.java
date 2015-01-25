package p54.intercellar.view;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Bottle;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottleDetailsFragment extends InterCellarFragment<BottleController> {

    public BottleDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottle_details, container, false);
    }

    public void showBottleDetails(long id) {
        Bottle bottle = getController().getBottle(id);
        if (bottle != null) {
            setBottleDetails(bottle);
        } else {
            setNoBottleMessage();
        }
    }

    private void setBottleDetails(Bottle bottle) {
        ((TextView) getView().findViewById(R.id.text_view_no_bottle)).setVisibility(View.INVISIBLE);

        ((TextView) getView().findViewById(R.id.text_view_bottle_name)).setText(bottle.getName());
        ((TextView) getView().findViewById(R.id.text_view_bottle_year)).setText(bottle.getYear());
        ((TextView) getView().findViewById(R.id.text_view_bottle_market)).setText(bottle.getMarket());
        ((TextView) getView().findViewById(R.id.text_view_bottle_price)).setText(String.valueOf(bottle.getPrice()));
        ((TextView) getView().findViewById(R.id.text_view_bottle_chateau)).setText(bottle.getChateau().toString());
        ((TextView) getView().findViewById(R.id.text_view_bottle_type)).setText(bottle.getType());
    }

    private void setNoBottleMessage() {
        ((TextView) getView().findViewById(R.id.text_view_bottle_name)).setVisibility(View.INVISIBLE);
        ((TextView) getView().findViewById(R.id.text_view_bottle_year)).setVisibility(View.INVISIBLE);
        ((TextView) getView().findViewById(R.id.text_view_bottle_market)).setVisibility(View.INVISIBLE);
        ((TextView) getView().findViewById(R.id.text_view_bottle_price)).setVisibility(View.INVISIBLE);
        ((TextView) getView().findViewById(R.id.text_view_bottle_chateau)).setVisibility(View.INVISIBLE);
        ((TextView) getView().findViewById(R.id.text_view_bottle_type)).setVisibility(View.INVISIBLE);
    }
}
