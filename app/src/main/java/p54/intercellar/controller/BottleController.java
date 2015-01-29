package p54.intercellar.controller;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import p54.intercellar.R;
import p54.intercellar.data.BottleManager;
import p54.intercellar.data.InterCellarDatabase;
import p54.intercellar.model.Bottle;
import p54.intercellar.model.Chateau;
import p54.intercellar.model.Rating;

/**
 * Created by Simon on 09/01/2015.
 */
public class BottleController extends InterCellarController<BottleManager> {
    private ChateauController chateauController;
    private RatingController ratingController;
    private long currentBottleId = -1;

    public BottleController(Context context) {
        super(context);
        chateauController = new ChateauController(context);
        ratingController = new RatingController(context);
    }

    public Bottle getBottle(long id) {
        return getManager().findById(id);
    }

    public List<Bottle> getBottleList() {
        return getManager().findAll();
    }

    public Bottle createBottle(Bottle bottle) {
        return getManager().create(bottle);
    }

    public Bottle updateBottle(Bottle bottle) { return getManager().update(bottle); }

    public List<Chateau> getChateauList() {
        return chateauController.getChateauList();
    }

    public List<Rating> getRatingList(long bottleId) { return ratingController.getRatingList(bottleId); }

    public int getBottleCount() {
        return getManager().count();
    }

    public String saveTakenPicture(Context context, Intent imageData) {
        String imagePath = "";

        if (imageData != null) {
            Bundle extras = imageData.getExtras();
            // TODO: get real image !!
            Bitmap image = (Bitmap) extras.get("data");

            if (image != null) {
                String fileName = "bottle_picture_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                FileOutputStream fileOutputStream = null;

                try {

                    fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();

                    imagePath = fileName;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        return imagePath;
    }

    public Bitmap getPicture(Context context, String fileName) {
        Bitmap bitmap = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public void setCurrentBottleId(long bottleId) {
        this.currentBottleId = bottleId;
    }

    public long getCurrentBottleId() {
        return currentBottleId;
    }

    public List<Bottle> getBottlesLike(String search) {
        return getManager().getBottlesLike(search);
    }

    public Bottle getBottleByBarCode(String barCode) {
        return getManager().findBottleByBarCode(barCode);
    }

    public void deleteBottle(long id) {
        getManager().delete(id);
    }
}
