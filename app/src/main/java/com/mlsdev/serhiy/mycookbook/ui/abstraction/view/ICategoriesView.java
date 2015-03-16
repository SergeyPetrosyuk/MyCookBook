package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

import android.content.Context;
import android.view.MenuItem;

import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;

import java.util.List;

/**
 * Created by android on 10.03.15.
 */
public interface ICategoriesView {

    void addCategory();
    void deleteCategory();
    void editCategory();
    void openAddNewCategoryScreen();
    Context getContext();
    void showList(List<RecipeCategory> categoryList, int displayType);
    void showCategoriesAsList(MenuItem item);
    void showCategoriesAsGrid(MenuItem item);
    void openCategory(int categoryId, String categoryName);

}
