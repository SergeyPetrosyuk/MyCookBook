package com.mlsdev.serhiy.mycookbook.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.mlsdev.serhiy.mycookbook.async.tasc.loader.LoadRecipeTaskLoader;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.interactor.AddRecipeInteractor;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.IAddRecipeInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnImageLoadedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnRecipeAddedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IAddRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IAddRecipeView;
import com.mlsdev.serhiy.mycookbook.utils.Constants;
import com.mlsdev.serhiy.mycookbook.utils.PrefManager;

import static com.mlsdev.serhiy.mycookbook.database.DBContract.*;

/**
 * Created by android on 06.03.15.
 */
public class AddRecipePresenter implements IAddRecipePresenter, OnImageLoadedListener, OnRecipeAddedListener,
                                           LoaderManager.LoaderCallbacks<Recipe>{
    private IAddRecipeView mView;
    private IAddRecipeInteractor mRecipeInteractor;
    private Intent mRecipeData;
    private boolean mIsEditing = false;
    private final int sRecipeLoaderId = 4;

    public AddRecipePresenter(IAddRecipeView mView) {
        this.mView = mView;
        mRecipeInteractor = new AddRecipeInteractor(this, this);
    }

    /**
     * @param data is an intent which contains the uri of the image which was chosen
     * */
    @Override
    public void loadImage(Intent data) {
        mView.startLoadImage();
        mRecipeInteractor.loadImage(data);
    }

    @Override
    public void addRecipe(String title, String ingredients, String instructions) {
        mView.startAdding();
        mRecipeInteractor.addRecipe(title, ingredients, instructions);
    }

    @Override
    public void setupData(Intent data) {
        mRecipeData = data;
        mRecipeInteractor.setupCategoryId(mRecipeData);
        mIsEditing = mRecipeData.getBooleanExtra(Constants.EXTRAS_IS_UPDATE, false);

        mView.setupCategoryName(mRecipeData.getStringExtra(CategoryEntry.COLUMN_NAME));

        if (mIsEditing){
            mView.getLoaderManagerForPresenter().initLoader(sRecipeLoaderId, null, this);
            mView.setActionBarTitle();
        }
    }

    @Override
    public void updateRecipe(String title, String ingredients, String instructions) {
        mView.startAdding();
        mRecipeData.putExtra(RecipeEntry.COLUMN_TITLE, title);
        mRecipeData.putExtra(RecipeEntry.COLUMN_INGREDIENTS, ingredients);
        mRecipeData.putExtra(RecipeEntry.COLUMN_INSTRUCTIONS, instructions);
        mRecipeInteractor.updateRecipe(mRecipeData);
    }

    @Override
    public boolean isEditing() {
        return mIsEditing;
    }

    @Override
    public void recipeWasAdded(long insertedId) {
        mView.openCreatedRecipe(insertedId);
        setDataChanged();
    }

    @Override
    public void recipeUpdated(boolean isUpdated) {
        setDataChanged();

        if (isUpdated)
            mView.onRecipeUpdated(mRecipeData);
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public void onErrorCategory() {
        mView.onErrorCategory();
    }

    /**
     * @param bitmap is a bitmap-image which was loaded by an interactor
     * */
    @Override
    public void onSuccessLoadImage(Bitmap bitmap) {
        mView.stopLoadImage();
        mView.setUpImage(bitmap);
        PrefManager.setRecipeImageStateChanged(getContext(), true);
    }

    @Override
    public void onErrorLoadImage() {
        mView.stopLoadImage();
    }

    @Override
    public Loader<Recipe> onCreateLoader(int id, Bundle args) {
        Loader<Recipe> loader = null;

        if (id == sRecipeLoaderId){
            loader = new LoadRecipeTaskLoader(mView.getContext(),
                    mRecipeData.getIntExtra(RecipeEntry.COLUMN_ID, 0));
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Recipe> loader, Recipe loadedRecipe) {
        Uri uri;
        String imageUriStr = loadedRecipe.getImageUri();

        if (!imageUriStr.equals(Constants.EMPTY_STRING)){
            uri = Uri.parse(Constants.CONTENT_MEDIA + imageUriStr);
            mView.setUpImage(uri);
        }

        mView.setupTitle(loadedRecipe.getTitle());
        mView.setupIngredients(loadedRecipe.getIngredients());
        mView.setupInstructions(loadedRecipe.getInstructions());
        mView.setupCategoryName(loadedRecipe.getCategoryName());
    }

    @Override
    public void onLoaderReset(Loader<Recipe> loader) {

    }

    private void setDataChanged(){
        PrefManager.setRecipeListStateChanged(mView.getContext(), true);
    }
}
