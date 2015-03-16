package com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter;

import android.content.Intent;

/**
 * Created by android on 06.03.15.
 */
public interface IAddRecipePresenter {

    void loadImage(Intent data);
    void addRecipe(String title, String ingredients, String instructions);
    void setupCategoryId(Intent data);

}
