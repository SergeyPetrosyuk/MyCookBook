package com.mlsdev.serhiy.mycookbook.async.tasc.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.model.Recipe;

/**
 * Created by android on 01.04.15.
 */
public class LoadRecipeTaskLoader extends AsyncTaskLoader<Recipe> {
    private Integer mRecipeId;
    private Recipe mData;

    public LoadRecipeTaskLoader(Context context, Integer recipeId) {
        super(context);
        this.mRecipeId = recipeId;
    }

    @Override
    public Recipe loadInBackground() {
        return DAO.getRecipeById(getContext(), mRecipeId);
    }

    @Override
    public void deliverResult(Recipe data) {
        mData = data;
        if (isReset()){ return; }
        if (isStarted()){ super.deliverResult(data); }
    }

    @Override
    protected void onStartLoading() {
        if (mData != null){
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null){
            forceLoad();
        }

        super.onStartLoading();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();

        if (mData != null){
            mData = null;
        }
    }
}
