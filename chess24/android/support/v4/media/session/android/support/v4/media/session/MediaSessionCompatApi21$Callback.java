/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.ResultReceiver
 */
package android.support.v4.media.session;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.media.session.MediaSessionCompatApi21;

static interface MediaSessionCompatApi21.Callback {
    public void onCommand(String var1, Bundle var2, ResultReceiver var3);

    public void onCustomAction(String var1, Bundle var2);

    public void onFastForward();

    public boolean onMediaButtonEvent(Intent var1);

    public void onPause();

    public void onPlay();

    public void onPlayFromMediaId(String var1, Bundle var2);

    public void onPlayFromSearch(String var1, Bundle var2);

    public void onRewind();

    public void onSeekTo(long var1);

    public void onSetRating(Object var1);

    public void onSetRating(Object var1, Bundle var2);

    public void onSkipToNext();

    public void onSkipToPrevious();

    public void onSkipToQueueItem(long var1);

    public void onStop();
}
