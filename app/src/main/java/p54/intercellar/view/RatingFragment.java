package p54.intercellar.view;


import android.app.ActionBar;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.controller.RatingController;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Rating;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends InterCellarFragment<BottleController> {
    public RatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ratings, container, false);
    }

    public void refreshRatingList() {
        List<Rating> ratingList = getController().getRatingList(getController().getCurrentBottleId());
        TableLayout ratingListTable = ((TableLayout) getView().findViewById(R.id.table_rating_list));
        for (Rating rating: ratingList) {
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

            TextView rateTextView = new TextView(getActivity());
            TextView commentTextView = new TextView(getActivity());
            TextView dateTextView = new TextView(getActivity());

            rateTextView.setText(String.valueOf(rating.getRate()));
            commentTextView.setText(rating.getComment());
            dateTextView.setText(rating.getDate().toString());

            tableRow.addView(rateTextView);
            tableRow.addView(commentTextView);
            tableRow.addView(dateTextView);

            ratingListTable.addView(tableRow);
        }

        ratingListTable.invalidate();
    }
}
