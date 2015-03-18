package com.mlsdev.serhiy.mycookbook.listener;

import android.text.Editable;
import android.text.TextWatcher;

import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;

/**
 * Created by android on 18.03.15.
 */
public class OnTextChangedListener implements TextWatcher {

    private IRecipesPresenter mPresenter;
    private int mCount = 0;

    public OnTextChangedListener(IRecipesPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mCount = start;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mCount < 1) {
            mPresenter.activateReadyButton(s.length());
        }
    }
}
