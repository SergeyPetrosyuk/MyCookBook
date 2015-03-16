package com.mlsdev.serhiy.mycookbook.ui.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.ui.activity.BaseActivity;

/**
 * Created by android on 03.03.15.
 */
public class LeftDrawerFragment extends Fragment implements View.OnClickListener {

    private Button mMainBtn;
    private Button mSettingsBtn;
    private Button mFavoritesBtn;
//    private Button mCategoriesBtn;
    private Button mRateUsBtn;
    private Button mFoundProblemBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_left_drawer, container, false);
        findViews(view);
        setupOnClickListeners();
        return view;
        
    }
    
    private void findViews(View view){
        mMainBtn        = (Button) view.findViewById(R.id.btn_main);
        mSettingsBtn    = (Button) view.findViewById(R.id.btn_settings);
        mFavoritesBtn   = (Button) view.findViewById(R.id.btn_favorite);
//        mCategoriesBtn  = (Button) view.findViewById(R.id.btn_categories);
        mRateUsBtn      = (Button) view.findViewById(R.id.btn_rate_us);
        mFoundProblemBtn= (Button) view.findViewById(R.id.btn_fount_problem);
    }
    
    private void setupOnClickListeners(){
        mMainBtn.setOnClickListener(this);
        mSettingsBtn.setOnClickListener(this);
        mFavoritesBtn.setOnClickListener(this);
//        mCategoriesBtn.setOnClickListener(this);
        mRateUsBtn.setOnClickListener(this);
        mFoundProblemBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        BaseActivity activity = (BaseActivity) getActivity();
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        String screenTitle = activity.getString(R.string.app_name);

        switch (v.getId()){
            case R.id.btn_main:
                screenTitle = activity.getString(R.string.app_name);
                fragmentTransaction.replace(R.id.fragment_holder_main_activity, new CategoriesFragment(), CategoriesFragment.class.getName())
                        .commit();
                break;
            case R.id.btn_settings:

                break;
            case R.id.btn_favorite:

                break;

            case R.id.btn_rate_us:

                break;
            case R.id.btn_fount_problem:

                break;
            default:
                break;
        }

        activity.setCurrentTitle(screenTitle);
        activity.getDrawerLayout().closeDrawer(Gravity.START);
    }
}
