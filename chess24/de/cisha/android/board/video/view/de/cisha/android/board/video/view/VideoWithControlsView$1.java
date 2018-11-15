/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.MediaController
 */
package de.cisha.android.board.video.view;

import android.view.View;
import android.widget.MediaController;

class VideoWithControlsView
implements View.OnClickListener {
    VideoWithControlsView() {
    }

    public void onClick(View view) {
        VideoWithControlsView.this._mediaControls.show();
    }
}
