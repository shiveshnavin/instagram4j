package com.github.instagram4j.instagram4j.requests.media;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.requests.IGGetRequest;
import com.github.instagram4j.instagram4j.requests.IGPaginatedRequest;
import com.github.instagram4j.instagram4j.responses.media.MediaGetCommentsResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@RequiredArgsConstructor
public class MediaGetCommentsRequest extends IGGetRequest<MediaGetCommentsResponse>
        implements IGPaginatedRequest {
    @NonNull
    private String _id;
    @Setter
    private String max_id;
    @Setter
    private String min_id;
    @Setter
    private SortOrder sortOrder;

    @Override
    public String path() {
        return "media/" + _id + "/comments/";
    }

    @Override
    public String getQueryString(IGClient client) {
        ArrayList<String> queryParams = new ArrayList<>();
        if (sortOrder != null) {
            queryParams.add("sort_order");
            queryParams.add(sortOrder.textValue);
            queryParams.add("carousel_index");
            queryParams.add("0");
            queryParams.add("analytics_module");
            queryParams.add("self_comments_v2_clips_viewer_self_clips_profile");
            queryParams.add("can_support_threading");
            queryParams.add("true");
            queryParams.add("is_carousel_bumped_post");
            queryParams.add("false");
            queryParams.add("feed_position");
            queryParams.add("0");
        }
        if (min_id != null) {
            queryParams.add("min_id");
            queryParams.add(min_id);
        }
        if (max_id != null) {
            queryParams.add("max_id");
            queryParams.add(max_id);
        }
        return mapQueryString(queryParams.toArray());
    }

    @Override
    public Class<MediaGetCommentsResponse> getResponseType() {
        return MediaGetCommentsResponse.class;
    }

    public enum SortOrder {
        RECENT("recent"), POPULAR("popular");
        private final String textValue;

        SortOrder(String textValue) {
            this.textValue = textValue;
        }

        public String getTextValue() {
            return textValue;
        }

        @Override
        public String toString() {
            return super.toString();
        }

        public static SortOrder valueOfString(String textValue) {
            if (RECENT.textValue.equals(textValue))
                return RECENT;
            if (POPULAR.textValue.equals(textValue))
                return POPULAR;
            return null;
        }
    }
}
