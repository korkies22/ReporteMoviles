/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt;

import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseConnection;
import java.sql.SQLException;

public interface PreparedStmt<T>
extends GenericRowMapper<T> {
    public CompiledStatement compile(DatabaseConnection var1, StatementBuilder.StatementType var2) throws SQLException;

    public CompiledStatement compile(DatabaseConnection var1, StatementBuilder.StatementType var2, int var3) throws SQLException;

    public String getStatement() throws SQLException;

    public StatementBuilder.StatementType getType();

    public void setArgumentHolderValue(int var1, Object var2) throws SQLException;
}
