/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.DownloadManager
 *  android.app.DownloadManager$Query
 *  android.database.Cursor
 *  android.net.Uri
 */
package de.cisha.android.board.video.storage;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoSeriesInfo;
import de.cisha.android.board.video.storage.LocalVideoService;
import de.cisha.android.board.video.storage.LocalVideoStorageMock;
import de.cisha.chess.util.Logger;
import java.io.File;
import java.util.Iterator;
import java.util.List;

class LocalVideoService
implements Runnable {
    LocalVideoService() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        var5_1 = de.cisha.android.board.video.storage.LocalVideoService.access$000(LocalVideoService.this).getAllLocalVideoSeries().iterator();
        var2_2 = false;
        do {
            if (!var5_1.hasNext()) ** GOTO lbl13
            var6_5 = var5_1.next();
            var7_6 = LocalVideoService.this.getLocalVideoSeries((VideoSeriesId)var6_5);
            if (var7_6.isSeriesObjectAvailable() && var7_6.getVideoList() != null) {
                if ((var7_6 = var7_6.getVideoList()) == null) continue;
            } else {
                de.cisha.android.board.video.storage.LocalVideoService.access$200(LocalVideoService.this, (VideoSeriesId)var6_5);
                var2_2 = true;
                continue;
lbl13: // 1 sources:
                if (var2_2) {
                    de.cisha.android.board.video.storage.LocalVideoService.access$500(LocalVideoService.this);
                }
                var5_1 = Logger.getInstance();
                var6_5 = LocalVideoStorageMock.class.getName();
                var7_6 = new StringBuilder();
                var7_6.append("updated missing data ");
                var7_6.append(var2_2);
                var5_1.debug((String)var6_5, var7_6.toString());
                return;
            }
            var6_5 = (DownloadManager)de.cisha.android.board.video.storage.LocalVideoService.access$300(LocalVideoService.this).getSystemService("download");
            var7_6 = var7_6.iterator();
            var1_3 = var2_2;
            do {
                block10 : {
                    block9 : {
                        var2_2 = var1_3;
                        if (!var7_6.hasNext()) ** break;
                        var8_7 = (LocalVideoInfo)var7_6.next();
                        var9_8 = new LocalVideoService.UniqueVideoId(var8_7.getSeriesId(), var8_7.getVideoId());
                        if (var8_7.isVideoObjectAvailable()) break block9;
                        de.cisha.android.board.video.storage.LocalVideoService.access$400(LocalVideoService.this, var9_8);
                        break block10;
                    }
                    var3_4 = de.cisha.android.board.video.storage.LocalVideoService.access$000(LocalVideoService.this).getDownloadIdForVideo(var8_7.getSeriesId(), var8_7.getVideoId());
                    if (var3_4 <= 0L) ** GOTO lbl52
                    var10_9 = var6_5.query(new DownloadManager.Query().setFilterById(new long[]{var3_4}));
                    if (!var10_9.moveToFirst()) {
                        de.cisha.android.board.video.storage.LocalVideoService.access$400(LocalVideoService.this, var9_8);
                    } else {
                        var2_2 = var1_3;
                        if (var10_9.getInt(var10_9.getColumnIndex("status")) == 8) {
                            var2_2 = var1_3;
                            if (!new File(Uri.parse((String)var10_9.getString(var10_9.getColumnIndex("local_uri"))).getPath()).exists()) {
                                var6_5.remove(new long[]{var3_4});
                                var8_7.setVideoFile(null);
                                de.cisha.android.board.video.storage.LocalVideoService.access$000(LocalVideoService.this).putVideoDownloadIdWithFilesize(var8_7.getSeriesId(), var8_7.getVideoId(), 0L, 0L);
                                de.cisha.android.board.video.storage.LocalVideoService.access$400(LocalVideoService.this, var9_8);
                                var2_2 = true;
                            }
                        }
                        var10_9.close();
                        var1_3 = var2_2;
                        continue;
lbl52: // 1 sources:
                        de.cisha.android.board.video.storage.LocalVideoService.access$400(LocalVideoService.this, var9_8);
                    }
                }
                var1_3 = true;
            } while (true);
            break;
        } while (true);
    }
}
