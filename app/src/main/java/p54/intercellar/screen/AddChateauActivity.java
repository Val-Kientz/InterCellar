package p54.intercellar.screen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import p54.intercellar.R;
import p54.intercellar.controller.ChateauController;
import p54.intercellar.model.Chateau;
import p54.intercellar.view.ChateauFormFragment;
import p54.intercellar.view.InterCellarFormFragment;

public class AddChateauActivity extends InterCellarActivity<ChateauController> implements InterCellarFormFragment.OnFormReady, InterCellarFormFragment.OnFormDestroy {
    public final static int CHATEAU_CREATED = 1;
    public final static int CHATEAU_NOT_CREATED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chateau);
    }

    public void onAddChateauButtonPressed(View v) {
        ChateauFormFragment chateauFormFragment = (ChateauFormFragment) getFragmentManager().findFragmentById(R.id.fragment_chateau_form);

        if (chateauFormFragment.checkRequiredFields()) {
            Chateau chateau = getChateauFromView(chateauFormFragment);

            Intent result = new Intent();
            try {
                getController().createChateau(chateau);
                setResult(CHATEAU_CREATED, result);
            } catch (Exception e) {
                setResult(CHATEAU_NOT_CREATED, result);
            }

            finish();
        }
    }

    @Override
    public void onFormReady(String fragmentClass) {

    }

    @Override
    public void onFormDestroy(String fragmentClass) {

    }

    private Chateau getChateauFromView(ChateauFormFragment chateauFormFragment) {
        Chateau chateau = new Chateau();
        Map<String, String> values = chateauFormFragment.getValues();

        chateau.setDomain(values.get("domain"));
        chateau.setRegion(values.get("region"));

        return chateau;
    }
}
