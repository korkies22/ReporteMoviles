/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 */
package de.cisha.android.board.broadcast.video;

import android.os.Binder;
import de.cisha.android.board.broadcast.video.VideoStreamService;

public class VideoStreamService.LocalBinder
extends Binder {
    VideoStreamService getService() {
        return VideoStreamService.this;
    }
}
