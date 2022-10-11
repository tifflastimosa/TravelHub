package edu.neu.madcourse.pettin.GroupChat;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import edu.neu.madcourse.pettin.R;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog alertDialog;

    /**
     * Constructor for the loading dialog.
     * @param activity Activity for the dialog to be used in.
     */
    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    /**
     * Method to create the loading dialog.
     */
    public void loadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(true);

        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Method to dismiss the dialog.
     */
    public void dismissDialog() {
        alertDialog.dismiss();
    }

}