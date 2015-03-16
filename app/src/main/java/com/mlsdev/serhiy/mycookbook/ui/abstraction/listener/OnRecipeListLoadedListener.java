package com.mlsdev.serhiy.mycookbook.ui.abstraction.listener;

import com.mlsdev.serhiy.mycookbook.model.Recipe;

import java.util.List;

/**
 * Created by android on 11.03.15.
 */
public interface OnRecipeListLoadedListener {

    void recipeListLoaded(List<Recipe> recipeList);

}
