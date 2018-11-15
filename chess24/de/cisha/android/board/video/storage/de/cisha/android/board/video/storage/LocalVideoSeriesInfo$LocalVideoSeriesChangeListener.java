/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoSeriesInfo;
import java.util.List;

public static interface LocalVideoSeriesInfo.LocalVideoSeriesChangeListener {
    public void onLocalVideoChanged(boolean var1, List<LocalVideoInfo> var2);
}
