package p54.intercellar.screen;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import p54.intercellar.R;
import p54.intercellar.controller.InterCellarController;
import p54.intercellar.controller.RatingController;
import p54.intercellar.model.Rating;

public class AddRatingActivity extends InterCellarActivity<RatingController> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long bottleId = getIntent().getLongExtra("bottleId", -1);
        getController().setCurrentBottleId(bottleId);
        setContentView(R.layout.activity_add_rating);
    }

    public void onAddClick(View v) {
        Rating rating = getRatingFromView();
        getController().addRatingForCurrentBottle(rating);
        setResult(0);
        finish();
    }

    private Rating getRatingFromView() {
        Rating rating = new Rating();

        double rate = Double.parseDouble(((EditText) findViewById(R.id.edit_text_rating_rate)).getText().toString());
        String comment = ((EditText) findViewById(R.id.edit_text_rating_comment)).getText().toString();

        rating.setRate(rate);
        rating.setComment(comment);
        rating.setDate(new Date());

        return rating;
    }
}
