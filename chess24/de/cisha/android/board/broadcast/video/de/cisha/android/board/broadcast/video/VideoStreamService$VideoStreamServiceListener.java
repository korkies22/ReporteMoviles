/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.video;

import de.cisha.android.board.broadcast.video.VideoStreamService;

public static interface VideoStreamService.VideoStreamServiceListener {
    public void onMediaPlayerPrepared();

    public void onStartBuffering();

    public void onStartPreparingMediaPlayer();

    public void onStopBuffering();

    public void onVideoBufferingUpdate(int var1);

    public void onVideoSizeChanged(int var1, int var2);

    public void onVideoStreamError();
}
