package com.mlsdev.serhiy.mycookbook.ui.activity;

import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.ui.fragment.AddCategoryFragment;

public class AddCategoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(getTitle().toString());

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder_add_category_screen, new AddCategoryFragment(), AddCategoryFragment.class.getName())
                    .commit();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_category;
    }

}
