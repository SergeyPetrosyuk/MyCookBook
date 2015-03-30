package com.mlsdev.serhiy.mycookbook.presenter;

import android.content.Context;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.mlsdev.serhiy.mycookbook.adapter.RecipeAdapter;
import com.mlsdev.serhiy.mycookbook.asynk_task.DeleteRecipesTask;
import com.mlsdev.serhiy.mycookbook.asynk_task.LoadCategoryNameTask;
import com.mlsdev.serhiy.mycookbook.asynk_task.LoadRecipeListTask;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.interactor.CategoryEditeOrDeleteInteractor;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.ICategoryEditeOrDeleteInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.IDeleteRecipesInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.interactor.ILoadCategoryNameInteractor;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnDeleteRecipeListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnEditDeleteListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnLoadCategoryListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnRecipeListLoadedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipesView;

import java.util.List;

import static com.mlsdev.serhiy.mycookbook.database.DBContract.*;

/**
 * Created by android on 11.03.15.
 */
public class RecipesPresenter implements IRecipesPresenter, OnRecipeListLoadedListener, OnEditDeleteListener,
        OnLoadCategoryListener, OnDeleteRecipeListener{

    private IRecipesView mView;
    private boolean mIsEditorOpened = false;
    private ICategoryEditeOrDeleteInteractor mInteractor;
    private Bundle mCategoryData;
    private ILoadCategoryNameInteractor mLoadCategoryNameInteractor;

    public RecipesPresenter(IRecipesView mView) {
        this.mView = mView;
        mLoadCategoryNameInteractor = new LoadCategoryNameTask(this, this);
    }

    @Override
    public void loadRecipeList(int categoryId) {
        new LoadRecipeListTask(this, this).execute(categoryId);
        mInteractor = new CategoryEditeOrDeleteInteractor(this, this);
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public void openRecipe(int position) {
        RecipeAdapter adapter = (RecipeAdapter) mView.getAdepter();
        Recipe recipe = (Recipe) adapter.getItem(position);

        Bundle data = new Bundle();

        data.putInt(RecipeEntry.COLUMN_ID, recipe.get_id());
        data.putInt(RecipeEntry.COLUMN_CATEGORY_ID, recipe.getCategoryId());
        data.putString(CategoryEntry.COLUMN_NAME, recipe.getCategoryName());
        data.putString(RecipeEntry.COLUMN_TITLE, recipe.getTitle());
        data.putString(RecipeEntry.COLUMN_IMAGE_URI, recipe.getImageUri());
        data.putString(RecipeEntry.COLUMN_INGREDIENTS, recipe.getIngredients());
        data.putString(RecipeEntry.COLUMN_INSTRUCTIONS, recipe.getInstructions());

        mView.openRecipe(data);

        closeCategoryEditor();
    }

    @Override
    public void showEditor() {
        if (mIsEditorOpened){
            mView.hideCategoryEditor();
        } else {
            mView.showCategoryEditor();
        }

        mIsEditorOpened = mIsEditorOpened ? false : true;
    }

    @Override
    public void activateReadyButton(int textLength) {
        if (textLength > 0 && textLength < 2){
            mView.showReadyButton();
        } else if (textLength == 0) {
            mView.hideReadyButton();
        }
    }

    @Override
    public void editCategory(int categoryId, String newTitle) {
        mInteractor.editCategory(categoryId, newTitle);
    }

    @Override
    public void openAddRecipeScreen() {
        closeCategoryEditor();
        mView.openAddRecipeScreen();
    }

    @Override
    public void setCategoryData(Bundle categoryData) {
        mCategoryData = categoryData;
    }

    @Override
    public Integer getCategoryId() {
        return mCategoryData.getInt(DBContract.RecipeEntry.COLUMN_CATEGORY_ID);
    }

    @Override
    public void setupCategoryName() {
        mLoadCategoryNameInteractor.loadCatrgoryName(getCategoryId());
    }

    @Override
    public void deleteRecipes() {
        RecipeAdapter adapter = (RecipeAdapter) mView.getAdepter();
        new DeleteRecipesTask(this, this).deleteRecipe(adapter.getSelectedRecipeIds());
    }

    @Override
    public void recipeListLoaded(List<Recipe> recipeList) {
        mView.showRecipeList(recipeList);
    }

    @Override
    public void onErrorEditCategory() {

    }

    @Override
    public void onSuccessEditCategory() {
        mView.setupNewCategoryTitle();
        showEditor();
    }

    @Override
    public void onErrorDeleteCategory() {

    }

    @Override
    public void onSuccessDeleteCategory() {

    }

    private void closeCategoryEditor(){
        if (mIsEditorOpened){
            mView.hideCategoryEditor();
            mIsEditorOpened = mIsEditorOpened ? false : true;
        }
    }

    @Override
    public void onLoadCategorySuccess(RecipeCategory category) {
        mView.setupCategoryName(category.getName());
    }

    @Override
    public void onLoadCategoryFailure() {
        mView.setupCategoryName(mCategoryData.getString(CategoryEntry.COLUMN_NAME));
    }

    // Delete recipes list onSuccess
    @Override
    public void onSuccess() {
        mView.showDeleteAction(false);
        loadRecipeList(mCategoryData.getInt(DBContract.RecipeEntry.COLUMN_CATEGORY_ID));
    }

    // Delete recipes list onError
    @Override
    public void onError() {

    }
}
