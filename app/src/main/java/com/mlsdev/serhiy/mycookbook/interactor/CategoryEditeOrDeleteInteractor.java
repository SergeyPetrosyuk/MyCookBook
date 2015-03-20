package com.mlsdev.serhiy.mycookbook.interactor;

import android.content.Context;

import com.mlsdev.serhiy.mycookbook.asynk_task.UpdateCategoryTask;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.ICategoryEditeOrDeleteInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnEditDeleteListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;

/**
 * Created by android on 18.03.15.
 */
public class CategoryEditeOrDeleteInteractor implements ICategoryEditeOrDeleteInteractor, OnEditDeleteListener {

    private OnEditDeleteListener mListenere;
    private IRecipesPresenter mPresenter;

    public CategoryEditeOrDeleteInteractor(OnEditDeleteListener mListenere, IRecipesPresenter mPresenter) {
        this.mListenere = mListenere;
        this.mPresenter = mPresenter;
    }

    @Override
    public void editCategory(Integer id, String newTitle) {
        new UpdateCategoryTask(this, this).execute(id, newTitle);
    }

    @Override
    public void deleteCategory(Integer id) {

    }

    @Override
    public Context getContext() {
        return mPresenter.getContext();
    }

    @Override
    public void onErrorEditCategory() {
        mListenere.onErrorEditCategory();
    }

    @Override
    public void onSuccessEditCategory() {
        mListenere.onSuccessEditCategory();
    }

    @Override
    public void onErrorDeleteCategory() {
        mListenere.onErrorDeleteCategory();
    }

    @Override
    public void onSuccessDeleteCategory() {
        mListenere.onSuccessDeleteCategory();
    }
}
