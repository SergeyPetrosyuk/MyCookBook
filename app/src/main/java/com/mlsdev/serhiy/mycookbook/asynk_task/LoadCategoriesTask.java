package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.content.Context;
import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnCategoryListLoaded;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 10.03.15.
 */
public class LoadCategoriesTask extends AsyncTask<Void, Void, List<RecipeCategory>> {

    private Context mContext;
    private OnCategoryListLoaded listener;

    public LoadCategoriesTask(Context mContext, OnCategoryListLoaded listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    protected List<RecipeCategory> doInBackground(Void... params) {

        List<RecipeCategory> categoryList = DAO.getRecipeCategoryList(mContext);

        return categoryList;
    }

    @Override
    protected void onPostExecute(List<RecipeCategory> recipeCategories) {
        listener.categoriesLoaded(recipeCategories);
    }
}
