package com.mlsdev.serhiy.mycookbook.listener;

/**
 * Created by android on 31.03.15.
 */
public interface OnMoveToFavoritesListener {

    void onMoveToFavoritesSuccess(boolean aNewStatus);
    void onMoveToFavoritesError();

}
