package com.mlsdev.serhiy.mycookbook.listener;

import android.view.View;
import android.widget.AdapterView;

import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;

/**
 * Created by android on 12.03.15.
 */
public class OnRecipeItemClickListener implements AdapterView.OnItemClickListener {

    private IRecipesPresenter mPresenter;

    public OnRecipeItemClickListener(IRecipesPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        mPresenter.openRecipe(parent, position);
    }

}
