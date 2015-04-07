package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Map;

/**
 * Created by android on 05.03.15.
 */
public interface IAddRecipeView {

    void startLoadImage();
    void stopLoadImage();
    void setUpImage(Bitmap bitmap);
    void setUpImage(Uri imageUri);
    void setupTitle(String title);
    void setupIngredients(String ingredients);
    void setupInstructions(String instructions);
    Context getContext();
    void setupCategoryName(String categoryName);
    void openCreatedRecipe(long id);
    void onErrorCategory();
    void backToRecipe();
    void startAdding();
    void stopAdding();
    void onRecipeUpdated(Intent updatedRecipeData);
    LoaderManager getLoaderManagerForPresenter();
    void setActionBarTitle();
}
