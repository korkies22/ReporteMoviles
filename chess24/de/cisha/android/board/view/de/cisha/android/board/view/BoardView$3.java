/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnCompletionListener
 */
package de.cisha.android.board.view;

import android.media.MediaPlayer;

class BoardView
implements MediaPlayer.OnCompletionListener {
    BoardView() {
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
    }
}
