package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnImageLoadedListener;

/**
 * Created by android on 06.03.15.
 */
public class LoadImageFromStorageTask extends AsyncTask<Uri, Void, Bitmap> {
    private OnImageLoadedListener mListener;

    public LoadImageFromStorageTask(OnImageLoadedListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected Bitmap doInBackground(Uri... params) {
        Uri uri = params[0];

        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = mListener.getContext().getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        return  BitmapFactory.decodeFile(picturePath, options);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (bitmap != null){
            mListener.onSuccessLoadImage(bitmap);
        } else {
            mListener.onErrorLoadImage();
        }
    }
}
