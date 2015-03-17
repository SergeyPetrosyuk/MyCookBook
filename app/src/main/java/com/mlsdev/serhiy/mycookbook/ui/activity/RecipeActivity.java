package com.mlsdev.serhiy.mycookbook.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.ui.fragment.RecipeFragment;

public class RecipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        String recipeTitle = bundle.getString(DBContract.RecipeEntry.COLUMN_TITLE, getString(R.string.app_name));

        setActionBarTitle(recipeTitle);

        if (savedInstanceState == null){
            Fragment fragment = new RecipeFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder_view_recipe_screen, fragment, RecipeFragment.class.getName())
                    .commit();
        }

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recipe;
    }

}
