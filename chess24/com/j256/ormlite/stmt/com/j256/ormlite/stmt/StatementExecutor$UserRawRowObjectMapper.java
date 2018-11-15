/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementExecutor;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

private static class StatementExecutor.UserRawRowObjectMapper<UO>
implements GenericRowMapper<UO> {
    private String[] columnNames;
    private final DataType[] columnTypes;
    private final RawRowObjectMapper<UO> mapper;

    public StatementExecutor.UserRawRowObjectMapper(RawRowObjectMapper<UO> rawRowObjectMapper, DataType[] arrdataType) {
        this.mapper = rawRowObjectMapper;
        this.columnTypes = arrdataType;
    }

    private String[] getColumnNames(DatabaseResults databaseResults) throws SQLException {
        if (this.columnNames != null) {
            return this.columnNames;
        }
        this.columnNames = databaseResults.getColumnNames();
        return this.columnNames;
    }

    @Override
    public UO mapRow(DatabaseResults databaseResults) throws SQLException {
        int n = databaseResults.getColumnCount();
        Object[] arrobject = new Object[n];
        for (int i = 0; i < n; ++i) {
            arrobject[i] = i >= this.columnTypes.length ? null : this.columnTypes[i].getDataPersister().resultToJava(null, databaseResults, i);
        }
        return this.mapper.mapRow(this.getColumnNames(databaseResults), this.columnTypes, arrobject);
    }
}
