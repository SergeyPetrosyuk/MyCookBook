package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

import android.content.Context;
import android.net.Uri;

/**
 * Created by android on 13.03.15.
 */
public interface IRecipeView {

    void setImage(Uri uri);
    void setRecipeTitle(String title);
    void setIngredients(String ingredients);
    void setInstructions(String instructions);
    void setCategoryTitle(String categoryTitle);
    Context getContext();
    void showContent();

}
