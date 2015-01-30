package p54.intercellar.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import p54.intercellar.controller.CellarController;
import p54.intercellar.model.Cellar;
import p54.intercellar.view.InterCellarListFragment;

public class CellarFragment extends InterCellarListFragment<CellarController> {

    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;
    private ListAdapter mAdapter;

    public CellarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<Cellar>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, getController().getCellarList()));
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

    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l,view,position,id);

        if (null != mListener) {

            mListener.onFragmentInteraction(getController().getCellarList().get(position).getId());
        }
    }


    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(long id);
    }

}
