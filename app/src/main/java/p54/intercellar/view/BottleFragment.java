package p54.intercellar.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Bottle;

public class BottleFragment extends InterCellarListFragment<BottleController> {
    private OnBottleClick mListener;

    public BottleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<Bottle>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, getController().getBottleList()));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBottleClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBottleClick");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getListView().invalidate();
    }

    @Override
    public void onResume() {
        super.onResume();
        getListView().invalidate();
    }

    public void refreshList() {
        setListAdapter(new ArrayAdapter<Bottle>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, getController().getBottleList()));
    }

    public void refreshList(List<Bottle> bottleList) {
        setListAdapter(new ArrayAdapter<Bottle>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, bottleList));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            mListener.onBottleClick(getController().getBottleList().get(position).getId());
        }
    }

    public interface OnBottleClick {
        public void onBottleClick(long id);
    }
}
