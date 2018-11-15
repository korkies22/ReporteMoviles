/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.video.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.chess.util.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

class LocalVideoStorageDbHelper
extends SQLiteOpenHelper
implements ILocalVideoStorage {
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_NAME = "LocalVideoDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String NUM_TYPE = " INTEGER";
    private static final String SQL_CREATE_TABLE_VIDEO_DATA = "CREATE TABLE video_data(series_id INTEGER,video_id INTEGER,video_json TEXT,video_download_request_id INTEGER,video_file_size INTEGER,creation_date INTEGER DEFAULT (CURRENT_TIMESTAMP),edit_date INTEGER DEFAULT (CURRENT_TIMESTAMP),PRIMARY KEY(series_id,video_id))";
    private static final String SQL_CREATE_TABLE_VIDEO_SERIES_AVAILABLE = "CREATE TABLE series_offline_available(series_id INTEGER PRIMARY KEY,offline_available INTEGER,series_json TEXT,creation_date INTEGER DEFAULT (CURRENT_TIMESTAMP),edit_date INTEGER DEFAULT (CURRENT_TIMESTAMP))";
    private static final String SQL_CREATE_TRIGGER_EDIT_DATE = "CREATE TRIGGER IF NOT EXISTS update_edit_date_video_data AFTER UPDATE  ON video_data BEGIN  \tUPDATE video_data \tSET edit_date= (CURRENT_TIMESTAMP) \t\tWHERE series_id= old.series_id            AND video_id= old.video_id; END";
    private static final String SQL_CREATE_TRIGGER_EDIT_DATE_SERIES = "CREATE TRIGGER IF NOT EXISTS update_edit_date_video_series AFTER UPDATE  ON series_offline_available BEGIN  \tUPDATE series_offline_available \tSET edit_date= (CURRENT_TIMESTAMP) \t\tWHERE series_id= old.series_id; END";
    private static final String TEXT_TYPE = " TEXT";

    public LocalVideoStorageDbHelper(Context context, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DATABASE_NAME);
        stringBuilder.append(string);
        super(context, stringBuilder.toString(), null, 1);
    }

    private String getDropTableStatement(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DROP TABLE IF EXISTS ");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private static String getVideoWhereClause(VideoSeriesId videoSeriesId, VideoId videoId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("series_id=");
        stringBuilder.append(videoSeriesId.getUuid());
        stringBuilder.append(" AND ");
        stringBuilder.append("video_id");
        stringBuilder.append("=");
        stringBuilder.append(videoId.getUuid());
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<VideoSeriesId> getAllLocalVideoSeries() {
        LinkedList<VideoSeriesId> linkedList = new LinkedList<VideoSeriesId>();
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            Cursor cursor = sQLiteDatabase.query("series_offline_available", new String[]{"series_id", "offline_available"}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    int n = cursor.getInt(cursor.getColumnIndex("offline_available")) > 0 ? 1 : 0;
                    if (n == 0) continue;
                    n = cursor.getInt(cursor.getColumnIndex("series_id"));
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(n);
                    stringBuilder.append("");
                    linkedList.add(new VideoSeriesId(stringBuilder.toString()));
                } while (cursor.moveToNext());
            }
            cursor.close();
            sQLiteDatabase.setTransactionSuccessful();
            return linkedList;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    @Override
    public List<VideoId> getAllLocalVideosForSeries(VideoSeriesId object) {
        LinkedList<VideoId> linkedList = new LinkedList<VideoId>();
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("series_id=");
            stringBuilder.append(object.getUuid());
            object = stringBuilder.toString();
            object = sQLiteDatabase.query("video_data", new String[]{"video_id"}, (String)object, null, null, null, null);
            if (object.moveToFirst()) {
                do {
                    linkedList.add(new VideoId(object.getString(object.getColumnIndex("video_id"))));
                } while (object.moveToNext());
            }
            object.close();
            sQLiteDatabase.setTransactionSuccessful();
            return linkedList;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    @Override
    public List<ILocalVideoStorage.VideoDownloadRequestTupel> getAllVideoDownloadRequestIds() {
        LinkedList<ILocalVideoStorage.VideoDownloadRequestTupel> linkedList = new LinkedList<ILocalVideoStorage.VideoDownloadRequestTupel>();
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            Cursor cursor = sQLiteDatabase.query("video_data", new String[]{"series_id", "video_id", "video_download_request_id"}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String string = cursor.getString(cursor.getColumnIndex("video_id"));
                    String string2 = cursor.getString(cursor.getColumnIndex("series_id"));
                    long l = cursor.getLong(cursor.getColumnIndex("video_download_request_id"));
                    linkedList.add(new ILocalVideoStorage.VideoDownloadRequestTupel(new VideoSeriesId(string2), new VideoId(string), l));
                } while (cursor.moveToNext());
            }
            cursor.close();
            sQLiteDatabase.setTransactionSuccessful();
            return linkedList;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long getDownloadIdForVideo(VideoSeriesId object, VideoId videoId) {
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            object = LocalVideoStorageDbHelper.getVideoWhereClause((VideoSeriesId)object, videoId);
            object = sQLiteDatabase.query("video_data", new String[]{"video_download_request_id"}, (String)object, null, null, null, null);
            long l = object.moveToFirst() ? object.getLong(object.getColumnIndex("video_download_request_id")) : 0L;
            object.close();
            sQLiteDatabase.setTransactionSuccessful();
            return l;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long getFilesizeForVideodownload(long l) {
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            CharSequence charSequence = new StringBuilder();
            charSequence.append("video_download_request_id=");
            charSequence.append(l);
            charSequence = charSequence.toString();
            charSequence = sQLiteDatabase.query("video_data", new String[]{"video_file_size"}, (String)charSequence, null, null, null, null);
            l = charSequence.moveToFirst() ? charSequence.getLong(charSequence.getColumnIndex("video_file_size")) : 0L;
            charSequence.close();
            sQLiteDatabase.setTransactionSuccessful();
            return l;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Video getVideo(VideoSeriesId object, VideoId videoId, JSONAPIResultParser<Video> jSONAPIResultParser) {
        SQLiteDatabase sQLiteDatabase;
        block6 : {
            block5 : {
                sQLiteDatabase = this.getReadableDatabase();
                try {
                    sQLiteDatabase.beginTransaction();
                    object = LocalVideoStorageDbHelper.getVideoWhereClause((VideoSeriesId)object, videoId);
                    videoId = sQLiteDatabase.query("video_data", new String[]{"video_json"}, (String)object, null, null, null, null);
                    if (!videoId.moveToFirst() || (object = videoId.getString(videoId.getColumnIndex("video_json"))) == null) break block5;
                    try {
                        object = jSONAPIResultParser.parseResult(new JSONObject((String)object));
                        break block6;
                    }
                    catch (JSONException jSONException) {
                        Logger.getInstance().debug(LocalVideoStorageDbHelper.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                    }
                    catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                        Logger.getInstance().debug(LocalVideoStorageDbHelper.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
                    }
                }
                catch (Throwable throwable) {
                    sQLiteDatabase.endTransaction();
                    throw throwable;
                }
            }
            object = null;
        }
        videoId.close();
        sQLiteDatabase.setTransactionSuccessful();
        sQLiteDatabase.endTransaction();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public VideoSeries getVideoSeries(VideoSeriesId object, JSONAPIResultParser<VideoSeries> jSONAPIResultParser) {
        StringBuilder stringBuilder;
        SQLiteDatabase sQLiteDatabase;
        block6 : {
            block5 : {
                sQLiteDatabase = this.getReadableDatabase();
                try {
                    sQLiteDatabase.beginTransaction();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("series_id=");
                    stringBuilder.append(object.getUuid());
                    object = stringBuilder.toString();
                    stringBuilder = sQLiteDatabase.query("series_offline_available", new String[]{"series_id", "series_json"}, (String)object, null, null, null, null);
                    if (!stringBuilder.moveToFirst() || (object = stringBuilder.getString(stringBuilder.getColumnIndex("series_json"))) == null) break block5;
                    try {
                        object = jSONAPIResultParser.parseResult(new JSONObject((String)object));
                        break block6;
                    }
                    catch (JSONException jSONException) {
                        Logger.getInstance().debug(LocalVideoStorageDbHelper.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                    }
                    catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                        Logger.getInstance().debug(LocalVideoStorageDbHelper.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
                    }
                }
                catch (Throwable throwable) {
                    sQLiteDatabase.endTransaction();
                    throw throwable;
                }
            }
            object = null;
        }
        stringBuilder.close();
        sQLiteDatabase.setTransactionSuccessful();
        sQLiteDatabase.endTransaction();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isVideoJSONNotNull(VideoSeriesId object, VideoId object2) {
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            boolean bl = false;
            object = LocalVideoStorageDbHelper.getVideoWhereClause((VideoSeriesId)object, (VideoId)object2);
            object = sQLiteDatabase.query("video_data", new String[]{"video_json"}, (String)object, null, null, null, null);
            boolean bl2 = bl;
            if (object.moveToFirst()) {
                object2 = object.getString(object.getColumnIndex("video_json"));
                bl2 = bl;
                if (object2 != null) {
                    bl2 = bl;
                    if (!object2.isEmpty()) {
                        bl2 = true;
                    }
                }
            }
            object.close();
            sQLiteDatabase.setTransactionSuccessful();
            return bl2;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isVideoSeriesJSONNotNull(VideoSeriesId object) {
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            boolean bl = false;
            CharSequence charSequence = new StringBuilder();
            charSequence.append("series_id=");
            charSequence.append(object.getUuid());
            object = ((StringBuilder)charSequence).toString();
            object = sQLiteDatabase.query("series_offline_available", new String[]{"series_id", "series_json"}, (String)object, null, null, null, null);
            boolean bl2 = bl;
            if (object.moveToFirst()) {
                charSequence = object.getString(object.getColumnIndex("series_json"));
                bl2 = bl;
                if (charSequence != null) {
                    bl2 = bl;
                    if (!charSequence.isEmpty()) {
                        bl2 = true;
                    }
                }
            }
            object.close();
            sQLiteDatabase.setTransactionSuccessful();
            return bl2;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    @Override
    public boolean isVideoSeriesLocalAvailable(VideoSeriesId object) {
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            boolean bl = false;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("series_id=");
            stringBuilder.append(object.getUuid());
            object = stringBuilder.toString();
            object = sQLiteDatabase.query("series_offline_available", new String[]{"series_id", "offline_available"}, (String)object, null, null, null, null);
            boolean bl2 = bl;
            if (object.moveToFirst()) {
                bl2 = bl;
                if (object.getInt(object.getColumnIndex("offline_available")) > 0) {
                    bl2 = true;
                }
            }
            object.close();
            sQLiteDatabase.setTransactionSuccessful();
            return bl2;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.beginTransaction();
        try {
            sQLiteDatabase.execSQL(SQL_CREATE_TABLE_VIDEO_SERIES_AVAILABLE);
            sQLiteDatabase.execSQL(SQL_CREATE_TABLE_VIDEO_DATA);
            sQLiteDatabase.execSQL(SQL_CREATE_TRIGGER_EDIT_DATE);
            sQLiteDatabase.execSQL(SQL_CREATE_TRIGGER_EDIT_DATE_SERIES);
            sQLiteDatabase.setTransactionSuccessful();
            return;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }

    @Override
    public void putVideoDownloadIdWithFilesize(VideoSeriesId videoSeriesId, VideoId videoId, long l, long l2) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            String string = LocalVideoStorageDbHelper.getVideoWhereClause(videoSeriesId, videoId);
            Cursor cursor = sQLiteDatabase.query("video_data", new String[]{"series_id", "video_id"}, string, null, null, null, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("video_download_request_id", Long.valueOf(l));
            contentValues.put("video_file_size", Long.valueOf(l2));
            if (cursor.moveToFirst()) {
                sQLiteDatabase.update("video_data", contentValues, string, null);
            } else {
                contentValues.put("series_id", videoSeriesId.getUuid());
                contentValues.put("video_id", videoId.getUuid());
                sQLiteDatabase.insert("video_data", null, contentValues);
            }
            cursor.close();
            sQLiteDatabase.setTransactionSuccessful();
            return;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean putVideoJSON(VideoSeriesId object, VideoId videoId, JSONObject jSONObject) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            boolean bl = false;
            String string = LocalVideoStorageDbHelper.getVideoWhereClause((VideoSeriesId)object, videoId);
            Cursor cursor = sQLiteDatabase.query("video_data", new String[]{"video_json"}, string, null, null, null, null);
            if (cursor.moveToFirst()) {
                videoId = new ContentValues();
                object = jSONObject != null ? jSONObject.toString() : null;
                videoId.put("video_json", (String)object);
                if (sQLiteDatabase.update("video_data", (ContentValues)videoId, string, null) > 0) {
                    bl = true;
                }
            } else {
                string = new ContentValues();
                string.put("series_id", object.getUuid());
                string.put("video_id", videoId.getUuid());
                object = jSONObject != null ? jSONObject.toString() : null;
                string.put("video_json", (String)object);
                sQLiteDatabase.insert("video_data", null, (ContentValues)string);
            }
            cursor.close();
            sQLiteDatabase.setTransactionSuccessful();
            return bl;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean putVideoSeriesJSON(VideoSeriesId videoSeriesId, JSONObject object) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            object = object != null ? object.toString() : null;
            contentValues.put("series_json", (String)object);
            object = new StringBuilder();
            object.append("series_id=");
            object.append(videoSeriesId.getUuid());
            boolean bl = sQLiteDatabase.update("series_offline_available", contentValues, object.toString(), null) > 0;
            sQLiteDatabase.setTransactionSuccessful();
            return bl;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean removeAllDataForVideo(VideoSeriesId videoSeriesId, VideoId videoId) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        try {
            sQLiteDatabase.beginTransaction();
            boolean bl = sQLiteDatabase.delete("video_data", LocalVideoStorageDbHelper.getVideoWhereClause(videoSeriesId, videoId), null) > 0;
            sQLiteDatabase.setTransactionSuccessful();
            return bl;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean removeAllDataForVideoSeries(VideoSeriesId videoSeriesId) {
        boolean bl;
        SQLiteDatabase sQLiteDatabase;
        block2 : {
            sQLiteDatabase = this.getWritableDatabase();
            try {
                sQLiteDatabase.beginTransaction();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("series_id=");
                stringBuilder.append(videoSeriesId.getUuid());
                int n = sQLiteDatabase.delete("series_offline_available", stringBuilder.toString(), null);
                bl = false;
                n = n > 0 ? 1 : 0;
                stringBuilder = new StringBuilder();
                stringBuilder.append("series_id=");
                stringBuilder.append(videoSeriesId.getUuid());
                int n2 = sQLiteDatabase.delete("video_data", stringBuilder.toString(), null);
                if (n == 0 && n2 <= 0) break block2;
            }
            catch (Throwable throwable) {
                sQLiteDatabase.endTransaction();
                throw throwable;
            }
            bl = true;
        }
        sQLiteDatabase.setTransactionSuccessful();
        sQLiteDatabase.endTransaction();
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean setVideoSeriesAsLocal(VideoSeriesId videoSeriesId, boolean bl) {
        SQLiteDatabase sQLiteDatabase;
        boolean bl2;
        Cursor cursor;
        block3 : {
            block4 : {
                sQLiteDatabase = this.getWritableDatabase();
                try {
                    sQLiteDatabase.beginTransaction();
                    boolean bl3 = false;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("series_id=");
                    stringBuilder.append(videoSeriesId.getUuid());
                    String string = stringBuilder.toString();
                    cursor = sQLiteDatabase.query("series_offline_available", new String[]{"series_id", "offline_available"}, string, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        boolean bl4 = cursor.getInt(cursor.getColumnIndex("offline_available")) > 0;
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("offline_available", Boolean.valueOf(bl));
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("series_id=");
                        stringBuilder2.append(videoSeriesId.getUuid());
                        bl2 = bl3;
                        if (sQLiteDatabase.update("series_offline_available", contentValues, stringBuilder2.toString(), null) <= 0) break block3;
                        bl2 = bl3;
                        if (bl4 == bl) break block3;
                        break block4;
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("series_id", videoSeriesId.getUuid());
                    contentValues.put("offline_available", Boolean.valueOf(bl));
                    bl2 = bl3;
                    if (sQLiteDatabase.insert("series_offline_available", null, contentValues) < 0L) break block3;
                }
                catch (Throwable throwable) {
                    sQLiteDatabase.endTransaction();
                    throw throwable;
                }
            }
            bl2 = true;
        }
        cursor.close();
        sQLiteDatabase.setTransactionSuccessful();
        sQLiteDatabase.endTransaction();
        return bl2;
    }
}
