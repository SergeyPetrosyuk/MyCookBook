package com.mlsdev.serhiy.mycookbook.adapter;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.adapter.holder.PositionHolder;
import com.mlsdev.serhiy.mycookbook.adapter.holder.RecipeViewHolder;
import com.mlsdev.serhiy.mycookbook.listener.OnRecipeClickListener;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipeListAdapter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipesView;
import com.mlsdev.serhiy.mycookbook.utils.Constants;
import com.mlsdev.serhiy.mycookbook.utils.PrefManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by android on 11.03.15.
 */
public class RecipeListAdapter extends BaseAdapter implements IRecipeListAdapter {
    private List<Recipe> mRecipeList;
    private IRecipesView mView;
    private IRecipesPresenter mPresenter;
    private int mSelectedRecipeCount = 0;
    private int mVisibilityState = View.GONE;
    private Map<Integer, Drawable> mDrawableMap;

    public RecipeListAdapter(IRecipesView mView, IRecipesPresenter mPresenter) {
        this.mView = mView;
        this.mPresenter = mPresenter;
        mRecipeList = new ArrayList<>();
        mDrawableMap = new HashMap<>();
    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView                      = LayoutInflater.from(mView.getContext()).inflate(R.layout.recipe_list_item, parent, false);
            ImageView imageView              = (ImageView) convertView.findViewById(R.id.iv_recipe_icon);
            TextView nameTextView            = (TextView) convertView.findViewById(R.id.tv_recipe_title);
            View foreground                  = convertView.findViewById(R.id.recipe_item_foreground);
            ImageView favoriteStatus         = (ImageView) convertView.findViewById(R.id.iv_favorite_status);
            ImageButton selectForDeletingBtn = (ImageButton) convertView.findViewById(R.id.ib_select_for_deleting);
            ProgressBar progressBar          = (ProgressBar) convertView.findViewById(R.id.pb_recipe_list_item);
            RecipeViewHolder viewHolder      = new RecipeViewHolder(imageView, nameTextView, foreground, favoriteStatus,
                                                                    selectForDeletingBtn, progressBar);
            foreground.setTag(new PositionHolder(position));
            foreground.setOnClickListener(new OnRecipeClickListener(mPresenter));
            foreground.setOnLongClickListener(new OnLongPressedListener());
            convertView.setTag(viewHolder);
            selectForDeletingBtn.setTag(position);
            selectForDeletingBtn.setOnClickListener(new OnSelectRecipeClickListener());
        }

        RecipeViewHolder viewHolder = (RecipeViewHolder) convertView.getTag();
        ((PositionHolder) viewHolder.getForeground().getTag()).setmPosition(position);

        viewHolder.getNameTextView().setText(mRecipeList.get(position).getTitle());
        viewHolder.getSelectForDeleteBtn().setVisibility(mVisibilityState);
        viewHolder.getSelectForDeleteBtn().setTag(position);
        viewHolder.getSelectForDeleteBtn().setSelected(mRecipeList.get(position).getIsChecked());

        setUpImage(mRecipeList.get(position), viewHolder.getIconImageView(), viewHolder.getProgressBar(), position);
        setUpFavoriteStatusIcon(mRecipeList.get(position), viewHolder.getFavoriteStatus());

        TransitionDrawable transitionDrawable = (TransitionDrawable)  viewHolder.getSelectForDeleteBtn().getDrawable();

        if (mRecipeList.get(position).getIsChecked()) {
            transitionDrawable.startTransition(0);
        } else {
            transitionDrawable.resetTransition();
        }
        return convertView;
    }

    @Override
    public void setData(List<Recipe> recipeList) {
        if (getRecipeListState()) {

            if (PrefManager.getRecipeImageState(mView.getContext())){
                mDrawableMap.clear();
            }

            if (mRecipeList.size() != recipeList.size()){
                mRecipeList.clear();
                mDrawableMap.clear();
                mRecipeList.addAll(recipeList);
                mSelectedRecipeCount = 0;
                mVisibilityState = View.GONE;
            } else {
                for (int i = 0; i < mRecipeList.size(); i++){
                    mRecipeList.get(i).setIsFavorite(recipeList.get(i).getIsFavorite());
                    mRecipeList.get(i).setImageUri(recipeList.get(i).getImageUri());
                }
            }

            this.notifyDataSetChanged();

            PrefManager.setRecipeListStateChanged(mView.getContext(), false);
            PrefManager.setRecipeImageStateChanged(mView.getContext(), false);
        }

        checkSelectedCount();
    }

    @Override
    public Recipe getRecipe(int position) {
        return (Recipe) getItem(position);
    }

    @Override
    public void unselectAllRecipes() {
        mSelectedRecipeCount = 0;
        for (Recipe recipe : mRecipeList){
            recipe.setIsChecked(false);
        }
        
        checkSelectedCount();
    }

    /**
     * CallBackImageLoader
     * */
    private class CallBackImageLoader implements Callback {

        ProgressBar progressBar;
        ImageView imageView;
        Integer position;

        private CallBackImageLoader(ProgressBar progressBar, ImageView imageView, Integer position) {
            this.progressBar = progressBar;
            this.imageView = imageView;
            this.position = position;
        }

        @Override
        public void onSuccess() {
            mDrawableMap.put(position, imageView.getDrawable());
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onError() {
            progressBar.setVisibility(View.GONE);
            imageView.setImageResource(R.mipmap.no_image);
        }
    }

    /**
     * OnLongClickListener
     * */
    private class OnLongPressedListener implements View.OnLongClickListener{
        @Override
        public boolean onLongClick(View v) {
            int position = ((PositionHolder)v.getTag()).getmPosition();
            mVisibilityState = View.VISIBLE;
            mRecipeList.get(position).setIsChecked(true);
            mSelectedRecipeCount++;
            checkSelectedCount();
            mView.showEditAction(false);
            mView.showUnselectAllAction(true);
            notifyDataSetChanged();
            return true;
        }
    }

    @Override
    public List<Integer> getSelectedRecipeIds(){
        List<Integer> recipeIdListForDelete = new ArrayList<>();

        for (Recipe recipe : mRecipeList){
            if (recipe.getIsChecked()){
                recipeIdListForDelete.add(recipe.get_id());
            }
        }

        mSelectedRecipeCount = 0;
        checkSelectedCount();
        return recipeIdListForDelete;
    }

    /*
    * OnSelectRecipeClickListener
    * */
    private class OnSelectRecipeClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            ImageButton imageButton = (ImageButton) view;
            TransitionDrawable transitionDrawable = (TransitionDrawable) imageButton.getDrawable();
            int position = (int) view.getTag();
            mRecipeList.get(position).setIsChecked(!mRecipeList.get(position).getIsChecked());

            if (mRecipeList.get(position).getIsChecked()) {
                mSelectedRecipeCount++;
                transitionDrawable.startTransition(100);
            } else {
                mSelectedRecipeCount--;
                transitionDrawable.reverseTransition(100);
            }

            checkSelectedCount();
        }
    }

    private void checkSelectedCount(){
        Log.e(Constants.LOG_TAG, "mVisibilityState = " + mSelectedRecipeCount);

        if (mSelectedRecipeCount > 0){
            mView.showDeleteAction(true);
            mView.showEditAction(false);
            mView.showUnselectAllAction(true);
        } else {
            mView.showDeleteAction(false);
            mView.showEditAction(true);
            mView.showUnselectAllAction(false);
            mVisibilityState = View.GONE;
            notifyDataSetInvalidated();
        }
    }

    private void setUpImage(Recipe recipe, ImageView image, ProgressBar progressBar, int position){

        if (mDrawableMap.containsKey(position)){
            if (image.getDrawable() == null) {
                Drawable drawable = mDrawableMap.get(position);
                image.setImageDrawable(drawable);
                progressBar.setVisibility(View.GONE);
            }

            return;
        }

        if (!recipe.getImageUri().equals("")) {

            Uri imageUri = Uri.parse("content://media" + recipe.getImageUri());

            Picasso.with(mView.getContext())
                    .load(imageUri)
                    .resize(750, 400)
                    .centerCrop().
                    into(image, new CallBackImageLoader(progressBar, image, position));

        } else {
            image.setImageResource(R.mipmap.no_image);
            mDrawableMap.put(position, image.getDrawable());
            progressBar.setVisibility(View.GONE);
        }

    }

    private void setUpFavoriteStatusIcon(Recipe recipe, ImageView favoriteStatusImg){
        if (recipe.getIsFavorite())
            favoriteStatusImg.setVisibility(View.VISIBLE);
        else
            favoriteStatusImg.setVisibility(View.GONE);
    }

    private boolean getRecipeListState(){
        return PrefManager.getRecipeListState(mView.getContext());
    }

}
