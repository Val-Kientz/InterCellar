package p54.intercellar.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import p54.intercellar.R;
import p54.intercellar.screen.BottleActivity;

/**
 * Created by sreiss on 29/01/15.
 */
public class YesCancelAlert {
    private AlertDialog.Builder alertDialog;

    public YesCancelAlert(Context context, String message, String title, String yesString, String cancelString, DialogInterface.OnClickListener yesAction) {
        if (context != null) {
            alertDialog = new AlertDialog.Builder(context);

            alertDialog.setTitle(title);
            alertDialog.setMessage(message);

            alertDialog.setPositiveButton(yesString, yesAction);
            alertDialog.setNegativeButton(cancelString, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
    }

    public void show() {
        alertDialog.show();
    }
}
