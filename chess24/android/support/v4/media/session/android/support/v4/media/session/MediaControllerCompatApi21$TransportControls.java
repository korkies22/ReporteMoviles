/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.Rating
 *  android.media.session.MediaController
 *  android.media.session.MediaController$TransportControls
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.media.Rating;
import android.media.session.MediaController;
import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompatApi21;

public static class MediaControllerCompatApi21.TransportControls {
    public static void fastForward(Object object) {
        ((MediaController.TransportControls)object).fastForward();
    }

    public static void pause(Object object) {
        ((MediaController.TransportControls)object).pause();
    }

    public static void play(Object object) {
        ((MediaController.TransportControls)object).play();
    }

    public static void playFromMediaId(Object object, String string, Bundle bundle) {
        ((MediaController.TransportControls)object).playFromMediaId(string, bundle);
    }

    public static void playFromSearch(Object object, String string, Bundle bundle) {
        ((MediaController.TransportControls)object).playFromSearch(string, bundle);
    }

    public static void rewind(Object object) {
        ((MediaController.TransportControls)object).rewind();
    }

    public static void seekTo(Object object, long l) {
        ((MediaController.TransportControls)object).seekTo(l);
    }

    public static void sendCustomAction(Object object, String string, Bundle bundle) {
        ((MediaController.TransportControls)object).sendCustomAction(string, bundle);
    }

    public static void setRating(Object object, Object object2) {
        ((MediaController.TransportControls)object).setRating((Rating)object2);
    }

    public static void skipToNext(Object object) {
        ((MediaController.TransportControls)object).skipToNext();
    }

    public static void skipToPrevious(Object object) {
        ((MediaController.TransportControls)object).skipToPrevious();
    }

    public static void skipToQueueItem(Object object, long l) {
        ((MediaController.TransportControls)object).skipToQueueItem(l);
    }

    public static void stop(Object object) {
        ((MediaController.TransportControls)object).stop();
    }
}
