package com.mlsdev.serhiy.mycookbook.ui.abstraction.view;

import android.content.Context;
import android.content.Intent;

import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;

import java.util.List;

/**
 * Created by android on 11.03.15.
 */
public interface IChooseCategoryView {

    void showCategoryList(List<RecipeCategory> categoryList);
    Context getContext();
    void closeItSelfWithResult(Intent dataForResult);

}
