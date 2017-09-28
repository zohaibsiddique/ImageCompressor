package com.example.dell.imagecompressor;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import java.util.ArrayList;

public class ProgressAlbums extends AsyncTask<ArrayList<String>, Void, String> {
    ProgressDialog progress;
    private Context context;
    Util utils;

    public ProgressAlbums(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(ArrayList<String>... arrayLists) {
        utils = new Util(context);
        ArrayList<String> list = null;

        for(int j = 0; j<arrayLists[0].size();j++) {
            list = utils.getAllFilesDirectoryWithNotNullImageDirectories(arrayLists[0].get(j));
            for (int i = 0; i < list.size(); i++) {
                Bitmap compressedBitmap = ImageUtils.getInstant().getCompressedBitmap(list.get(i));
                Util.saveImage(compressedBitmap, context);
            }
        }
        return String.valueOf(list.size());
    }

    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        progress.setMessage("Compressing...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(false);
        progress.show();
    }


    @Override
    protected void onPostExecute(String result) {
        progress.cancel();
        Util.alertDialog(context, "Result", "Total compressed pictures: " +result+ "\n" + "Save images to: "+
        new SessionManager().getPreferences(context, Util.preferenceKey));
    }
}