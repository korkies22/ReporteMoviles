// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.storage;

import android.content.ContentValues;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.LinkedList;
import java.util.List;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

class LocalVideoStorageDbHelper extends SQLiteOpenHelper implements ILocalVideoStorage
{
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_NAME = "LocalVideoDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String NUM_TYPE = " INTEGER";
    private static final String SQL_CREATE_TABLE_VIDEO_DATA = "CREATE TABLE video_data(series_id INTEGER,video_id INTEGER,video_json TEXT,video_download_request_id INTEGER,video_file_size INTEGER,creation_date INTEGER DEFAULT (CURRENT_TIMESTAMP),edit_date INTEGER DEFAULT (CURRENT_TIMESTAMP),PRIMARY KEY(series_id,video_id))";
    private static final String SQL_CREATE_TABLE_VIDEO_SERIES_AVAILABLE = "CREATE TABLE series_offline_available(series_id INTEGER PRIMARY KEY,offline_available INTEGER,series_json TEXT,creation_date INTEGER DEFAULT (CURRENT_TIMESTAMP),edit_date INTEGER DEFAULT (CURRENT_TIMESTAMP))";
    private static final String SQL_CREATE_TRIGGER_EDIT_DATE = "CREATE TRIGGER IF NOT EXISTS update_edit_date_video_data AFTER UPDATE  ON video_data BEGIN  \tUPDATE video_data \tSET edit_date= (CURRENT_TIMESTAMP) \t\tWHERE series_id= old.series_id            AND video_id= old.video_id; END";
    private static final String SQL_CREATE_TRIGGER_EDIT_DATE_SERIES = "CREATE TRIGGER IF NOT EXISTS update_edit_date_video_series AFTER UPDATE  ON series_offline_available BEGIN  \tUPDATE series_offline_available \tSET edit_date= (CURRENT_TIMESTAMP) \t\tWHERE series_id= old.series_id; END";
    private static final String TEXT_TYPE = " TEXT";
    
    public LocalVideoStorageDbHelper(final Context context, final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append("LocalVideoDB.db");
        sb.append(s);
        super(context, sb.toString(), (SQLiteDatabase.CursorFactory)null, 1);
    }
    
    private String getDropTableStatement(final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(s);
        return sb.toString();
    }
    
    private static String getVideoWhereClause(final VideoSeriesId videoSeriesId, final VideoId videoId) {
        final StringBuilder sb = new StringBuilder();
        sb.append("series_id=");
        sb.append(videoSeriesId.getUuid());
        sb.append(" AND ");
        sb.append("video_id");
        sb.append("=");
        sb.append(videoId.getUuid());
        return sb.toString();
    }
    
    public List<VideoSeriesId> getAllLocalVideoSeries() {
        while (true) {
            final LinkedList<VideoSeriesId> list = new LinkedList<VideoSeriesId>();
            final SQLiteDatabase readableDatabase = this.getReadableDatabase();
            while (true) {
                Label_0176: {
                    try {
                        readableDatabase.beginTransaction();
                        final Cursor query = readableDatabase.query("series_offline_available", new String[] { "series_id", "offline_available" }, (String)null, (String[])null, (String)null, (String)null, (String)null);
                        Label_0152: {
                            if (query.moveToFirst()) {
                                while (query.getInt(query.getColumnIndex("offline_available")) > 0) {
                                    final int n = 1;
                                    if (n != 0) {
                                        final int int1 = query.getInt(query.getColumnIndex("series_id"));
                                        final StringBuilder sb = new StringBuilder();
                                        sb.append(int1);
                                        sb.append("");
                                        list.add(new VideoSeriesId(sb.toString()));
                                    }
                                    if (!query.moveToNext()) {
                                        break Label_0152;
                                    }
                                }
                                break Label_0176;
                            }
                        }
                        query.close();
                        readableDatabase.setTransactionSuccessful();
                        return list;
                    }
                    finally {
                        readableDatabase.endTransaction();
                    }
                }
                final int n = 0;
                continue;
            }
        }
    }
    
    public List<VideoId> getAllLocalVideosForSeries(final VideoSeriesId videoSeriesId) {
        final LinkedList<VideoId> list = new LinkedList<VideoId>();
        final SQLiteDatabase readableDatabase = this.getReadableDatabase();
        try {
            readableDatabase.beginTransaction();
            final StringBuilder sb = new StringBuilder();
            sb.append("series_id=");
            sb.append(videoSeriesId.getUuid());
            final Cursor query = readableDatabase.query("video_data", new String[] { "video_id" }, sb.toString(), (String[])null, (String)null, (String)null, (String)null);
            if (query.moveToFirst()) {
                do {
                    list.add(new VideoId(query.getString(query.getColumnIndex("video_id"))));
                } while (query.moveToNext());
            }
            query.close();
            readableDatabase.setTransactionSuccessful();
            return list;
        }
        finally {
            readableDatabase.endTransaction();
        }
    }
    
    public List<VideoDownloadRequestTupel> getAllVideoDownloadRequestIds() {
        final LinkedList<VideoDownloadRequestTupel> list = new LinkedList<VideoDownloadRequestTupel>();
        final SQLiteDatabase readableDatabase = this.getReadableDatabase();
        try {
            readableDatabase.beginTransaction();
            final Cursor query = readableDatabase.query("video_data", new String[] { "series_id", "video_id", "video_download_request_id" }, (String)null, (String[])null, (String)null, (String)null, (String)null);
            if (query.moveToFirst()) {
                do {
                    list.add(new VideoDownloadRequestTupel(new VideoSeriesId(query.getString(query.getColumnIndex("series_id"))), new VideoId(query.getString(query.getColumnIndex("video_id"))), query.getLong(query.getColumnIndex("video_download_request_id"))));
                } while (query.moveToNext());
            }
            query.close();
            readableDatabase.setTransactionSuccessful();
            return list;
        }
        finally {
            readableDatabase.endTransaction();
        }
    }
    
    public long getDownloadIdForVideo(final VideoSeriesId videoSeriesId, final VideoId videoId) {
        while (true) {
            final SQLiteDatabase readableDatabase = this.getReadableDatabase();
            while (true) {
                try {
                    readableDatabase.beginTransaction();
                    final Cursor query = readableDatabase.query("video_data", new String[] { "video_download_request_id" }, getVideoWhereClause(videoSeriesId, videoId), (String[])null, (String)null, (String)null, (String)null);
                    if (query.moveToFirst()) {
                        final long long1 = query.getLong(query.getColumnIndex("video_download_request_id"));
                        query.close();
                        readableDatabase.setTransactionSuccessful();
                        return long1;
                    }
                }
                finally {
                    readableDatabase.endTransaction();
                }
                final long long1 = 0L;
                continue;
            }
        }
    }
    
    public long getFilesizeForVideodownload(long long1) {
        while (true) {
            final SQLiteDatabase readableDatabase = this.getReadableDatabase();
            while (true) {
                try {
                    readableDatabase.beginTransaction();
                    final StringBuilder sb = new StringBuilder();
                    sb.append("video_download_request_id=");
                    sb.append(long1);
                    final Cursor query = readableDatabase.query("video_data", new String[] { "video_file_size" }, sb.toString(), (String[])null, (String)null, (String)null, (String)null);
                    if (query.moveToFirst()) {
                        long1 = query.getLong(query.getColumnIndex("video_file_size"));
                        query.close();
                        readableDatabase.setTransactionSuccessful();
                        return long1;
                    }
                }
                finally {
                    readableDatabase.endTransaction();
                }
                long1 = 0L;
                continue;
            }
        }
    }
    
    public Video getVideo(final VideoSeriesId videoSeriesId, VideoId query, final JSONAPIResultParser<Video> jsonapiResultParser) {
        while (true) {
            final SQLiteDatabase readableDatabase = this.getReadableDatabase();
            while (true) {
                Label_0154: {
                    try {
                        readableDatabase.beginTransaction();
                        query = (VideoId)readableDatabase.query("video_data", new String[] { "video_json" }, getVideoWhereClause(videoSeriesId, query), (String[])null, (String)null, (String)null, (String)null);
                        if (((Cursor)query).moveToFirst()) {
                            final String string = ((Cursor)query).getString(((Cursor)query).getColumnIndex("video_json"));
                            if (string != null) {
                                Video video;
                                try {
                                    video = jsonapiResultParser.parseResult(new JSONObject(string));
                                }
                                catch (JSONException ex) {
                                    Logger.getInstance().debug(LocalVideoStorageDbHelper.class.getName(), JSONException.class.getName(), (Throwable)ex);
                                    break Label_0154;
                                }
                                catch (InvalidJsonForObjectException ex2) {
                                    Logger.getInstance().debug(LocalVideoStorageDbHelper.class.getName(), InvalidJsonForObjectException.class.getName(), ex2);
                                    break Label_0154;
                                }
                                ((Cursor)query).close();
                                readableDatabase.setTransactionSuccessful();
                                return video;
                            }
                        }
                    }
                    finally {
                        readableDatabase.endTransaction();
                    }
                }
                Video video = null;
                continue;
            }
        }
    }
    
    public VideoSeries getVideoSeries(final VideoSeriesId videoSeriesId, final JSONAPIResultParser<VideoSeries> jsonapiResultParser) {
        while (true) {
            final SQLiteDatabase readableDatabase = this.getReadableDatabase();
            while (true) {
                Label_0185: {
                    try {
                        readableDatabase.beginTransaction();
                        Object query = new StringBuilder();
                        ((StringBuilder)query).append("series_id=");
                        ((StringBuilder)query).append(videoSeriesId.getUuid());
                        query = readableDatabase.query("series_offline_available", new String[] { "series_id", "series_json" }, ((StringBuilder)query).toString(), (String[])null, (String)null, (String)null, (String)null);
                        if (((Cursor)query).moveToFirst()) {
                            final String string = ((Cursor)query).getString(((Cursor)query).getColumnIndex("series_json"));
                            if (string != null) {
                                VideoSeries videoSeries;
                                try {
                                    videoSeries = jsonapiResultParser.parseResult(new JSONObject(string));
                                }
                                catch (JSONException ex) {
                                    Logger.getInstance().debug(LocalVideoStorageDbHelper.class.getName(), JSONException.class.getName(), (Throwable)ex);
                                    break Label_0185;
                                }
                                catch (InvalidJsonForObjectException ex2) {
                                    Logger.getInstance().debug(LocalVideoStorageDbHelper.class.getName(), InvalidJsonForObjectException.class.getName(), ex2);
                                    break Label_0185;
                                }
                                ((Cursor)query).close();
                                readableDatabase.setTransactionSuccessful();
                                return videoSeries;
                            }
                        }
                    }
                    finally {
                        readableDatabase.endTransaction();
                    }
                }
                VideoSeries videoSeries = null;
                continue;
            }
        }
    }
    
    public boolean isVideoJSONNotNull(final VideoSeriesId videoSeriesId, final VideoId videoId) {
        final SQLiteDatabase readableDatabase = this.getReadableDatabase();
        try {
            readableDatabase.beginTransaction();
            final boolean b = false;
            final Cursor query = readableDatabase.query("video_data", new String[] { "video_json" }, getVideoWhereClause(videoSeriesId, videoId), (String[])null, (String)null, (String)null, (String)null);
            boolean b2 = b;
            if (query.moveToFirst()) {
                final String string = query.getString(query.getColumnIndex("video_json"));
                b2 = b;
                if (string != null) {
                    b2 = b;
                    if (!string.isEmpty()) {
                        b2 = true;
                    }
                }
            }
            query.close();
            readableDatabase.setTransactionSuccessful();
            return b2;
        }
        finally {
            readableDatabase.endTransaction();
        }
    }
    
    public boolean isVideoSeriesJSONNotNull(final VideoSeriesId videoSeriesId) {
        final SQLiteDatabase readableDatabase = this.getReadableDatabase();
        try {
            readableDatabase.beginTransaction();
            final boolean b = false;
            final StringBuilder sb = new StringBuilder();
            sb.append("series_id=");
            sb.append(videoSeriesId.getUuid());
            final Cursor query = readableDatabase.query("series_offline_available", new String[] { "series_id", "series_json" }, sb.toString(), (String[])null, (String)null, (String)null, (String)null);
            boolean b2 = b;
            if (query.moveToFirst()) {
                final String string = query.getString(query.getColumnIndex("series_json"));
                b2 = b;
                if (string != null) {
                    b2 = b;
                    if (!string.isEmpty()) {
                        b2 = true;
                    }
                }
            }
            query.close();
            readableDatabase.setTransactionSuccessful();
            return b2;
        }
        finally {
            readableDatabase.endTransaction();
        }
    }
    
    public boolean isVideoSeriesLocalAvailable(final VideoSeriesId videoSeriesId) {
        final SQLiteDatabase readableDatabase = this.getReadableDatabase();
        try {
            readableDatabase.beginTransaction();
            final boolean b = false;
            final StringBuilder sb = new StringBuilder();
            sb.append("series_id=");
            sb.append(videoSeriesId.getUuid());
            final Cursor query = readableDatabase.query("series_offline_available", new String[] { "series_id", "offline_available" }, sb.toString(), (String[])null, (String)null, (String)null, (String)null);
            boolean b2 = b;
            if (query.moveToFirst()) {
                b2 = b;
                if (query.getInt(query.getColumnIndex("offline_available")) > 0) {
                    b2 = true;
                }
            }
            query.close();
            readableDatabase.setTransactionSuccessful();
            return b2;
        }
        finally {
            readableDatabase.endTransaction();
        }
    }
    
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL("CREATE TABLE series_offline_available(series_id INTEGER PRIMARY KEY,offline_available INTEGER,series_json TEXT,creation_date INTEGER DEFAULT (CURRENT_TIMESTAMP),edit_date INTEGER DEFAULT (CURRENT_TIMESTAMP))");
            sqLiteDatabase.execSQL("CREATE TABLE video_data(series_id INTEGER,video_id INTEGER,video_json TEXT,video_download_request_id INTEGER,video_file_size INTEGER,creation_date INTEGER DEFAULT (CURRENT_TIMESTAMP),edit_date INTEGER DEFAULT (CURRENT_TIMESTAMP),PRIMARY KEY(series_id,video_id))");
            sqLiteDatabase.execSQL("CREATE TRIGGER IF NOT EXISTS update_edit_date_video_data AFTER UPDATE  ON video_data BEGIN  \tUPDATE video_data \tSET edit_date= (CURRENT_TIMESTAMP) \t\tWHERE series_id= old.series_id            AND video_id= old.video_id; END");
            sqLiteDatabase.execSQL("CREATE TRIGGER IF NOT EXISTS update_edit_date_video_series AFTER UPDATE  ON series_offline_available BEGIN  \tUPDATE series_offline_available \tSET edit_date= (CURRENT_TIMESTAMP) \t\tWHERE series_id= old.series_id; END");
            sqLiteDatabase.setTransactionSuccessful();
        }
        finally {
            sqLiteDatabase.endTransaction();
        }
    }
    
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int n, final int n2) {
    }
    
    public void putVideoDownloadIdWithFilesize(final VideoSeriesId videoSeriesId, final VideoId videoId, final long n, final long n2) {
        final SQLiteDatabase writableDatabase = this.getWritableDatabase();
        try {
            writableDatabase.beginTransaction();
            final String videoWhereClause = getVideoWhereClause(videoSeriesId, videoId);
            final Cursor query = writableDatabase.query("video_data", new String[] { "series_id", "video_id" }, videoWhereClause, (String[])null, (String)null, (String)null, (String)null);
            final ContentValues contentValues = new ContentValues();
            contentValues.put("video_download_request_id", n);
            contentValues.put("video_file_size", n2);
            if (query.moveToFirst()) {
                writableDatabase.update("video_data", contentValues, videoWhereClause, (String[])null);
            }
            else {
                contentValues.put("series_id", videoSeriesId.getUuid());
                contentValues.put("video_id", videoId.getUuid());
                writableDatabase.insert("video_data", (String)null, contentValues);
            }
            query.close();
            writableDatabase.setTransactionSuccessful();
        }
        finally {
            writableDatabase.endTransaction();
        }
    }
    
    public boolean putVideoJSON(final VideoSeriesId videoSeriesId, VideoId videoId, final JSONObject jsonObject) {
    Label_0145_Outer:
        while (true) {
            final SQLiteDatabase writableDatabase = this.getWritableDatabase();
            while (true) {
            Label_0197:
                while (true) {
                    Label_0192: {
                        try {
                            writableDatabase.beginTransaction();
                            boolean b = false;
                            Object videoWhereClause = getVideoWhereClause(videoSeriesId, videoId);
                            final Cursor query = writableDatabase.query("video_data", new String[] { "video_json" }, (String)videoWhereClause, (String[])null, (String)null, (String)null, (String)null);
                            if (query.moveToFirst()) {
                                videoId = (VideoId)new ContentValues();
                                if (jsonObject == null) {
                                    break Label_0192;
                                }
                                final String string = jsonObject.toString();
                                ((ContentValues)videoId).put("video_json", string);
                                if (writableDatabase.update("video_data", (ContentValues)videoId, (String)videoWhereClause, (String[])null) > 0) {
                                    b = true;
                                }
                            }
                            else {
                                videoWhereClause = new ContentValues();
                                ((ContentValues)videoWhereClause).put("series_id", videoSeriesId.getUuid());
                                ((ContentValues)videoWhereClause).put("video_id", videoId.getUuid());
                                if (jsonObject == null) {
                                    break Label_0197;
                                }
                                final String string2 = jsonObject.toString();
                                ((ContentValues)videoWhereClause).put("video_json", string2);
                                writableDatabase.insert("video_data", (String)null, (ContentValues)videoWhereClause);
                            }
                            query.close();
                            writableDatabase.setTransactionSuccessful();
                            return b;
                        }
                        finally {
                            writableDatabase.endTransaction();
                        }
                    }
                    final String string = null;
                    continue Label_0145_Outer;
                }
                final String string2 = null;
                continue;
            }
        }
    }
    
    public boolean putVideoSeriesJSON(final VideoSeriesId videoSeriesId, final JSONObject jsonObject) {
    Label_0086_Outer:
        while (true) {
            final SQLiteDatabase writableDatabase = this.getWritableDatabase();
            while (true) {
            Label_0111:
                while (true) {
                    try {
                        writableDatabase.beginTransaction();
                        final ContentValues contentValues = new ContentValues();
                        if (jsonObject != null) {
                            final String string = jsonObject.toString();
                            contentValues.put("series_json", string);
                            final StringBuilder sb = new StringBuilder();
                            sb.append("series_id=");
                            sb.append(videoSeriesId.getUuid());
                            if (writableDatabase.update("series_offline_available", contentValues, sb.toString(), (String[])null) > 0) {
                                final boolean b = true;
                                writableDatabase.setTransactionSuccessful();
                                return b;
                            }
                            break Label_0111;
                        }
                    }
                    finally {
                        writableDatabase.endTransaction();
                    }
                    final String string = null;
                    continue Label_0086_Outer;
                }
                final boolean b = false;
                continue;
            }
        }
    }
    
    public boolean removeAllDataForVideo(final VideoSeriesId videoSeriesId, final VideoId videoId) {
        while (true) {
            final SQLiteDatabase writableDatabase = this.getWritableDatabase();
            while (true) {
                try {
                    writableDatabase.beginTransaction();
                    if (writableDatabase.delete("video_data", getVideoWhereClause(videoSeriesId, videoId), (String[])null) > 0) {
                        final boolean b = true;
                        writableDatabase.setTransactionSuccessful();
                        return b;
                    }
                }
                finally {
                    writableDatabase.endTransaction();
                }
                final boolean b = false;
                continue;
            }
        }
    }
    
    public boolean removeAllDataForVideoSeries(final VideoSeriesId videoSeriesId) {
    Label_0116_Outer:
        while (true) {
            final SQLiteDatabase writableDatabase = this.getWritableDatabase();
            while (true) {
            Label_0142:
                while (true) {
                    try {
                        writableDatabase.beginTransaction();
                        final StringBuilder sb = new StringBuilder();
                        sb.append("series_id=");
                        sb.append(videoSeriesId.getUuid());
                        final int delete = writableDatabase.delete("series_offline_available", sb.toString(), (String[])null);
                        final boolean b = false;
                        if (delete > 0) {
                            final int n = 1;
                            final StringBuilder sb2 = new StringBuilder();
                            sb2.append("series_id=");
                            sb2.append(videoSeriesId.getUuid());
                            final int delete2 = writableDatabase.delete("video_data", sb2.toString(), (String[])null);
                            if (n == 0 && delete2 <= 0) {
                                writableDatabase.setTransactionSuccessful();
                                return b;
                            }
                            break Label_0142;
                        }
                    }
                    finally {
                        writableDatabase.endTransaction();
                    }
                    final int n = 0;
                    continue Label_0116_Outer;
                }
                final boolean b = true;
                continue;
            }
        }
    }
    
    public boolean setVideoSeriesAsLocal(final VideoSeriesId videoSeriesId, final boolean b) {
    Label_0245_Outer:
        while (true) {
            final SQLiteDatabase writableDatabase = this.getWritableDatabase();
            while (true) {
            Label_0278:
                while (true) {
                    Label_0273: {
                        try {
                            writableDatabase.beginTransaction();
                            final boolean b2 = false;
                            Object o = new StringBuilder();
                            ((StringBuilder)o).append("series_id=");
                            ((StringBuilder)o).append(videoSeriesId.getUuid());
                            o = ((StringBuilder)o).toString();
                            o = writableDatabase.query("series_offline_available", new String[] { "series_id", "offline_available" }, (String)o, (String[])null, (String)null, (String)null, (String)null);
                            boolean b4;
                            if (((Cursor)o).moveToFirst()) {
                                if (((Cursor)o).getInt(((Cursor)o).getColumnIndex("offline_available")) <= 0) {
                                    break Label_0273;
                                }
                                final boolean b3 = true;
                                final ContentValues contentValues = new ContentValues();
                                contentValues.put("offline_available", b);
                                final StringBuilder sb = new StringBuilder();
                                sb.append("series_id=");
                                sb.append(videoSeriesId.getUuid());
                                b4 = b2;
                                if (writableDatabase.update("series_offline_available", contentValues, sb.toString(), (String[])null) > 0) {
                                    b4 = b2;
                                    if (b3 != b) {
                                        break Label_0278;
                                    }
                                }
                            }
                            else {
                                final ContentValues contentValues2 = new ContentValues();
                                contentValues2.put("series_id", videoSeriesId.getUuid());
                                contentValues2.put("offline_available", b);
                                b4 = b2;
                                if (writableDatabase.insert("series_offline_available", (String)null, contentValues2) >= 0L) {
                                    break Label_0278;
                                }
                            }
                            ((Cursor)o).close();
                            writableDatabase.setTransactionSuccessful();
                            return b4;
                        }
                        finally {
                            writableDatabase.endTransaction();
                        }
                    }
                    final boolean b3 = false;
                    continue Label_0245_Outer;
                }
                boolean b4 = true;
                continue;
            }
        }
    }
}
