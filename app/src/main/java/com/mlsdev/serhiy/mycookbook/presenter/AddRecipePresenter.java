package com.mlsdev.serhiy.mycookbook.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.mlsdev.serhiy.mycookbook.interactor.AddRecipeInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.IAddRecipeInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnImageLoadedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnRecipeAddedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IAddRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IAddRecipeView;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

/**
 * Created by android on 06.03.15.
 */
public class AddRecipePresenter implements IAddRecipePresenter, OnImageLoadedListener, OnRecipeAddedListener {
    private IAddRecipeView mView;
    private IAddRecipeInteractor mRecipeInteractor;

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
        mRecipeInteractor.addRecipe(title, ingredients, instructions);
    }

    @Override
    public void setupCategoryId(Intent data) {
        mRecipeInteractor.setupCategoryId(data);
        String categoryName = data.getStringExtra(Constants.EXTRAS_CATEGORY_NAME);
        mView.setupCategoryName(categoryName);
    }

    @Override
    public void recipeWasAdded(long insertedId) {
        mView.openCreatedRecipe(insertedId);
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
    }

    @Override
    public void onErrorLoadImage() {
        mView.stopLoadImage();
    }
}
