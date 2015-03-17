package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.content.Intent;
import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnRecipeAddedListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by android on 11.03.15.
 */
public class UpdateRecipeTask extends AsyncTask<Intent, Void, Boolean> {

    private OnRecipeAddedListener listener;

    public UpdateRecipeTask(OnRecipeAddedListener listener) {
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Intent... params) {

        Intent dataForUpdate = params[0];

        boolean isUpdated = DAO.updateRecipe(listener.getContext(), dataForUpdate);

        return isUpdated;
    }

    @Override
    protected void onPostExecute(Boolean isUpdated) {
        listener.recipeUpdated(isUpdated);
    }
}
