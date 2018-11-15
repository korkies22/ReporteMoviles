/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.query.Clause;
import java.sql.SQLException;
import java.util.List;

interface Comparison
extends Clause {
    public void appendOperation(StringBuilder var1);

    public void appendValue(DatabaseType var1, StringBuilder var2, List<ArgumentHolder> var3) throws SQLException;

    public String getColumnName();
}
