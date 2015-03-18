package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.app.Fragment;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.adapter.RecipeAdapter;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.presenter.RecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipesView;
import com.mlsdev.serhiy.mycookbook.ui.activity.AddRecipeActivity;
import com.mlsdev.serhiy.mycookbook.ui.activity.RecipeActivity;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

import java.util.List;

/**
 * Created by android on 04.03.15.
 */
public class RecipeListFragment extends Fragment implements View.OnClickListener, IRecipesView {

    private Button mAddNoteBtn;
    private AbsListView mResipeListView;
    private RecipeAdapter mRecipeAdapter;
    private IRecipesPresenter mPresenter;
    private Integer mCategoryId = 0;
    private Bundle mCategoryData;
    private RelativeLayout mEditorContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mCategoryData = getArguments();
        mCategoryId = mCategoryData.getInt(DBContract.RecipeEntry.COLUMN_CATEGORY_ID);

        mPresenter = new RecipesPresenter(this);
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        findViews(view);
        initViews();

        mPresenter.loadRecipeList(mCategoryId);

        return view;
    }
    
    private void findViews(View view){
        mAddNoteBtn = (Button) view.findViewById(R.id.btn_add_note);
        mResipeListView = (AbsListView) view.findViewById(R.id.lv_recipes);
        mEditorContainer = (RelativeLayout) view.findViewById(R.id.rl_category_name_editor);
    }
    
    private void initViews(){
        mAddNoteBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_edit_category :
                mPresenter.showEditor();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadRecipeList(mCategoryId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_note:
                Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
                intent.putExtras(mCategoryData);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recipe_list, menu);
    }

    @Override
    public void showRecipeList(List<Recipe> recipeList) {
        if (recipeList.size() > 0) {
            mRecipeAdapter = new RecipeAdapter(recipeList, this, mPresenter);
            mResipeListView.setAdapter(mRecipeAdapter);
            mResipeListView.invalidate();
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void openRecipe(Bundle recipeData) {
        Intent intent = new Intent(getActivity(), RecipeActivity.class);
        intent.putExtras(recipeData);
        startActivity(intent);
    }

    @Override
    public BaseAdapter getAdepter() {
        return mRecipeAdapter;
    }

    @Override
    public void showCategoryEditor() {
        mEditorContainer.setVisibility(View.VISIBLE);
        Animation showEditor = AnimationUtils.loadAnimation(getActivity(), R.anim.show_category_editor);
        mEditorContainer.startAnimation(showEditor);
    }

    @Override
    public void hideCategoryEditor() {
        Animation hideEditor = AnimationUtils.loadAnimation(getActivity(), R.anim.hide_category_editor);
        hideEditor.setFillAfter(true);
        mEditorContainer.startAnimation(hideEditor);
    }
}
