package com.mlsdev.serhiy.mycookbook.listener;

import android.view.View;

import com.mlsdev.serhiy.mycookbook.adapter.holder.PositionHolder;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;

/**
 * Created by android on 16.03.15.
 */
public class OnRecipeClickListener implements View.OnClickListener {

    private IRecipesPresenter mPresenter;

    public OnRecipeClickListener(IRecipesPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onClick(View listItem) {
        PositionHolder positionHolder = (PositionHolder) listItem.getTag();
        mPresenter.openRecipe(positionHolder.getmPosition());
    }
}
