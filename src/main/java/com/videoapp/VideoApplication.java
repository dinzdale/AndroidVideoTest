package com.videoapp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.videoapp.model.BitmapMemCache;
import com.squareup.otto.Bus;

/**
 * Created by craptop on 12/15/13.
 */
public class VideoApplication extends Application {
    public static RequestQueue requestQueue;
    public static ImageLoader imageLoader;
    private Bus bus;

    public Bus getBus() {
        return bus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(requestQueue,new BitmapMemCache());
        bus = new Bus();

    }

}
