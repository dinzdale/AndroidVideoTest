package com.videoapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.gmjproductions.R;
import com.videoapp.model.VideoGallery;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: craptop
 * Date: 10/13/13
 * Time: 6:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class VideoListFragment extends Fragment {
    private ListView videoList;
    private AdapterView.OnItemClickListener listener = null;
    MainVideoActivity mActivity;
    int currentGroup;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_list, null);
        videoList = (ListView) view.findViewById(R.id.listView);
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
// notify host activity
                if (listener != null) {
                    listener.onItemClick(parent, view, position, id);
                }
            }
        });
        if (mActivity.videoGallery != null) {
            updateData(mActivity.videoGallery);
        }
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.
        mActivity = (MainVideoActivity) activity;
        if (activity instanceof AdapterView.OnItemClickListener) {
            listener = (AdapterView.OnItemClickListener) activity;
        } else {
            listener = null;
        }
    }

    class VideoListAdapter extends ArrayAdapter<VideoGallery.VideoGalleryItem> {

        public VideoListAdapter(Context context, List<VideoGallery.VideoGalleryItem> objects) {
            super(context, -1, objects);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.video_list_item, null);
                holder.videoTitle = (TextView) convertView.findViewById(R.id.video_title);
                holder.videoDescription = (TextView) convertView.findViewById(R.id.video_description);
                holder.thumbnail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
                holder.networklogo = (NetworkImageView) convertView.findViewById(R.id.networklogo);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();

            }
            VideoGallery.VideoGalleryItem item = getItem(position);

            holder.videoTitle.setText(item.getName());
            if (currentGroup == 0) {
                holder.videoDescription.setText(item.getVideoName());
            } else {
                holder.videoDescription.setText(item.getEntityReleaseYear());
            }
            holder.thumbnail.setImageUrl(item.getEntityPosterArtUrl(), VideoApplication.imageLoader);
            holder.networklogo.setImageUrl(item.getNetworkLogoUrl(), VideoApplication.imageLoader);
            return convertView;
        }

        class Holder {
            public TextView videoTitle;
            public TextView videoDescription;
            public NetworkImageView thumbnail;
            public NetworkImageView networklogo;
        }
    }

    private void updateData(final VideoGallery videoGallery) {
        Bundle bundle = getArguments();
        currentGroup = 0;
        if (bundle != null) {
            currentGroup = bundle.getInt(VideoDetailsFragment.ARG_ENTITY_GROUP_INDEX);
        }
        videoList.setAdapter(new VideoListAdapter(getActivity(), videoGallery.getVideoGalleries().get(currentGroup).getItems()));
        ((BaseAdapter) videoList.getAdapter()).notifyDataSetChanged();
    }
}