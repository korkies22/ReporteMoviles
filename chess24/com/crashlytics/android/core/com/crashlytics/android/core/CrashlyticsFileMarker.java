/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.persistence.FileStore;
import java.io.File;
import java.io.IOException;

class CrashlyticsFileMarker {
    private final FileStore fileStore;
    private final String markerName;

    public CrashlyticsFileMarker(String string, FileStore fileStore) {
        this.markerName = string;
        this.fileStore = fileStore;
    }

    private File getMarkerFile() {
        return new File(this.fileStore.getFilesDir(), this.markerName);
    }

    public boolean create() {
        try {
            boolean bl = this.getMarkerFile().createNewFile();
            return bl;
        }
        catch (IOException iOException) {
            Logger logger = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error creating marker: ");
            stringBuilder.append(this.markerName);
            logger.e("CrashlyticsCore", stringBuilder.toString(), iOException);
            return false;
        }
    }

    public boolean isPresent() {
        return this.getMarkerFile().exists();
    }

    public boolean remove() {
        return this.getMarkerFile().delete();
    }
}
