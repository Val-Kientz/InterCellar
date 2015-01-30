package p54.intercellar.view;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import p54.intercellar.R;
import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Rating;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends InterCellarFragment<BottleController> {

    private TextView titleRatingList;

    public RatingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ratings, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        long bottleId = getController().getCurrentBottleId();
        titleRatingList = (TextView) getView().findViewById(R.id.title_rating_list);
        refreshRatingList(bottleId);
    }

    private List<Map<String, String>> formatForDetailedList(List<Rating> ratingList) {
        List<Map<String, String>> formatedRatingList = new ArrayList<Map<String, String>>(ratingList.size());

        for (Rating rating: ratingList) {
            Map<String, String> listItem = new HashMap<String, String>();
            String formattedDate = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
                    .format(rating.getDate());
            listItem.put("text1", rating.getRate() + " " + getString(R.string.given_on) + " " + formattedDate);
            listItem.put("text2", getString(R.string.comment) + " " + rating.getComment());
            formatedRatingList.add(listItem);
        }

        return formatedRatingList;
    }

    public void refreshRatingList(long bottleId) {
        String[] stringAdapter = new String[]{"text1", "text2"};
        int[] layoutIds = new int[]{android.R.id.text1, android.R.id.text2};
        List<Rating> ratingList = getController().getRatingList(bottleId);

        if (ratingList != null && !ratingList.isEmpty()) {
            List<Map<String, String>> detailedList = formatForDetailedList(ratingList);

            ListView ratingListView = ((ListView) getView().findViewById(R.id.list_rating_list));

            SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                    detailedList,
                    android.R.layout.simple_list_item_2,
                    stringAdapter,
                    layoutIds
            );

            ratingListView.setAdapter(adapter);
            ViewGroup.LayoutParams layoutParams = ratingListView.getLayoutParams();
            layoutParams.height = computeListViewHeight(ratingListView);
            ratingListView.setLayoutParams(layoutParams);
            titleRatingList.setText(R.string.ratings);
            titleRatingList.setTextAppearance(getActivity(), android.R.style.TextAppearance_DeviceDefault_Large);
            titleRatingList.invalidate();
        } else {
            titleRatingList.setText(R.string.no_ratings_yet);
            titleRatingList.setTextAppearance(getActivity(), android.R.style.TextAppearance_DeviceDefault_Small);
            titleRatingList.invalidate();
        }
    }

    private int computeListViewHeight(ListView listView) {
        int height = 0;
        int width = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        ListAdapter listAdapter = listView.getAdapter();

        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i+= 1) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(width, View.MeasureSpec.UNSPECIFIED);
            height += view.getMeasuredHeight() + listView.getDividerHeight();
        }

        return height;
    }
}
