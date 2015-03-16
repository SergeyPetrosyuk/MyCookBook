package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.presenter.AddRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IAddRecipePresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IAddRecipeView;
import com.mlsdev.serhiy.mycookbook.ui.activity.ChooseCategoryActivity;
import com.mlsdev.serhiy.mycookbook.ui.activity.GetImageActivity;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.mlsdev.serhiy.mycookbook.database.DBContract.*;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddRecipePresenter = new AddRecipePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);

        Bundle categoryData = getArguments();
        Intent intent = new Intent();
        intent.putExtras(categoryData);

        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        findViews(view);
        activateViews();

        mAddRecipePresenter.setupCategoryId(intent);

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
    }

    private void activateViews(){
        mAddImageButton.setOnClickListener(this);
        mAddNoteButton.setOnClickListener(this);
//        mCategory.setOnClickListener(this);
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
//            mAddRecipePresenter.setupCategoryId(data);
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
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
//            case R.id.et_recipe_category:
//                intent = new Intent(getActivity(), ChooseCategoryActivity.class);
//                startActivityForResult(intent, Constants.REQUEST_GET_CATEGORY);
//                break;
            case R.id.btn_add_recipe:
                String recipeTitle = mRecipeTitle.getText().toString();
                String ingredients = mIngredients.getText().toString();
                String instructions= mInstructions.getText().toString();
                mAddRecipePresenter.addRecipe(recipeTitle, ingredients, instructions);
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
