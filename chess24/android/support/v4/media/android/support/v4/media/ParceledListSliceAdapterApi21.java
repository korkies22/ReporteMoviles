/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 */
package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.support.annotation.RequiresApi;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RequiresApi(value=21)
class ParceledListSliceAdapterApi21 {
    private static Constructor sConstructor;

    static {
        try {
            sConstructor = Class.forName("android.content.pm.ParceledListSlice").getConstructor(List.class);
            return;
        }
        catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
            return;
        }
    }

    ParceledListSliceAdapterApi21() {
    }

    static Object newInstance(List<MediaBrowser.MediaItem> list) {
        try {
            list = sConstructor.newInstance(list);
            return list;
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
            return null;
        }
    }
}
