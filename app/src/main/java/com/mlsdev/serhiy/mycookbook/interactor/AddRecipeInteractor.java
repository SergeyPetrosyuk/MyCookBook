package com.mlsdev.serhiy.mycookbook.interactor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.mlsdev.serhiy.mycookbook.asynk_task.AddRecipeTask;
import com.mlsdev.serhiy.mycookbook.asynk_task.LoadImageFromStorageTask;
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
            Intent dataForInsert = new Intent();
            String imageUri = "";
            if (mUri != null) {
                imageUri = mUri.getPath();
            }

            dataForInsert.putExtra(RecipeEntry.COLUMN_TITLE, title);
            dataForInsert.putExtra(RecipeEntry.COLUMN_INGREDIENTS, ingredients);
            dataForInsert.putExtra(RecipeEntry.COLUMN_INSTRUCTIONS, instructions);
            dataForInsert.putExtra(RecipeEntry.COLUMN_IMAGE_URI, imageUri);
            dataForInsert.putExtra(RecipeEntry.COLUMN_CATEGORY_ID, mCategoryId);

            new AddRecipeTask(this).execute(dataForInsert);
        }
    }

    @Override
    public void setupCategoryId(Intent data) {
        mCategoryId = data.getIntExtra(Constants.EXTRAS_CATEGORY_ID, 1);
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
}
