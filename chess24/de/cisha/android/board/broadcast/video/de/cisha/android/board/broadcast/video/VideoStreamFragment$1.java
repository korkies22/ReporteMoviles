/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.widget.MediaController
 */
package de.cisha.android.board.broadcast.video;

import android.content.Context;
import android.view.View;
import android.widget.MediaController;

class VideoStreamFragment
extends MediaController {
    VideoStreamFragment(Context context) {
        super(context);
    }

    public void hide() {
        super.hide();
        if (VideoStreamFragment.this._showFullScreenButton) {
            VideoStreamFragment.this._fullsreenButton.setVisibility(4);
        }
    }

    public void show(int n) {
        super.show(n);
        if (VideoStreamFragment.this._showFullScreenButton) {
            VideoStreamFragment.this._fullsreenButton.setVisibility(0);
        }
    }
}
