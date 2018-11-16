// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.storage;

import android.provider.BaseColumns;

public final class VideoDbContract
{
    public abstract static class SeriesOffline implements BaseColumns
    {
        public static final String COLUMN_NAME_AVAILABLE = "offline_available";
        public static final String COLUMN_NAME_CREATION_DATE = "creation_date";
        public static final String COLUMN_NAME_EDIT_DATE = "edit_date";
        public static final String COLUMN_NAME_SERIES_ID = "series_id";
        public static final String COLUMN_NAME_SERIES_JSON = "series_json";
        public static final String TABLE_NAME = "series_offline_available";
    }
    
    public abstract static class VideoData implements BaseColumns
    {
        public static final String COLUMN_NAME_CREATION_DATE = "creation_date";
        public static final String COLUMN_NAME_EDIT_DATE = "edit_date";
        public static final String COLUMN_NAME_SERIES_ID = "series_id";
        public static final String COLUMN_NAME_VIDEO_DOWNLOAD_REQUEST_ID = "video_download_request_id";
        public static final String COLUMN_NAME_VIDEO_FILE_SIZE = "video_file_size";
        public static final String COLUMN_NAME_VIDEO_ID = "video_id";
        public static final String COLUMN_NAME_VIDEO_JSON = "video_json";
        public static final String TABLE_NAME = "video_data";
    }
}
