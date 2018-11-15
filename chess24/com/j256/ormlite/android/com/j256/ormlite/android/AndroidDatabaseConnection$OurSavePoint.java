/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.android;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import java.sql.Savepoint;

private static class AndroidDatabaseConnection.OurSavePoint
implements Savepoint {
    private String name;

    public AndroidDatabaseConnection.OurSavePoint(String string) {
        this.name = string;
    }

    @Override
    public int getSavepointId() {
        return 0;
    }

    @Override
    public String getSavepointName() {
        return this.name;
    }
}
