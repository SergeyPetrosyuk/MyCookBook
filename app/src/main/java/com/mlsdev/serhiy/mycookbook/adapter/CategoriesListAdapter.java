package com.mlsdev.serhiy.mycookbook.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.adapter.holder.PositionHolder;
import com.mlsdev.serhiy.mycookbook.listener.OnCategoryClickListener;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnListChangedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.presenter.ICategoriesPresenter;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.ICategoriesView;
import com.mlsdev.serhiy.mycookbook.utils.Constants;
import com.mlsdev.serhiy.mycookbook.utils.PrefManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by android on 10.03.15.
 */
public class CategoriesListAdapter extends BaseAdapter implements OnListChangedListener {

    private List<RecipeCategory> mCategoryList;
    private ICategoriesView mView;
    private ICategoriesPresenter mPresenter;
    private int mItemLayoutId;

    public CategoriesListAdapter(List<RecipeCategory> mCategoryList, ICategoriesView mView,
                                 ICategoriesPresenter mPresenter) {
        this.mCategoryList = mCategoryList;
        this.mView = mView;
        this.mPresenter = mPresenter;
        mItemLayoutId = R.layout.category_grid_item;
    }

    public void setupItemLayoutId() {
        if (PrefManager.categoriesDisplayType(mView.getContext()) == Constants.PREF_CATS_LIST_DISPLAY_TYPE){
            mItemLayoutId = R.layout.category_list_item;
        } else {
            mItemLayoutId = R.layout.category_grid_item;
        }
    }

    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null){
            convertView = LayoutInflater.from(mView.getContext()).inflate(mItemLayoutId, parent, false);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.tv_category_item);
            ImageView categoryImageView = (ImageView) convertView.findViewById(R.id.iv_category_image);
            View foregroundView = convertView.findViewById(R.id.category_item_foreground);
            foregroundView.setTag(new PositionHolder(position));
            foregroundView.setOnClickListener(new OnCategoryClickListener(mPresenter));
            ViewHolder viewHolder = new ViewHolder(nameTextView, categoryImageView, foregroundView);
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.nameTextView.setText(mCategoryList.get(position).getName());
        viewHolder.categoryId = mCategoryList.get(position).getId();
        viewHolder.imageUriStr = mCategoryList.get(position).getImageUriStr();

        Uri uri = null;

        if (!viewHolder.imageUriStr.equals("")){
            uri = Uri.parse("content://media" + viewHolder.imageUriStr);
        } else {
            viewHolder.categoryImage.setImageResource(R.mipmap.no_image);
        }

        if (uri != null) {
            Picasso.with(mView.getContext()).load(uri).resize(750, 400).centerCrop().into(viewHolder.categoryImage,
                    new CallBackImageLoader(viewHolder.categoryImage));
        }

        return convertView;
    }

    @Override
    public void listChanged() {
        notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView nameTextView;
        ImageView categoryImage;
        String imageUriStr;
        int categoryId;
        View foregroundView;

        private ViewHolder(TextView nameTextView, ImageView categoryImage, View foregroundView) {
            this.nameTextView = nameTextView;
            this.categoryImage = categoryImage;
            this.foregroundView = foregroundView;
        }
    }

    /**
     * CallBackImageLoader
     * */
    private class CallBackImageLoader implements Callback {

        ImageView imageView;

        private CallBackImageLoader(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void onSuccess() { }

        @Override
        public void onError() {
            imageView.setImageResource(R.mipmap.no_image);
        }
    }
}
