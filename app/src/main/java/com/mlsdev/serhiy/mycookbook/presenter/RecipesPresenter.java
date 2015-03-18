package com.mlsdev.serhiy.mycookbook.presenter;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;

import com.mlsdev.serhiy.mycookbook.adapter.RecipeAdapter;
import com.mlsdev.serhiy.mycookbook.asynk_task.LoadRecipeListTask;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnRecipeListLoadedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipesView;

import java.util.List;

import static com.mlsdev.serhiy.mycookbook.database.DBContract.*;

/**
 * Created by android on 11.03.15.
 */
public class RecipesPresenter implements IRecipesPresenter, OnRecipeListLoadedListener {

    private IRecipesView mView;
    private boolean mIsEditorOpened = false;

    public RecipesPresenter(IRecipesView mView) {
        this.mView = mView;
    }

    @Override
    public void loadRecipeList(int categoryId) {
        new LoadRecipeListTask(this, this).execute(categoryId);
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public void openRecipe(int position) {
        RecipeAdapter adapter = (RecipeAdapter) mView.getAdepter();
        Recipe recipe = (Recipe) adapter.getItem(position);

        Bundle data = new Bundle();

        data.putInt(RecipeEntry.COLUMN_ID, recipe.get_id());
        data.putInt(RecipeEntry.COLUMN_CATEGORY_ID, recipe.getCategoryId());
        data.putString(CategoryEntry.COLUMN_NAME, recipe.getCategoryName());
        data.putString(RecipeEntry.COLUMN_TITLE, recipe.getTitle());
        data.putString(RecipeEntry.COLUMN_IMAGE_URI, recipe.getImageUri());
        data.putString(RecipeEntry.COLUMN_INGREDIENTS, recipe.getIngredients());
        data.putString(RecipeEntry.COLUMN_INSTRUCTIONS, recipe.getInstructions());

        mView.openRecipe(data);
    }

    @Override
    public void showEditor() {
        if (mIsEditorOpened){
            mView.hideCategoryEditor();
        } else {
            mView.showCategoryEditor();
        }

        mIsEditorOpened = mIsEditorOpened ? false : true;
    }

    @Override
    public void recipeListLoaded(List<Recipe> recipeList) {
        mView.showRecipeList(recipeList);
    }
}
