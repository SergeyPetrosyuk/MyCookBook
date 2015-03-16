package com.mlsdev.serhiy.mycookbook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.mlsdev.serhiy.mycookbook.database.DBHelper;
import com.mlsdev.serhiy.mycookbook.model.Recipe;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

import java.util.Map;
import java.util.Set;

import static com.mlsdev.serhiy.mycookbook.database.DBContract.*;

/**
 * Created by android on 10.03.15.
 */
public class TestDB extends AndroidTestCase {

    public static final boolean DB_IS_OPENED = true;

    public void testCreateDB() throws Exception {
        mContext.deleteDatabase(DB_NAME);
        SQLiteDatabase database = new DBHelper(mContext).getWritableDatabase();
        assertEquals(DB_IS_OPENED, database.isOpen());
        database.close();
    }

    public void testInsertSelect(){
        // Get a writable database
        SQLiteDatabase database = new DBHelper(mContext).getWritableDatabase();
        ContentValues categoryContentValues = new ContentValues();
        categoryContentValues.put(CategoryEntry.COLUMN_NAME, "Salads");

        // Insert data into db
        long insertedCategoryRowId = database.insert(CategoryEntry.TABLE_NAME, null, categoryContentValues);
        Log.d(Constants.LOG_TAG, "New row id is " + insertedCategoryRowId);

        //Verify we got a row id back
        assertTrue(insertedCategoryRowId != -1);

        // Read data from db
        Cursor cursorCategory = database.query(CategoryEntry.TABLE_NAME, null, null, null, null, null, null);
        validateCursor(cursorCategory, categoryContentValues);

        // So we have a category id we can insert a recipe
        ContentValues recipeContentValues = getContentValuesForRecipe(insertedCategoryRowId);
        long insertedRecipeRowId = database.insert(RecipeEntry.TABLE_NAME, null, recipeContentValues);

        //Verify we got a row id back
        assertTrue(insertedRecipeRowId != -1);

        // Read data from db
        Cursor cursorRecipe = database.query(RecipeEntry.TABLE_NAME, null, null, null, null, null, null);
        validateCursor(cursorRecipe, recipeContentValues);

        database.close();
    }

    public static void validateCursor(Cursor cursor, ContentValues contentValues){
        assertTrue(cursor.moveToFirst());
        Set<Map.Entry<String, Object>> entrySet = contentValues.valueSet();

        for (Map.Entry<String,Object> entry : entrySet){
            String column = entry.getKey();
            String expectedValue = entry.getValue().toString();
            Integer columnIndex = cursor.getColumnIndex(column);

            // Verify we got a correct column id
            assertTrue(columnIndex != -1);

            // Verify we got a correct data
            assertEquals(expectedValue, cursor.getString(columnIndex));
        }

        // Close a cursor after all actions
        cursor.close();
    }

    public static ContentValues getContentValuesForRecipe(long insertedCategoryRowId){
        ContentValues values = new ContentValues();

        values.put(RecipeEntry.COLUMN_TITLE, "My first recipe");
        values.put(RecipeEntry.COLUMN_CATEGORY_ID, insertedCategoryRowId);
        values.put(RecipeEntry.COLUMN_INGREDIENTS, "1. Broccoli \n 2. Apples \n 3. Water");
        values.put(RecipeEntry.COLUMN_INSTRUCTIONS, "Замішате все в одній кастрюлі. \n Другий крок - їсти все це руками.");
        values.put(RecipeEntry.COLUMN_IMAGE_URI, "image_uri is here");

        return values;
    }

}
