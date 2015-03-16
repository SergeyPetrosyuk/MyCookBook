package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnCategoryCreatedListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by android on 10.03.15.
 */
public class AddCategoryTask extends AsyncTask<String, Void, Long> {

    private OnCategoryCreatedListener categoryCreatedListener;
    private String mCategoryName = "";

    public AddCategoryTask(OnCategoryCreatedListener categoryCreatedListener) {
        this.categoryCreatedListener = categoryCreatedListener;
    }

    @Override
    protected Long doInBackground(String... params) {
        mCategoryName = params[0];

        long insertedCategoryId = DAO.insertCategory(
                categoryCreatedListener.getContext(),
                mCategoryName
            );

        return insertedCategoryId;
    }

    @Override
    protected void onPostExecute(Long insertedCategoryId) {
        if (insertedCategoryId != -1) {
            categoryCreatedListener.onSuccess(Integer.parseInt(insertedCategoryId.toString()), mCategoryName);
        } else {
            categoryCreatedListener.onError();
        }
    }
}
