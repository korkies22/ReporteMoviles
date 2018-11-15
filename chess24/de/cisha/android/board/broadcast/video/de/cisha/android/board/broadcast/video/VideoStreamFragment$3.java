/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.MediaController
 */
package de.cisha.android.board.broadcast.video;

import android.view.View;
import android.widget.MediaController;

class VideoStreamFragment
implements View.OnClickListener {
    VideoStreamFragment() {
    }

    public void onClick(View view) {
        VideoStreamFragment.this._mediaController.show();
        VideoStreamFragment.this._mediaController.requestFocus();
    }
}
