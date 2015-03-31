package com.mlsdev.serhiy.mycookbook.listener;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.ImageButton;

import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipePresenter;

/**
 * Created by android on 31.03.15.
 */
public class OnFavoriteBtnClickListener implements View.OnClickListener {

    private ImageButton mFavoriteBtn;
    private IRecipePresenter mPresenter;

    public OnFavoriteBtnClickListener(ImageButton mFavoriteBtn, IRecipePresenter mPresenter) {
        this.mFavoriteBtn = mFavoriteBtn;
        this.mPresenter = mPresenter;
        checkIfAlreadyFavorite();
    }

    @Override
    public void onClick(View v) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) mFavoriteBtn.getDrawable();

        if (mFavoriteBtn.isSelected())
            transitionDrawable.reverseTransition(500);
        else
            transitionDrawable.startTransition(500);

        mFavoriteBtn.setSelected(!mFavoriteBtn.isSelected());
        mPresenter.favoriteAction();
    }

    private void checkIfAlreadyFavorite(){
        if (mPresenter.isRecipeFavorite()) {
            TransitionDrawable transitionDrawable = (TransitionDrawable) mFavoriteBtn.getDrawable();
            transitionDrawable.startTransition(0);
            mFavoriteBtn.setSelected(true);
        }
    }
}
