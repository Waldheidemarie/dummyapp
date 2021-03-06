package com.hms.example.dummyapplication;

import android.app.Application;
import android.content.Context;

import com.facebook.appevents.AppEventsLogger;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.feature.dynamicinstall.FeatureCompat;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
        HwAds.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        FeatureCompat.install(base);

    }
}
