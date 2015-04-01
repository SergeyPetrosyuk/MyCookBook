package com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;

/**
 * Created by android on 11.03.15.
 */
public interface IRecipesPresenter {

    Context getContext();
    void openRecipe(int position);
    void showEditor();
    void activateReadyButton(int editTextChars);
    void editCategory(int categoryId, String newTitle);
    void openAddRecipeScreen();
    void setCategoryData(Bundle categoryData);
    Integer getCategoryId();
    void setupCategoryName();
    void deleteRecipes();
    void deleteCategory();
    boolean isOnlyFavorites();
    void setupIsFavorite(boolean newStatus);
    void viewOnCreateState();
    void viewOnResumeState();
}
