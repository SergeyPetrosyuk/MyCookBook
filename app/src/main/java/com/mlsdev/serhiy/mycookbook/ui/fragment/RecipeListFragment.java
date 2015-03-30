package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageButton mDeleteCategoryBtn;
    private AbsListView mResipeListView;
    private IRecipesPresenter mPresenter;
    private RelativeLayout mEditorContainer;
    private Button mReadyBtn;
    private RelativeLayout mEditTextHolder;
    private EditText mEditCategoryName;
    private Menu mMenu;
    private AlertDialog.Builder dialog;
    private AlertDialog.Builder mDeleteCategoryDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mPresenter = new RecipesPresenter(this);
        mPresenter.setCategoryData(getArguments());
        mPresenter.setupCategoryName();

        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        findViews(view);
        initViews();

        createDialog();
        mPresenter.loadRecipeList(mPresenter.getCategoryId());

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
        mReadyBtn.setOnClickListener(this);
        mDeleteCategoryBtn.setOnClickListener(this);
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
            case R.id.action_delete_recipes:
                dialog.create().show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadRecipeList(mPresenter.getCategoryId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_note:
                mPresenter.openAddRecipeScreen();
                break;
            case R.id.btn_ready_edit_category:
                mPresenter.editCategory(mPresenter.getCategoryId(), mEditCategoryName.getText().toString());
                break;
            case R.id.bt_delete_category:
                mDeleteCategoryDialog.create().show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recipe_list, menu);
        mMenu = menu;
    }

    @Override
    public void showRecipeList(List<Recipe> recipeList) {
        RecipeAdapter adapter = new RecipeAdapter(this, mPresenter);
        adapter.setData(recipeList);
        mResipeListView.setAdapter(adapter);
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
        return (BaseAdapter) mResipeListView.getAdapter();
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
        if (mMenu != null)
            mMenu.getItem(0).setVisible(isShow);
    }

    @Override
    public void onDeleteCategorySuccess() {
        getActivity().finish();
    }

    @Override
    public void onDeleteCategoryError() {

    }

    @Override
    public void onEditCategorySuccess() {
        ((BaseActivity) getActivity()).setActionBarTitle(mEditCategoryName.getText().toString());
        getActivity().setTitle(mEditCategoryName.getText().toString());
    }

    @Override
    public void onEditCategoryError() {
        Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
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

    private void createDialog(){
        // Delete recipes dialog.
        dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(getActivity().getString(R.string.delete_this_recipes));
        dialog.setPositiveButton(R.string.add_note_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteRecipes();
            }
        });
        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        // Delete category dialog.
        mDeleteCategoryDialog = new AlertDialog.Builder(getActivity());
        mDeleteCategoryDialog.setPositiveButton(R.string.add_note_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteCategory();
            }
        });
        mDeleteCategoryDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        mDeleteCategoryDialog.setIcon(R.mipmap.ic_delete_action_darck)
                .setMessage(R.string.delete_category_with_recipes)
                .setTitle(R.string.delete);
    }

}
