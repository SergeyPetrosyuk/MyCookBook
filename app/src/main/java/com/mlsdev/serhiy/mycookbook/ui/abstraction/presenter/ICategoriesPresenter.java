package com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter;

import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;

import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.ICategoriesView;

/**
 * Created by android on 10.03.15.
 */
public interface ICategoriesPresenter {

    void loadCategories();
    void addCategory();
    void deleteCategory(long id);
    void editCategory(long id);
    Context getContext();
    void changeItemDisplayType(MenuItem item);
    int getIconForDisplayTypeAction();
    void openCategory(AdapterView<?> parent, int position);

}
