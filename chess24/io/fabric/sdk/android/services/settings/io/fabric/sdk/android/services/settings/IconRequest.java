/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 */
package io.fabric.sdk.android.services.settings;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;

public class IconRequest {
    public final String hash;
    public final int height;
    public final int iconResourceId;
    public final int width;

    public IconRequest(String string, int n, int n2, int n3) {
        this.hash = string;
        this.iconResourceId = n;
        this.width = n2;
        this.height = n3;
    }

    public static IconRequest build(Context object, String string) {
        if (string != null) {
            try {
                int n = CommonUtils.getAppIconResourceId(object);
                Logger logger = Fabric.getLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("App icon resource ID is ");
                stringBuilder.append(n);
                logger.d("Fabric", stringBuilder.toString());
                logger = new BitmapFactory.Options();
                logger.inJustDecodeBounds = true;
                BitmapFactory.decodeResource((Resources)object.getResources(), (int)n, (BitmapFactory.Options)logger);
                object = new IconRequest(string, n, logger.outWidth, logger.outHeight);
                return object;
            }
            catch (Exception exception) {
                Fabric.getLogger().e("Fabric", "Failed to load icon", exception);
            }
        }
        return null;
    }
}
