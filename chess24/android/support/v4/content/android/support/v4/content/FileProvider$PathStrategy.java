/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package android.support.v4.content;

import android.net.Uri;
import android.support.v4.content.FileProvider;
import java.io.File;

static interface FileProvider.PathStrategy {
    public File getFileForUri(Uri var1);

    public Uri getUriForFile(File var1);
}
