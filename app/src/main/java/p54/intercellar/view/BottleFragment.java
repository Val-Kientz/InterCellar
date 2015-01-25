package p54.intercellar.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import p54.intercellar.controller.BottleController;
import p54.intercellar.model.Bottle;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class BottleFragment extends InterCellarListFragment<BottleController> {
    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static BottleFragment newInstance() {
        BottleFragment fragment = new BottleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
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
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(getController().getBottleList().get(position).getId());
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(long id);
    }
}
