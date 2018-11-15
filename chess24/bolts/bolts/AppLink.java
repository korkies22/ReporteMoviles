/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package bolts;

import android.net.Uri;
import java.util.Collections;
import java.util.List;

public class AppLink {
    private Uri sourceUrl;
    private List<Target> targets;
    private Uri webUrl;

    public AppLink(Uri object, List<Target> list, Uri uri) {
        this.sourceUrl = object;
        object = list;
        if (list == null) {
            object = Collections.emptyList();
        }
        this.targets = object;
        this.webUrl = uri;
    }

    public Uri getSourceUrl() {
        return this.sourceUrl;
    }

    public List<Target> getTargets() {
        return Collections.unmodifiableList(this.targets);
    }

    public Uri getWebUrl() {
        return this.webUrl;
    }

    public static class Target {
        private final String appName;
        private final String className;
        private final String packageName;
        private final Uri url;

        public Target(String string, String string2, Uri uri, String string3) {
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

}
