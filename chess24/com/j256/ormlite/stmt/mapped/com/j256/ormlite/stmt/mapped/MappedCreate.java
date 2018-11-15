/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.mapped.BaseMappedStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.GeneratedKeyHolder;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class MappedCreate<T, ID>
extends BaseMappedStatement<T, ID> {
    private String dataClassName;
    private final String queryNextSequenceStmt;
    private int versionFieldTypeIndex;

    private MappedCreate(TableInfo<T, ID> tableInfo, String string, FieldType[] arrfieldType, String string2, int n) {
        super(tableInfo, string, arrfieldType);
        this.dataClassName = tableInfo.getDataClass().getSimpleName();
        this.queryNextSequenceStmt = string2;
        this.versionFieldTypeIndex = n;
    }

    private void assignIdValue(T t, Number number, String string, ObjectCache objectCache) throws SQLException {
        this.idField.assignIdValue(t, number, objectCache);
        if (logger.isLevelEnabled(Log.Level.DEBUG)) {
            logger.debug("assigned id '{}' from {} to '{}' in {} object", new Object[]{number, string, this.idField.getFieldName(), this.dataClassName});
        }
    }

    private void assignSequenceId(DatabaseConnection object, T t, ObjectCache objectCache) throws SQLException {
        long l = object.queryForLong(this.queryNextSequenceStmt);
        logger.debug("queried for sequence {} using stmt: {}", l, (Object)this.queryNextSequenceStmt);
        if (l == 0L) {
            object = new StringBuilder();
            object.append("Should not have returned 0 for stmt: ");
            object.append(this.queryNextSequenceStmt);
            throw new SQLException(object.toString());
        }
        this.assignIdValue(t, l, "sequence", objectCache);
    }

    public static <T, ID> MappedCreate<T, ID> build(DatabaseType object, TableInfo<T, ID> tableInfo) {
        FieldType[] arrfieldType;
        int n;
        int n2;
        int n3;
        StringBuilder stringBuilder = new StringBuilder(128);
        MappedCreate.appendTableName((DatabaseType)object, stringBuilder, "INSERT INTO ", tableInfo.getTableName());
        FieldType[] arrfieldType2 = tableInfo.getFieldTypes();
        int n4 = arrfieldType2.length;
        int n5 = 0;
        int n6 = -1;
        for (n2 = n5; n2 < n4; ++n2) {
            arrfieldType = arrfieldType2[n2];
            n3 = n5;
            n = n6;
            if (MappedCreate.isFieldCreatable((DatabaseType)object, (FieldType)arrfieldType)) {
                if (arrfieldType.isVersion()) {
                    n6 = n5;
                }
                n3 = n5 + 1;
                n = n6;
            }
            n5 = n3;
            n6 = n;
        }
        arrfieldType2 = new FieldType[n5];
        if (n5 == 0) {
            object.appendInsertNoColumns(stringBuilder);
        } else {
            stringBuilder.append('(');
            arrfieldType = tableInfo.getFieldTypes();
            n3 = arrfieldType.length;
            n = n2 = 0;
            n5 = 1;
            while (n2 < n3) {
                FieldType fieldType = arrfieldType[n2];
                if (MappedCreate.isFieldCreatable((DatabaseType)object, fieldType)) {
                    if (n5 != 0) {
                        n5 = 0;
                    } else {
                        stringBuilder.append(",");
                    }
                    MappedCreate.appendFieldColumnName((DatabaseType)object, stringBuilder, fieldType, null);
                    arrfieldType2[n] = fieldType;
                    ++n;
                }
                ++n2;
            }
            stringBuilder.append(") VALUES (");
            arrfieldType = tableInfo.getFieldTypes();
            n = arrfieldType.length;
            n5 = 1;
            for (n2 = 0; n2 < n; ++n2) {
                if (!MappedCreate.isFieldCreatable((DatabaseType)object, arrfieldType[n2])) continue;
                if (n5 != 0) {
                    n5 = 0;
                } else {
                    stringBuilder.append(",");
                }
                stringBuilder.append("?");
            }
            stringBuilder.append(")");
        }
        object = MappedCreate.buildQueryNextSequence((DatabaseType)object, tableInfo.getIdField());
        return new MappedCreate<T, ID>(tableInfo, stringBuilder.toString(), arrfieldType2, (String)object, n6);
    }

    private static String buildQueryNextSequence(DatabaseType databaseType, FieldType object) {
        if (object == null) {
            return null;
        }
        if ((object = object.getGeneratedIdSequence()) == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(64);
        databaseType.appendSelectNextValFromSequence(stringBuilder, (String)object);
        return stringBuilder.toString();
    }

    private boolean foreignCollectionsAreAssigned(FieldType[] arrfieldType, Object object) throws SQLException {
        int n = arrfieldType.length;
        for (int i = 0; i < n; ++i) {
            if (arrfieldType[i].extractJavaFieldValue(object) != null) continue;
            return false;
        }
        return true;
    }

    private static boolean isFieldCreatable(DatabaseType databaseType, FieldType fieldType) {
        if (fieldType.isForeignCollection()) {
            return false;
        }
        if (fieldType.isReadOnly()) {
            return false;
        }
        if (databaseType.isIdSequenceNeeded() && databaseType.isSelectSequenceBeforeInsert()) {
            return true;
        }
        if (fieldType.isGeneratedId() && !fieldType.isSelfGeneratedId() && !fieldType.isAllowGeneratedIdInsert()) {
            return false;
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public int insert(DatabaseType var1_1, DatabaseConnection var2_4, T var3_5, ObjectCache var4_6) throws SQLException {
        block24 : {
            block23 : {
                if (this.idField == null) ** GOTO lbl-1000
                var5_7 = this.idField.isAllowGeneratedIdInsert() != false && this.idField.isObjectsFieldValueDefault(var3_5) == false ? 0 : 1;
                if (!this.idField.isSelfGeneratedId() || !this.idField.isGeneratedId()) break block23;
                if (var5_7 != 0) {
                    this.idField.assignField(var3_5, this.idField.generateId(), false, var4_6);
                }
                ** GOTO lbl-1000
            }
            if (!this.idField.isGeneratedIdSequence() || !var1_1.isSelectSequenceBeforeInsert()) break block24;
            if (var5_7 != 0) {
                this.assignSequenceId((DatabaseConnection)var2_4, var3_5, var4_6);
            }
            ** GOTO lbl-1000
        }
        if (this.idField.isGeneratedId() && var5_7 != 0) {
            var1_1 = new KeyHolder();
        } else lbl-1000: // 4 sources:
        {
            var1_1 = null;
        }
        if (!this.tableInfo.isForeignAutoCreate()) ** GOTO lbl29
        var7_8 = this.tableInfo.getFieldTypes();
        var6_12 = var7_8.length;
        var5_7 = 0;
        do {
            block21 : {
                block22 : {
                    if (var5_7 >= var6_12) ** GOTO lbl29
                    var8_13 = var7_8[var5_7];
                    if (var8_13.isForeignAutoCreate() && (var9_14 = var8_13.extractRawJavaFieldValue(var3_5)) != null && var8_13.getForeignIdField().isObjectsFieldValueDefault(var9_14)) {
                        var8_13.createWithForeignDao(var9_14);
                    }
                    break block21;
lbl29: // 2 sources:
                    var8_13 = this.getFieldObjects(var3_5);
                    if (this.versionFieldTypeIndex >= 0 && var8_13[this.versionFieldTypeIndex] == null) {
                        var9_14 = this.argFieldTypes[this.versionFieldTypeIndex];
                        var7_9 = var9_14.moveToNextValue(null);
                        var8_13[this.versionFieldTypeIndex] = var9_14.convertJavaFieldToSqlArgValue(var7_9);
                        break block22;
                    }
                    var7_10 = null;
                }
                var5_7 = var2_4.insert(this.statement, (Object[])var8_13, this.argFieldTypes, (GeneratedKeyHolder)var1_1);
                {
                    catch (SQLException var1_2) {
                        MappedCreate.logger.debug("insert data with statement '{}' and {} args, threw exception: {}", (Object)this.statement, (Object)((Object[])var8_13).length, (Object)var1_2);
                        if (((Object)var8_13).length > 0) {
                            MappedCreate.logger.trace("insert arguments: {}", var8_13);
                        }
                        throw var1_2;
                    }
                }
                try {
                    MappedCreate.logger.debug("insert data with statement '{}' and {} args, changed {} rows", (Object)this.statement, (Object)((Object)var8_13).length, (Object)var5_7);
                    if (((Object)var8_13).length > 0) {
                        MappedCreate.logger.trace("insert arguments: {}", var8_13);
                    }
                    if (var5_7 > 0) {
                        if (var7_11 != null) {
                            this.argFieldTypes[this.versionFieldTypeIndex].assignField(var3_5, var7_11, false, null);
                        }
                        if (var1_1 != null) {
                            if ((var1_1 = var1_1.getKey()) == null) {
                                throw new SQLException("generated-id key was not set by the update call");
                            }
                            if (var1_1.longValue() == 0L) {
                                throw new SQLException("generated-id key must not be 0 value");
                            }
                            this.assignIdValue(var3_5, (Number)var1_1, "keyholder", var4_6);
                        }
                        if (var4_6 != null && this.foreignCollectionsAreAssigned(this.tableInfo.getForeignCollections(), var3_5)) {
                            var1_1 = this.idField.extractJavaFieldValue(var3_5);
                            var4_6.put(this.clazz, var1_1, var3_5);
                            return var5_7;
                        }
                    }
                    break;
                }
                catch (SQLException var1_3) {
                    var2_4 = new StringBuilder();
                    var2_4.append("Unable to run insert stmt on object ");
                    var2_4.append(var3_5);
                    var2_4.append(": ");
                    var2_4.append(this.statement);
                    throw SqlExceptionUtil.create(var2_4.toString(), var1_3);
                }
            }
            ++var5_7;
        } while (true);
        return var5_7;
    }

    private static class KeyHolder
    implements GeneratedKeyHolder {
        Number key;

        private KeyHolder() {
        }

        @Override
        public void addKey(Number number) throws SQLException {
            if (this.key == null) {
                this.key = number;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("generated key has already been set to ");
            stringBuilder.append(this.key);
            stringBuilder.append(", now set to ");
            stringBuilder.append(number);
            throw new SQLException(stringBuilder.toString());
        }

        public Number getKey() {
            return this.key;
        }
    }

}
