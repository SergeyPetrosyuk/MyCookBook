package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

import android.content.Context;
import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.model.Recipe;

import java.util.List;

/**
 * Created by android on 11.03.15.
 */
public interface IRecipesView {

    void showRecipeList(List<Recipe> recipeList);
    Context getContext();
    void openRecipe(Bundle recipeData);

}
