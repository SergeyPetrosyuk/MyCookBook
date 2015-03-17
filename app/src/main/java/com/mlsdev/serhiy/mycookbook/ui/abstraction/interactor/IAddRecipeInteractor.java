package com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by android on 06.03.15.
 */
public interface IAddRecipeInteractor {

    void loadImage(Intent data);
    void addRecipe(String title, String ingredients, String instructions);
    void setupCategoryId(Intent data);
    void updateRecipe(Intent updatedData);

}
