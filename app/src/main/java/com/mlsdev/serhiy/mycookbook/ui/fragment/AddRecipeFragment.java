package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.presenter.AddRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IAddRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IAddRecipeView;
import com.mlsdev.serhiy.mycookbook.ui.activity.GetImageActivity;
import com.mlsdev.serhiy.mycookbook.utils.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by android on 05.03.15.
 */
public class AddRecipeFragment extends Fragment implements IAddRecipeView, View.OnClickListener {

    private ProgressBar mProgressBar;
    private ImageView mRecipeImage;
    private Button mAddImageButton;
    private Button mAddNoteButton;
    private EditText mRecipeTitle;
    private EditText mIngredients;
    private EditText mInstructions;
    private EditText mCategory;
    private IAddRecipePresenter mAddRecipePresenter;
    private ProgressBar mProgressBarAddRecipe;
    private RelativeLayout mConatiner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddRecipePresenter = new AddRecipePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);

        Bundle data = getArguments();
        Intent intent = new Intent();
        intent.putExtras(data);

        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        findViews(view);
        activateViews();

        mAddRecipePresenter.setupData(intent);

        return view;

    }

    private void findViews(View view){
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_add_recipe_image);
        mRecipeImage = (ImageView) view.findViewById(R.id.iv_add_recipe_image);
        mAddImageButton = (Button) view.findViewById(R.id.btn_add_image);
        mAddNoteButton = (Button) view.findViewById(R.id.btn_add_recipe);
        mRecipeTitle = (EditText) view.findViewById(R.id.et_recipe_title);
        mIngredients = (EditText) view.findViewById(R.id.et_recipe_ingredients);
        mInstructions = (EditText) view.findViewById(R.id.et_recipe_instructions);
        mCategory = (EditText) view.findViewById(R.id.et_recipe_category);
        mProgressBarAddRecipe = (ProgressBar) view.findViewById(R.id.pb_add_edit_recipe);
        mConatiner = (RelativeLayout) view.findViewById(R.id.rl_add_edit_recipe_container);
    }

    private void activateViews(){
        mAddImageButton.setOnClickListener(this);
        mAddNoteButton.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (Constants.REQUEST_GET_IMAGE == requestCode
                && Activity.RESULT_OK == resultCode
                && data != null) {
            mAddRecipePresenter.loadImage(data);
        } else if (Constants.REQUEST_GET_CATEGORY == requestCode
                && Activity.RESULT_OK == resultCode
                && data != null){
        }
    }

    @Override
    public void startLoadImage() {
        mRecipeImage.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoadImage() {
        mRecipeImage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * @param bitmap is an image which we get from the presenter to setup it into mNoteImage
     * */
    @Override
    public void setUpImage(Bitmap bitmap) {
        mRecipeImage.setImageBitmap(bitmap);
    }

    @Override
    public void setUpImage(Uri imageUri) {
        Picasso.with(getActivity()).load(imageUri).centerCrop().resize(700, 400).into(mRecipeImage);
    }

    @Override
    public void setupTitle(String title) {
        mRecipeTitle.setText(title);
    }

    @Override
    public void setupIngredients(String ingredients) {
        mIngredients.setText(ingredients);
    }

    @Override
    public void setupInstructions(String instructions) {
        mInstructions.setText(instructions);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void setupCategoryName(String categoryName) {
        mCategory.setText(categoryName);
    }

    @Override
    public void openCreatedRecipe(long id) {
        getActivity().finish();
    }

    @Override
    public void onErrorCategory() {
        Toast.makeText(getActivity(), getActivity().getString(R.string.add_recipe_empty_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void backToRecipe() {
        getActivity().finish();
    }

    @Override
    public void startAdding() {
        mAddNoteButton.setEnabled(false);
        mAddImageButton.setEnabled(false);
        Animation fadeContainer = AnimationUtils.loadAnimation(getActivity(), R.anim.disable_container);
        fadeContainer.setFillAfter(true);
        mConatiner.startAnimation(fadeContainer);
        mProgressBarAddRecipe.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopAdding() {
        mAddNoteButton.setEnabled(true);
        mAddImageButton.setEnabled(true);
        mProgressBarAddRecipe.setVisibility(View.GONE);
        Animation fadeContainer = AnimationUtils.loadAnimation(getActivity(), R.anim.enable_container);
        fadeContainer.setFillAfter(true);
        mConatiner.startAnimation(fadeContainer);
    }

    @Override
    public void onRecipeUpdated(Intent updatedRecipeData) {
        getActivity().setIntent(updatedRecipeData);
        getActivity().getFragmentManager().popBackStackImmediate();
    }

    @Override
    public LoaderManager getLoaderManagerForPresenter() {
        return getLoaderManager();
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.btn_add_recipe:
                String recipeTitle = mRecipeTitle.getText().toString();
                String ingredients = mIngredients.getText().toString();
                String instructions= mInstructions.getText().toString();

                if (!mAddRecipePresenter.isEditing()) {
                    mAddRecipePresenter.addRecipe(recipeTitle, ingredients, instructions);
                } else {
                    mAddRecipePresenter.updateRecipe(recipeTitle, ingredients, instructions);
                }

                break;
            case R.id.btn_add_image:
                intent = new Intent(getActivity(),GetImageActivity.class);
                startActivityForResult(intent, Constants.REQUEST_GET_IMAGE);
                break;
            default:
                break;
        }
    }
}
