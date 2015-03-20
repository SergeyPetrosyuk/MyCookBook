package com.mlsdev.serhiy.mycookbook.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.adapter.holder.PositionHolder;
import com.mlsdev.serhiy.mycookbook.adapter.holder.RecipeViewHolder;
import com.mlsdev.serhiy.mycookbook.listener.OnRecipeClickListener;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.IRecipesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipesView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 11.03.15.
 */
public class RecipeAdapter extends BaseAdapter {

    private List<Recipe> mRecipeList;
    private IRecipesView mView;
    private IRecipesPresenter mPresenter;
    private int mSelectedRecipeCount = 0;
    private int mVisibilityState = View.GONE;
    private List<RelativeLayout> mCheckboxHolders;

    public RecipeAdapter(List<Recipe> recipeList, IRecipesView mView, IRecipesPresenter mPresenter) {
        this.mRecipeList = recipeList;
        this.mView = mView;
        this.mPresenter = mPresenter;
        mVisibilityState = mSelectedRecipeCount > 0 ? View.VISIBLE : View.GONE;
        mCheckboxHolders = new ArrayList<>();
    }

    public RecipeAdapter(IRecipesView mView, IRecipesPresenter mPresenter) {
        this.mView = mView;
        this.mPresenter = mPresenter;
        mRecipeList = new ArrayList<>();
        mCheckboxHolders = new ArrayList<>();
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
            convertView = LayoutInflater.from(mView.getContext()).inflate(R.layout.recipe_list_item, parent, false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_recipe_icon);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.tv_recipe_title);
            View foreground = convertView.findViewById(R.id.recipe_item_foreground);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.recipe_item_checkbox);
            RelativeLayout checkBoxHolder = (RelativeLayout) convertView.findViewById(R.id.recipe_item_checkbox_holder);
            RecipeViewHolder viewHolder = new RecipeViewHolder(imageView, nameTextView, foreground, checkBoxHolder, checkBox);

            foreground.setTag(new PositionHolder(position));
            foreground.setOnClickListener(new OnRecipeClickListener(mPresenter));
            foreground.setOnLongClickListener(new OnLongPressedListener(checkBoxHolder, checkBox));
            checkBoxHolder.setOnClickListener(new OnCheckBoxHolderClickListener(checkBox));
            checkBoxHolder.setVisibility(mVisibilityState);
            convertView.setTag(viewHolder);
            mCheckboxHolders.add(checkBoxHolder);
        }

        RecipeViewHolder viewHolder = (RecipeViewHolder) convertView.getTag();
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.pb_recipe_list_item);

        if (!mRecipeList.get(position).getImageUri().equals("")) {

            Uri imageUri = Uri.parse("content://media" + mRecipeList.get(position).getImageUri());

            Picasso.with(mView.getContext())
                    .load(imageUri)
                    .resize(750, 400)
                    .centerCrop().
                    into(viewHolder.getIconImageView(), new CallBackImageLoader(progressBar, viewHolder.getIconImageView()));
        } else {
            viewHolder.getIconImageView().setImageResource(R.mipmap.no_image);
            progressBar.setVisibility(View.GONE);
        }

        viewHolder.getNameTextView().setText(mRecipeList.get(position).getTitle());

        return convertView;
    }

    public void setData(List<Recipe> recipeList) {
        if (recipeList.size() == mRecipeList.size())
            return;

        mRecipeList.clear();
        mRecipeList.addAll(recipeList);
        this.notifyDataSetChanged();
    }

    /**
     * CallBackImageLoader
     * */
    private class CallBackImageLoader implements Callback {

        ProgressBar progressBar;
        ImageView imageView;

        private CallBackImageLoader(ProgressBar progressBar, ImageView imageView) {
            this.progressBar = progressBar;
            this.imageView = imageView;
        }

        @Override
        public void onSuccess() {
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

        public RelativeLayout checkBoxHolder;
        public CheckBox checkBox;

        private OnLongPressedListener(RelativeLayout checkBoxHolder, CheckBox checkBox) {
            this.checkBoxHolder = checkBoxHolder;
            this.checkBox = checkBox;
        }

        @Override
        public boolean onLongClick(View v) {
            checkBoxHolder.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);
            increaseCheckedItems();
            updateTopViews();
            return true;
        }
    }

    private class OnCheckBoxHolderClickListener implements View.OnClickListener{
        public RelativeLayout checkBoxHolder;
        public CheckBox checkBox;

        private OnCheckBoxHolderClickListener(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        @Override
        public void onClick(View v) {
            boolean isChecked = checkBox.isChecked();
            checkBox.setPressed(true);
            checkBox.setChecked(!isChecked);

            if (!isChecked){
                decreaseCheckedItems();
            } else {
                increaseCheckedItems();
            }

            if (mSelectedRecipeCount == 0){

            }
        }
    }

    private void increaseCheckedItems(){
        mSelectedRecipeCount++;
    }

    private void decreaseCheckedItems(){
        mSelectedRecipeCount--;
    }

    private void updateTopViews(){
        for (RelativeLayout checkboxHolder : mCheckboxHolders){
            checkboxHolder.setVisibility(View.VISIBLE);
        }
    }
}
