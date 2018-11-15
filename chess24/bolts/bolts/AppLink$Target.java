/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package bolts;

import android.net.Uri;
import bolts.AppLink;

public static class AppLink.Target {
    private final String appName;
    private final String className;
    private final String packageName;
    private final Uri url;

    public AppLink.Target(String string, String string2, Uri uri, String string3) {
        this.packageName = string;
        this.className = string2;
        this.url = uri;
        this.appName = string3;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getClassName() {
        return this.className;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public Uri getUrl() {
        return this.url;
    }
}
