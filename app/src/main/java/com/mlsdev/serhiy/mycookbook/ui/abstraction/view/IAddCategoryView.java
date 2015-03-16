package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

import android.content.Context;

/**
 * Created by android on 10.03.15.
 */
public interface IAddCategoryView {

    void addCategory();
    void openCreatedCategory(int categoryId, String categoryName);
    void showError(String errorText);
    void showProgressBar();
    void hideProgressBar();
    Context getContext();
    void backToCategoriesList();
    
}
