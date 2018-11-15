/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompatApi21;
import java.util.List;

public static interface MediaControllerCompatApi21.Callback {
    public void onAudioInfoChanged(int var1, int var2, int var3, int var4, int var5);

    public void onExtrasChanged(Bundle var1);

    public void onMetadataChanged(Object var1);

    public void onPlaybackStateChanged(Object var1);

    public void onQueueChanged(List<?> var1);

    public void onQueueTitleChanged(CharSequence var1);

    public void onSessionDestroyed();

    public void onSessionEvent(String var1, Bundle var2);
}
