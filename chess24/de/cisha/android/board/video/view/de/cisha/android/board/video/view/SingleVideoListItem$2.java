/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package de.cisha.android.board.video.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.view.CouchImageView;

class SingleVideoListItem
implements Runnable {
    SingleVideoListItem() {
    }

    @Override
    public void run() {
        SingleVideoListItem.this.setBackgroundResource(0);
        SingleVideoListItem.this.setDownloadProgress(0.0f);
        SingleVideoListItem.this._downloadProgress.setVisibility(8);
        SingleVideoListItem.this._downloadImage.setVisibility(8);
        SingleVideoListItem.this._playImage.setAlpha(255);
        SingleVideoListItem.this._videoImage.setAlpha(255);
    }
}
