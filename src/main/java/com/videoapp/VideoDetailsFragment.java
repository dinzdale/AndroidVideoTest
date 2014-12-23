package com.videoapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.gmjproductions.R;
import com.videoapp.model.VideoGallery;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: craptop
 * Date: 10/13/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class VideoDetailsFragment extends Fragment {

    public static String ARG_ENTITY_GROUP_INDEX = "ARG_ENTITY_GROUP_INDEX";
    public static String ARG_ENTITY_INDEX = "ARG_ENTITY_INDEX";
    int groupIndex;
    int selectionIndex;
    ViewGroup parent;
    TextView title;
    NetworkImageView posterArt;
    NetworkImageView entityImage;
    TextView networkID;
    TextView episodeInfo;
    TextView rating;
    TextView videoDetails;
    VideoGallery videoGallery;
    MainVideoActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainVideoActivity) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_ENTITY_GROUP_INDEX, groupIndex);
        outState.putInt(ARG_ENTITY_INDEX, selectionIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = (ViewGroup) inflater.inflate(R.layout.video_details, null);
        title = (TextView) parent.findViewById(R.id.video_title);
        posterArt = (NetworkImageView) parent.findViewById(R.id.poster_art);
        episodeInfo = (TextView) parent.findViewById(R.id.episodeInfo);
        networkID = (TextView) parent.findViewById(R.id.network_id);
        rating = (TextView) parent.findViewById(R.id.rating);
        entityImage = (NetworkImageView) parent.findViewById(R.id.entityImage);
        videoDetails = (TextView) parent.findViewById(R.id.video_details_text);

        Bundle bundle = getArguments();
        if (savedInstanceState != null) {
            groupIndex = savedInstanceState.getInt(ARG_ENTITY_GROUP_INDEX);
            selectionIndex = savedInstanceState.getInt(ARG_ENTITY_INDEX);
        } else if (bundle != null) {
            groupIndex = bundle.getInt(ARG_ENTITY_GROUP_INDEX);
            selectionIndex = bundle.getInt(ARG_ENTITY_INDEX);
        }
        if (mActivity.videoGallery != null) {
            VideoGallery.VideoGalleryItem item = mActivity.videoGallery.getVideoGalleries().get(groupIndex).getItems().get(selectionIndex);
            title.setText(item.getName());
            posterArt.setImageUrl(item.getEntityPosterArtUrl(), VideoApplication.imageLoader);
            List<String> mappedProviderCodes = mActivity.videoGallery.getMappedProviderCodes(item.getEntityProviderCodes());
            // network names are messed up and don't actually map properly
            networkID.setText(item.getNetworkName());
            if (groupIndex == 0) {
                episodeInfo.setVisibility(View.VISIBLE);
                episodeInfo.setText(getString(R.string.season_episode, item.getEpisodeSeasonNumber(), item.getEntityEpisodeCount()));
            }
            rating.setText(item.getVideoRating());
            entityImage.setImageUrl(item.getEntityThumbnailUrl(), VideoApplication.imageLoader);
            videoDetails.setText(item.getVideoDescription());
        }
        return parent;
    }

}