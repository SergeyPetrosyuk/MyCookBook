package com.mlsdev.serhiy.mycookbook.presenter;

import android.content.Context;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.interactor.AddCategoryInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.IAddCategoryInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnCategoryCreatedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IAddCategoryPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IAddCategoryView;

/**
 * Created by android on 10.03.15.
 */
public class AddCategoryPresenter implements IAddCategoryPresenter, OnCategoryCreatedListener {

    private IAddCategoryView mView;
    private IAddCategoryInteractor mInteractor;

    public AddCategoryPresenter(IAddCategoryView view) {
        this.mView = view;
        mInteractor = new AddCategoryInteractor(this);
    }

    @Override
    public void addCategory(String name) {
        mView.showProgressBar();

        if (name.length() < 1){
            mView.hideProgressBar();
            mView.showError(mView.getContext().getString(R.string.add_category_empty_title_error));
        } else {
            mInteractor.addCategory(name);
        }

    }

    @Override
    public void onSuccess(int createdCategoryId, String createdCategoryName) {
        mView.hideProgressBar();
        mView.openCreatedCategory(createdCategoryId, createdCategoryName);
    }

    @Override
    public void onError() {
        mView.hideProgressBar();
        mView.showError(mView.getContext().getString(R.string.add_category_error));
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }
}
