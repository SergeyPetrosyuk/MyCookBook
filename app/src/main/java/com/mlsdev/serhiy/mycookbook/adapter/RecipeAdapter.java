package com.mlsdev.serhiy.mycookbook.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IRecipesView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by android on 11.03.15.
 */
public class RecipeAdapter extends BaseAdapter {

    private List<Recipe> mRecipeList;
    private IRecipesView mView;

    public RecipeAdapter(List<Recipe> recipeList, IRecipesView mView) {
        this.mRecipeList = recipeList;
        this.mView = mView;
    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public Object getItem(int position) {
        int reversePosition = getCount() - 1- position;
        return mRecipeList.get(reversePosition);
    }

    @Override
    public long getItemId(int position) {
        return getCount() - 1- position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int reversePosition = getCount() - 1- position;

        if (convertView == null){
            convertView = LayoutInflater.from(mView.getContext()).inflate(R.layout.recipe_list_item, parent, false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_recipe_icon);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.tv_recipe_title);

            ViewHolder viewHolder = new ViewHolder(imageView, nameTextView);
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.pb_recipe_list_item);

        if (!mRecipeList.get(reversePosition).getImageUri().equals("")) {

            Uri imageUri = Uri.parse("content://media" + mRecipeList.get(reversePosition).getImageUri());

            Picasso.with(mView.getContext())
                    .load(imageUri)
                    .resize(750, 375)
                    .centerCrop().
                    into(viewHolder.iconImageView, new CallBackImageLoader(progressBar, viewHolder.iconImageView));
        } else {
            viewHolder.iconImageView.setImageResource(R.mipmap.no_image);
        }

        viewHolder.nameTextView.setText(mRecipeList.get(reversePosition).getTitle());

        return convertView;
    }

    private class ViewHolder{
        ImageView iconImageView;
        TextView nameTextView;

        private ViewHolder(ImageView iconImageView, TextView nameTextView) {
            this.iconImageView = iconImageView;
            this.nameTextView = nameTextView;
        }
    }

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

}
