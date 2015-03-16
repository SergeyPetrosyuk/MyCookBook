package com.mlsdev.serhiy.mycookbook.ui.activity;

import android.os.Bundle;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.ui.fragment.CategoriesFragment;

public class MainActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(getTitle().toString());
        
        initLeftDrawerNav();
        if (savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder_main_activity, new CategoriesFragment())
                    .commit();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }
}
