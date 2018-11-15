/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package de.cisha.android.board.video.view;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.view.CouchImageView;

class SingleVideoListItem
implements Runnable {
    final /* synthetic */ float val$downloadProgess;
    final /* synthetic */ LocalVideoInfo.LocalVideoServiceDownloadState val$state;

    SingleVideoListItem(LocalVideoInfo.LocalVideoServiceDownloadState localVideoServiceDownloadState, float f) {
        this.val$state = localVideoServiceDownloadState;
        this.val$downloadProgess = f;
    }

    @Override
    public void run() {
        if (this.val$state == LocalVideoInfo.LocalVideoServiceDownloadState.DOWNLOADING) {
            SingleVideoListItem.this._downloadProgress.setVisibility(0);
            SingleVideoListItem.this._downloadProgress.setTextColor(SingleVideoListItem.this.getResources().getColor(2131099702));
            SingleVideoListItem.this._downloadImage.setVisibility(0);
            SingleVideoListItem.this._playImage.setAlpha(100);
            SingleVideoListItem.this._videoImage.setAlpha(100);
            SingleVideoListItem.this.setDownloadProgress(this.val$downloadProgess);
            return;
        }
        if (this.val$state == LocalVideoInfo.LocalVideoServiceDownloadState.WAITING_FOR_DISK_SPACE) {
            SingleVideoListItem.this._downloadProgress.setText(2131690395);
            SingleVideoListItem.this._downloadProgress.setTextColor(-65536);
            return;
        }
        if (this.val$state == LocalVideoInfo.LocalVideoServiceDownloadState.COMPLETED) {
            SingleVideoListItem.this._downloadProgress.setText((CharSequence)"");
            SingleVideoListItem.this._playImage.setAlpha(255);
            SingleVideoListItem.this._videoImage.setAlpha(255);
            return;
        }
        LocalVideoInfo.LocalVideoServiceDownloadState localVideoServiceDownloadState = this.val$state;
        localVideoServiceDownloadState = LocalVideoInfo.LocalVideoServiceDownloadState.WAITING_FOR_WIRELESS_LAN;
    }
}
