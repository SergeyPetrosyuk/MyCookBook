package com.mlsdev.serhiy.mycookbook.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.ui.fragment.AddRecipeFragment;

public class AddRecipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle((String) getTitle());

        FragmentManager fragmentManager = getFragmentManager();

        if (savedInstanceState == null) {
            Fragment fragment = new AddRecipeFragment();
            fragment.setArguments(getIntent().getExtras());

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_holder_add_note_screen, fragment, AddRecipeFragment.class.getName())
                    .commit();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_note;
    }

}
