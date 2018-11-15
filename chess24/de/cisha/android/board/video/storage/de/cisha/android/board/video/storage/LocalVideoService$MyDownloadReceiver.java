/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.DownloadManager
 *  android.app.DownloadManager$Query
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.net.Uri
 */
package de.cisha.android.board.video.storage;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoService;
import java.util.Set;

private class LocalVideoService.MyDownloadReceiver
extends BroadcastReceiver {
    private long _id;
    private Uri _remoteUri;
    private LocalVideoInfo _video;

    public LocalVideoService.MyDownloadReceiver(long l, LocalVideoInfo localVideoInfo, Uri uri) {
        this._id = l;
        this._video = localVideoInfo;
        this._remoteUri = uri;
        LocalVideoService.this._currentDownloads.add(this);
    }

    static /* synthetic */ long access$1100(LocalVideoService.MyDownloadReceiver myDownloadReceiver) {
        return myDownloadReceiver._id;
    }

    public void onReceive(Context context, Intent intent) {
        final long l = intent.getLongExtra("extra_download_id", 0L);
        if (this._id == l) {
            context.unregisterReceiver((BroadcastReceiver)this);
            LocalVideoService.this.runOnBackgroundThread(new Runnable(){

                @Override
                public void run() {
                    LocalVideoService.this._currentDownloads.remove((Object)MyDownloadReceiver.this);
                    DownloadManager downloadManager = (DownloadManager)LocalVideoService.this._context.getSystemService("download");
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(new long[]{l});
                    query = downloadManager.query(query);
                    if (query.moveToFirst()) {
                        int n = query.getColumnIndex("status");
                        int n2 = query.getColumnIndex("reason");
                        int n3 = query.getColumnIndex("local_uri");
                        int n4 = query.getColumnIndex("bytes_so_far");
                        n = query.getInt(n);
                        long l2 = LocalVideoService.this._localVideoStorage.getFilesizeForVideodownload(l);
                        if (n == 8) {
                            MyDownloadReceiver.this._video.setVideoFile(Uri.parse((String)query.getString(n3)));
                            MyDownloadReceiver.this._video.setDownloadProgress(query.getLong(n4), l2);
                            MyDownloadReceiver.this._video.setState(LocalVideoInfo.LocalVideoServiceDownloadState.COMPLETED);
                        } else if (n == 16) {
                            if (query.getInt(n2) == 1006) {
                                MyDownloadReceiver.this._video.setState(LocalVideoInfo.LocalVideoServiceDownloadState.WAITING_FOR_DISK_SPACE);
                            }
                            downloadManager.remove(new long[]{l});
                            MyDownloadReceiver.this._video.setDownloadProgress(0L, LocalVideoService.this._localVideoStorage.getFilesizeForVideodownload(l));
                            LocalVideoService.this._localVideoStorage.putVideoDownloadIdWithFilesize(MyDownloadReceiver.this._video.getSeriesId(), MyDownloadReceiver.this._video.getVideoId(), 0L, l2);
                            LocalVideoService.this.loadVideoData(new LocalVideoService.UniqueVideoId(MyDownloadReceiver.this._video.getSeriesId(), MyDownloadReceiver.this._video.getVideoId()), MyDownloadReceiver.this._remoteUri);
                        }
                    }
                    query.close();
                    if (LocalVideoService.this._currentDownloads.isEmpty()) {
                        LocalVideoService.this.scheduleUpdateMissingDataProcess();
                    }
                }
            });
        }
    }

}
