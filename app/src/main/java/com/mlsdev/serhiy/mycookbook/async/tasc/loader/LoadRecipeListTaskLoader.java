package com.mlsdev.serhiy.mycookbook.async.tasc.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

import java.util.List;

/**
 * Created by android on 01.04.15.
 */
public class LoadRecipeListTaskLoader extends AsyncTaskLoader<List<Recipe>> {

    private List<Recipe> mData;
    private Bundle mArgs;

    public LoadRecipeListTaskLoader(Context context, Bundle args) {
        super(context);
        this.mArgs = args;
    }

    @Override
    public List<Recipe> loadInBackground() {
        int categoryId = mArgs.getInt(DBContract.RecipeEntry.COLUMN_CATEGORY_ID);
        boolean isFavorite = mArgs.getBoolean(Constants.EXTRAS_IS_FAVORITE);
        List<Recipe> recipeList;

        if (!isFavorite) {
            recipeList = DAO.getRecipeList(getContext(), categoryId);
        } else {
            recipeList = DAO.getRecipeListByFavoriteStatus(getContext(), categoryId);
        }

        return recipeList;
    }

    @Override
    public void deliverResult(List<Recipe> data) {
        mData = data;

        if (isReset()){
            return;
        }

        if (isStarted()) {
            super.deliverResult(data);
        }
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
