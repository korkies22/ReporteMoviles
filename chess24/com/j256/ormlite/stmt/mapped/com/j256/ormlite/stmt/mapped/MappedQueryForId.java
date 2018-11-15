/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.mapped.BaseMappedQuery;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class MappedQueryForId<T, ID>
extends BaseMappedQuery<T, ID> {
    private final String label;

    protected MappedQueryForId(TableInfo<T, ID> tableInfo, String string, FieldType[] arrfieldType, FieldType[] arrfieldType2, String string2) {
        super(tableInfo, string, arrfieldType, arrfieldType2);
        this.label = string2;
    }

    public static <T, ID> MappedQueryForId<T, ID> build(DatabaseType object, TableInfo<T, ID> tableInfo, FieldType arrfieldType) throws SQLException {
        FieldType[] arrfieldType2 = arrfieldType;
        if (arrfieldType == null) {
            arrfieldType2 = arrfieldType = tableInfo.getIdField();
            if (arrfieldType == null) {
                object = new StringBuilder();
                object.append("Cannot query-for-id with ");
                object.append(tableInfo.getDataClass());
                object.append(" because it doesn't have an id field");
                throw new SQLException(object.toString());
            }
        }
        object = MappedQueryForId.buildStatement((DatabaseType)object, tableInfo, (FieldType)arrfieldType2);
        arrfieldType = tableInfo.getFieldTypes();
        return new MappedQueryForId<T, ID>(tableInfo, (String)object, new FieldType[]{arrfieldType2}, arrfieldType, "query-for-id");
    }

    protected static <T, ID> String buildStatement(DatabaseType databaseType, TableInfo<T, ID> tableInfo, FieldType fieldType) {
        StringBuilder stringBuilder = new StringBuilder(64);
        MappedQueryForId.appendTableName(databaseType, stringBuilder, "SELECT * FROM ", tableInfo.getTableName());
        MappedQueryForId.appendWhereFieldEq(databaseType, fieldType, stringBuilder, null);
        return stringBuilder.toString();
    }

    private void logArgs(Object[] arrobject) {
        if (arrobject.length > 0) {
            logger.trace("{} arguments: {}", (Object)this.label, (Object)arrobject);
        }
    }

    public T execute(DatabaseConnection object, ID ID, ObjectCache objectCache) throws SQLException {
        Object[] arrobject;
        if (objectCache != null && (arrobject = objectCache.get(this.clazz, ID)) != null) {
            return (T)arrobject;
        }
        arrobject = new Object[]{this.convertIdToFieldObject(ID)};
        if ((object = object.queryForOne(this.statement, arrobject, this.argFieldTypes, this, objectCache)) == null) {
            logger.debug("{} using '{}' and {} args, got no results", (Object)this.label, (Object)this.statement, (Object)arrobject.length);
        } else {
            if (object == DatabaseConnection.MORE_THAN_ONE) {
                logger.error("{} using '{}' and {} args, got >1 results", (Object)this.label, (Object)this.statement, (Object)arrobject.length);
                this.logArgs(arrobject);
                object = new StringBuilder();
                object.append(this.label);
                object.append(" got more than 1 result: ");
                object.append(this.statement);
                throw new SQLException(object.toString());
            }
            logger.debug("{} using '{}' and {} args, got 1 result", (Object)this.label, (Object)this.statement, (Object)arrobject.length);
        }
        this.logArgs(arrobject);
        return (T)object;
    }
}
