package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.adapter.RecipeAdapter;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);

        mCategoryData = getArguments();
        mCategoryId = mCategoryData.getInt(Constants.EXTRAS_CATEGORY_ID);

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
    }
    
    private void initViews(){
        mAddNoteBtn.setOnClickListener(this);
//        mResipeListView.setOnItemClickListener(new OnRecipeItemClickListener(mPresenter));
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
}
