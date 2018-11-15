/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.persistence;

import java.io.File;

public interface FileStore {
    public File getCacheDir();

    public File getExternalCacheDir();

    public File getExternalFilesDir();

    public File getFilesDir();
}
