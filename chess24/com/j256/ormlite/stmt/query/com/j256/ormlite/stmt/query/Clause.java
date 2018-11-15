/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.sql.SQLException;
import java.util.List;

public interface Clause {
    public void appendSql(DatabaseType var1, String var2, StringBuilder var3, List<ArgumentHolder> var4) throws SQLException;
}
