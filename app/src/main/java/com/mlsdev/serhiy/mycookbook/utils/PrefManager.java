package com.mlsdev.serhiy.mycookbook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by android on 11.03.15.
 */
public class PrefManager{

    public static int categoriesDisplayType(Context context){
        int displayType = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getInt(Constants.PREF_CATS_DISPLAY_TYPE_KEY, Constants.PREF_CATS_GRID_DISPLAY_TYPE);

        return displayType;
    }

    public static void setupCategoriesDisplayType(Context context, int dispayType){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(Constants.PREF_CATS_DISPLAY_TYPE_KEY, dispayType).commit();
    }

    public static void setRecipeListStateChanged(Context context, boolean isChanged){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(Constants.PREF_RECIPE_LIST_CHANGED_KEY, isChanged).commit();
    }

    public static boolean getRecipeListState(Context context){
        boolean isChanged = PreferenceManager
                .getDefaultSharedPreferences(context)
                    .getBoolean(Constants.PREF_RECIPE_LIST_CHANGED_KEY, false);

        return isChanged;
    }

    public static void setRecipeImageStateChanged(Context context, boolean isChanged){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(Constants.PREF_RECIPE_FAVORITE_CHANGED_KEY, isChanged).commit();
    }

    public static boolean getRecipeImageState(Context context){
        boolean isChanged = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean(Constants.PREF_RECIPE_FAVORITE_CHANGED_KEY, false);

        return isChanged;
    }

}
