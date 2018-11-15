/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.DownloadManager
 *  android.app.DownloadManager$Query
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 */
package de.cisha.android.board.video.storage;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoService;
import java.util.Set;

class LocalVideoService.MyDownloadReceiver
implements Runnable {
    final /* synthetic */ long val$id;

    LocalVideoService.MyDownloadReceiver(long l) {
        this.val$id = l;
    }

    @Override
    public void run() {
        MyDownloadReceiver.this.this$0._currentDownloads.remove((Object)MyDownloadReceiver.this);
        DownloadManager downloadManager = (DownloadManager)MyDownloadReceiver.this.this$0._context.getSystemService("download");
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(new long[]{this.val$id});
        query = downloadManager.query(query);
        if (query.moveToFirst()) {
            int n = query.getColumnIndex("status");
            int n2 = query.getColumnIndex("reason");
            int n3 = query.getColumnIndex("local_uri");
            int n4 = query.getColumnIndex("bytes_so_far");
            n = query.getInt(n);
            long l = MyDownloadReceiver.this.this$0._localVideoStorage.getFilesizeForVideodownload(this.val$id);
            if (n == 8) {
                MyDownloadReceiver.this._video.setVideoFile(Uri.parse((String)query.getString(n3)));
                MyDownloadReceiver.this._video.setDownloadProgress(query.getLong(n4), l);
                MyDownloadReceiver.this._video.setState(LocalVideoInfo.LocalVideoServiceDownloadState.COMPLETED);
            } else if (n == 16) {
                if (query.getInt(n2) == 1006) {
                    MyDownloadReceiver.this._video.setState(LocalVideoInfo.LocalVideoServiceDownloadState.WAITING_FOR_DISK_SPACE);
                }
                downloadManager.remove(new long[]{this.val$id});
                MyDownloadReceiver.this._video.setDownloadProgress(0L, MyDownloadReceiver.this.this$0._localVideoStorage.getFilesizeForVideodownload(this.val$id));
                MyDownloadReceiver.this.this$0._localVideoStorage.putVideoDownloadIdWithFilesize(MyDownloadReceiver.this._video.getSeriesId(), MyDownloadReceiver.this._video.getVideoId(), 0L, l);
                MyDownloadReceiver.this.this$0.loadVideoData(new LocalVideoService.UniqueVideoId(MyDownloadReceiver.this._video.getSeriesId(), MyDownloadReceiver.this._video.getVideoId()), MyDownloadReceiver.this._remoteUri);
            }
        }
        query.close();
        if (MyDownloadReceiver.this.this$0._currentDownloads.isEmpty()) {
            MyDownloadReceiver.this.this$0.scheduleUpdateMissingDataProcess();
        }
    }
}
