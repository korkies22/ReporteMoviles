/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

private static class DaoManager.ClassConnectionSource {
    Class<?> clazz;
    ConnectionSource connectionSource;

    public DaoManager.ClassConnectionSource(ConnectionSource connectionSource, Class<?> class_) {
        this.connectionSource = connectionSource;
        this.clazz = class_;
    }

    public boolean equals(Object object) {
        if (object != null) {
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (DaoManager.ClassConnectionSource)object;
            if (!this.clazz.equals(object.clazz)) {
                return false;
            }
            if (!this.connectionSource.equals(object.connectionSource)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 31 * (this.clazz.hashCode() + 31) + this.connectionSource.hashCode();
    }
}
