package com.mlsdev.serhiy.mycookbook.listener;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.ImageButton;

import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;

/**
 * Created by android on 31.03.15.
 */
public class OnFilterByFavoriteClickListener implements View.OnClickListener {
    private ImageButton mFavoriteBtn;
    private IRecipesPresenter mPresenter;

    public OnFilterByFavoriteClickListener(ImageButton mFavoriteBtn, IRecipesPresenter mPresenter) {
        this.mFavoriteBtn = mFavoriteBtn;
        this.mPresenter = mPresenter;
    }

    @Override
    public void onClick(View v) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) mFavoriteBtn.getDrawable();

        if (mFavoriteBtn.isSelected())
            transitionDrawable.reverseTransition(300);
        else
            transitionDrawable.startTransition(300);

        mFavoriteBtn.setSelected(!mFavoriteBtn.isSelected());
        mPresenter.setupIsFavorite(mFavoriteBtn.isSelected());
        mPresenter.viewOnResumeState();
    }
}
