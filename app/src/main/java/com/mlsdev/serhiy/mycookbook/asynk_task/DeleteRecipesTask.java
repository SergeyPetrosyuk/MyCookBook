package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.IRecipeInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnDeleteRecipeListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 19.03.15.
 */
public class DeleteRecipesTask extends AsyncTask<List<Integer>, Void, Integer> {

    private IRecipesPresenter mPresenter;
    private OnDeleteRecipeListener mListener;

    public DeleteRecipesTask(IRecipesPresenter mPresenter, OnDeleteRecipeListener mListener) {
        this.mPresenter = mPresenter;
        this.mListener = mListener;
    }

    @Override
    protected Integer doInBackground(List<Integer>... params) {
        List<Integer> recipeIds = params[0];
        return DAO.deleteRecipes(mPresenter.getContext(), recipeIds);
    }

    @Override
    protected void onPostExecute(Integer deletedRows) {
        if (deletedRows != -1){
            mListener.onSuccess();
        }
    }

//    @Override
//    public void deleteRecipe(List<Integer> recipeIds) {
//        this.execute(recipeIds);
//    }
}
