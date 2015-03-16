package com.mlsdev.serhiy.mycookbook.ui.abstraction.listener;

import android.content.Context;

/**
 * Created by android on 10.03.15.
 */
public interface OnCategoryCreatedListener {

    void onSuccess(int createdCategoryId, String createdCategoryName);
    void onError();
    Context getContext();

}
