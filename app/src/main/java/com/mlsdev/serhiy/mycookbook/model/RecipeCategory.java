package com.mlsdev.serhiy.mycookbook.model;

/**
 * Created by android on 06.03.15.
 */
public class RecipeCategory {

    private int _id;
    private String mName;
    private String mImageUriStr;

    public RecipeCategory() {
    }

    public RecipeCategory(int _id, String name) {
        this._id = _id;
        this.mName = name;
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return mName;
    }

    public String getImageUriStr() {
        return mImageUriStr;
    }

    public void setImageUriStr(String mImageUriStr) {
        this.mImageUriStr = mImageUriStr;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
