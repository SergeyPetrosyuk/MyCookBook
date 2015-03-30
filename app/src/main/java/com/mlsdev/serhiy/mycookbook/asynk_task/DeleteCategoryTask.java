package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnEditDeleteListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;

/**
 * Created by android on 30.03.15.
 */
public class DeleteCategoryTask extends AsyncTask<Integer, Void, Integer>{
    private IRecipesPresenter mPresenter;
    private OnEditDeleteListener mListener;

    public DeleteCategoryTask(IRecipesPresenter mPresenter, OnEditDeleteListener mListener) {
        this.mPresenter = mPresenter;
        this.mListener = mListener;
    }


    @Override
    protected Integer doInBackground(Integer... params) {
        Integer categoryId = params[0];
        return DAO.deleteCategory(mPresenter.getContext(), categoryId);
    }

    @Override
    protected void onPostExecute(Integer deletedRowsCount) {
        if (deletedRowsCount != null)
            mListener.onSuccessDeleteCategory();
        else
            mListener.onErrorDeleteCategory();
    }
}
