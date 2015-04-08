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
    private TransitionDrawable mTransitionDrawable;

    public OnFilterByFavoriteClickListener(ImageButton mFavoriteBtn, IRecipesPresenter mPresenter) {
        this.mFavoriteBtn = mFavoriteBtn;
        this.mPresenter = mPresenter;
        this.mTransitionDrawable = (TransitionDrawable) mFavoriteBtn.getDrawable();
        checkFilterState();
    }

    @Override
    public void onClick(View v) {
        if (mFavoriteBtn.isSelected())
            mTransitionDrawable.reverseTransition(300);
        else
            mTransitionDrawable.startTransition(300);

        mFavoriteBtn.setSelected(!mFavoriteBtn.isSelected());
        mPresenter.setupIsFavorite(mFavoriteBtn.isSelected());
        mPresenter.viewOnResumeState();
    }

    private void checkFilterState(){
        boolean state = mPresenter.getFilterState();

        if (state){
            mTransitionDrawable.startTransition(0);
            mFavoriteBtn.setSelected(true);
        }
    }
}
