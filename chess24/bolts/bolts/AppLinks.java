/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 */
package bolts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import bolts.MeasurementEvent;

public final class AppLinks {
    static final String KEY_NAME_APPLINK_DATA = "al_applink_data";
    static final String KEY_NAME_EXTRAS = "extras";
    static final String KEY_NAME_TARGET = "target_url";

    public static Bundle getAppLinkData(Intent intent) {
        return intent.getBundleExtra(KEY_NAME_APPLINK_DATA);
    }

    public static Bundle getAppLinkExtras(Intent intent) {
        if ((intent = AppLinks.getAppLinkData(intent)) == null) {
            return null;
        }
        return intent.getBundle(KEY_NAME_EXTRAS);
    }

    public static Uri getTargetUrl(Intent intent) {
        Object object = AppLinks.getAppLinkData(intent);
        if (object != null && (object = object.getString(KEY_NAME_TARGET)) != null) {
            return Uri.parse((String)object);
        }
        return intent.getData();
    }

    public static Uri getTargetUrlFromInboundIntent(Context context, Intent intent) {
        Object object = AppLinks.getAppLinkData(intent);
        if (object != null && (object = object.getString(KEY_NAME_TARGET)) != null) {
            MeasurementEvent.sendBroadcastEvent(context, "al_nav_in", intent, null);
            return Uri.parse((String)object);
        }
        return null;
    }
}
