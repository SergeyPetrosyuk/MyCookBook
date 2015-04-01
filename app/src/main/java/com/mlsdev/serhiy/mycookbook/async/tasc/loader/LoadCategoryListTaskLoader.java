package com.mlsdev.serhiy.mycookbook.async.tasc.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;

import java.util.List;

/**
 * Created by android on 01.04.15.
 */
public class LoadCategoryListTaskLoader extends AsyncTaskLoader<List<RecipeCategory>> {

    private List<RecipeCategory> mData;

    public LoadCategoryListTaskLoader(Context context) {
        super(context);
    }

    @Override
    public List<RecipeCategory> loadInBackground() {
        List<RecipeCategory> categoryList = DAO.getRecipeCategoryList(getContext());
        return categoryList;
    }

    @Override
    public void deliverResult(List<RecipeCategory> data) {
        if (isReset()){
            return;
        }

        List<RecipeCategory> oldData = mData;
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldData != null && oldData != data){
            oldData = null;
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
