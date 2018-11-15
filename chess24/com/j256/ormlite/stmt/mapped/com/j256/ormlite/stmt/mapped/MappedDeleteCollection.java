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
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public class MappedDeleteCollection<T, ID>
extends BaseMappedStatement<T, ID> {
    private MappedDeleteCollection(TableInfo<T, ID> tableInfo, String string, FieldType[] arrfieldType) {
        super(tableInfo, string, arrfieldType);
    }

    private static void appendWhereIds(DatabaseType databaseType, FieldType fieldType, StringBuilder stringBuilder, int n, FieldType[] arrfieldType) {
        stringBuilder.append("WHERE ");
        databaseType.appendEscapedEntityName(stringBuilder, fieldType.getColumnName());
        stringBuilder.append(" IN (");
        boolean bl = true;
        for (int i = 0; i < n; ++i) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(',');
            }
            stringBuilder.append('?');
            if (arrfieldType == null) continue;
            arrfieldType[i] = fieldType;
        }
        stringBuilder.append(") ");
    }

    private static <T, ID> MappedDeleteCollection<T, ID> build(DatabaseType object, TableInfo<T, ID> tableInfo, int n) throws SQLException {
        FieldType fieldType = tableInfo.getIdField();
        if (fieldType == null) {
            object = new StringBuilder();
            object.append("Cannot delete ");
            object.append(tableInfo.getDataClass());
            object.append(" because it doesn't have an id field defined");
            throw new SQLException(object.toString());
        }
        StringBuilder stringBuilder = new StringBuilder(128);
        MappedDeleteCollection.appendTableName((DatabaseType)object, stringBuilder, "DELETE FROM ", tableInfo.getTableName());
        FieldType[] arrfieldType = new FieldType[n];
        MappedDeleteCollection.appendWhereIds((DatabaseType)object, fieldType, stringBuilder, n, arrfieldType);
        return new MappedDeleteCollection<T, ID>(tableInfo, stringBuilder.toString(), arrfieldType);
    }

    public static <T, ID> int deleteIds(DatabaseType object, TableInfo<T, ID> tableInfo, DatabaseConnection databaseConnection, Collection<ID> object2, ObjectCache objectCache) throws SQLException {
        object = MappedDeleteCollection.build((DatabaseType)object, tableInfo, object2.size());
        Object[] arrobject = new Object[object2.size()];
        FieldType fieldType = tableInfo.getIdField();
        object2 = object2.iterator();
        int n = 0;
        while (object2.hasNext()) {
            arrobject[n] = fieldType.convertJavaFieldToSqlArgValue(object2.next());
            ++n;
        }
        return MappedDeleteCollection.updateRows(databaseConnection, tableInfo.getDataClass(), object, arrobject, objectCache);
    }

    public static <T, ID> int deleteObjects(DatabaseType object, TableInfo<T, ID> tableInfo, DatabaseConnection databaseConnection, Collection<T> object2, ObjectCache objectCache) throws SQLException {
        object = MappedDeleteCollection.build((DatabaseType)object, tableInfo, object2.size());
        Object[] arrobject = new Object[object2.size()];
        FieldType fieldType = tableInfo.getIdField();
        object2 = object2.iterator();
        int n = 0;
        while (object2.hasNext()) {
            arrobject[n] = fieldType.extractJavaFieldToSqlArgValue(object2.next());
            ++n;
        }
        return MappedDeleteCollection.updateRows(databaseConnection, tableInfo.getDataClass(), object, arrobject, objectCache);
    }

    private static <T, ID> int updateRows(DatabaseConnection databaseConnection, Class<T> serializable, MappedDeleteCollection<T, ID> mappedDeleteCollection, Object[] arrobject, ObjectCache objectCache) throws SQLException {
        int n;
        block7 : {
            n = databaseConnection.delete(mappedDeleteCollection.statement, arrobject, mappedDeleteCollection.argFieldTypes);
            if (n <= 0 || objectCache == null) break block7;
            int n2 = arrobject.length;
            for (int i = 0; i < n2; ++i) {
                objectCache.remove(serializable, arrobject[i]);
                continue;
            }
        }
        try {
            logger.debug("delete-collection with statement '{}' and {} args, changed {} rows", (Object)mappedDeleteCollection.statement, (Object)arrobject.length, (Object)n);
            if (arrobject.length > 0) {
                logger.trace("delete-collection arguments: {}", (Object)arrobject);
            }
            return n;
        }
        catch (SQLException sQLException) {
            serializable = new StringBuilder();
            serializable.append("Unable to run delete collection stmt: ");
            serializable.append(mappedDeleteCollection.statement);
            throw SqlExceptionUtil.create(serializable.toString(), sQLException);
        }
    }
}
