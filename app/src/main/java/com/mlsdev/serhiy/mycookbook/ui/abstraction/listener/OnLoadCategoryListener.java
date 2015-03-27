package com.mlsdev.serhiy.mycookbook.ui.abstraction.listener;

import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;

/**
 * Created by android on 27.03.15.
 */
public interface OnLoadCategoryListener {

    void onLoadCategorySuccess(RecipeCategory category);
    void onLoadCategoryFailure();

}
