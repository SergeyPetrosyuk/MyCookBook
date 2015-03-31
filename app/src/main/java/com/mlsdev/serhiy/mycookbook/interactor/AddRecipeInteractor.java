package com.mlsdev.serhiy.mycookbook.interactor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.asynk_task.AddRecipeTask;
import com.mlsdev.serhiy.mycookbook.asynk_task.LoadImageFromStorageTask;
import com.mlsdev.serhiy.mycookbook.asynk_task.UpdateRecipeTask;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.IAddRecipeInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnImageLoadedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnRecipeAddedListener;
import com.mlsdev.serhiy.mycookbook.utils.Constants;
import com.squareup.picasso.Picasso;

import static com.mlsdev.serhiy.mycookbook.database.DBContract.*;

/**
 * Created by android on 06.03.15.
 */
public class AddRecipeInteractor implements IAddRecipeInteractor, OnImageLoadedListener, OnRecipeAddedListener {
    private OnImageLoadedListener mListener;
    private OnRecipeAddedListener mOnRecipeAddedListener;
    private Uri mUri;
    private int mCategoryId = 0;
    private Intent mDataForInsert;

    public AddRecipeInteractor(OnImageLoadedListener mListener, OnRecipeAddedListener mOnRecipeAddedListener) {
        this.mListener = mListener;
        this.mOnRecipeAddedListener = mOnRecipeAddedListener;
    }

    @Override
    public void loadImage(Intent data) {
        Uri uri = null;

        if (data != null){ uri = data.getData(); }

        if (uri != null){
            mUri = uri;
            new LoadImageFromStorageTask(this).execute(uri);
        }
    }

    @Override
    public void addRecipe(String title, String ingredients, String instructions) {

        if (mCategoryId == 0){
            onErrorCategory();
        } else {
            mDataForInsert = new Intent();
            String imageUri = "";
            if (mUri != null) {
                imageUri = mUri.getPath();
            }

            mDataForInsert.putExtra(RecipeEntry.COLUMN_TITLE, title);
            mDataForInsert.putExtra(RecipeEntry.COLUMN_INGREDIENTS, ingredients);
            mDataForInsert.putExtra(RecipeEntry.COLUMN_INSTRUCTIONS, instructions);
            mDataForInsert.putExtra(RecipeEntry.COLUMN_IMAGE_URI, imageUri);
            mDataForInsert.putExtra(RecipeEntry.COLUMN_CATEGORY_ID, mCategoryId);

            new AddRecipeTask(this).execute(mDataForInsert);
        }
    }

    @Override
    public void setupCategoryId(Intent data) {
        mCategoryId = data.getIntExtra(RecipeEntry.COLUMN_CATEGORY_ID, 1);
    }

    @Override
    public void updateRecipe(Intent updatedData) {

        if (mUri != null){
            updatedData.putExtra(RecipeEntry.COLUMN_IMAGE_URI, mUri.getPath());
        }

        new UpdateRecipeTask(this).execute(new Intent().putExtras(updatedData));
    }

    @Override
    public Context getContext() {
        return mListener.getContext();
    }

    @Override
    public void onErrorCategory() {
        mOnRecipeAddedListener.onErrorCategory();
    }

    @Override
    public void onSuccessLoadImage(Bitmap bitmap) {
        mListener.onSuccessLoadImage(bitmap);
    }

    @Override
    public void onErrorLoadImage() {
        mListener.onErrorLoadImage();
    }

    @Override
    public void recipeWasAdded(long insertedId) {
        mOnRecipeAddedListener.recipeWasAdded(insertedId);
    }

    @Override
    public void recipeUpdated(boolean isUpdated) {
        mOnRecipeAddedListener.recipeUpdated(isUpdated);
    }
}
