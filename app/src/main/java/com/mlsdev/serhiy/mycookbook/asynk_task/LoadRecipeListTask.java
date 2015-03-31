package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnRecipeListLoadedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;

import java.util.List;

/**
 * Created by android on 11.03.15.
 */
public class LoadRecipeListTask extends AsyncTask<Integer, Void, List<Recipe>> {

    private OnRecipeListLoadedListener mListener;
    private IRecipesPresenter mPresenter;

    public LoadRecipeListTask(OnRecipeListLoadedListener mListener, IRecipesPresenter mPresenter) {
        this.mListener = mListener;
        this.mPresenter = mPresenter;
    }

    @Override
    protected List<Recipe> doInBackground(Integer... params) {
        int categoryId = params[0];
        if (!mPresenter.isOnlyFavorites())
            return DAO.getRecipeList(mPresenter.getContext(), categoryId);
        else
            return DAO.getRecipeListByFavoriteStatus(mPresenter.getContext(), categoryId);
    }

    @Override
    protected void onPostExecute(List<Recipe> recipeList) {
        mListener.recipeListLoaded(recipeList);
    }
}
