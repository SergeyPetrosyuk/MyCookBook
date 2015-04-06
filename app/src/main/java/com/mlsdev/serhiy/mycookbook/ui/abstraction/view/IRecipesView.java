package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipeListAdapter;

import java.util.List;

/**
 * Created by android on 11.03.15.
 */
public interface IRecipesView {
    void showRecipeList(List<Recipe> recipeList);
    Context getContext();
    void openRecipe(Intent recipeData);
    IRecipeListAdapter getAdepter();
    void showCategoryEditor();
    void hideCategoryEditor();
    void showReadyButton();
    void hideReadyButton();
    void openAddRecipeScreen();
    void setupCategoryName(String categoryName);
    void showDeleteAction(boolean isShow);
    void showEditAction(boolean isShow);
    void showUnselectAllAction(boolean isShow);
    void onDeleteCategorySuccess();
    void onEditCategorySuccess();
    void onEditCategoryError();
    String getNewCategoryName();
    void showDeleteCategoryDialog();
    void showDeleteRecipesDialog();

    LoaderManager getLoaderManagerForPresenter();
}
