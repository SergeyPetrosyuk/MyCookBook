package com.mlsdev.serhiy.mycookbook.presenter;

import android.net.Uri;
import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipeView;

import static com.mlsdev.serhiy.mycookbook.database.DBContract.*;

/**
 * Created by android on 13.03.15.
 */
public class RecipePresenter implements IRecipePresenter {
    private IRecipeView mView;

    public RecipePresenter(IRecipeView mView) {
        this.mView = mView;
    }

    @Override
    public void openRecipe(Bundle recipeData) {
        Uri imageUri;

        String imageUriStr = recipeData.getString(RecipeEntry.COLUMN_IMAGE_URI);

        if (!imageUriStr.equals("")){
            imageUri = Uri.parse("content://media" + imageUriStr);
            mView.setImage(imageUri);
        }

        String recipeTitle = recipeData.getString(RecipeEntry.COLUMN_TITLE, mView.getContext().getString(R.string.app_name));
        String recipeCategory = recipeData.getString(CategoryEntry.COLUMN_NAME);
        String recipeIngredients = recipeData.getString(RecipeEntry.COLUMN_INGREDIENTS);
        String recipeInstructions = recipeData.getString(RecipeEntry.COLUMN_INSTRUCTIONS);

        mView.setRecipeTitle(recipeTitle);
        mView.setCategoryTitle(recipeCategory);
        mView.setIngredients(recipeIngredients);
        mView.setInstructions(recipeInstructions);
        mView.showContent();
    }
}
