/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.table;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.ObjectFactory;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TableInfo<T, ID> {
    private static final FieldType[] NO_FOREIGN_COLLECTIONS = new FieldType[0];
    private final BaseDaoImpl<T, ID> baseDaoImpl;
    private final Constructor<T> constructor;
    private final Class<T> dataClass;
    private Map<String, FieldType> fieldNameMap;
    private final FieldType[] fieldTypes;
    private final boolean foreignAutoCreate;
    private final FieldType[] foreignCollections;
    private final FieldType idField;
    private final String tableName;

    public TableInfo(DatabaseType databaseType, BaseDaoImpl<T, ID> baseDaoImpl, DatabaseTableConfig<T> databaseTableConfig) throws SQLException {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:296)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public TableInfo(ConnectionSource connectionSource, BaseDaoImpl<T, ID> baseDaoImpl, Class<T> class_) throws SQLException {
        this(connectionSource.getDatabaseType(), baseDaoImpl, DatabaseTableConfig.fromClass(connectionSource, class_));
    }

    private static <T, ID> void wireNewInstance(BaseDaoImpl<T, ID> baseDaoImpl, T t) {
        if (t instanceof BaseDaoEnabled) {
            ((BaseDaoEnabled)t).setDao(baseDaoImpl);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public T createObject() throws SQLException {
        void var1_6;
        Object var1_1 = null;
        try {
            void var1_3;
            if (this.baseDaoImpl != null) {
                ObjectFactory<T> objectFactory = this.baseDaoImpl.getObjectFactory();
            }
            if (var1_3 == null) {
                T t = this.constructor.newInstance(new Object[0]);
            } else {
                T t = var1_3.createObject(this.constructor, this.baseDaoImpl.getDataClass());
            }
            TableInfo.wireNewInstance(this.baseDaoImpl, var1_6);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not create object for ");
            stringBuilder.append(this.constructor.getDeclaringClass());
            throw SqlExceptionUtil.create(stringBuilder.toString(), exception);
        }
        return var1_6;
    }

    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    public Class<T> getDataClass() {
        return this.dataClass;
    }

    public FieldType getFieldTypeByColumnName(String charSequence) {
        Object object = this.fieldNameMap;
        int n = 0;
        if (object == null) {
            object = new HashMap<String, FieldType>();
            for (FieldType fieldType : this.fieldTypes) {
                object.put(fieldType.getColumnName().toLowerCase(), fieldType);
            }
            this.fieldNameMap = object;
        }
        if ((object = this.fieldNameMap.get(charSequence.toLowerCase())) != null) {
            return object;
        }
        FieldType[] arrfieldType = this.fieldTypes;
        int n2 = arrfieldType.length;
        for (int i = n; i < n2; ++i) {
            object = arrfieldType[i];
            if (!object.getFieldName().equals(charSequence)) continue;
            charSequence = new StringBuilder();
            charSequence.append("You should use columnName '");
            charSequence.append(object.getColumnName());
            charSequence.append("' for table ");
            charSequence.append(this.tableName);
            charSequence.append(" instead of fieldName '");
            charSequence.append(object.getFieldName());
            charSequence.append("'");
            throw new IllegalArgumentException(charSequence.toString());
        }
        object = new StringBuilder();
        object.append("Unknown column name '");
        object.append((String)charSequence);
        object.append("' in table ");
        object.append(this.tableName);
        throw new IllegalArgumentException(object.toString());
    }

    public FieldType[] getFieldTypes() {
        return this.fieldTypes;
    }

    public FieldType[] getForeignCollections() {
        return this.foreignCollections;
    }

    public FieldType getIdField() {
        return this.idField;
    }

    public String getTableName() {
        return this.tableName;
    }

    public boolean hasColumnName(String string) {
        FieldType[] arrfieldType = this.fieldTypes;
        int n = arrfieldType.length;
        for (int i = 0; i < n; ++i) {
            if (!arrfieldType[i].getColumnName().equals(string)) continue;
            return true;
        }
        return false;
    }

    public boolean isForeignAutoCreate() {
        return this.foreignAutoCreate;
    }

    public boolean isUpdatable() {
        if (this.idField != null && this.fieldTypes.length > 1) {
            return true;
        }
        return false;
    }

    public String objectToString(T t) {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append(t.getClass().getSimpleName());
        for (FieldType fieldType : this.fieldTypes) {
            stringBuilder.append(' ');
            stringBuilder.append(fieldType.getColumnName());
            stringBuilder.append("=");
            try {
                stringBuilder.append(fieldType.extractJavaFieldValue(t));
            }
            catch (Exception exception) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Could not generate toString of field ");
                stringBuilder.append(fieldType);
                throw new IllegalStateException(stringBuilder.toString(), exception);
            }
        }
        return stringBuilder.toString();
    }
}
