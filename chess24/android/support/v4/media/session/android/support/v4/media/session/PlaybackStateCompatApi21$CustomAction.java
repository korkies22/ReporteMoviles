/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.session.PlaybackState
 *  android.media.session.PlaybackState$CustomAction
 *  android.media.session.PlaybackState$CustomAction$Builder
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.media.session.PlaybackState;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompatApi21;

static final class PlaybackStateCompatApi21.CustomAction {
    PlaybackStateCompatApi21.CustomAction() {
    }

    public static String getAction(Object object) {
        return ((PlaybackState.CustomAction)object).getAction();
    }

    public static Bundle getExtras(Object object) {
        return ((PlaybackState.CustomAction)object).getExtras();
    }

    public static int getIcon(Object object) {
        return ((PlaybackState.CustomAction)object).getIcon();
    }

    public static CharSequence getName(Object object) {
        return ((PlaybackState.CustomAction)object).getName();
    }

    public static Object newInstance(String string, CharSequence charSequence, int n, Bundle bundle) {
        string = new PlaybackState.CustomAction.Builder(string, charSequence, n);
        string.setExtras(bundle);
        return string.build();
    }
}
