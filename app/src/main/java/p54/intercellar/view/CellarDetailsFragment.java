package p54.intercellar.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.List;

import p54.intercellar.R;
import p54.intercellar.controller.CellarController;
import p54.intercellar.controller.ShelfController;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Cellar;
import p54.intercellar.model.Shelf;
import p54.intercellar.screen.BottleDetailsActivity;

public class CellarDetailsFragment  extends InterCellarFragment<CellarController>
{
    private ShelfController shelfController;

    public CellarDetailsFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cellar_details, container, false);
    }

    public void showCellarDetails(long id)
    {
        CellarController controller = getController();
        Cellar cellar = controller.getCellar(id);

        if(cellar !=null)
        {
            setCellarDetail(cellar);
        }
        else
        {

        }

    }

        /*test test test

        GridLayout shelfLayout = ((GridLayout) getView().findViewById(R.id.shelf_grid_layout));
        shelfLayout.setRowCount(1);
        shelfLayout.setColumnCount(8);

        int cpt;
        for(cpt=0; cpt<8;cpt++)
        {
            GridLayout.Spec row = GridLayout.spec(0,1);
            GridLayout.Spec col = GridLayout.spec(cpt,1);
            GridLayout.LayoutParams shelfLayoutParams = new GridLayout.LayoutParams(row,col);

            Context cont = getController().getContext();
            Button b = new Button(getActivity());
            b.setText("TEST");

            shelfLayout.addView(b,shelfLayoutParams);
            cpt++;
        }
*/


    //TO BE TESTED
    public void setCellarDetail(Cellar cellar)
    {
        List<Shelf> listShelves = cellar.getShelfList();
        GridLayout shelfLayout = (GridLayout) getView().findViewById(R.id.shelf_grid_layout);
        int rowCount = 0;
        for(Shelf s : listShelves)
        {
            List<Bottle> listBottle = s.getBottleList();
            shelfLayout.setRowCount(1);
            shelfLayout.setColumnCount(listBottle.size());

            int columnCount = 0;
            for (final Bottle b : listBottle) {
                GridLayout.Spec row = GridLayout.spec(rowCount, 1);
                GridLayout.Spec col = GridLayout.spec(columnCount, 1);
                GridLayout.LayoutParams shelfLayoutParams = new GridLayout.LayoutParams(row, col);

                Button button = new Button(getActivity());
                button.setText(b.getName());


                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BottleDetailsActivity.class);
                        intent.putExtra("bottleId",b.getId());
                        startActivity(intent);
                    }
                });

                shelfLayout.addView(button, shelfLayoutParams);
                columnCount++;
            }
            rowCount++;
        }

    }

}
