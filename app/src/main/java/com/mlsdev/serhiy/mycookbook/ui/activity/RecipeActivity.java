package com.mlsdev.serhiy.mycookbook.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.database.DBContract;
import com.mlsdev.serhiy.mycookbook.ui.fragment.RecipeFragment;
import com.mlsdev.serhiy.mycookbook.utils.Constants;

public class RecipeActivity extends BaseActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        setActionBarTitle(Constants.EMPTY_STRING);
        Fragment fragment = new RecipeFragment();

        if (savedInstanceState != null){
            fragment = getFragmentManager().findFragmentByTag(RecipeFragment.class.getName());
        } else {
            fragment.setArguments(bundle);
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder_view_recipe_screen, fragment, RecipeFragment.class.getName())
                .commit();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recipe;
    }

}
