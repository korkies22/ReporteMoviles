/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import java.util.ArrayList;
import java.util.List;

static final class BaseDaoImpl
extends ThreadLocal<List<com.j256.ormlite.dao.BaseDaoImpl<?, ?>>> {
    BaseDaoImpl() {
    }

    @Override
    protected List<com.j256.ormlite.dao.BaseDaoImpl<?, ?>> initialValue() {
        return new ArrayList(10);
    }
}
