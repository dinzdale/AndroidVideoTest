package com.videoapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.gmjproductions.R;
import com.videoapp.model.VideoDataLoadedEvent;
import com.videoapp.model.VideoDataService;
import com.videoapp.model.VideoGallery;
import com.squareup.otto.Subscribe;

import java.util.List;

import java.util.logging.Logger;

public class MainVideoActivity extends SherlockFragmentActivity implements AdapterView.OnItemClickListener {
    Logger LOG = Logger.getLogger("MainVideoActivity");

    boolean twoPane;
    VideoDataService videoDataService;
    VideoGallery videoGallery;
    boolean serviceBound = false;
    //BroadcastReceiver broadcastReceiver;
    int entity_index;
    int selection_index;

    Menu menu;

    @Subscribe
    public void onVideoDataEvent(VideoDataLoadedEvent event) {
        if (event.isDataLoaded()) {
            doBindService();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        ((VideoApplication) getApplication()).getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        ((VideoApplication) getApplication()).getBus().unregister(this);
        unBindService();

        FragmentManager fm = getSupportFragmentManager();
        int cnt = fm.getBackStackEntryCount();
        for (int i = 0; i < cnt; i++) {
            fm.popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.first_menu_id:
                entity_index = 0;
                selection_index = 0;
                item.setChecked(true);
                getSupportActionBar().setTitle(videoGallery.getVideoGalleries().get(0).getHeader().getName());
                break;
            case R.id.second_menu_id:
                entity_index = 1;
                selection_index = 0;
                item.setChecked(true);
                getSupportActionBar().setTitle(videoGallery.getVideoGalleries().get(1).getHeader().getName());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        FragmentManager fm = getSupportFragmentManager();
        if (twoPane) {
            refreshList();
            VideoDetailsFragment videoDetailsFragment = getNewVideoDetailsFragment();
            fm.beginTransaction().replace(R.id.frag_container2, videoDetailsFragment).commit();
        } else {
            fm.popBackStack();
            refreshList();
        }
        return true;
    }

    private void refreshList() {
        FragmentManager fm = getSupportFragmentManager();
        VideoListFragment videoListFragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(VideoDetailsFragment.ARG_ENTITY_GROUP_INDEX, entity_index);
        bundle.putInt(VideoDetailsFragment.ARG_ENTITY_INDEX, selection_index);
        videoListFragment.setArguments(bundle);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_container, videoListFragment);
        ft.commit();

    }

    private VideoDetailsFragment getNewVideoDetailsFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(VideoDetailsFragment.ARG_ENTITY_GROUP_INDEX, entity_index);
        bundle.putInt(VideoDetailsFragment.ARG_ENTITY_INDEX, selection_index);
        VideoDetailsFragment fragment = new VideoDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(VideoDetailsFragment.ARG_ENTITY_GROUP_INDEX, entity_index);
        outState.putInt(VideoDetailsFragment.ARG_ENTITY_INDEX, selection_index);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        entity_index = savedInstanceState.getInt(VideoDetailsFragment.ARG_ENTITY_GROUP_INDEX);
        selection_index = savedInstanceState.getInt(VideoDetailsFragment.ARG_ENTITY_INDEX);
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        twoPane = (findViewById(R.id.frag_container2) != null);
        startService(new Intent(this, VideoDataService.class));
    }

    private void doBindService() {
        bindService(new Intent(this, VideoDataService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    private void unBindService() {
        unbindService(serviceConnection);
        serviceBound = false;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            serviceBound = true;
            videoDataService = ((VideoDataService.TheBinder) service).getService();
            videoGallery = videoDataService.getVideoGallery();
            refreshList();
            // add second fragment
            if (twoPane) {
                FragmentManager fm = getSupportFragmentManager();
                VideoDetailsFragment detailsFragment = getNewVideoDetailsFragment();
                fm.beginTransaction().replace(R.id.frag_container2, detailsFragment).commit();
            }
            getSupportActionBar().setTitle(videoGallery.getVideoGalleries().get(entity_index).getHeader().getName());
            menu.findItem(R.id.first_menu_id).setTitle(videoGallery.getVideoGalleries().get(0).getHeader().getName());
            menu.findItem(R.id.second_menu_id).setTitle(videoGallery.getVideoGalleries().get(1).getHeader().getName());

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(MainVideoActivity.this, "Service Disconnected", Toast.LENGTH_SHORT);
            serviceBound = false;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VideoGallery.VideoGalleryItem data = (VideoGallery.VideoGalleryItem) parent.getAdapter().getItem(position);
        selection_index = position;
        VideoDetailsFragment fragment = getNewVideoDetailsFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (!twoPane) {
            transaction.replace(R.id.frag_container, fragment);
            transaction.addToBackStack(null);
        } else {
            transaction.replace(R.id.frag_container2, fragment);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> frags = fm.getFragments();
        if (!twoPane) {
            int cnt = fm.getBackStackEntryCount();
            if (cnt > 0) {
                fm.popBackStack();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}
