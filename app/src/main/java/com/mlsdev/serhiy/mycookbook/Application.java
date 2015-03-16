package com.mlsdev.serhiy.mycookbook;

import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by android on 03.03.15.
 */

public class Application extends android.app.Application {

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();
        CalligraphyConfig.initDefault("fonts/roboto_light.ttf");

    }

}