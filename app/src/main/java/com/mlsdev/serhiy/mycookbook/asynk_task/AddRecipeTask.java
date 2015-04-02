package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.content.Intent;
import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnRecipeAddedListener;

/**
 * Created by android on 11.03.15.
 */
public class AddRecipeTask extends AsyncTask<Intent, Void, Recipe> {

    private OnRecipeAddedListener listener;

    public AddRecipeTask(OnRecipeAddedListener listener) {
        this.listener = listener;
    }

    @Override
    protected Recipe doInBackground(Intent... params) {

        Intent dataForInsert = params[0];

        Recipe recipe = DAO.insertRecipe(listener.getContext(), dataForInsert);

        return recipe;
    }

    @Override
    protected void onPostExecute(Recipe recipe) {
        listener.recipeWasAdded(recipe.get_id());
    }
}
