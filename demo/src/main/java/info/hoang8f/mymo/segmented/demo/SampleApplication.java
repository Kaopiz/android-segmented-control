package info.hoang8f.mymo.segmented.demo;

import android.app.Application;

import info.hoang8f.android.segmented.utils.TypefaceProvider;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}
