package com.mlsdev.serhiy.mycookbook.listener;

import android.view.View;

import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.ICategoriesPresenter;

/**
 * Created by android on 13.03.15.
 */
public class OncategoryClickListener implements View.OnClickListener {

    private ICategoriesPresenter mPresenter;

    public OncategoryClickListener(ICategoriesPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onClick(View v) {

    }
}
