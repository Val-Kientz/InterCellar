package p54.intercellar.screen;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.zxing.client.android.Intents;

import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import p54.intercellar.R;
import p54.intercellar.controller.InterCellarController;
import p54.intercellar.controller.RatingController;
import p54.intercellar.model.Rating;
import p54.intercellar.view.InterCellarFormFragment;
import p54.intercellar.view.RatingFormFragment;

public class AddRatingActivity extends InterCellarActivity<RatingController> implements InterCellarFormFragment.OnFormReady, InterCellarFormFragment.OnFormDestroy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long bottleId = getIntent().getLongExtra("bottleId", -1);
        getController().setCurrentBottleId(bottleId);
        setContentView(R.layout.activity_add_rating);
    }

    public void onAddClick(View v) {
        RatingFormFragment ratingFormFragment = (RatingFormFragment) getFragmentManager().findFragmentById(R.id.fragment_rating_form);

        if (ratingFormFragment.checkRequiredFields()) {
            Rating rating = getRatingFromView(ratingFormFragment);
            getController().addRatingForCurrentBottle(rating);
            setResult(0);
            finish();
        }
    }

    private Rating getRatingFromView(RatingFormFragment ratingFormFragment) {
        Rating rating = new Rating();
        Map<String, String> values = ratingFormFragment.getValues();

        rating.setRate(Double.parseDouble(values.get("rate")));
        rating.setComment(values.get("comment"));
        rating.setDate(new Date());

        return rating;
    }

    @Override
    public void onFormReady(String fragmentClass) {

    }

    @Override
    public void onFormDestroy(String fragmentClass) {

    }
}
