package com.videoapp.model;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.videoapp.VideoApplication;

import org.json.JSONObject;

/**
 * Created by craptop on 12/14/13.
 */
public class VideoDataService extends Service {
    public static String VIDEO_DATA_READY = "VIDEO_DATA_READY";
    String urlS = "http://xfinitytv.comcast.net/api/xfinity/ipad/home/videos?filter&type=json";
    RequestQueue requestQueue;
    VideoGallery videoGallery;
    private IBinder mBinder = new TheBinder();


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestQueue = VideoApplication.requestQueue;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(urlS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                videoGallery = new VideoGallery(jsonObject);
                ((VideoApplication)getApplication()).getBus().post(new VideoDataLoadedEvent(true));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );
        requestQueue.add(jsonObjectRequest);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    public class TheBinder extends Binder {
        public VideoDataService getService() {
            return VideoDataService.this;
        }
    }

    public VideoGallery getVideoGallery() {
        return videoGallery;
    }
}
