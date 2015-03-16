package com.mlsdev.serhiy.mycookbook.ui.abstraction.listener;

import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;

import java.util.List;

/**
 * Created by android on 10.03.15.
 */
public interface OnCategoryListLoaded {

    void categoriesLoaded(List<RecipeCategory> categoryList);

}
