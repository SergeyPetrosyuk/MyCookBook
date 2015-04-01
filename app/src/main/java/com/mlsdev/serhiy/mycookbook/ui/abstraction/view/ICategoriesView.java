package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.BaseAdapter;

import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;

import java.util.List;

/**
 * Created by android on 10.03.15.
 */
public interface ICategoriesView {

    void openAddNewCategoryScreen();
    Context getContext();
    void showList(List<RecipeCategory> categoryList, int displayType);
    void showCategoriesAsList(MenuItem item);
    void showCategoriesAsGrid(MenuItem item);
    void openCategory(Intent intent);
    BaseAdapter getAdapter();
    LoaderManager getLoaderManagerForPresenter();

}
