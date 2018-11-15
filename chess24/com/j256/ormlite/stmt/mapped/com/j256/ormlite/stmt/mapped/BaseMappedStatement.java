/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseMappedStatement<T, ID> {
    protected static Logger logger = LoggerFactory.getLogger(BaseMappedStatement.class);
    protected final FieldType[] argFieldTypes;
    protected final Class<T> clazz;
    protected final FieldType idField;
    protected final String statement;
    protected final TableInfo<T, ID> tableInfo;

    protected BaseMappedStatement(TableInfo<T, ID> tableInfo, String string, FieldType[] arrfieldType) {
        this.tableInfo = tableInfo;
        this.clazz = tableInfo.getDataClass();
        this.idField = tableInfo.getIdField();
        this.statement = string;
        this.argFieldTypes = arrfieldType;
    }

    static void appendFieldColumnName(DatabaseType databaseType, StringBuilder stringBuilder, FieldType fieldType, List<FieldType> list) {
        databaseType.appendEscapedEntityName(stringBuilder, fieldType.getColumnName());
        if (list != null) {
            list.add(fieldType);
        }
        stringBuilder.append(' ');
    }

    static void appendTableName(DatabaseType databaseType, StringBuilder stringBuilder, String string, String string2) {
        if (string != null) {
            stringBuilder.append(string);
        }
        databaseType.appendEscapedEntityName(stringBuilder, string2);
        stringBuilder.append(' ');
    }

    static void appendWhereFieldEq(DatabaseType databaseType, FieldType fieldType, StringBuilder stringBuilder, List<FieldType> list) {
        stringBuilder.append("WHERE ");
        BaseMappedStatement.appendFieldColumnName(databaseType, stringBuilder, fieldType, list);
        stringBuilder.append("= ?");
    }

    protected Object convertIdToFieldObject(ID ID) throws SQLException {
        return this.idField.convertJavaFieldToSqlArgValue(ID);
    }

    protected Object[] getFieldObjects(Object object) throws SQLException {
        Object[] arrobject = this.argFieldTypes;
        arrobject = new Object[arrobject.length];
        for (int i = 0; i < this.argFieldTypes.length; ++i) {
            FieldType fieldType = this.argFieldTypes[i];
            arrobject[i] = fieldType.isAllowGeneratedIdInsert() ? fieldType.getFieldValueIfNotDefault(object) : fieldType.extractJavaFieldToSqlArgValue(object);
            if (arrobject[i] != null || fieldType.getDefaultValue() == null) continue;
            arrobject[i] = fieldType.getDefaultValue();
        }
        return arrobject;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MappedStatement: ");
        stringBuilder.append(this.statement);
        return stringBuilder.toString();
    }
}
