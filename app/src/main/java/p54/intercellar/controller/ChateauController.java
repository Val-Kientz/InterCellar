package p54.intercellar.controller;

import android.content.Context;

import java.util.List;

import p54.intercellar.data.ChateauManager;
import p54.intercellar.model.Chateau;

/**
 * Created by Simon on 09/01/2015.
 */
public class ChateauController extends InterCellarController<ChateauManager> {
    public ChateauController(Context context) {
        super(context);
    }

    public Chateau createChateau(Chateau chateau) {
        return getManager().create(chateau);
    }

    public List<Chateau> getChateauList() {
        return getManager().findAll();
    }
}
