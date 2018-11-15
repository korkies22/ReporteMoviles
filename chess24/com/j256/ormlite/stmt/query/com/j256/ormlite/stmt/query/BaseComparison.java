/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.stmt.query;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.ColumnArg;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.query.Comparison;
import java.sql.SQLException;
import java.util.List;

abstract class BaseComparison
implements Comparison {
    private static final String NUMBER_CHARACTERS = "0123456789.-+";
    protected final String columnName;
    protected final FieldType fieldType;
    private final Object value;

    protected BaseComparison(String string, FieldType fieldType, Object object, boolean bl) throws SQLException {
        if (bl && fieldType != null && !fieldType.isComparable()) {
            object = new StringBuilder();
            object.append("Field '");
            object.append(string);
            object.append("' is of data type ");
            object.append(fieldType.getDataPersister());
            object.append(" which can not be compared");
            throw new SQLException(object.toString());
        }
        this.columnName = string;
        this.fieldType = fieldType;
        this.value = object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void appendArgOrValue(DatabaseType var1_1, FieldType var2_2, StringBuilder var3_3, List<ArgumentHolder> var4_4, Object var5_5) throws SQLException {
        block11 : {
            block10 : {
                block9 : {
                    if (var5_5 == null) {
                        var1_1 = new StringBuilder();
                        var1_1.append("argument for '");
                        var1_1.append(var2_2.getFieldName());
                        var1_1.append("' is null");
                        throw new SQLException(var1_1.toString());
                    }
                    var7_6 = var5_5 instanceof ArgumentHolder;
                    var6_7 = false;
                    if (!var7_6) break block9;
                    var3_3.append('?');
                    var1_1 = (ArgumentHolder)var5_5;
                    var1_1.setMetaInfo(this.columnName, (FieldType)var2_2);
                    var4_4.add(var1_1);
                    ** GOTO lbl54
                }
                if (!(var5_5 instanceof ColumnArg)) break block10;
                var2_2 = (ColumnArg)var5_5;
                var4_4 = var2_2.getTableName();
                if (var4_4 != null) {
                    var1_1.appendEscapedEntityName(var3_3, (String)var4_4);
                    var3_3.append('.');
                }
                var1_1.appendEscapedEntityName(var3_3, var2_2.getColumnName());
                ** GOTO lbl54
            }
            if (!var2_2.isArgumentHolderRequired()) break block11;
            var3_3.append('?');
            var1_1 = new SelectArg();
            var1_1.setMetaInfo(this.columnName, (FieldType)var2_2);
            var1_1.setValue(var5_5);
            var4_4.add(var1_1);
            ** GOTO lbl54
        }
        if (var2_2.isForeign() && var2_2.getType().isAssignableFrom(var5_5.getClass())) {
            var2_2 = var2_2.getForeignIdField();
            this.appendArgOrValue((DatabaseType)var1_1, (FieldType)var2_2, var3_3, (List<ArgumentHolder>)var4_4, var2_2.extractJavaFieldValue(var5_5));
        } else {
            if (var2_2.isEscapedValue()) {
                var1_1.appendEscapedWord(var3_3, var2_2.convertJavaFieldToSqlArgValue(var5_5).toString());
            } else if (var2_2.isForeign()) {
                var1_1 = var2_2.convertJavaFieldToSqlArgValue(var5_5).toString();
                if (var1_1.length() > 0 && "0123456789.-+".indexOf(var1_1.charAt(0)) < 0) {
                    var3_3 = new StringBuilder();
                    var3_3.append("Foreign field ");
                    var3_3.append(var2_2);
                    var3_3.append(" does not seem to be producing a numerical value '");
                    var3_3.append((String)var1_1);
                    var3_3.append("'. Maybe you are passing the wrong object to comparison: ");
                    var3_3.append(this);
                    throw new SQLException(var3_3.toString());
                }
                var3_3.append((String)var1_1);
            } else {
                var3_3.append(var2_2.convertJavaFieldToSqlArgValue(var5_5));
            }
lbl54: // 6 sources:
            var6_7 = true;
        }
        if (var6_7 == false) return;
        var3_3.append(' ');
    }

    @Override
    public abstract void appendOperation(StringBuilder var1);

    @Override
    public void appendSql(DatabaseType databaseType, String string, StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        if (string != null) {
            databaseType.appendEscapedEntityName(stringBuilder, string);
            stringBuilder.append('.');
        }
        databaseType.appendEscapedEntityName(stringBuilder, this.columnName);
        stringBuilder.append(' ');
        this.appendOperation(stringBuilder);
        this.appendValue(databaseType, stringBuilder, list);
    }

    @Override
    public void appendValue(DatabaseType databaseType, StringBuilder stringBuilder, List<ArgumentHolder> list) throws SQLException {
        this.appendArgOrValue(databaseType, this.fieldType, stringBuilder, list, this.value);
    }

    @Override
    public String getColumnName() {
        return this.columnName;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.columnName);
        stringBuilder.append(' ');
        this.appendOperation(stringBuilder);
        stringBuilder.append(' ');
        stringBuilder.append(this.value);
        return stringBuilder.toString();
    }
}
