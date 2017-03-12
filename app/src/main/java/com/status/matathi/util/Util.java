package com.status.matathi.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mahesh on 1/27/2017.
 */

public class Util {

    public static ClipboardManager getClipboardManager(Context context, String s) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("randText", s);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(context, "Status copied successfully", Toast.LENGTH_SHORT).show();
        return clipboardManager;
    }

    public static ArrayList<String> getListCategeriesMessage(String string) {
        return null;
    }

    public static void shareMarathiStatus(Context context, String message, boolean iswhatApp) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        if (iswhatApp) {
            sharingIntent.setPackage("com.whatsapp");
        }
        sharingIntent.putExtra(android.content.Intent.EXTRA_TITLE, "Marathi Status");
        sharingIntent.putExtra(sharingIntent.EXTRA_SUBJECT, "Marathi Status");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void shareMessageWhatApp(Context context, String message) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            shareMarathiStatus(context, message, true);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "WhatsApp  app not Installed.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public static void shareApp(Context context) {
        String url = "मस्त नवीन मराठी स्टेटस अँप डाउनलोड करा.खालील लिंक वर क्लिक करा.\n" +
                "https://play.google.com/store/apps/details?id=com.mahesh.matathi.marathistatus";
        shareMarathiStatus(context, url, false);
    }
}
