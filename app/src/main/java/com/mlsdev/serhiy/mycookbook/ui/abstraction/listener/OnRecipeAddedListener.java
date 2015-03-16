package com.mlsdev.serhiy.mycookbook.ui.abstraction.listener;

import android.content.Context;

/**
 * Created by android on 11.03.15.
 */
public interface OnRecipeAddedListener {

    void recipeWasAdded(long insertedId);
    Context getContext();
    void onErrorCategory();
}
