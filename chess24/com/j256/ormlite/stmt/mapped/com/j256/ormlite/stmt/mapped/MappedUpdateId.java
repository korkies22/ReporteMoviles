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

public class MappedUpdateId<T, ID>
extends BaseMappedStatement<T, ID> {
    private MappedUpdateId(TableInfo<T, ID> tableInfo, String string, FieldType[] arrfieldType) {
        super(tableInfo, string, arrfieldType);
    }

    public static <T, ID> MappedUpdateId<T, ID> build(DatabaseType object, TableInfo<T, ID> tableInfo) throws SQLException {
        FieldType fieldType = tableInfo.getIdField();
        if (fieldType == null) {
            object = new StringBuilder();
            object.append("Cannot update-id in ");
            object.append(tableInfo.getDataClass());
            object.append(" because it doesn't have an id field");
            throw new SQLException(object.toString());
        }
        StringBuilder stringBuilder = new StringBuilder(64);
        MappedUpdateId.appendTableName((DatabaseType)object, stringBuilder, "UPDATE ", tableInfo.getTableName());
        stringBuilder.append("SET ");
        MappedUpdateId.appendFieldColumnName((DatabaseType)object, stringBuilder, fieldType, null);
        stringBuilder.append("= ? ");
        MappedUpdateId.appendWhereFieldEq((DatabaseType)object, fieldType, stringBuilder, null);
        return new MappedUpdateId<T, ID>(tableInfo, stringBuilder.toString(), new FieldType[]{fieldType, fieldType});
    }

    private Object extractIdToFieldObject(T t) throws SQLException {
        return this.idField.extractJavaFieldToSqlArgValue(t);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int execute(DatabaseConnection object, T t, ID object2, ObjectCache objectCache) throws SQLException {
        try {
            Object[] arrobject = new Object[]{this.convertIdToFieldObject(object2), this.extractIdToFieldObject(t)};
            int n = object.update(this.statement, arrobject, this.argFieldTypes);
            if (n > 0) {
                if (objectCache != null) {
                    object = this.idField.extractJavaFieldValue(t);
                    if ((object = objectCache.updateId(this.clazz, object, object2)) != null && object != t) {
                        this.idField.assignField(object, object2, false, objectCache);
                    }
                }
                this.idField.assignField(t, object2, false, objectCache);
            }
            logger.debug("updating-id with statement '{}' and {} args, changed {} rows", (Object)this.statement, (Object)arrobject.length, (Object)n);
            if (arrobject.length > 0) {
                logger.trace("updating-id arguments: {}", (Object)arrobject);
            }
            return n;
        }
        catch (SQLException sQLException) {
            object2 = new StringBuilder();
            object2.append("Unable to run update-id stmt on object ");
            object2.append(t);
            object2.append(": ");
            object2.append(this.statement);
            throw SqlExceptionUtil.create(object2.toString(), sQLException);
        }
    }
}
