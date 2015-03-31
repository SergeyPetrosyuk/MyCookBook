package com.mlsdev.serhiy.mycookbook.model;

/**
 * Created by android on 04.03.15.
 */
public class Recipe {
    
    private int _id              = 0;
    private String mImageUri     = null;
    private String mTitle        = null;
    private String mCategoryName = null;
    private String mIngredients  = null;
    private String mInstructions = null;
    private int mCategoryId      = 0;
    private boolean mIsFavorite  = false;
    
    public Recipe() {
    }

    public Recipe(int _id, String mImageUri, String mTitle, String mCategoryName, String mIngredients,
                  String mInstructions, int mCategoryId) {
        this._id = _id;
        this.mImageUri = mImageUri;
        this.mTitle = mTitle;
        this.mCategoryName = mCategoryName;
        this.mIngredients = mIngredients;
        this.mInstructions = mInstructions;
        this.mCategoryId = mCategoryId;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public void setIngredients(String mIngredients) {
        this.mIngredients = mIngredients;
    }

    public void setInstructions(String mInstructions) {
        this.mInstructions = mInstructions;
    }

    public void setCategoryId(int mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public void setIsFavorite(boolean aIsFavorite){
        mIsFavorite = aIsFavorite;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public int get_id() {
        return _id;
    }

    public String getImageUri() {
        return mImageUri;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public String getInstructions() {
        return mInstructions;
    }

    public boolean getIsFavorite(){
        return mIsFavorite;
    }
}
