package com.videoapp.model;

/**
 * Created by gjacobs on 12/22/14.
 */
public class VideoDataLoadedEvent {
    boolean isDataLoaded;

    public VideoDataLoadedEvent(boolean isDataLoaded) {
        this.isDataLoaded = isDataLoaded;
    }

    public boolean isDataLoaded() {
        return isDataLoaded;
    }
}
