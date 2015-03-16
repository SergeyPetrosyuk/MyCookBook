package com.mlsdev.serhiy.mycookbook.ui.abstraction.listener;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by android on 06.03.15.
 */
public interface OnImageLoadedListener {

    Context getContext();
    void onSuccessLoadImage(Bitmap bitmap);
    void onErrorLoadImage();

}
