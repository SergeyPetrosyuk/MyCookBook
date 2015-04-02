package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.content.Intent;
import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnRecipeAddedListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by android on 11.03.15.
 */
public class UpdateRecipeTask extends AsyncTask<Intent, Void, Recipe> {

    private OnRecipeAddedListener listener;

    public UpdateRecipeTask(OnRecipeAddedListener listener) {
        this.listener = listener;
    }

    @Override
    protected Recipe doInBackground(Intent... params) {

        Intent dataForUpdate = params[0];

        Recipe recipe = DAO.updateRecipe(listener.getContext(), dataForUpdate);

        return recipe;
    }

    @Override
    protected void onPostExecute(Recipe recipe) {
        if (recipe != null)
            listener.recipeUpdated(true);
        else
            listener.recipeUpdated(false);
    }
}
