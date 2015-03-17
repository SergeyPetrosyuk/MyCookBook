package com.mlsdev.serhiy.mycookbook.listener;

import android.view.View;

import com.mlsdev.serhiy.mycookbook.adapter.holder.PositionHolder;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.ICategoriesPresenter;

/**
 * Created by android on 16.03.15.
 */
public class OnCategoryClickListener implements View.OnClickListener {

    private ICategoriesPresenter mPresenter;

    public OnCategoryClickListener(ICategoriesPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }


    @Override
    public void onClick(View listItem) {
        PositionHolder holder = (PositionHolder) listItem.getTag();
        mPresenter.openCategory(holder.getmPosition());
    }
}
