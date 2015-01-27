package p54.intercellar.controller;

import android.content.Context;

import java.util.List;

import p54.intercellar.data.RatingManager;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Rating;

/**
 * Created by Simon on 09/01/2015.
 */
public class RatingController extends InterCellarController<RatingManager> {
    private long currentBottleId;

    public RatingController(Context context) {
        super(context);
    }

    public List<Rating> getRatingList(long bottleId) {
        return getManager().listRatingsByBottleId(bottleId);
    }

    public void addRatingForCurrentBottle(Rating rating) {
        getManager().addRatingForBottle(currentBottleId, rating);
    }

    public void setCurrentBottleId(long bottleId) {
        this.currentBottleId = bottleId;
    }

    public long getCurrentBottleId() {
        return currentBottleId;
    }
}
