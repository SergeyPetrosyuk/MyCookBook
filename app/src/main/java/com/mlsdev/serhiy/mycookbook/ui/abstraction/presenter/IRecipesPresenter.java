package com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter;

import android.content.Context;
import android.widget.AdapterView;

/**
 * Created by android on 11.03.15.
 */
public interface IRecipesPresenter {

    void loadRecipeList(int categoryId);
    Context getContext();
    void openRecipe(AdapterView<?> parent, int position);

}
