package com.mlsdev.serhiy.mycookbook.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.ui.fragment.RecipeListFragment;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

public class CategoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle categoryData = getIntent().getExtras();
        String categoryName = categoryData.getString(DBContract.CategoryEntry.COLUMN_NAME, "");
        setActionBarTitle(categoryName);

        if (savedInstanceState == null){
            Fragment fragment = new RecipeListFragment();
            fragment.setArguments(categoryData);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder_recipe_activity, fragment, RecipeListFragment.class.getName())
                    .commit();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_category;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_category, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
