package com.mlsdev.serhiy.mycookbook.asynk_task;

import android.os.AsyncTask;

import com.mlsdev.serhiy.mycookbook.database.DAO;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.ILoadCategoryNameInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnLoadCategoryListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;

/**
 * Created by android on 27.03.15.
 */
public class LoadCategoryNameTask extends AsyncTask<Integer, Void, RecipeCategory> implements ILoadCategoryNameInteractor {

    private OnLoadCategoryListener mListener;
    private IRecipesPresenter mPresenter;

    public LoadCategoryNameTask(OnLoadCategoryListener mListener, IRecipesPresenter mPresenter) {
        this.mListener = mListener;
        this.mPresenter = mPresenter;
    }

    @Override
    protected RecipeCategory doInBackground(Integer... params) {
        Integer categoryId = params[0];
        return DAO.getCategoryById(mPresenter.getContext(), categoryId);
    }

    @Override
    protected void onPostExecute(RecipeCategory category) {
        if (category != null){
            mListener.onLoadCategorySuccess(category);
        } else {
            mListener.onLoadCategoryFailure();
        }
    }

    @Override
    public void loadCatrgoryName(Integer categoryId) {
        this.execute(categoryId);
    }
}
