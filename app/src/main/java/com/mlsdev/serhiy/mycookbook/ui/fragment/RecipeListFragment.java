package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.adapter.RecipeAdapter;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.listener.OnTextChangedListener;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.presenter.RecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipesView;
import com.mlsdev.serhiy.mycookbook.ui.activity.AddRecipeActivity;
import com.mlsdev.serhiy.mycookbook.ui.activity.BaseActivity;
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
    private Button mReadyBtn;
    private RelativeLayout mEditTextHolder;
    private EditText mEditCategoryName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mCategoryData = getArguments();
        String categoryName = mCategoryData.getString(DBContract.CategoryEntry.COLUMN_NAME, "");
        ((BaseActivity) getActivity()).setActionBarTitle(categoryName);
        mCategoryId = mCategoryData.getInt(DBContract.RecipeEntry.COLUMN_CATEGORY_ID);
        mPresenter = new RecipesPresenter(this);
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        findViews(view);
        initViews();

        mRecipeAdapter = new RecipeAdapter(this, mPresenter);

        mPresenter.loadRecipeList(mCategoryId);

        return view;
    }
    
    private void findViews(View view){
        mAddNoteBtn = (Button) view.findViewById(R.id.btn_add_note);
        mResipeListView = (AbsListView) view.findViewById(R.id.lv_recipes);
        mEditorContainer = (RelativeLayout) view.findViewById(R.id.rl_category_name_editor);
        mReadyBtn = (Button) view.findViewById(R.id.btn_ready_edit_category);
        mEditTextHolder = (RelativeLayout) view.findViewById(R.id.rl_edit_text_holder_edit_category);

        mEditCategoryName = (EditText) view.findViewById(R.id.et_edit_category_name);
        mEditCategoryName.addTextChangedListener(new OnTextChangedListener(mPresenter));
        mReadyBtn.setOnClickListener(this);
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
                mPresenter.openAddRecipeScreen();
                break;
            case R.id.btn_ready_edit_category:
                mPresenter.editCategory(mCategoryId, mEditCategoryName.getText().toString());
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
        mRecipeAdapter.setData(recipeList);
        mResipeListView.setAdapter(mRecipeAdapter);
        mResipeListView.invalidate();
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
        ObjectAnimator mover = ObjectAnimator.ofFloat(mEditorContainer, "translationY", 0);
        mover.setDuration(400);
        mover.start();
        showSoftKeyboard(getActivity(), mEditCategoryName);
    }

    @Override
    public void hideCategoryEditor() {
        if (mEditCategoryName.getText().length() > 0)
            mEditCategoryName.setText(Constants.EMPTY_STRING);

        ObjectAnimator mover = ObjectAnimator.ofFloat(mEditorContainer, "translationY", -mEditorContainer.getHeight());
        mover.setDuration(500);
        mover.start();
        mEditCategoryName.clearFocus();
        hideSoftKeyboard(getActivity());
    }

    @Override
    public void showReadyButton() {
        Animation showBtn = AnimationUtils.loadAnimation(getActivity(), R.anim.show_ready_btn);
        Animation makeEditTextShorter = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_edit_text_to_smoller_size);
        mReadyBtn.setVisibility(View.VISIBLE);
        mReadyBtn.startAnimation(showBtn);
        mEditTextHolder.startAnimation(makeEditTextShorter);
    }

    @Override
    public void hideReadyButton() {
        Animation showBtn = AnimationUtils.loadAnimation(getActivity(), R.anim.hide_ready_btn);
        Animation makeEditTextShorter = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_edit_text_to_bigger_size);
        mReadyBtn.startAnimation(showBtn);
        mEditTextHolder.startAnimation(makeEditTextShorter);
    }

    @Override
    public void setupNewCategoryTitle() {
        ((BaseActivity) getActivity()).setActionBarTitle(mEditCategoryName.getText().toString());
        getActivity().setTitle(mEditCategoryName.getText().toString());
    }

    @Override
    public void openAddRecipeScreen() {
        String newTitle = ((BaseActivity) getActivity()).getSupportActionBar().getTitle().toString();

        Intent categoryData = new Intent(getContext(), AddRecipeActivity.class);
        categoryData.putExtra(DBContract.CategoryEntry.COLUMN_NAME, newTitle);
        categoryData.putExtra(DBContract.RecipeEntry.COLUMN_CATEGORY_ID, mCategoryId);
        startActivity(categoryData);
    }

    @Override
    public void invalidateListView() {
        mResipeListView.invalidateViews();
        mResipeListView.invalidate();
    }

    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private static void showSoftKeyboard(Activity activity, EditText edittext){
        edittext.setFocusableInTouchMode(true);
        edittext.requestFocus();
        final InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT);
    }

}
