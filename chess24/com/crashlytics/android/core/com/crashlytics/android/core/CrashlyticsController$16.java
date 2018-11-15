/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.core;

import android.content.Context;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsNdkData;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

class CrashlyticsController
implements Callable<Boolean> {
    final /* synthetic */ CrashlyticsNdkData val$ndkData;

    CrashlyticsController(CrashlyticsNdkData crashlyticsNdkData) {
        this.val$ndkData = crashlyticsNdkData;
    }

    @Override
    public Boolean call() throws Exception {
        File file;
        TreeSet<File> treeSet = this.val$ndkData.timestampedDirectories;
        String string = CrashlyticsController.this.getPreviousSessionId();
        if (string != null && !treeSet.isEmpty() && (file = treeSet.first()) != null) {
            CrashlyticsController.this.finalizeMostRecentNativeCrash(CrashlyticsController.this.crashlyticsCore.getContext(), file, string);
        }
        CrashlyticsController.this.recursiveDelete(treeSet);
        return Boolean.TRUE;
    }
}
