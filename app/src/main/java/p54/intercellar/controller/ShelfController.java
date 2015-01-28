package p54.intercellar.controller;

import android.content.Context;

import java.util.List;

import p54.intercellar.data.ShelfManager;
import p54.intercellar.model.Shelf;

/**
 * Created by Simon on 09/01/2015.
 */
public class ShelfController extends InterCellarController<ShelfManager> {

    protected ShelfController(Context context) {
        super(context);
    }

    public List<Shelf> getShelvesList()
    {
        return getManager().findAll();
    }
}
