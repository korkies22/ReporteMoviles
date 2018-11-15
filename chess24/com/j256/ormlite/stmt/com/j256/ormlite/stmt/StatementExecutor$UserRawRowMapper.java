/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementExecutor;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

private static class StatementExecutor.UserRawRowMapper<UO>
implements GenericRowMapper<UO> {
    private String[] columnNames;
    private final RawRowMapper<UO> mapper;
    private final GenericRowMapper<String[]> stringRowMapper;

    public StatementExecutor.UserRawRowMapper(RawRowMapper<UO> rawRowMapper, GenericRowMapper<String[]> genericRowMapper) {
        this.mapper = rawRowMapper;
        this.stringRowMapper = genericRowMapper;
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
        String[] arrstring = this.stringRowMapper.mapRow(databaseResults);
        return this.mapper.mapRow(this.getColumnNames(databaseResults), arrstring);
    }
}
