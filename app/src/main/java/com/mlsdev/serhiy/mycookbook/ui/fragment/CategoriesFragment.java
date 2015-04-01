package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.adapter.CategoriesListAdapter;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.listener.OnCategoryItemClickListener;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.presenter.CategoriesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.ICategoriesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.ICategoriesView;
import com.mlsdev.serhiy.mycookbook.ui.activity.AddCategoryActivity;
import com.mlsdev.serhiy.mycookbook.ui.activity.CategoryActivity;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

import java.util.List;

/**
 * Created by android on 10.03.15.
 */
public class CategoriesFragment extends Fragment implements View.OnClickListener, ICategoriesView {

    private Button mAddCategoryButton;
    private CategoriesListAdapter mCategoriesListAdapter;
    private ListView mCategoriesListView;
    private GridView mCategoriesGridView;
    private View mRootView;

    private ICategoriesPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CategoriesPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        setRetainInstance(true);

        mRootView = inflater.inflate(R.layout.fragment_categories, container, false);

        mCategoriesGridView = (GridView) mRootView.findViewById(R.id.gv_categories_list);
        mCategoriesListView = (ListView) mRootView.findViewById(R.id.lv_categories_list);
        mAddCategoryButton = (Button) mRootView.findViewById(R.id.btn_add_new_category);

        mPresenter.viewOnCreateState();

        mAddCategoryButton.setOnClickListener(this);

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCategoriesListAdapter != null){
            mPresenter.viewOnResumeState();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_new_category){
            mPresenter.addCategory();
        }
    }

    @Override
    public void openAddNewCategoryScreen() {
        Intent intent = new Intent(getActivity(), AddCategoryActivity.class);

        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showList(List<RecipeCategory> categoryList, int displayType) {
        mCategoriesListAdapter = new CategoriesListAdapter(categoryList, this, mPresenter);
        mCategoriesGridView.setAdapter(mCategoriesListAdapter);
        mCategoriesListView.setAdapter(mCategoriesListAdapter);

        if (displayType == Constants.PREF_CATS_GRID_DISPLAY_TYPE) {
            showGridView();
            mCategoriesGridView.invalidate();
        } else {
            showListView();
            mCategoriesListView.invalidate();
        }
    }

    @Override
    public void showCategoriesAsList(MenuItem item) {
        item.setIcon(R.mipmap.icon_view_grid);
        showListView();
        mCategoriesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoriesAsGrid(MenuItem item) {
        item.setIcon(R.mipmap.icon_view_list);
        showGridView();
        mCategoriesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void openCategory(int categoryId, String categoryName) {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra(DBContract.RecipeEntry.COLUMN_CATEGORY_ID, categoryId);
        intent.putExtra(DBContract.CategoryEntry.COLUMN_NAME, categoryName);
        startActivity(intent);
    }

    @Override
    public BaseAdapter getAdapter() {
        return mCategoriesListAdapter;
    }

    @Override
    public LoaderManager getLoaderManagerForPresenter() {
        return getLoaderManager();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_categories_fragment, menu);
        MenuItem menuItem = menu.findItem(R.id.action_display_type);
        menuItem.setIcon(mPresenter.getIconForDisplayTypeAction());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_display_type:
                mPresenter.changeItemDisplayType(item);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showGridView(){
        mCategoriesGridView.setVisibility(View.VISIBLE);
        mCategoriesListView.setVisibility(View.GONE);
        mCategoriesListAdapter.setupItemLayoutId();
    }

    private void showListView(){
        mCategoriesGridView.setVisibility(View.GONE);
        mCategoriesListView.setVisibility(View.VISIBLE);
        mCategoriesListAdapter.setupItemLayoutId();
    }

}
