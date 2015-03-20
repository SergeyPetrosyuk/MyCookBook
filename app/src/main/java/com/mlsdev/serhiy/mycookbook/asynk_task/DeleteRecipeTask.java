package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.IRecipeInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnDeleteRecipeListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipePresenter;

/**
 * Created by android on 19.03.15.
 */
public class DeleteRecipeTask extends AsyncTask<Integer, Void, Integer> implements IRecipeInteractor {

    private IRecipePresenter mPresenter;
    private OnDeleteRecipeListener mListener;

    public DeleteRecipeTask(IRecipePresenter mPresenter, OnDeleteRecipeListener mListener) {
        this.mPresenter = mPresenter;
        this.mListener = mListener;
    }

    @Override
    protected Integer doInBackground(Integer... params) {

        Integer recipeId = params[0];
        Integer deletedRows = DAO.deleteRecipe(mPresenter.getContext(), recipeId);
        return deletedRows;
    }

    @Override
    protected void onPostExecute(Integer deletedRows) {
        if (deletedRows != -1){
            mListener.onSuccess();
        }
    }

    @Override
    public void deleteRecipe(Integer recipeId) {
        this.execute(recipeId);
    }
}
