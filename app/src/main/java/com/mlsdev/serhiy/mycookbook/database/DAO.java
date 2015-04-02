package com.mlsdev.serhiy.mycookbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.model.RecipeCategory;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.mlsdev.serhiy.mycookbook.database.DBContract.*;

/**
 * Created by android on 10.03.15.
 */
public class DAO {

    public static Recipe insertRecipe(Context context, Intent dataForInsert){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String title = dataForInsert.getStringExtra(RecipeEntry.COLUMN_TITLE);
        String ingredients = dataForInsert.getStringExtra(RecipeEntry.COLUMN_INGREDIENTS);
        String instructions = dataForInsert.getStringExtra(RecipeEntry.COLUMN_INSTRUCTIONS);
        String imageUri = dataForInsert.getStringExtra(RecipeEntry.COLUMN_IMAGE_URI);
        int categoryId = dataForInsert.getIntExtra(RecipeEntry.COLUMN_CATEGORY_ID, 0);

        title = title != null ? title : "";
        ingredients = ingredients != null ? ingredients : "";
        instructions = instructions != null? instructions : "";
        imageUri = imageUri != null ? imageUri : "";

        contentValues.put(RecipeEntry.COLUMN_TITLE, title);
        contentValues.put(RecipeEntry.COLUMN_INGREDIENTS, ingredients);
        contentValues.put(RecipeEntry.COLUMN_INSTRUCTIONS, instructions);
        contentValues.put(RecipeEntry.COLUMN_IMAGE_URI, imageUri);
        contentValues.put(RecipeEntry.COLUMN_CATEGORY_ID, categoryId);

        try {
            Long insertedRow = database.insert(RecipeEntry.TABLE_NAME, null, contentValues);
            return getRecipeById(context, insertedRow.intValue());
        } finally {
            database.close();
            dbHelper.close();
        }

    }

    public static Recipe updateRecipe(Context context, Intent dataForInsert){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RecipeEntry.COLUMN_TITLE, dataForInsert.getStringExtra(RecipeEntry.COLUMN_TITLE));
        contentValues.put(RecipeEntry.COLUMN_INGREDIENTS, dataForInsert.getStringExtra(RecipeEntry.COLUMN_INGREDIENTS));
        contentValues.put(RecipeEntry.COLUMN_INSTRUCTIONS, dataForInsert.getStringExtra(RecipeEntry.COLUMN_INSTRUCTIONS));
        contentValues.put(RecipeEntry.COLUMN_IMAGE_URI, dataForInsert.getStringExtra(RecipeEntry.COLUMN_IMAGE_URI));

        Integer recipeId = dataForInsert.getIntExtra(RecipeEntry.COLUMN_ID, 0);

        if (recipeId == 0){
            return null;
        }

        String where = RecipeEntry.TABLE_NAME + "." + RecipeEntry.COLUMN_ID + " = ?";
        String[] whereArgs = new String[]{recipeId.toString()};

        try {
            database.update(
                    RecipeEntry.TABLE_NAME,
                    contentValues,
                    where,
                    whereArgs
            );

            return getRecipeById(context, recipeId);
        } finally {
            database.close();
            dbHelper.close();
        }

    }

    public static int deleteRecipe(Context context, Integer aRecipeId){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String where = RecipeEntry.COLUMN_ID + " = ? ";
        String[] whereArgs = new String[]{aRecipeId.toString()};

        try {
            int deletedRows = database.delete(
                    RecipeEntry.TABLE_NAME,
                    where,
                    whereArgs
            );

            return deletedRows;
        } finally {
            database.close();
            dbHelper.close();
        }
    }

    public static int deleteRecipes(Context context, List<Integer> aRecipeIds){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String recipeIdsStr = "";

        for (int i = 0; i < aRecipeIds.size(); i++){
            recipeIdsStr += "'" + aRecipeIds.get(i).toString() + "'";

            if (i < aRecipeIds.size()-1){
                recipeIdsStr += ",";
            }
        }

        String where = RecipeEntry.COLUMN_ID + " IN (" + recipeIdsStr + ") ";

        try {
            database.execSQL("DELETE FROM " + RecipeEntry.TABLE_NAME + " WHERE " + where);
            return 1;
        } finally {
            database.close();
            dbHelper.close();
        }
    }

    public static Recipe getRecipeById(Context context, Integer recipeId){
        Recipe recipe = new Recipe();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String selection = RecipeEntry.TABLE_NAME + "." + RecipeEntry.COLUMN_ID + " = ? ";
        String[] selectionArgs = new String[]{
                recipeId.toString(),
            };

        final SQLiteQueryBuilder recipeWithCategoryQueryBuilder = new SQLiteQueryBuilder();

        recipeWithCategoryQueryBuilder.setTables(
                RecipeEntry.TABLE_NAME + " INNER JOIN " + CategoryEntry.TABLE_NAME +
                        " ON " + RecipeEntry.TABLE_NAME + "." + RecipeEntry.COLUMN_CATEGORY_ID +
                        " = " + CategoryEntry.TABLE_NAME + "." + CategoryEntry.COLUMN_ID
        );

        Cursor cursor = recipeWithCategoryQueryBuilder.query(
                database,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()){
                recipe = getRecipeFromCursor(cursor);
                return recipe;
            } else {
                return null;
            }

        } finally {
            if (cursor != null){ cursor.close(); }
            database.close();
            dbHelper.close();
        }
    }

    public static List<Recipe> getRecipeList(Context context, Integer categoryId){
        String projection = CategoryEntry.TABLE_NAME + "." + CategoryEntry.COLUMN_ID + " = ?";
        String[] whereArgs = new String[]{categoryId.toString()};
        return loadRecipeList(context, projection, whereArgs);
    }

    public static List<Recipe> getRecipeListByFavoriteStatus(Context context, Integer categoryId){
        String projection = CategoryEntry.TABLE_NAME + "." + CategoryEntry.COLUMN_ID + " = ? AND " +
                RecipeEntry.COLUMN_IS_FAVORITE + " = ? ";
        String[] whereArgs = new String[]{categoryId.toString(), Constants.FAVORITE};
        return loadRecipeList(context, projection, whereArgs);
    }

    private static List<Recipe> loadRecipeList(Context context, String where, String[] whereArgs){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        final SQLiteQueryBuilder recipeWithCategoryQueryBuilder = new SQLiteQueryBuilder();

        recipeWithCategoryQueryBuilder.setTables(
                RecipeEntry.TABLE_NAME + " INNER JOIN " + CategoryEntry.TABLE_NAME +
                        " ON " + RecipeEntry.TABLE_NAME + "." + RecipeEntry.COLUMN_CATEGORY_ID +
                        " = " + CategoryEntry.TABLE_NAME + "." + CategoryEntry.COLUMN_ID
        );

        Cursor cursor = recipeWithCategoryQueryBuilder.query(
                database,
                null,
                where,
                whereArgs,
                null,
                null,
                RecipeEntry.COLUMN_ID + " DESC"
        );

        List<Recipe> recipeList = new ArrayList<>();

        try {

            while (cursor.moveToNext()){
                recipeList.add(getRecipeFromCursor(cursor));
            }

        } finally {
            cursor.close();
            database.close();
            dbHelper.close();
        }

        return recipeList;
    }

    public static long insertCategory(Context context, String category){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CategoryEntry.COLUMN_NAME, category);

        try {
            long insertedRowId = database.insert(CategoryEntry.TABLE_NAME, null, contentValues);
            return insertedRowId;
        } finally {
            database.close();
            dbHelper.close();
        }

    }

    public static List<RecipeCategory> getRecipeCategoryList(Context context){
        List<RecipeCategory> recipeCategoryList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(
                CategoryEntry.TABLE_NAME,
                new String[]{CategoryEntry.COLUMN_ID, CategoryEntry.COLUMN_NAME},
                null, null, null, null,  null);

        try {
            while (cursor.moveToNext()){
                RecipeCategory category = new RecipeCategory();

                int idColumnIndex = cursor.getColumnIndex(CategoryEntry.COLUMN_ID);
                int nameColumnIndex = cursor.getColumnIndex(CategoryEntry.COLUMN_NAME);

                category.setId(cursor.getInt(idColumnIndex));
                category.setName(cursor.getString(nameColumnIndex));

                recipeCategoryList.add(category);
            }

            for (RecipeCategory category : recipeCategoryList){
                category.setImageUriStr(getLastCategoryImageUri(database, category.getId()));
            }

            return recipeCategoryList;
        } finally {
            if (cursor != null) { cursor.close(); }
            if (database != null) { database.close(); }
            if (dbHelper != null) { dbHelper.close(); }
        }

    }

    public static String getLastCategoryImageUri(SQLiteDatabase database, Integer categoryId){
        String imageUriStr = "";
        String[] columns = new String[]{RecipeEntry.COLUMN_IMAGE_URI};
        String[] selectionArgs = new String[]{categoryId.toString()};
        String projection = RecipeEntry.COLUMN_CATEGORY_ID + " = ?";
        String orderBy = RecipeEntry.COLUMN_ID + " DESC";
        String limit = "1";

        Cursor cursor = null;

        try {
            cursor = database.query(
                    RecipeEntry.TABLE_NAME,
                    columns,
                    projection,
                    selectionArgs,
                    null,
                    null,
                    orderBy,
                    limit
            );

            while (cursor.moveToNext()){
                int columnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_IMAGE_URI);
                String uriFromCursor = cursor.getString(columnIndex);
                imageUriStr = uriFromCursor == null ? Constants.EMPTY_STRING : uriFromCursor;
            }
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return imageUriStr;
    }

    public static int updateCategory(Context context, Integer categoryId, String newTitle){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CategoryEntry.COLUMN_NAME, newTitle);

        String where = CategoryEntry.COLUMN_ID + " = ? ";
        String[] whereArgs = new String[]{categoryId.toString()};

        try {
            int updatedRowCount = database.update(CategoryEntry.TABLE_NAME, contentValues, where, whereArgs);
            return updatedRowCount;
        } finally {
            database.close();
            dbHelper.close();
        }
    }

    public static RecipeCategory getCategoryById(Context context, Integer aCategoryId){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = null;

        String where = CategoryEntry.COLUMN_ID + " = ? ";
        String[] whereArgs = new String[]{aCategoryId.toString()};

        try {
            RecipeCategory category = new RecipeCategory();
            cursor = database.query(CategoryEntry.TABLE_NAME, null, where, whereArgs, null, null, null);

            if (cursor.moveToFirst()) {

                int columnIndexCategoryName = cursor.getColumnIndex(CategoryEntry.COLUMN_NAME);
                int columnIndexCategoryId = cursor.getColumnIndex(CategoryEntry.COLUMN_ID);

                Integer categoryId = cursor.getInt(columnIndexCategoryId);
                String categoryName = cursor.getString(columnIndexCategoryName);

                category.setName(categoryName);
                category.setId(categoryId);

                return category;
            } else {
                return null;
            }
        } finally {
            if (cursor != null) cursor.close();
            if (database != null) database.close();
            if (dbHelper != null) dbHelper.close();
        }
    }

    public static Integer deleteCategory(Context aContext, Integer aCategoryId){
        DBHelper dbHelper = new DBHelper(aContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            // Delete recipes which are related to the category
            String whereCategory = RecipeEntry.COLUMN_CATEGORY_ID + " = ? ";
            String[] whereCategoryArgs = new String[]{aCategoryId.toString()};
            database.delete(RecipeEntry.TABLE_NAME, whereCategory, whereCategoryArgs);

            //Delete category
            String where = CategoryEntry.COLUMN_ID + " = ? ";
            String[] whereArgs = new String[]{aCategoryId.toString()};
            Integer deletedRowsCount = database.delete(
                    CategoryEntry.TABLE_NAME, where, whereArgs
                );

            if (deletedRowsCount > 0){
                return deletedRowsCount;
            } else {
                return null;
            }
        } finally {
            if (database != null) database.close();
            if (dbHelper != null) dbHelper.close();
        }
    }

    public static Integer moveRecipeToFavorites(Context aContext, Integer aRecipeId, boolean aNewStatus){
        DBHelper dbHelper = new DBHelper(aContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeEntry.COLUMN_IS_FAVORITE, aNewStatus ? 1 : 0);

        String where = RecipeEntry.COLUMN_ID + " = ? ";
        String[] whereArgs = new String[]{aRecipeId.toString()};

        try {
            Integer updatedRows = database.update(RecipeEntry.TABLE_NAME, contentValues, where, whereArgs);
            return updatedRows;
        } finally {
            if (database != null) database.close();
            if (dbHelper != null) dbHelper.close();
        }
    }

    private static Recipe getRecipeFromCursor(Cursor cursor){
        int columnIndexName = cursor.getColumnIndex(RecipeEntry.COLUMN_TITLE);
        int columnIndexCategoryId = cursor.getColumnIndex(RecipeEntry.COLUMN_CATEGORY_ID);
        int columnIndexIngredients = cursor.getColumnIndex(RecipeEntry.COLUMN_INGREDIENTS);
        int columnIndexInstructions = cursor.getColumnIndex(RecipeEntry.COLUMN_INSTRUCTIONS);
        int columnIndexImageUri = cursor.getColumnIndex(RecipeEntry.COLUMN_IMAGE_URI);
        int columnIndexCategoryName = cursor.getColumnIndex(CategoryEntry.COLUMN_NAME);
        int columnIndexRecipeId = cursor.getColumnIndex(RecipeEntry.COLUMN_ID);
        int columnIndexRecipeIsFavorite = cursor.getColumnIndex(RecipeEntry.COLUMN_IS_FAVORITE);

        String recipeName = cursor.getString(columnIndexName);
        String categoryName = cursor.getString(columnIndexCategoryName);
        String ingredients = cursor.getString(columnIndexIngredients);
        String instructions = cursor.getString(columnIndexInstructions);
        String imageUri = cursor.getString(columnIndexImageUri);
        imageUri = imageUri == null ? Constants.EMPTY_STRING : imageUri;
        int recipeCategoryId = cursor.getInt(columnIndexCategoryId);
        int recipeId = cursor.getInt(columnIndexRecipeId);
        boolean isRecipeInFavorites = cursor.getInt(columnIndexRecipeIsFavorite) == 0 ? false : true;

        Recipe recipe = new Recipe();
        recipe.setCategoryName(categoryName);
        recipe.setImageUri(imageUri);
        recipe.setInstructions(instructions);
        recipe.setIngredients(ingredients);
        recipe.setTitle(recipeName);
        recipe.setCategoryId(recipeCategoryId);
        recipe.set_id(recipeId);
        recipe.setIsFavorite(isRecipeInFavorites);

        return recipe;
    }
}
