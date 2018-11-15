/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.stmt.mapped.MappedCreate;
import com.j256.ormlite.support.GeneratedKeyHolder;
import java.sql.SQLException;

private static class MappedCreate.KeyHolder
implements GeneratedKeyHolder {
    Number key;

    private MappedCreate.KeyHolder() {
    }

    @Override
    public void addKey(Number number) throws SQLException {
        if (this.key == null) {
            this.key = number;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("generated key has already been set to ");
        stringBuilder.append(this.key);
        stringBuilder.append(", now set to ");
        stringBuilder.append(number);
        throw new SQLException(stringBuilder.toString());
    }

    public Number getKey() {
        return this.key;
    }
}
