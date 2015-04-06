package com.mlsdev.serhiy.mycookbook.listener;

import android.view.View;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipesView;

/**
 * Created by android on 06.04.15.
 */
public class OnActionBarItemClickListener implements View.OnClickListener {
    private IRecipesPresenter mRecipesPresenter;
    private IRecipesView mRecipesView;

    public OnActionBarItemClickListener(IRecipesPresenter mRecipesPresenter, IRecipesView mRecipesView) {
        this.mRecipesPresenter = mRecipesPresenter;
        this.mRecipesView = mRecipesView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_note:
                mRecipesPresenter.openAddRecipeScreen();
                break;
            case R.id.btn_ready_edit_category:
                mRecipesPresenter.editCategory(mRecipesView.getNewCategoryName());
                break;
            case R.id.bt_delete_category:
                mRecipesView.showDeleteCategoryDialog();
                break;
            case R.id.ib_edit_category :
                mRecipesPresenter.showEditor();
                break;
            case R.id.ib_delete_recipes :
                mRecipesView.showDeleteRecipesDialog();
                break;
            case R.id.ib_unselect_all :
                mRecipesView.getAdepter().unselectAllRecipes();
                break;
            default:
                break;
        }
    }
}
