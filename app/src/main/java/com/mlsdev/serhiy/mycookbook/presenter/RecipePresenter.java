package com.mlsdev.serhiy.mycookbook.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.asynk_task.DeleteRecipeTask;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.IRecipeInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnDeleteRecipeListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipeView;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

import static com.mlsdev.serhiy.mycookbook.database.DBContract.*;

/**
 * Created by android on 13.03.15.
 */
public class RecipePresenter implements IRecipePresenter, OnDeleteRecipeListener {
    private IRecipeView mView;
    private Bundle mRecipeData;
    private IRecipeInteractor mInteractor;

    public RecipePresenter(IRecipeView mView) {
        this.mView = mView;
        mInteractor = new DeleteRecipeTask(this,this);
    }

    @Override
    public void openRecipe(Bundle recipeData, boolean isAfterEditing) {

        Uri imageUri;

        String imageUriStr = recipeData.getString(RecipeEntry.COLUMN_IMAGE_URI);

        if (!imageUriStr.equals("")){
            imageUri = Uri.parse("content://media" + imageUriStr);
            mView.setImage(imageUri);
        }

        String recipeTitle = recipeData.getString(RecipeEntry.COLUMN_TITLE, mView.getContext().getString(R.string.app_name));
        String recipeCategory = recipeData.getString(CategoryEntry.COLUMN_NAME);
        String recipeIngredients = recipeData.getString(RecipeEntry.COLUMN_INGREDIENTS);
        String recipeInstructions = recipeData.getString(RecipeEntry.COLUMN_INSTRUCTIONS);

        mView.setRecipeTitle(recipeTitle);
        mView.setCategoryTitle(recipeCategory);
        mView.setIngredients(recipeIngredients);
        mView.setInstructions(recipeInstructions);
        mView.showContent();
    }

    @Override
    public void openUpdateScreen(Bundle dataForUpdate) {
        dataForUpdate.putBoolean(Constants.EXTRAS_IS_UPDATE, true);
        mView.showUpdateFragment(dataForUpdate);
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
    public void onSuccess() {
        mView.onRecipeDeleted();
    }

    @Override
    public void onError() {

    }
}
