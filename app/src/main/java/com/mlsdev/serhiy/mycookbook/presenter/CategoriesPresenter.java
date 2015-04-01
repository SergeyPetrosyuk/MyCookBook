package com.mlsdev.serhiy.mycookbook.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.BaseAdapter;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.async.tasc.loader.LoadCategoryListTaskLoader;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnCategoryListLoaded;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.ICategoriesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.ICategoriesView;
import com.mlsdev.serhiy.mycookbook.ui.activity.CategoryActivity;
import com.mlsdev.serhiy.mycookbook.utils.Constants;
import com.mlsdev.serhiy.mycookbook.utils.PrefManager;

import java.util.List;

/**
 * Created by android on 10.03.15.
 */
public class CategoriesPresenter implements ICategoriesPresenter, OnCategoryListLoaded, LoaderManager.LoaderCallbacks<List<RecipeCategory>> {

    private ICategoriesView mView;
    private static final int sCategoryLoaderId = 0;

    public CategoriesPresenter(ICategoriesView view) {
        this.mView = view;
    }

    @Override
    public void addCategory() {
        mView.openAddNewCategoryScreen();
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public void changeItemDisplayType(MenuItem item) {
        int currentDisplayType = PrefManager.categoriesDisplayType(getContext());
        int newDisplayType = currentDisplayType == Constants.PREF_CATS_LIST_DISPLAY_TYPE
                ? Constants.PREF_CATS_GRID_DISPLAY_TYPE
                : Constants.PREF_CATS_LIST_DISPLAY_TYPE;

        PrefManager.setupCategoriesDisplayType(getContext(), newDisplayType);

        if (currentDisplayType == Constants.PREF_CATS_LIST_DISPLAY_TYPE){
            mView.showCategoriesAsGrid(item);
        } else {
            mView.showCategoriesAsList(item);
        }
    }

    @Override
    public int getIconForDisplayTypeAction() {
        int iconResId = PrefManager.categoriesDisplayType(getContext()) ==  Constants.PREF_CATS_GRID_DISPLAY_TYPE
                ? R.mipmap.icon_view_list : R.mipmap.icon_view_grid;

        return iconResId;
    }

    @Override
    public void openCategory(int position) {
        BaseAdapter adapter = mView.getAdapter();
        RecipeCategory category = (RecipeCategory) adapter.getItem(position);

        Intent intent = new Intent(getContext(), CategoryActivity.class);
        intent.putExtra(DBContract.RecipeEntry.COLUMN_CATEGORY_ID, category.getId());
        intent.putExtra(DBContract.CategoryEntry.COLUMN_NAME, category.getName());

        mView.openCategory(intent);
    }

    @Override
    public void viewOnCreateState() {
        mView.getLoaderManagerForPresenter().initLoader(sCategoryLoaderId, null, this);
    }

    @Override
    public void viewOnResumeState() {
        mView.getLoaderManagerForPresenter().restartLoader(sCategoryLoaderId, null, this);
    }

    @Override
    public void categoriesLoaded(List<RecipeCategory> categoryList) {
        int currentDisplayType = PrefManager.categoriesDisplayType(getContext());
        mView.showList(categoryList, currentDisplayType);
    }

    @Override
    public Loader<List<RecipeCategory>> onCreateLoader(int id, Bundle args) {
        Loader<List<RecipeCategory>> loader = null;

        if (sCategoryLoaderId == id) {
            loader = new LoadCategoryListTaskLoader(mView.getContext());
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<RecipeCategory>> loader, List<RecipeCategory> categoryList) {
        int currentDisplayType = PrefManager.categoriesDisplayType(getContext());
        mView.showList(categoryList, currentDisplayType);
    }

    @Override
    public void onLoaderReset(Loader<List<RecipeCategory>> loader) {

    }

}
