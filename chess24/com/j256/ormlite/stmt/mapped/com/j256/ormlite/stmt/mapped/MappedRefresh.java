/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.mapped.MappedQueryForId;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class MappedRefresh<T, ID>
extends MappedQueryForId<T, ID> {
    private MappedRefresh(TableInfo<T, ID> tableInfo, String string, FieldType[] arrfieldType, FieldType[] arrfieldType2) {
        super(tableInfo, string, arrfieldType, arrfieldType2, "refresh");
    }

    public static <T, ID> MappedRefresh<T, ID> build(DatabaseType object, TableInfo<T, ID> tableInfo) throws SQLException {
        FieldType fieldType = tableInfo.getIdField();
        if (fieldType == null) {
            object = new StringBuilder();
            object.append("Cannot refresh ");
            object.append(tableInfo.getDataClass());
            object.append(" because it doesn't have an id field");
            throw new SQLException(object.toString());
        }
        object = MappedRefresh.buildStatement((DatabaseType)object, tableInfo, fieldType);
        fieldType = tableInfo.getIdField();
        FieldType[] arrfieldType = tableInfo.getFieldTypes();
        return new MappedRefresh<T, ID>(tableInfo, (String)object, new FieldType[]{fieldType}, arrfieldType);
    }

    public int executeRefresh(DatabaseConnection databaseConnection, T t, ObjectCache objectCache) throws SQLException {
        if ((databaseConnection = super.execute(databaseConnection, this.idField.extractJavaFieldValue(t), null)) == null) {
            return 0;
        }
        for (FieldType fieldType : this.resultsFieldTypes) {
            if (fieldType == this.idField) continue;
            fieldType.assignField(t, fieldType.extractJavaFieldValue(databaseConnection), false, objectCache);
        }
        return 1;
    }
}
