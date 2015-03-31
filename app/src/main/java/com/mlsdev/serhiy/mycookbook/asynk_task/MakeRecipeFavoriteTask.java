package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.listener.OnMoveToFavoritesListener;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipePresenter;

/**
 * Created by android on 31.03.15.
 */
public class MakeRecipeFavoriteTask extends AsyncTask<Recipe, Void, Boolean>{
    private IRecipePresenter mPresenter;
    private OnMoveToFavoritesListener moveToFavoritesListener;

    public MakeRecipeFavoriteTask(IRecipePresenter mPresenter, OnMoveToFavoritesListener moveToFavoritesListener) {
        this.mPresenter = mPresenter;
        this.moveToFavoritesListener = moveToFavoritesListener;
    }

    @Override
    protected Boolean doInBackground(Recipe... params) {
        Recipe recipe = params[0];
        Integer updatedRows = DAO.moveRecipeToFavorites(mPresenter.getContext(), recipe.get_id(), !recipe.getIsFavorite());
        return updatedRows > 0 ? !recipe.getIsFavorite() : recipe.getIsFavorite();
    }

    @Override
    protected void onPostExecute(Boolean newStatus) {
        moveToFavoritesListener.onMoveToFavoritesSuccess(newStatus);
    }
}
