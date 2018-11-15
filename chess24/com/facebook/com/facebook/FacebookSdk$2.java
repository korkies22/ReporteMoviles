/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook;

import android.content.Context;
import java.io.File;
import java.util.concurrent.Callable;

static final class FacebookSdk
implements Callable<File> {
    FacebookSdk() {
    }

    @Override
    public File call() throws Exception {
        return applicationContext.getCacheDir();
    }
}
