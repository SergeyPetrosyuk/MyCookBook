package com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor;

import android.content.Context;

/**
 * Created by android on 18.03.15.
 */
public interface ICategoryEditeOrDeleteInteractor {

    void editCategory(Integer id, String newTitle);
    void deleteCategory(Integer id);
    Context getContext();

}
