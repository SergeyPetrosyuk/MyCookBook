package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.ICategoryEditeOrDeleteInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnCategoryEditDeleteListener;

/**
 * Created by android on 18.03.15.
 */
public class UpdateCategoryTask extends AsyncTask<Object, Void, Integer> {

    private OnCategoryEditDeleteListener mListener;
    private ICategoryEditeOrDeleteInteractor mInteractor;

    public UpdateCategoryTask(OnCategoryEditDeleteListener mListener, ICategoryEditeOrDeleteInteractor mInteractor) {
        this.mListener = mListener;
        this.mInteractor = mInteractor;
    }

    @Override
    protected Integer doInBackground(Object... params) {
        Integer categoryId = (Integer) params[0];
        String newCategoryTitle = (String) params[1];

        Integer updatedRows = DAO.updateCategory(mInteractor.getContext(), categoryId, newCategoryTitle);

        return updatedRows;
    }

    @Override
    protected void onPostExecute(Integer updatedRows) {
        if (updatedRows != -1){
            mListener.onSuccessEditCategory();
        } else {
            mListener.onErrorEditCategory();
        }
    }
}
