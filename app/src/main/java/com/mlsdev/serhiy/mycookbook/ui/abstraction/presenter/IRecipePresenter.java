package com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by android on 13.03.15.
 */
public interface IRecipePresenter {

    void openRecipe(Bundle recipeData, boolean isAfterEditing);
    void openUpdateScreen(Bundle dataForUpdate);
    void setupRecipeData(Bundle recipeData);
    void deleteRecipe();
    Context getContext();

}
