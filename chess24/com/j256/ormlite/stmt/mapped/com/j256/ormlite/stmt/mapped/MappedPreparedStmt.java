/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.mapped.BaseMappedQuery;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class MappedPreparedStmt<T, ID>
extends BaseMappedQuery<T, ID>
implements PreparedQuery<T>,
PreparedDelete<T>,
PreparedUpdate<T> {
    private final ArgumentHolder[] argHolders;
    private final Long limit;
    private final StatementBuilder.StatementType type;

    public MappedPreparedStmt(TableInfo<T, ID> tableInfo, String string, FieldType[] arrfieldType, FieldType[] arrfieldType2, ArgumentHolder[] arrargumentHolder, Long l, StatementBuilder.StatementType statementType) {
        super(tableInfo, string, arrfieldType, arrfieldType2);
        this.argHolders = arrargumentHolder;
        this.limit = l;
        this.type = statementType;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private CompiledStatement assignStatementArguments(CompiledStatement var1_1) throws SQLException {
        block11 : {
            try {
                if (this.limit != null) {
                    var1_1.setMaxRows(this.limit.intValue());
                }
                var3_8 = var4_2 = null;
                if (MappedPreparedStmt.logger.isLevelEnabled(Log.Level.TRACE)) {
                    var3_8 = var4_2;
                    if (this.argHolders.length > 0) {
                        var3_8 = new Object[this.argHolders.length];
                    }
                }
                var2_10 = 0;
lbl10: // 2 sources:
                if (var2_10 < this.argHolders.length) {
                    var5_11 = this.argHolders[var2_10].getSqlArgValue();
                    var4_4 = this.argFieldTypes[var2_10];
                    if (var4_4 == null) {
                        var4_5 = this.argHolders[var2_10].getSqlType();
                    } else {
                        var4_6 = var4_4.getSqlType();
                    }
                    var1_1.setObject(var2_10, var5_11, (SqlType)var4_7);
                    if (var3_8 != null) {
                        var3_8[var2_10] = var5_11;
                    }
                    break block11;
                }
                MappedPreparedStmt.logger.debug("prepared statement '{}' with {} args", (Object)this.statement, (Object)this.argHolders.length);
                if (var3_8 == null) return var1_1;
                MappedPreparedStmt.logger.trace("prepared statement arguments: {}", (Object)var3_8);
                return var1_1;
            }
            catch (Throwable var3_9) {
                var1_1.close();
                throw var3_9;
            }
        }
        ++var2_10;
        ** GOTO lbl10
    }

    @Override
    public CompiledStatement compile(DatabaseConnection databaseConnection, StatementBuilder.StatementType statementType) throws SQLException {
        return this.compile(databaseConnection, statementType, -1);
    }

    @Override
    public CompiledStatement compile(DatabaseConnection object, StatementBuilder.StatementType statementType, int n) throws SQLException {
        if (this.type != statementType) {
            object = new StringBuilder();
            object.append("Could not compile this ");
            object.append((Object)this.type);
            object.append(" statement since the caller is expecting a ");
            object.append((Object)statementType);
            object.append(" statement.  Check your QueryBuilder methods.");
            throw new SQLException(object.toString());
        }
        return this.assignStatementArguments(object.compileStatement(this.statement, statementType, this.argFieldTypes, n));
    }

    @Override
    public String getStatement() {
        return this.statement;
    }

    @Override
    public StatementBuilder.StatementType getType() {
        return this.type;
    }

    @Override
    public void setArgumentHolderValue(int n, Object object) throws SQLException {
        if (n < 0) {
            object = new StringBuilder();
            object.append("argument holder index ");
            object.append(n);
            object.append(" must be >= 0");
            throw new SQLException(object.toString());
        }
        if (this.argHolders.length <= n) {
            object = new StringBuilder();
            object.append("argument holder index ");
            object.append(n);
            object.append(" is not valid, only ");
            object.append(this.argHolders.length);
            object.append(" in statement (index starts at 0)");
            throw new SQLException(object.toString());
        }
        this.argHolders[n].setValue(object);
    }
}
