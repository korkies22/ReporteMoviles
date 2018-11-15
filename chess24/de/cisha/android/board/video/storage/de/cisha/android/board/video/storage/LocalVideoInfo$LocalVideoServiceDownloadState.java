/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.video.storage.LocalVideoInfo;

public static enum LocalVideoInfo.LocalVideoServiceDownloadState {
    COMPLETED,
    DOWNLOADING,
    WAITING_FOR_DISK_SPACE,
    WAITING_FOR_WIRELESS_LAN;
    

    private LocalVideoInfo.LocalVideoServiceDownloadState() {
    }
}
