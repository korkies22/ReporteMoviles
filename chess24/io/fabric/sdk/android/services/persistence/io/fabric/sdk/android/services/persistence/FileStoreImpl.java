/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 */
package io.fabric.sdk.android.services.persistence;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.persistence.FileStore;
import java.io.File;

public class FileStoreImpl
implements FileStore {
    private final String contentPath;
    private final Context context;
    private final String legacySupport;

    public FileStoreImpl(Kit object) {
        if (object.getContext() == null) {
            throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
        }
        this.context = object.getContext();
        this.contentPath = object.getPath();
        object = new StringBuilder();
        object.append("Android/");
        object.append(this.context.getPackageName());
        this.legacySupport = object.toString();
    }

    @Override
    public File getCacheDir() {
        return this.prepare(this.context.getCacheDir());
    }

    @Override
    public File getExternalCacheDir() {
        File file;
        if (this.isExternalStorageAvailable()) {
            if (Build.VERSION.SDK_INT >= 8) {
                file = this.context.getExternalCacheDir();
            } else {
                file = Environment.getExternalStorageDirectory();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.legacySupport);
                stringBuilder.append("/cache/");
                stringBuilder.append(this.contentPath);
                file = new File(file, stringBuilder.toString());
            }
        } else {
            file = null;
        }
        return this.prepare(file);
    }

    @TargetApi(value=8)
    @Override
    public File getExternalFilesDir() {
        boolean bl = this.isExternalStorageAvailable();
        File file = null;
        if (bl) {
            if (Build.VERSION.SDK_INT >= 8) {
                file = this.context.getExternalFilesDir(null);
            } else {
                file = Environment.getExternalStorageDirectory();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.legacySupport);
                stringBuilder.append("/files/");
                stringBuilder.append(this.contentPath);
                file = new File(file, stringBuilder.toString());
            }
        }
        return this.prepare(file);
    }

    @Override
    public File getFilesDir() {
        return this.prepare(this.context.getFilesDir());
    }

    boolean isExternalStorageAvailable() {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            Fabric.getLogger().w("Fabric", "External Storage is not mounted and/or writable\nHave you declared android.permission.WRITE_EXTERNAL_STORAGE in the manifest?");
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    File prepare(File file) {
        if (file != null) {
            if (file.exists()) return file;
            if (file.mkdirs()) {
                return file;
            }
            Fabric.getLogger().w("Fabric", "Couldn't create file");
            return null;
        } else {
            Fabric.getLogger().d("Fabric", "Null File");
        }
        return null;
    }
}
