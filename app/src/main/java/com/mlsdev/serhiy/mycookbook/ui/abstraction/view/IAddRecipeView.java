package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Map;

/**
 * Created by android on 05.03.15.
 */
public interface IAddRecipeView {

    void startLoadImage();
    void stopLoadImage();
    void setUpImage(Bitmap bitmap);
    Context getContext();
    void setupCategoryName(String categoryName);
    void openCreatedRecipe(long id);
    void onErrorCategory();

}
