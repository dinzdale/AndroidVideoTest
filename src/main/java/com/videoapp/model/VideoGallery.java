package com.videoapp.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by craptop on 12/8/13.
 */
public class VideoGallery {
    static Logger LOG = Logger.getLogger(VideoGallery.class.getName());

    HashMap<String, String> providerCodesMap = new HashMap<String, String>();
    ArrayList<String> providerCodes = new ArrayList<String>();

    ArrayList<VideoGalleryEntry> videoGalleries = new ArrayList<VideoGalleryEntry>();

    public ArrayList<VideoGalleryEntry> getVideoGalleries() {
        return videoGalleries;
    }

    public List<String> getMappedProviderCodes(String providerCodes) {
        List<String> mappedCodes = new ArrayList<String>();
        String[] providerCodesS = providerCodes.split(",");
        for (String nxtIndex : providerCodesS) {
            mappedCodes.add(providerCodesMap.get(nxtIndex));
        }
        return mappedCodes;
    }

    public VideoGallery(JSONObject jsonObject) {
        try {
            Gson gson = new Gson();
            // get providerCodeMap
            String providerCodesMapS = jsonObject.getString("providerCodesMap");
            providerCodesMap = gson.fromJson(providerCodesMapS, providerCodesMap.getClass());

            // get providerCodes
            String providerCodesS = jsonObject.getString("providerCodes");
            providerCodes = gson.fromJson(providerCodesS, providerCodes.getClass());

            // get videoGalleries
            String videoGalleriesString = jsonObject.getString("videoGalleries");
            Type type = new TypeToken<ArrayList<VideoGalleryEntry>>() {
            }.getType();
            videoGalleries = gson.fromJson(videoGalleriesString, type);

            //videoGalleries = gson.fromJson(jsonObject.toString(),videoGalleries.getClass());
        } catch (Exception ex) {
            LOG.severe(ex.getMessage());
        }


    }


    public static class VideoGalleryEntry {
        ArrayList<VideoGalleryItem> items = new ArrayList<VideoGalleryItem>();
        VideoGalleryHeader header = new VideoGalleryHeader();

        public ArrayList<VideoGalleryItem> getItems() {
            return items;
        }

        public VideoGalleryHeader getHeader() {
            return header;
        }
    }

    public static class VideoGalleryItem {

        int entityEpisodeCount;
        String videoPid;
        String networkLogoSmallUrl;
        String link;
        String videoExpirationDate;
        String entityThumbnailUrl;
        boolean entityIsOnDemand;
        String videoName;
        String entityType;
        String networkGlobalUid;
        boolean videoHasExpired;
        String videoNetworkDisplayName;
        String masterLinkType;
        long entityLongFormCount;
        String videoGlobalUid;
        String videoDescription;
        long entityLongFormProtectedCount;
        String description;
        String name;
        long videoId;
        long videoPrimaryEntityId;
        boolean isAdult;
        String videoRating;
        String networkName;
        String videoReleaseUrl;
        boolean videoIsProtected;
        String videoBrand;
        String entityProviderCodes;
        String entityWatchOnTvUrl;
        String entityPosterArtUrl;
        String networkLogoUrl;
        int episodeNumber;
        int episodeSeasonNumber;
        String entityReleaseYear;

        Image image = new Image();

        public static class Image {
            private String alt;
            private String src;

            public String getAlt() {
                return alt;
            }

            public String getSrc() {
                return src;
            }
        }

        public int getEntityEpisodeCount() {
            return entityEpisodeCount;
        }

        public String getVideoPid() {
            return videoPid;
        }

        public String getNetworkLogoSmallUrl() {
            return networkLogoSmallUrl;
        }

        public String getLink() {
            return link;
        }

        public String getVideoExpirationDate() {
            return videoExpirationDate;
        }

        public String getEntityThumbnailUrl() {
            return entityThumbnailUrl;
        }

        public boolean isEntityIsOnDemand() {
            return entityIsOnDemand;
        }

        public String getVideoName() {
            return videoName;
        }

        public String getEntityType() {
            return entityType;
        }

        public String getNetworkGlobalUid() {
            return networkGlobalUid;
        }

        public boolean isVideoHasExpired() {
            return videoHasExpired;
        }

        public String getVideoNetworkDisplayName() {
            return videoNetworkDisplayName;
        }

        public String getMasterLinkType() {
            return masterLinkType;
        }

        public long getEntityLongFormCount() {
            return entityLongFormCount;
        }

        public long getEntityLongFormProtectedCount() {
            return entityLongFormProtectedCount;
        }

        public String getVideoGlobalUid() {
            return videoGlobalUid;
        }

        public String getVideoDescription() {
            return videoDescription;
        }


        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public long getVideoId() {
            return videoId;
        }

        public long getVideoPrimaryEntityId() {
            return videoPrimaryEntityId;
        }

        public boolean isAdult() {
            return isAdult;
        }

        public String getVideoRating() {
            return videoRating;
        }

        public String getNetworkName() {
            return networkName;
        }

        public String getVideoReleaseUrl() {
            return videoReleaseUrl;
        }

        public boolean isVideoIsProtected() {
            return videoIsProtected;
        }

        public String getVideoBrand() {
            return videoBrand;
        }

        public String getEntityProviderCodes() {
            return entityProviderCodes;
        }

        public String getEntityWatchOnTvUrl() {
            return entityWatchOnTvUrl;
        }

        public String getEntityPosterArtUrl() {
            return entityPosterArtUrl;
        }

        public String getNetworkLogoUrl() {
            return networkLogoUrl;
        }

        public int getEpisodeNumber() {
            return episodeNumber;
        }

        public int getEpisodeSeasonNumber() {
            return episodeSeasonNumber;
        }

        public String getEntityReleaseYear() {
            return entityReleaseYear;
        }

        public Image getImage() {
            return image;
        }

    }

    public static class VideoGalleryHeader {
        String seeAllLink;
        String description;
        String name;

        public String getSeeAllLink() {
            return seeAllLink;
        }

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }
    }
}
