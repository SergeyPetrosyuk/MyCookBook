package com.mlsdev.serhiy.mycookbook.interactor;

import com.mlsdev.serhiy.mycookbook.asynk_task.LoadCategoriesTask;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.ICategoriesInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnCategoryListLoaded;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.ICategoriesPresenter;

import java.util.List;

/**
 * Created by android on 10.03.15.
 */
public class CategoriesInteractor implements ICategoriesInteractor, OnCategoryListLoaded {

    private ICategoriesPresenter mPresenter;
    private OnCategoryListLoaded mListener;

    public CategoriesInteractor(ICategoriesPresenter presenter, OnCategoryListLoaded listener) {
        this.mPresenter = presenter;
        this.mListener = listener;
    }

    @Override
    public void deleteCategory(long id) {

    }

    @Override
    public void editCategory(long id) {

    }

    @Override
    public void loadCategories() {
        new LoadCategoriesTask(mPresenter.getContext(), this).execute();
    }

    @Override
    public void categoriesLoaded(List<RecipeCategory> categoryList) {
        mListener.categoriesLoaded(categoryList);
    }
}
