/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.mapped.BaseMappedStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class MappedDelete<T, ID>
extends BaseMappedStatement<T, ID> {
    private MappedDelete(TableInfo<T, ID> tableInfo, String string, FieldType[] arrfieldType) {
        super(tableInfo, string, arrfieldType);
    }

    public static <T, ID> MappedDelete<T, ID> build(DatabaseType object, TableInfo<T, ID> tableInfo) throws SQLException {
        FieldType fieldType = tableInfo.getIdField();
        if (fieldType == null) {
            object = new StringBuilder();
            object.append("Cannot delete from ");
            object.append(tableInfo.getDataClass());
            object.append(" because it doesn't have an id field");
            throw new SQLException(object.toString());
        }
        StringBuilder stringBuilder = new StringBuilder(64);
        MappedDelete.appendTableName((DatabaseType)object, stringBuilder, "DELETE FROM ", tableInfo.getTableName());
        MappedDelete.appendWhereFieldEq((DatabaseType)object, fieldType, stringBuilder, null);
        return new MappedDelete<T, ID>(tableInfo, stringBuilder.toString(), new FieldType[]{fieldType});
    }

    public int delete(DatabaseConnection object, T t, ObjectCache object2) throws SQLException {
        int n;
        block4 : {
            try {
                Object[] arrobject = this.getFieldObjects(t);
                n = object.delete(this.statement, arrobject, this.argFieldTypes);
                logger.debug("delete data with statement '{}' and {} args, changed {} rows", (Object)this.statement, (Object)arrobject.length, (Object)n);
                if (arrobject.length > 0) {
                    logger.trace("delete arguments: {}", (Object)arrobject);
                }
                if (n <= 0 || object2 == null) break block4;
            }
            catch (SQLException sQLException) {
                object2 = new StringBuilder();
                object2.append("Unable to run delete stmt on object ");
                object2.append(t);
                object2.append(": ");
                object2.append(this.statement);
                throw SqlExceptionUtil.create(object2.toString(), sQLException);
            }
            object = this.idField.extractJavaFieldToSqlArgValue(t);
            object2.remove(this.clazz, object);
        }
        return n;
    }

    public int deleteById(DatabaseConnection databaseConnection, ID ID, ObjectCache object) throws SQLException {
        int n;
        block4 : {
            try {
                Object[] arrobject = new Object[]{this.convertIdToFieldObject(ID)};
                n = databaseConnection.delete(this.statement, arrobject, this.argFieldTypes);
                logger.debug("delete data with statement '{}' and {} args, changed {} rows", (Object)this.statement, (Object)arrobject.length, (Object)n);
                if (arrobject.length > 0) {
                    logger.trace("delete arguments: {}", (Object)arrobject);
                }
                if (n <= 0 || object == null) break block4;
            }
            catch (SQLException sQLException) {
                object = new StringBuilder();
                object.append("Unable to run deleteById stmt on id ");
                object.append(ID);
                object.append(": ");
                object.append(this.statement);
                throw SqlExceptionUtil.create(object.toString(), sQLException);
            }
            object.remove(this.clazz, ID);
        }
        return n;
    }
}
