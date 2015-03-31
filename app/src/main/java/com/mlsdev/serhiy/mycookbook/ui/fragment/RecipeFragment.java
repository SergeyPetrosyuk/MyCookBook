package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.listener.OnFavoriteBtnClickListener;
import com.mlsdev.serhiy.mycookbook.presenter.RecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipeView;
import com.mlsdev.serhiy.mycookbook.ui.activity.BaseActivity;
import com.mlsdev.serhiy.mycookbook.ui.activity.RecipeActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by android on 13.03.15.
 */
public class RecipeFragment extends Fragment implements IRecipeView {

    private ImageView mRecipeImage;
    private ImageButton mFavoriteBtn;
    private TextView mRecipeTitle;
    private Bundle recipeData;
    private IRecipePresenter mPresenter;
    private RelativeLayout mContentContainer;
    private TextView mCategoryTextView;
    private TextView mIngredientsTextView;
    private TextView mInstructionsTextView;
    private boolean mIsAfterEditing = false;
    private AlertDialog.Builder dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new RecipePresenter(this);
        recipeData = ((RecipeActivity)getActivity()).getRecipeData();
        createDialog();

        setRetainInstance(true);
        setHasOptionsMenu(true);

        ((BaseActivity) getActivity()).setIsMoreThanOneFragment(false);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recipe, container, false);

        mRecipeImage = (ImageView) view.findViewById(R.id.iv_view_recipe_image);
        mRecipeTitle = (TextView) view.findViewById(R.id.tv_view_recipe_title);
        mCategoryTextView = (TextView) view.findViewById(R.id.tv_label_category);
        mIngredientsTextView = (TextView) view.findViewById(R.id.tv_label_ingredients);
        mInstructionsTextView = (TextView) view.findViewById(R.id.tv_label_instructions);
        mContentContainer = (RelativeLayout) view.findViewById(R.id.rl_recipe_content);
        mFavoriteBtn = (ImageButton) view.findViewById(R.id.ibt_make_recipe_favorite);

        mPresenter.openRecipe(recipeData, mIsAfterEditing);

        if (mIsAfterEditing){
            mIsAfterEditing = false;
        }

        mFavoriteBtn.setOnClickListener(new OnFavoriteBtnClickListener(mFavoriteBtn, mPresenter));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recipeData = ((RecipeActivity)getActivity()).getRecipeData();
        mPresenter.setupRecipeData(recipeData);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recipe, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.edit_recipe_action :
                mPresenter.openUpdateScreen(recipeData);
                break;
            case R.id.action_delete_recipe :
                dialog.create().show();
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setImage(Uri uri) {
        Picasso.with(getActivity()).load(uri).resize(700,400).centerCrop().into(mRecipeImage);
    }

    @Override
    public void setRecipeTitle(String title) {
        mRecipeTitle.setText(title);
    }

    @Override
    public void setIngredients(String ingredients) {
        mIngredientsTextView.setText(ingredients);
    }

    @Override
    public void setInstructions(String instructions) {
        mInstructionsTextView.setText(instructions);
    }

    @Override
    public void setCategoryTitle(String categoryTitle) {
        mCategoryTextView.setText(categoryTitle);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showContent() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.show_recipe_content);
        mContentContainer.startAnimation(animation);
    }

    @Override
    public void showUpdateFragment(Bundle dataForUpdate) {
        mIsAfterEditing = true;
        ((BaseActivity)getActivity()).setIsMoreThanOneFragment(true);
        Fragment updateFragment = new AddRecipeFragment();
        updateFragment.setArguments(dataForUpdate);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder_view_recipe_screen, updateFragment)
                .addToBackStack(AddRecipeFragment.class.getName())
                .commit();
    }

    @Override
    public void onRecipeDeleted() {
        getActivity().finish();
    }

    private void createDialog(){
        dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(getActivity().getString(R.string.delete_this_recipe));
        dialog.setPositiveButton(R.string.add_note_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteRecipe();
            }
        });

        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
    }
}
