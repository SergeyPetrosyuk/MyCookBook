package com.mlsdev.serhiy.mycookbook.presenter;

import android.content.Intent;
import android.widget.AdapterView;

import com.mlsdev.serhiy.mycookbook.adapter.ChooseCategoryListAdapter;
import com.mlsdev.serhiy.mycookbook.asynk_task.LoadCategoriesTask;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnCategoryListLoaded;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IChooseCategoryPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IChooseCategoryView;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

import java.util.List;

/**
 * Created by android on 11.03.15.
 */
public class ChooseCategoryPresenter implements IChooseCategoryPresenter, OnCategoryListLoaded {
    private IChooseCategoryView mView;

    public ChooseCategoryPresenter(IChooseCategoryView mView) {
        this.mView = mView;
    }

    @Override
    public void loadCategoryList() {
        new LoadCategoriesTask(mView.getContext(), this).execute();
    }

    @Override
    public void categoryIsChoisen(AdapterView<?> parent, int position) {
        ChooseCategoryListAdapter adapter = (ChooseCategoryListAdapter) parent.getAdapter();
        RecipeCategory category = (RecipeCategory) adapter.getItem(position);
        Intent dataForResult = new Intent();
        dataForResult.putExtra(Constants.EXTRAS_CATEGORY_NAME, category.getName());
        dataForResult.putExtra(Constants.EXTRAS_CATEGORY_ID, category.getId());
        mView.closeItSelfWithResult(dataForResult);
    }

    @Override
    public void categoriesLoaded(List<RecipeCategory> categoryList) {
        mView.showCategoryList(categoryList);
    }
}
