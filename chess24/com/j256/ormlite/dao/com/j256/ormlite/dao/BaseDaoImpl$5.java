/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.util.Iterator;

static final class BaseDaoImpl
extends com.j256.ormlite.dao.BaseDaoImpl<T, ID> {
    BaseDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig databaseTableConfig) {
        super(connectionSource, databaseTableConfig);
    }
}
