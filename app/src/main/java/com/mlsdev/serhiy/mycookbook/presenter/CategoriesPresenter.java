package com.mlsdev.serhiy.mycookbook.presenter;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.adapter.CategoriesListAdapter;
import com.mlsdev.serhiy.mycookbook.interactor.CategoriesInteractor;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.ICategoriesInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnCategoryListLoaded;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.ICategoriesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.ICategoriesView;
import com.mlsdev.serhiy.mycookbook.utils.Constants;
import com.mlsdev.serhiy.mycookbook.utils.PrefManager;

import java.util.List;

/**
 * Created by android on 10.03.15.
 */
public class CategoriesPresenter implements ICategoriesPresenter, OnCategoryListLoaded {

    private ICategoriesView mView;
    private ICategoriesInteractor mInteractor;

    public CategoriesPresenter(ICategoriesView view) {
        this.mView = view;
        mInteractor = new CategoriesInteractor(this, this);
    }

    @Override
    public void loadCategories() {
        mInteractor.loadCategories();
    }

    @Override
    public void addCategory() {
        mView.openAddNewCategoryScreen();
    }

    @Override
    public void deleteCategory(long id) {

    }

    @Override
    public void editCategory(long id) {

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
        mView.openCategory(category.getId(), category.getName());
    }

    @Override
    public void categoriesLoaded(List<RecipeCategory> categoryList) {
        int currentDisplayType = PrefManager.categoriesDisplayType(getContext());
        mView.showList(categoryList, currentDisplayType);
    }
}
