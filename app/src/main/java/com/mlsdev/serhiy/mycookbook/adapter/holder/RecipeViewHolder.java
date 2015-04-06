package com.mlsdev.serhiy.mycookbook.adapter.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by android on 16.03.15.
 */
public class RecipeViewHolder {

    private ImageView iconImageView;
    private TextView nameTextView;
    private View foreground;
    private ImageView favoriteStatus;
    private ImageButton selectForDeleteBtn;
    private ProgressBar progressBar;

    public RecipeViewHolder(ImageView iconImageView, TextView nameTextView, View foreground,
                            ImageView favoriteStatus, ImageButton selectForDeleteBtn,
                            ProgressBar progressBar) {
        this.iconImageView = iconImageView;
        this.nameTextView = nameTextView;
        this.foreground = foreground;
        this.favoriteStatus = favoriteStatus;
        this.selectForDeleteBtn = selectForDeleteBtn;
        this.progressBar = progressBar;
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

    public void setFavoriteStatus(ImageView favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }

    public void setSelectForDeleteBtn(ImageButton selectForDeleteBtn) {
        this.selectForDeleteBtn = selectForDeleteBtn;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
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

    public ImageView getFavoriteStatus() {
        return favoriteStatus;
    }

    public ImageButton getSelectForDeleteBtn() {
        return selectForDeleteBtn;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
