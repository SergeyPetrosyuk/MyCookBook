package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.mlsdev.serhiy.mycookbook.model.Recipe;

import java.util.List;

/**
 * Created by android on 11.03.15.
 */
public interface IRecipesView {

    void showRecipeList(List<Recipe> recipeList);
    Context getContext();
    void openRecipe(Bundle recipeData);
    BaseAdapter getAdepter();

    void showCategoryEditor();
    void hideCategoryEditor();

    void showReadyButton();
    void hideReadyButton();

    void setupNewCategoryTitle();
    void openAddRecipeScreen();
    void setupCategoryName(String categoryName);

}
