package com.mlsdev.serhiy.mycookbook.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by android on 16.03.15.
 */
public class RecipeViewHolder {

    private ImageView iconImageView;
    private TextView nameTextView;
    private View foreground;

    public RecipeViewHolder(ImageView iconImageView, TextView nameTextView, View foreground) {
        this.iconImageView = iconImageView;
        this.nameTextView = nameTextView;
        this.foreground = foreground;
    }

    public ImageView getIconImageView() {
        return iconImageView;
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public View getForeground() {
        return foreground;
    }

    public void setIconImageView(ImageView iconImageView) {
        this.iconImageView = iconImageView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public void setForeground(View foreground) {
        this.foreground = foreground;
    }
}
