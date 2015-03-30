package com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor;

import java.util.List;

/**
 * Created by android on 27.03.15.
 */
public interface IDeleteRecipesInteractor {

    void deleteRecipe(List<Integer> recipeIds);

}
