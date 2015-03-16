package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.content.Intent;
import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnRecipeAddedListener;

/**
 * Created by android on 11.03.15.
 */
public class AddRecipeTask extends AsyncTask<Intent, Void, Long> {

    private OnRecipeAddedListener listener;

    public AddRecipeTask(OnRecipeAddedListener listener) {
        this.listener = listener;
    }

    @Override
    protected Long doInBackground(Intent... params) {

        Intent dataForInsert = params[0];

        long insertedId = DAO.insertRecipe(listener.getContext(), dataForInsert);

        return insertedId;
    }

    @Override
    protected void onPostExecute(Long id) {
        listener.recipeWasAdded(id);
    }
}
