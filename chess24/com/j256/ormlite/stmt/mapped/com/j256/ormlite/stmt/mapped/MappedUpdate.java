/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.mapped.BaseMappedStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

public class MappedUpdate<T, ID>
extends BaseMappedStatement<T, ID> {
    private final FieldType versionFieldType;
    private final int versionFieldTypeIndex;

    private MappedUpdate(TableInfo<T, ID> tableInfo, String string, FieldType[] arrfieldType, FieldType fieldType, int n) {
        super(tableInfo, string, arrfieldType);
        this.versionFieldType = fieldType;
        this.versionFieldTypeIndex = n;
    }

    public static <T, ID> MappedUpdate<T, ID> build(DatabaseType object, TableInfo<T, ID> tableInfo) throws SQLException {
        int n;
        FieldType[] arrfieldType;
        int n2;
        FieldType[] arrfieldType2;
        int n3;
        FieldType fieldType = tableInfo.getIdField();
        if (fieldType == null) {
            object = new StringBuilder();
            object.append("Cannot update ");
            object.append(tableInfo.getDataClass());
            object.append(" because it doesn't have an id field");
            throw new SQLException(object.toString());
        }
        StringBuilder stringBuilder = new StringBuilder(64);
        MappedUpdate.appendTableName((DatabaseType)object, stringBuilder, "UPDATE ", tableInfo.getTableName());
        Object object2 = tableInfo.getFieldTypes();
        int n4 = ((FieldType[])object2).length;
        int n5 = -1;
        FieldType[] arrfieldType3 = null;
        int n6 = n3 = 0;
        while (n3 < n4) {
            arrfieldType2 = object2[n3];
            n = n6;
            arrfieldType = arrfieldType3;
            n2 = n5;
            if (MappedUpdate.isFieldUpdatable((FieldType)arrfieldType2, fieldType)) {
                if (arrfieldType2.isVersion()) {
                    n5 = n6;
                    arrfieldType3 = arrfieldType2;
                }
                n = n6 + 1;
                n2 = n5;
                arrfieldType = arrfieldType3;
            }
            ++n3;
            n6 = n;
            arrfieldType3 = arrfieldType;
            n5 = n2;
        }
        n6 = n3 = n6 + 1;
        if (arrfieldType3 != null) {
            n6 = n3 + 1;
        }
        arrfieldType = new FieldType[n6];
        arrfieldType2 = tableInfo.getFieldTypes();
        n = arrfieldType2.length;
        n6 = 1;
        n2 = n3 = 0;
        while (n3 < n) {
            object2 = arrfieldType2[n3];
            if (MappedUpdate.isFieldUpdatable((FieldType)object2, fieldType)) {
                if (n6 != 0) {
                    stringBuilder.append("SET ");
                    n6 = 0;
                } else {
                    stringBuilder.append(", ");
                }
                MappedUpdate.appendFieldColumnName((DatabaseType)object, stringBuilder, (FieldType)object2, null);
                arrfieldType[n2] = object2;
                stringBuilder.append("= ?");
                ++n2;
            }
            ++n3;
        }
        stringBuilder.append(' ');
        MappedUpdate.appendWhereFieldEq((DatabaseType)object, fieldType, stringBuilder, null);
        arrfieldType[n2] = fieldType;
        if (arrfieldType3 != null) {
            stringBuilder.append(" AND ");
            MappedUpdate.appendFieldColumnName((DatabaseType)object, stringBuilder, (FieldType)arrfieldType3, null);
            stringBuilder.append("= ?");
            arrfieldType[n2 + 1] = arrfieldType3;
        }
        return new MappedUpdate<T, ID>(tableInfo, stringBuilder.toString(), arrfieldType, (FieldType)arrfieldType3, n5);
    }

    private static boolean isFieldUpdatable(FieldType fieldType, FieldType fieldType2) {
        if (fieldType != fieldType2 && !fieldType.isForeignCollection() && !fieldType.isReadOnly()) {
            return true;
        }
        return false;
    }

    /*
     * Exception decompiling
     */
    public int update(DatabaseConnection var1_1, T var2_3, ObjectCache var3_4) throws SQLException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 10[SIMPLE_IF_TAKEN]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }
}
