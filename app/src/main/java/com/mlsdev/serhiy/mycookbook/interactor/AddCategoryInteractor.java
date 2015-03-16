package com.mlsdev.serhiy.mycookbook.interactor;

import android.content.Context;

import com.mlsdev.serhiy.mycookbook.asynk_task.AddCategoryTask;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.IAddCategoryInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnCategoryCreatedListener;

/**
 * Created by android on 10.03.15.
 */
public class AddCategoryInteractor implements IAddCategoryInteractor, OnCategoryCreatedListener {
    private OnCategoryCreatedListener categoryCreatedListener;

    public AddCategoryInteractor(OnCategoryCreatedListener categoryCreatedListener) {
        this.categoryCreatedListener = categoryCreatedListener;
    }

    @Override
    public void addCategory(String categoryName) {
        new AddCategoryTask(this).execute(categoryName);
    }

    @Override
    public void onSuccess(int createdCategoryId, String createdCategoryName) {
        categoryCreatedListener.onSuccess(createdCategoryId, createdCategoryName);
    }

    @Override
    public void onError() {
        categoryCreatedListener.onError();
    }

    @Override
    public Context getContext() {
        return categoryCreatedListener.getContext();
    }
}
