package com.mlsdev.serhiy.mycookbook.listener;

import android.view.View;
import android.widget.AdapterView;

import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.ICategoriesPresenter;

/**
 * Created by android on 12.03.15.
 */
public class OnCategoryItemClickListener implements AdapterView.OnItemClickListener {

    private ICategoriesPresenter mPresenter;

    public OnCategoryItemClickListener(ICategoriesPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPresenter.openCategory(parent, position);
    }

}
