package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.adapter.RecipeListAdapter;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.listener.OnActionBarItemClickListener;
import com.mlsdev.serhiy.mycookbook.listener.OnFilterByFavoriteClickListener;
import com.mlsdev.serhiy.mycookbook.listener.OnTextChangedListener;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.presenter.RecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipeListAdapter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipesView;
import com.mlsdev.serhiy.mycookbook.ui.activity.AddRecipeActivity;
import com.mlsdev.serhiy.mycookbook.ui.activity.BaseActivity;
import com.mlsdev.serhiy.mycookbook.ui.activity.RecipeActivity;
import com.mlsdev.serhiy.mycookbook.utils.AnimationFactory;
import com.mlsdev.serhiy.mycookbook.utils.Constants;
import com.mlsdev.serhiy.mycookbook.utils.DialogHelper;

import java.util.List;

/**
 * Created by android on 04.03.15.
 */
public class RecipeListFragment extends Fragment implements IRecipesView {

    private static final int sOnCreateState = 0;
    private static final int sOnResumeState = 1;
    private static int sFragmentState;

    private Button mAddNoteBtn;
    private ImageButton mDeleteCategoryBtn;
    private AbsListView mResipeListView;
    private IRecipesPresenter mPresenter;
    private RelativeLayout mEditorContainer;
    private Button mReadyBtn;
    private RelativeLayout mEditTextHolder;
    private EditText mEditCategoryName;
    private RecipeListAdapter mRecipeListAdapter;
    private BaseActivity mActivity;
    private DialogHelper mDialogHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        sFragmentState = sOnCreateState;
        mActivity = ((BaseActivity) getActivity());

        mPresenter = new RecipesPresenter(this);
        mPresenter.setCategoryData(getArguments());
        mPresenter.setupCategoryName();
        mDialogHelper = new DialogHelper(getActivity(), mPresenter);

        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        findViews(view);
        initViews();

        mPresenter.viewOnCreateState();

        defineActionBarIcons();
        return view;
    }
    
    private void findViews(View view){
        mDeleteCategoryBtn = (ImageButton) view.findViewById(R.id.bt_delete_category);
        mAddNoteBtn = (Button) view.findViewById(R.id.btn_add_note);
        mResipeListView = (AbsListView) view.findViewById(R.id.lv_recipes);
        mEditorContainer = (RelativeLayout) view.findViewById(R.id.rl_category_name_editor);
        mReadyBtn = (Button) view.findViewById(R.id.btn_ready_edit_category);
        mEditTextHolder = (RelativeLayout) view.findViewById(R.id.rl_edit_text_holder_edit_category);

        mEditCategoryName = (EditText) view.findViewById(R.id.et_edit_category_name);
        mEditCategoryName.addTextChangedListener(new OnTextChangedListener(mPresenter));
        mReadyBtn.setOnClickListener(new OnActionBarItemClickListener(mPresenter, this));
        mDeleteCategoryBtn.setOnClickListener(new OnActionBarItemClickListener(mPresenter, this));
    }
    
    private void initViews(){
        mAddNoteBtn.setOnClickListener(new OnActionBarItemClickListener(mPresenter, this));
    }

    @Override
    public String getNewCategoryName() {
        return mEditCategoryName.getText().toString();
    }

    @Override
    public void showDeleteCategoryDialog(){
        mDialogHelper.showDeleteCategoryDialog();
    }

    @Override
    public void showDeleteRecipesDialog(){
        mDialogHelper.showDeleteRecipeDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sFragmentState == sOnResumeState)
            mPresenter.viewOnResumeState();
        else
            sFragmentState = sOnResumeState;

        showFilterIcon();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideActionIcons();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.viewOnDestroyState();
    }

    @Override
    public void showRecipeList(List<Recipe> recipeList) {
        if (mRecipeListAdapter == null) {
            mRecipeListAdapter = new RecipeListAdapter(this, mPresenter);
            mResipeListView.setAdapter(mRecipeListAdapter);
        } else {
            mResipeListView.setAdapter(mRecipeListAdapter);
        }

        mRecipeListAdapter.setData(recipeList);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void openRecipe(Intent recipeData) {
        startActivity(recipeData);
    }

    @Override
    public IRecipeListAdapter getAdepter() {
        return (IRecipeListAdapter) mResipeListView.getAdapter();
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
        mReadyBtn.setVisibility(View.VISIBLE);
        mReadyBtn.startAnimation(AnimationFactory.getShowReadyEditButtonAnim(getActivity()));
        mEditTextHolder.startAnimation(AnimationFactory.getMakeEditTextShorterAnim(getActivity()));
    }

    @Override
    public void hideReadyButton() {
        mReadyBtn.startAnimation(AnimationFactory.getHideReadyEditButtonAnim(getActivity()));
        mEditTextHolder.startAnimation(AnimationFactory.getMakeEditTextLongerAnim(getActivity()));
    }

    @Override
    public void openAddRecipeScreen() {
        String newTitle = ((BaseActivity) getActivity()).getSupportActionBar().getTitle().toString();
        Intent categoryData = new Intent(getContext(), AddRecipeActivity.class);
        categoryData.putExtra(DBContract.CategoryEntry.COLUMN_NAME, newTitle);
        categoryData.putExtra(DBContract.RecipeEntry.COLUMN_CATEGORY_ID, mPresenter.getCategoryId());
        startActivity(categoryData);
    }

    @Override
    public void setupCategoryName(String categoryName) {
        ((BaseActivity) getActivity()).setActionBarTitle(categoryName);
    }

    @Override
    public void showDeleteAction(boolean isShow) {
        mActivity.showDeleteRecipesBtn(isShow);
    }

    @Override
    public void showEditAction(boolean isShow) {
        mActivity.showEditCategoryBtn(isShow);
    }

    @Override
    public void showUnselectAllAction(boolean isShow) {
        mActivity.showUnselectRecipesBtn(isShow);
    }

    @Override
    public void onDeleteCategorySuccess() {
        getActivity().finish();
    }

    @Override
    public void onEditCategorySuccess() {
        ((BaseActivity) getActivity()).setActionBarTitle(mEditCategoryName.getText().toString());
        getActivity().setTitle(mEditCategoryName.getText().toString());
    }

    @Override
    public void onEditCategoryError() {
        Toast.makeText(getActivity(), "Error while edit category", Toast.LENGTH_LONG).show();
    }

    @Override
    public LoaderManager getLoaderManagerForPresenter() {
        return getLoaderManager();
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

    private void defineActionBarIcons(){
        mActivity.getFilterBtn().setOnClickListener(new OnFilterByFavoriteClickListener(mActivity.getFilterBtn(), mPresenter));
        mActivity.getDeleteRecipesImageButton().setOnClickListener(new OnActionBarItemClickListener(mPresenter, this));
        mActivity.getUnselectAllImageButton().setOnClickListener(new OnActionBarItemClickListener(mPresenter, this));
        mActivity.getEditCategoryImageButton().setOnClickListener(new OnActionBarItemClickListener(mPresenter, this));
    }

    private void hideActionIcons(){
        mActivity.showFilterBtn(false);
        mActivity.showDeleteRecipesBtn(false);
        mActivity.showEditCategoryBtn(false);
        mActivity.showUnselectRecipesBtn(false);
    }

    private void showFilterIcon(){
        mActivity.showFilterBtn(true);
    }
}
