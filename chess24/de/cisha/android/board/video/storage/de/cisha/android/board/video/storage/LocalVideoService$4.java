/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.DownloadManager
 *  android.app.DownloadManager$Query
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 */
package de.cisha.android.board.video.storage;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoStorageMock;
import de.cisha.chess.util.Logger;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class LocalVideoService
implements Runnable {
    LocalVideoService() {
    }

    @Override
    public void run() {
        Object object;
        DownloadManager downloadManager = (DownloadManager)LocalVideoService.this._context.getSystemService("download");
        Object object2 = LocalVideoService.this._localVideoStorage.getAllVideoDownloadRequestIds();
        HashMap<Long, ILocalVideoStorage.VideoDownloadRequestTupel> hashMap = new HashMap<Long, ILocalVideoStorage.VideoDownloadRequestTupel>();
        Object object3 = new long[object2.size()];
        object2 = object2.iterator();
        int n = 0;
        while (object2.hasNext()) {
            object = (ILocalVideoStorage.VideoDownloadRequestTupel)object2.next();
            object3[n] = object.getDownloadId();
            hashMap.put(object.getDownloadId(), (ILocalVideoStorage.VideoDownloadRequestTupel)object);
            ++n;
        }
        object2 = new DownloadManager.Query();
        if (((long[])object3).length > 0) {
            object2.setFilterById((long[])object3);
        }
        if ((downloadManager = downloadManager.query((DownloadManager.Query)object2)).moveToFirst()) {
            int n2 = downloadManager.getColumnIndex("_id");
            int n3 = downloadManager.getColumnIndex("status");
            int n4 = downloadManager.getColumnIndex("bytes_so_far");
            int n5 = downloadManager.getColumnIndex("local_uri");
            n = 0;
            do {
                long l = downloadManager.getLong(n2);
                int n6 = downloadManager.getInt(n3);
                long l2 = downloadManager.getLong(n4);
                long l3 = LocalVideoService.this._localVideoStorage.getFilesizeForVideodownload(l);
                object3 = (ILocalVideoStorage.VideoDownloadRequestTupel)hashMap.get(l);
                if (object3 != null) {
                    object3 = LocalVideoService.this.getLocalVideo(object3.getVideoId(), object3.getSeriesId());
                    object3.setDownloadProgress(l2, l3);
                    object2 = Logger.getInstance();
                    object = LocalVideoStorageMock.class.getName();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("download progress ");
                    stringBuilder.append(l2);
                    stringBuilder.append(" / ");
                    stringBuilder.append(l3);
                    object2.debug((String)object, stringBuilder.toString());
                    if (n6 == 8 && !new File(Uri.parse((String)downloadManager.getString(n5)).getPath()).exists()) {
                        object3.setDownloadProgress(0L, 0L);
                        object3.setVideoFile(null);
                        object3.setState(LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING);
                    }
                }
                if (n6 == 8) continue;
                n = 1;
            } while (downloadManager.moveToNext());
            downloadManager.close();
        } else {
            n = 0;
        }
        if (n != 0) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){

                @Override
                public void run() {
                    LocalVideoService.this.startUpdateDownloadStateProcess();
                }
            }, 5000L);
            return;
        }
        LocalVideoService.this._updateStateOn = false;
    }

}
