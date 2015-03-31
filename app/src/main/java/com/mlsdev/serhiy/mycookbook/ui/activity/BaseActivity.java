package com.mlsdev.serhiy.mycookbook.ui.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mlsdev.serhiy.mycookbook.R;
import com.mlsdev.serhiy.mycookbook.utils.TypefaceSpan;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by android on 26.02.15.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mCurrentTitle;
    private boolean mIsMoreThanOneFragment = false;
    private ImageButton mFilterImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        if (!(this instanceof MainActivity)) {
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        mToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        
        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mFilterImageButton = (ImageButton) mToolbar.findViewById(R.id.iv_filter_by_favorites);
        }

        mCurrentTitle = getTitle().toString();
    }

    protected void initLeftDrawerNav(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.drawer_menu, R.string.app_name){

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setActionBarTitle(getString(R.string.drawer_menu));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                setActionBarTitle(mCurrentTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (mDrawerToggle != null) { mDrawerToggle.syncState(); }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null) { mDrawerToggle.onConfigurationChanged(newConfig); }
    }

    protected abstract int getLayoutResource();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (!mIsMoreThanOneFragment) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            getFragmentManager().popBackStackImmediate();
        }
        
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        if (!(this instanceof MainActivity)) {
            overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
    
    public void setActionBarTitle(String title){
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(this, "roboto_light.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        getSupportActionBar().setTitle(s);
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setCurrentTitle(String mCurrentTitle) {
        this.mCurrentTitle = mCurrentTitle;
    }

    protected void setupActionBarTransparency(boolean isSetup){
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent_bg));
    }

    public void setIsMoreThanOneFragment(boolean mIsMoreThanOneFragment) {
        this.mIsMoreThanOneFragment = mIsMoreThanOneFragment;
    }

    public ImageButton getFilterBtn() {
        return mFilterImageButton;
    }

    public void showFilterBtn(boolean isShow){
        if (isShow)
            mFilterImageButton.setVisibility(View.VISIBLE);
        else
            mFilterImageButton.setVisibility(View.GONE);
    }
}
