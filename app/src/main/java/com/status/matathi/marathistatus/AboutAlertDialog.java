package com.status.matathi.marathistatus;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Mahesh on 2/24/2017.
 */

public class AboutAlertDialog {

    Context context;

    public AboutAlertDialog(Context context) {
        this.context = context;
    }

    public void show() {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_aboutus, null, false);
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
        aBuilder.setView(view);
        aBuilder.setCancelable(false);
        aBuilder.create();
        final AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
        view.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
