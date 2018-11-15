/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.provider.BaseColumns
 */
package de.cisha.android.board.video.storage;

import android.provider.BaseColumns;
import de.cisha.android.board.video.storage.VideoDbContract;

public static abstract class VideoDbContract.VideoData
implements BaseColumns {
    public static final String COLUMN_NAME_CREATION_DATE = "creation_date";
    public static final String COLUMN_NAME_EDIT_DATE = "edit_date";
    public static final String COLUMN_NAME_SERIES_ID = "series_id";
    public static final String COLUMN_NAME_VIDEO_DOWNLOAD_REQUEST_ID = "video_download_request_id";
    public static final String COLUMN_NAME_VIDEO_FILE_SIZE = "video_file_size";
    public static final String COLUMN_NAME_VIDEO_ID = "video_id";
    public static final String COLUMN_NAME_VIDEO_JSON = "video_json";
    public static final String TABLE_NAME = "video_data";
}
