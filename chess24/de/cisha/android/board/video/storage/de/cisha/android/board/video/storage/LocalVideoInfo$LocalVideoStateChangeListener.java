/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package de.cisha.android.board.video.storage;

import android.net.Uri;
import de.cisha.android.board.video.storage.LocalVideoInfo;

public static interface LocalVideoInfo.LocalVideoStateChangeListener {
    public void onLocalVideoStateChanged(LocalVideoInfo var1, boolean var2, float var3, Uri var4, LocalVideoInfo.LocalVideoServiceDownloadState var5);
}
