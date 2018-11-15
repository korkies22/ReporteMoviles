/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.Dao;

public static class Dao.CreateOrUpdateStatus {
    private boolean created;
    private int numLinesChanged;
    private boolean updated;

    public Dao.CreateOrUpdateStatus(boolean bl, boolean bl2, int n) {
        this.created = bl;
        this.updated = bl2;
        this.numLinesChanged = n;
    }

    public int getNumLinesChanged() {
        return this.numLinesChanged;
    }

    public boolean isCreated() {
        return this.created;
    }

    public boolean isUpdated() {
        return this.updated;
    }
}
