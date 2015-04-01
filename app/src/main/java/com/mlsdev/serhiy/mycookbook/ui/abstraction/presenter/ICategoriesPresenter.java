package com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter;

import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;

import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.ICategoriesView;

/**
 * Created by android on 10.03.15.
 */
public interface ICategoriesPresenter {

    void addCategory();
    Context getContext();
    void changeItemDisplayType(MenuItem item);
    int getIconForDisplayTypeAction();
    void openCategory(int position);
    void viewOnCreateState();
    void viewOnResumeState();
}
