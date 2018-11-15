/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

private static class DaoManager.TableConfigConnectionSource {
    ConnectionSource connectionSource;
    DatabaseTableConfig<?> tableConfig;

    public DaoManager.TableConfigConnectionSource(ConnectionSource connectionSource, DatabaseTableConfig<?> databaseTableConfig) {
        this.connectionSource = connectionSource;
        this.tableConfig = databaseTableConfig;
    }

    public boolean equals(Object object) {
        if (object != null) {
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (DaoManager.TableConfigConnectionSource)object;
            if (!this.tableConfig.equals(object.tableConfig)) {
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
        return 31 * (this.tableConfig.hashCode() + 31) + this.connectionSource.hashCode();
    }
}
