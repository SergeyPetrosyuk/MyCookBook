package com.mlsdev.serhiy.mycookbook.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;

/**
 * Created by android on 06.04.15.
 */
public class DialogHelper {

    private AlertDialog.Builder mDeleteCategoryDialog;
    private AlertDialog.Builder mDeleteRecipesDialog;
    private Context mContext;
    private IRecipesPresenter mPresenter;

    public DialogHelper(Context mContext, IRecipesPresenter presenter) {
        this.mPresenter = presenter;
        this.mContext = mContext;
        crateDialogs();
    }

    private void crateDialogs(){
        // Create an alert dialog for the recipes deleting.
        mDeleteRecipesDialog = new AlertDialog.Builder(mContext);
        mDeleteRecipesDialog.setTitle(mContext.getString(R.string.delete_this_recipes));
        mDeleteRecipesDialog.setPositiveButton(R.string.add_note_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteRecipes();
            }
        });
        mDeleteRecipesDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        // Create an alert dialog for the recipes deleting.

        // Create an alert dialog for the category deleting.
        mDeleteCategoryDialog  = new AlertDialog.Builder(mContext);
        mDeleteCategoryDialog.setPositiveButton(R.string.add_note_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteCategory();
            }
        });
        mDeleteCategoryDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        mDeleteCategoryDialog.setIcon(R.mipmap.ic_delete_action_darck)
                .setMessage(R.string.delete_category_with_recipes)
                .setTitle(R.string.delete);
        // END Create an alert dialog for the category deleting.
    }

    public void showDeleteCategoryDialog(){
        mDeleteCategoryDialog.create().show();
    }

    public void showDeleteRecipeDialog(){
        mDeleteRecipesDialog.create().show();
    }
}
