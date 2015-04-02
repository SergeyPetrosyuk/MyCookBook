package com.mlsdev.serhiy.mycookbook.database;


/**
 * Created by android on 06.03.15.
 */
public class DBContract {

    public static final String DB_NAME = "my_cookbook";
    public static final int DB_VERSION = 8;

    public static class CategoryEntry{
        public static final String TABLE_NAME = "categories";

        // Columns
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";

        public static final String CRATE_TABLE_QUERY = "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                CategoryEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoryEntry.COLUMN_NAME + " TEXT NOT NULL" +
                ");";
    }

    public static class RecipeEntry{
        public static final String TABLE_NAME = "recipes";

        // Columns
        public static final String COLUMN_ID = "recipe_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_INGREDIENTS = "ingredients";
        public static final String COLUMN_INSTRUCTIONS = "instructions";
        public static final String COLUMN_IMAGE_URI = "image_uri";
        public static final String COLUMN_IS_FAVORITE = "is_favorite";

        public static final String CRATE_TABLE_QUERY = "CREATE TABLE " + RecipeEntry.TABLE_NAME + " (" +
                RecipeEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                RecipeEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
                RecipeEntry.COLUMN_TITLE + " TEXT, " +
                RecipeEntry.COLUMN_INGREDIENTS + " TEXT, " +
                RecipeEntry.COLUMN_INSTRUCTIONS + " TEXT, " +
                RecipeEntry.COLUMN_IMAGE_URI + " TEXT, " +
                RecipeEntry.COLUMN_IS_FAVORITE + " INTEGER DEFAULT 0, " +
                "FOREIGN KEY ( " + RecipeEntry.COLUMN_CATEGORY_ID + " ) REFERENCES " +
                    CategoryEntry.TABLE_NAME + " ( " + CategoryEntry.COLUMN_ID + " ) " +
                ");";
    }

}
