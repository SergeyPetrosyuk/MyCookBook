package com.mlsdev.serhiy.mycookbook.adapter.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by android on 16.03.15.
 */
public class RecipeViewHolder {

    private ImageView iconImageView;
    private TextView nameTextView;
    private View foreground;
    private RelativeLayout checkboxHolder;
    private CheckBox checkBox;
    private ImageView favoriteStatus;

    public RecipeViewHolder(ImageView iconImageView, TextView nameTextView, View foreground, RelativeLayout checkboxHolder, CheckBox checkBox, ImageView favoriteStatus) {
        this.iconImageView = iconImageView;
        this.nameTextView = nameTextView;
        this.foreground = foreground;
        this.checkboxHolder = checkboxHolder;
        this.checkBox = checkBox;
        this.favoriteStatus = favoriteStatus;
    }

    public ImageView getIconImageView() {
        return iconImageView;
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public ImageView getFavoriteStatus() {
        return favoriteStatus;
    }
}
