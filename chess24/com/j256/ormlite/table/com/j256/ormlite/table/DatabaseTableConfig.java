/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.table;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.JavaxPersistence;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseTableConfig<T> {
    private Constructor<T> constructor;
    private Class<T> dataClass;
    private List<DatabaseFieldConfig> fieldConfigs;
    private FieldType[] fieldTypes;
    private String tableName;

    public DatabaseTableConfig() {
    }

    public DatabaseTableConfig(Class<T> class_, String string, List<DatabaseFieldConfig> list) {
        this.dataClass = class_;
        this.tableName = string;
        this.fieldConfigs = list;
    }

    private DatabaseTableConfig(Class<T> class_, String string, FieldType[] arrfieldType) {
        this.dataClass = class_;
        this.tableName = string;
        this.fieldTypes = arrfieldType;
    }

    public DatabaseTableConfig(Class<T> class_, List<DatabaseFieldConfig> list) {
        this(class_, DatabaseTableConfig.extractTableName(class_), list);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private FieldType[] convertFieldConfigs(ConnectionSource object, String string, List<DatabaseFieldConfig> class_) throws SQLException {
        ArrayList<Field> arrayList = new ArrayList<Field>();
        Iterator iterator = class_.iterator();
        block2 : do {
            if (!iterator.hasNext()) {
                if (!arrayList.isEmpty()) return arrayList.toArray(new FieldType[arrayList.size()]);
                object = new StringBuilder();
                object.append("No fields were configured for class ");
                object.append(this.dataClass);
                throw new SQLException(object.toString());
            }
            DatabaseFieldConfig databaseFieldConfig = (DatabaseFieldConfig)iterator.next();
            Field field = null;
            class_ = this.dataClass;
            do {
                block8 : {
                    Object object2 = field;
                    if (class_ != null) {
                        object2 = class_.getDeclaredField(databaseFieldConfig.getFieldName());
                        if (object2 == null) break block8;
                        object2 = new FieldType((ConnectionSource)object, string, (Field)object2, databaseFieldConfig, this.dataClass);
                    }
                    if (object2 == null) {
                        object = new StringBuilder();
                        object.append("Could not find declared field with name '");
                        object.append(databaseFieldConfig.getFieldName());
                        object.append("' for ");
                        object.append(this.dataClass);
                        throw new SQLException(object.toString());
                    }
                    arrayList.add((Field)object2);
                    continue block2;
                    catch (NoSuchFieldException noSuchFieldException) {}
                }
                class_ = class_.getSuperclass();
            } while (true);
            break;
        } while (true);
    }

    private static <T> FieldType[] extractFieldTypes(ConnectionSource object, Class<T> class_, String string) throws SQLException {
        ArrayList<FieldType> arrayList = new ArrayList<FieldType>();
        for (Class<T> class_2 = class_; class_2 != null; class_2 = class_2.getSuperclass()) {
            Field[] arrfield = class_2.getDeclaredFields();
            int n = arrfield.length;
            for (int i = 0; i < n; ++i) {
                FieldType fieldType = FieldType.createFieldType((ConnectionSource)object, string, arrfield[i], class_);
                if (fieldType == null) continue;
                arrayList.add(fieldType);
            }
        }
        if (arrayList.isEmpty()) {
            object = new StringBuilder();
            object.append("No fields have a ");
            object.append(DatabaseField.class.getSimpleName());
            object.append(" annotation in ");
            object.append(class_);
            throw new IllegalArgumentException(object.toString());
        }
        return arrayList.toArray(new FieldType[arrayList.size()]);
    }

    public static <T> String extractTableName(Class<T> class_) {
        Object object = class_.getAnnotation(DatabaseTable.class);
        if (object != null && object.tableName() != null && object.tableName().length() > 0) {
            return object.tableName();
        }
        object = JavaxPersistence.getEntityName(class_);
        if (object == null) {
            return class_.getSimpleName().toLowerCase();
        }
        return object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static <T> Constructor<T> findNoArgConstructor(Class<T> class_) {
        Object object;
        try {
            object = class_.getDeclaredConstructors();
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't lookup declared constructors for ");
            stringBuilder.append(class_);
            throw new IllegalArgumentException(stringBuilder.toString(), exception);
        }
        int n = ((Constructor<?>[])object).length;
        for (int i = 0; i < n; ++i) {
            Constructor<?> constructor = object[i];
            if (constructor.getParameterTypes().length != 0) continue;
            if (constructor.isAccessible()) return constructor;
            constructor.setAccessible(true);
            return constructor;
        }
        if (class_.getEnclosingClass() == null) {
            object = new StringBuilder();
            object.append("Can't find a no-arg constructor for ");
            object.append(class_);
            throw new IllegalArgumentException(object.toString());
        }
        object = new StringBuilder();
        object.append("Can't find a no-arg constructor for ");
        object.append(class_);
        object.append(".  Missing static on inner class?");
        throw new IllegalArgumentException(object.toString());
        catch (SecurityException securityException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not open access to constructor for ");
        stringBuilder.append(class_);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static <T> DatabaseTableConfig<T> fromClass(ConnectionSource connectionSource, Class<T> class_) throws SQLException {
        String string;
        String string2 = string = DatabaseTableConfig.extractTableName(class_);
        if (connectionSource.getDatabaseType().isEntityNamesMustBeUpCase()) {
            string2 = string.toUpperCase();
        }
        return new DatabaseTableConfig<T>(class_, string2, DatabaseTableConfig.extractFieldTypes(connectionSource, class_, string2));
    }

    public void extractFieldTypes(ConnectionSource connectionSource) throws SQLException {
        if (this.fieldTypes == null) {
            if (this.fieldConfigs == null) {
                this.fieldTypes = DatabaseTableConfig.extractFieldTypes(connectionSource, this.dataClass, this.tableName);
                return;
            }
            this.fieldTypes = this.convertFieldConfigs(connectionSource, this.tableName, this.fieldConfigs);
        }
    }

    public Constructor<T> getConstructor() {
        if (this.constructor == null) {
            this.constructor = DatabaseTableConfig.findNoArgConstructor(this.dataClass);
        }
        return this.constructor;
    }

    public Class<T> getDataClass() {
        return this.dataClass;
    }

    public List<DatabaseFieldConfig> getFieldConfigs() {
        return this.fieldConfigs;
    }

    public FieldType[] getFieldTypes(DatabaseType databaseType) throws SQLException {
        if (this.fieldTypes == null) {
            throw new SQLException("Field types have not been extracted in table config");
        }
        return this.fieldTypes;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void initialize() {
        if (this.dataClass == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("dataClass was never set on ");
            stringBuilder.append(this.getClass().getSimpleName());
            throw new IllegalStateException(stringBuilder.toString());
        }
        if (this.tableName == null) {
            this.tableName = DatabaseTableConfig.extractTableName(this.dataClass);
        }
    }

    public void setConstructor(Constructor<T> constructor) {
        this.constructor = constructor;
    }

    public void setDataClass(Class<T> class_) {
        this.dataClass = class_;
    }

    public void setFieldConfigs(List<DatabaseFieldConfig> list) {
        this.fieldConfigs = list;
    }

    public void setTableName(String string) {
        this.tableName = string;
    }
}
