package com.mlsdev.serhiy.mycookbook.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.listener.OnListChangedListener;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.ICategoriesView;
import com.mlsdev.serhiy.mycookbook.ui.abstraction.view.IChooseCategoryView;
import com.mlsdev.serhiy.mycookbook.utils.Constants;
import com.mlsdev.serhiy.mycookbook.utils.PrefManager;

import java.util.List;

/**
 * Created by android on 10.03.15.
 */
public class ChooseCategoryListAdapter extends BaseAdapter implements OnListChangedListener {

    private List<RecipeCategory> mCategoryList;
    private IChooseCategoryView mView;

    public ChooseCategoryListAdapter(List<RecipeCategory> mCategoryList, IChooseCategoryView mView) {
        this.mCategoryList = mCategoryList;
        this.mView = mView;
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
            convertView = LayoutInflater.from(mView.getContext()).inflate(R.layout.choose_category_list_item, parent, false);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.tv_choose_category);
            ViewHolder viewHolder = new ViewHolder(nameTextView);
            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.nameTextView.setText(mCategoryList.get(position).getName());
        viewHolder.categoryId = mCategoryList.get(position).getId();

        return convertView;
    }

    @Override
    public void listChanged() {
        notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView nameTextView;
        int categoryId;

        private ViewHolder(TextView nameTextView) {
            this.nameTextView = nameTextView;
        }
    }
}
