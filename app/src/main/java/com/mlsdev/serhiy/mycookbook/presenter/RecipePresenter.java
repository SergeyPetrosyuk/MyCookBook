package com.mlsdev.serhiy.mycookbook.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.async.tasc.loader.LoadRecipeTaskLoader;
import com.mlsdev.serhiy.mycookbook.asynk_task.DeleteRecipeTask;
import com.mlsdev.serhiy.mycookbook.asynk_task.MakeRecipeFavoriteTask;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.listener.OnMoveToFavoritesListener;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.IRecipeInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnDeleteRecipeListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipeView;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

import static com.mlsdev.serhiy.mycookbook.database.DBContract.*;

/**
 * Created by android on 13.03.15.
 */
public class RecipePresenter implements IRecipePresenter, OnDeleteRecipeListener, OnMoveToFavoritesListener,
                                        LoaderManager.LoaderCallbacks<Recipe> {
    private IRecipeView mView;
    private Bundle mRecipeData;
    private IRecipeInteractor mInteractor;
    private boolean mIsRecipeFavorite = false;
    private final int sRecipeLoaderId = 3;

    public RecipePresenter(IRecipeView mView) {
        this.mView = mView;
        mInteractor = new DeleteRecipeTask(this,this);
    }

    private void openRecipe(Recipe recipe) {

        Uri imageUri;

        String imageUriStr = recipe.getImageUri();

        if (!imageUriStr.equals("")){
            imageUri = Uri.parse("content://media" + imageUriStr);
            mView.setImage(imageUri);
        }

        mIsRecipeFavorite = recipe.getIsFavorite();
        String recipeTitle = recipe.getTitle();
        String recipeCategory = recipe.getCategoryName();
        String recipeIngredients = recipe.getIngredients();
        String recipeInstructions = recipe.getInstructions();

        mView.setRecipeTitle(recipeTitle);
        mView.setCategoryTitle(recipeCategory);
        mView.setIngredients(recipeIngredients);
        mView.setInstructions(recipeInstructions);
        mView.showContent();
        mView.activateFavorite();
    }

    @Override
    public void openUpdateScreen(Bundle dataForUpdate) {
        mView.showUpdateFragment();
    }

    @Override
    public void setupRecipeData(Bundle aRecipeData) {
        mRecipeData = aRecipeData;
    }

    @Override
    public void deleteRecipe() {
        int recipeId = mRecipeData.getInt(RecipeEntry.COLUMN_ID);
        mInteractor.deleteRecipe(recipeId);
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public boolean isRecipeFavorite() {
        return mIsRecipeFavorite;
    }

    @Override
    public void favoriteAction() {
        Recipe recipe = new Recipe();
        recipe.set_id(mRecipeData.getInt(RecipeEntry.COLUMN_ID));
        recipe.setIsFavorite(mIsRecipeFavorite);

        new MakeRecipeFavoriteTask(this, this).execute(recipe);
    }

    @Override
    public void viewOnCreateState() {
        mView.getLoaderManagerForPresenter().initLoader(sRecipeLoaderId, null, this);
    }

    @Override
    public void viewOnResumeState() {
        mView.getLoaderManagerForPresenter().restartLoader(sRecipeLoaderId, null, this);
    }

    @Override
    public void onSuccess() {
        mView.onRecipeDeleted();
    }

    // Recipe deleting error
    @Override
    public void onError() {

    }

    @Override
    public void onMoveToFavoritesSuccess(boolean aNewStatus) {
        mView.updateFavoriteStatus(aNewStatus);
    }

    @Override
    public void onMoveToFavoritesError() {

    }

    @Override
    public Loader<Recipe> onCreateLoader(int id, Bundle args) {
        Loader<Recipe> loader = null;

        if (id == sRecipeLoaderId){
            loader = new LoadRecipeTaskLoader(mView.getContext(), mRecipeData.getInt(RecipeEntry.COLUMN_ID));
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Recipe> loader, Recipe loadedRecipe) {
        if (loadedRecipe != null)
            openRecipe(loadedRecipe);
    }

    @Override
    public void onLoaderReset(Loader<Recipe> loader) {

    }
}
