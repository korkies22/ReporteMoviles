/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementExecutor;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

private static class StatementExecutor.ObjectArrayRowMapper
implements GenericRowMapper<Object[]> {
    private final DataType[] columnTypes;

    public StatementExecutor.ObjectArrayRowMapper(DataType[] arrdataType) {
        this.columnTypes = arrdataType;
    }

    @Override
    public Object[] mapRow(DatabaseResults databaseResults) throws SQLException {
        int n = databaseResults.getColumnCount();
        Object[] arrobject = new Object[n];
        for (int i = 0; i < n; ++i) {
            DataType dataType = i >= this.columnTypes.length ? DataType.STRING : this.columnTypes[i];
            arrobject[i] = dataType.getDataPersister().resultToJava(null, databaseResults, i);
        }
        return arrobject;
    }
}
