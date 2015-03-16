package com.mlsdev.serhiy.mycookbook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.adapter.ChooseCategoryListAdapter;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.presenter.ChooseCategoryPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IChooseCategoryPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IChooseCategoryView;

import java.util.List;

public class ChooseCategoryActivity extends BaseActivity implements IChooseCategoryView, AdapterView.OnItemClickListener {

    private ListView mCategoryListView;
    private ChooseCategoryListAdapter mCategoryListAdapter;
    private IChooseCategoryPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ChooseCategoryPresenter(this);
        mCategoryListView = (ListView) findViewById(R.id.lv_choose_category);
        mCategoryListView.setOnItemClickListener(this);
        mPresenter.loadCategoryList();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_choose_category;
    }

    @Override
    public void showCategoryList(List<RecipeCategory> categoryList) {
        mCategoryListAdapter = new ChooseCategoryListAdapter(categoryList, this);
        mCategoryListView.setAdapter(mCategoryListAdapter);
        mCategoryListView.invalidate();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void closeItSelfWithResult(Intent dataForResult) {
        setResult(RESULT_OK, dataForResult);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPresenter.categoryIsChoisen(parent, position);
    }

}
