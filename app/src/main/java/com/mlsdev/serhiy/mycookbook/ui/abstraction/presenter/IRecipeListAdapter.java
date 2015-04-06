package com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter;

import com.mlsdev.serhiy.mycookbook.model.Recipe;

import java.util.List;

/**
 * Created by android on 06.04.15.
 */
public interface IRecipeListAdapter {

    void setData(List<Recipe> recipeList);
    Recipe getRecipe(int position);
    void unselectAllRecipes();
    List<Integer> getSelectedRecipeIds();

}
