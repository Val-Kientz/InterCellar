package p54.intercellar.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import p54.intercellar.data.CellarManager;
import p54.intercellar.model.Cellar;
import p54.intercellar.model.Shelf;

/**
 * Created by Simon on 09/01/2015.
 */
public class CellarController extends InterCellarController<CellarManager>
{
    private ShelfController shelfController;
    private long currentCellarId =-1;

    public CellarController(Context context) {
        super(context);

        shelfController = new ShelfController(context);

        Cellar cellar = new Cellar();
        cellar.setName("Epic Cellar");
        cellar.setShelfList(new ArrayList<Shelf>());
        getManager().create(cellar);

    }

    public List<Cellar> getCellarList()
    {
        return getManager().findAll();
    }
    public Cellar getCellar(long id)
    {
        return getManager().findById(id);
    }
    public Cellar createCellar(Cellar cellar)
    {
        return getManager().create(cellar);
    }
    public Cellar updateCellar(Cellar cellar)
    {
        return getManager().update(cellar);
    }
    public List<Shelf> getShelvesList()
    {
        return shelfController.getShelvesList();
    }
    public void setCurrentCellarId(long cellarId)
    {
        currentCellarId = cellarId;
    }
    public long getCurrentCellarId()
    {
        return currentCellarId;
    }


}
