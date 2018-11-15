/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.field.types.VoidType;
import com.j256.ormlite.misc.JavaxPersistence;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;

public class DatabaseFieldConfig {
    public static final boolean DEFAULT_CAN_BE_NULL = true;
    public static final DataType DEFAULT_DATA_TYPE;
    public static final boolean DEFAULT_FOREIGN_COLLECTION_ORDER_ASCENDING = true;
    private static final int DEFAULT_MAX_EAGER_FOREIGN_COLLECTION_LEVEL = 1;
    public static final Class<? extends DataPersister> DEFAULT_PERSISTER_CLASS;
    private boolean allowGeneratedIdInsert;
    private boolean canBeNull = true;
    private String columnDefinition;
    private String columnName;
    private DataPersister dataPersister;
    private DataType dataType = DEFAULT_DATA_TYPE;
    private String defaultValue;
    private String fieldName;
    private boolean foreign;
    private boolean foreignAutoCreate;
    private boolean foreignAutoRefresh;
    private boolean foreignCollection;
    private String foreignCollectionColumnName;
    private boolean foreignCollectionEager;
    private String foreignCollectionForeignFieldName;
    private int foreignCollectionMaxEagerLevel = 1;
    private boolean foreignCollectionOrderAscending = true;
    private String foreignCollectionOrderColumnName;
    private String foreignColumnName;
    private DatabaseTableConfig<?> foreignTableConfig;
    private String format;
    private boolean generatedId;
    private String generatedIdSequence;
    private boolean id;
    private boolean index;
    private String indexName;
    private int maxForeignAutoRefreshLevel = -1;
    private boolean persisted = true;
    private Class<? extends DataPersister> persisterClass = DEFAULT_PERSISTER_CLASS;
    private boolean readOnly;
    private boolean throwIfNull;
    private boolean unique;
    private boolean uniqueCombo;
    private boolean uniqueIndex;
    private String uniqueIndexName;
    private Enum<?> unknownEnumValue;
    private boolean useGetSet;
    private boolean version;
    private int width;

    static {
        DEFAULT_PERSISTER_CLASS = VoidType.class;
        DEFAULT_DATA_TYPE = DataType.UNKNOWN;
    }

    public DatabaseFieldConfig() {
    }

    public DatabaseFieldConfig(String string) {
        this.fieldName = string;
    }

    public DatabaseFieldConfig(String string, String string2, DataType dataType, String string3, int n, boolean bl, boolean bl2, boolean bl3, String string4, boolean bl4, DatabaseTableConfig<?> databaseTableConfig, boolean bl5, Enum<?> enum_, boolean bl6, String string5, boolean bl7, String string6, String string7, boolean bl8, int n2, int n3) {
        this.fieldName = string;
        this.columnName = string2;
        this.dataType = DataType.UNKNOWN;
        this.defaultValue = string3;
        this.width = n;
        this.canBeNull = bl;
        this.id = bl2;
        this.generatedId = bl3;
        this.generatedIdSequence = string4;
        this.foreign = bl4;
        this.foreignTableConfig = databaseTableConfig;
        this.useGetSet = bl5;
        this.unknownEnumValue = enum_;
        this.throwIfNull = bl6;
        this.format = string5;
        this.unique = bl7;
        this.indexName = string6;
        this.uniqueIndexName = string7;
        this.foreignAutoRefresh = bl8;
        this.maxForeignAutoRefreshLevel = n2;
        this.foreignCollectionMaxEagerLevel = n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Method findGetMethod(Field field, boolean bl) {
        Object object;
        block3 : {
            block4 : {
                String string = DatabaseFieldConfig.methodFromField(field, "get");
                try {
                    object = field.getDeclaringClass().getMethod(string, new Class[0]);
                    if (object.getReturnType() == field.getType()) break block3;
                    if (!bl) break block4;
                    object = new StringBuilder();
                    object.append("Return type of get method ");
                    object.append(string);
                    object.append(" does not return ");
                    object.append(field.getType());
                }
                catch (Exception exception) {}
                throw new IllegalArgumentException(object.toString());
            }
            return null;
        }
        return object;
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find appropriate get method for ");
            stringBuilder.append(field);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return null;
    }

    private String findIndexName(String string) {
        if (this.columnName == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("_");
            stringBuilder.append(this.fieldName);
            stringBuilder.append("_idx");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("_");
        stringBuilder.append(this.columnName);
        stringBuilder.append("_idx");
        return stringBuilder.toString();
    }

    public static Enum<?> findMatchingEnumVal(Field field, String string) {
        if (string != null && string.length() != 0) {
            for (Enum enum_ : (Enum[])field.getType().getEnumConstants()) {
                if (!enum_.name().equals(string)) continue;
                return enum_;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknwown enum unknown name ");
            stringBuilder.append(string);
            stringBuilder.append(" for field ");
            stringBuilder.append(field);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Method findSetMethod(Field object, boolean bl) {
        Method method;
        block3 : {
            block4 : {
                String string = DatabaseFieldConfig.methodFromField((Field)object, "set");
                try {
                    method = object.getDeclaringClass().getMethod(string, object.getType());
                    if (method.getReturnType() == Void.TYPE) break block3;
                    if (!bl) break block4;
                    object = new StringBuilder();
                    object.append("Return type of set method ");
                    object.append(string);
                    object.append(" returns ");
                    object.append(method.getReturnType());
                    object.append(" instead of void");
                }
                catch (Exception exception) {}
                throw new IllegalArgumentException(object.toString());
            }
            return null;
        }
        return method;
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find appropriate set method for ");
            stringBuilder.append(object);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return null;
    }

    public static DatabaseFieldConfig fromDatabaseField(DatabaseType object, String object2, Field field, DatabaseField databaseField) {
        object2 = new DatabaseFieldConfig();
        object2.fieldName = field.getName();
        if (object.isEntityNamesMustBeUpCase()) {
            object2.fieldName = object2.fieldName.toUpperCase();
        }
        object2.columnName = DatabaseFieldConfig.valueIfNotBlank(databaseField.columnName());
        object2.dataType = databaseField.dataType();
        object = databaseField.defaultValue();
        if (!object.equals("__ormlite__ no default value string was specified")) {
            object2.defaultValue = object;
        }
        object2.width = databaseField.width();
        object2.canBeNull = databaseField.canBeNull();
        object2.id = databaseField.id();
        object2.generatedId = databaseField.generatedId();
        object2.generatedIdSequence = DatabaseFieldConfig.valueIfNotBlank(databaseField.generatedIdSequence());
        object2.foreign = databaseField.foreign();
        object2.useGetSet = databaseField.useGetSet();
        object2.unknownEnumValue = DatabaseFieldConfig.findMatchingEnumVal(field, databaseField.unknownEnumName());
        object2.throwIfNull = databaseField.throwIfNull();
        object2.format = DatabaseFieldConfig.valueIfNotBlank(databaseField.format());
        object2.unique = databaseField.unique();
        object2.uniqueCombo = databaseField.uniqueCombo();
        object2.index = databaseField.index();
        object2.indexName = DatabaseFieldConfig.valueIfNotBlank(databaseField.indexName());
        object2.uniqueIndex = databaseField.uniqueIndex();
        object2.uniqueIndexName = DatabaseFieldConfig.valueIfNotBlank(databaseField.uniqueIndexName());
        object2.foreignAutoRefresh = databaseField.foreignAutoRefresh();
        object2.maxForeignAutoRefreshLevel = databaseField.maxForeignAutoRefreshLevel();
        object2.persisterClass = databaseField.persisterClass();
        object2.allowGeneratedIdInsert = databaseField.allowGeneratedIdInsert();
        object2.columnDefinition = DatabaseFieldConfig.valueIfNotBlank(databaseField.columnDefinition());
        object2.foreignAutoCreate = databaseField.foreignAutoCreate();
        object2.version = databaseField.version();
        object2.foreignColumnName = DatabaseFieldConfig.valueIfNotBlank(databaseField.foreignColumnName());
        object2.readOnly = databaseField.readOnly();
        return object2;
    }

    public static DatabaseFieldConfig fromField(DatabaseType databaseType, String object, Field field) throws SQLException {
        DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
        if (databaseField != null) {
            if (databaseField.persisted()) {
                return DatabaseFieldConfig.fromDatabaseField(databaseType, (String)object, field, databaseField);
            }
            return null;
        }
        object = field.getAnnotation(ForeignCollectionField.class);
        if (object != null) {
            return DatabaseFieldConfig.fromForeignCollection(databaseType, field, (ForeignCollectionField)object);
        }
        return JavaxPersistence.createFieldConfig(databaseType, field);
    }

    private static DatabaseFieldConfig fromForeignCollection(DatabaseType object, Field object2, ForeignCollectionField foreignCollectionField) {
        object = new DatabaseFieldConfig();
        object.fieldName = object2.getName();
        if (foreignCollectionField.columnName().length() > 0) {
            object.columnName = foreignCollectionField.columnName();
        }
        object.foreignCollection = true;
        object.foreignCollectionEager = foreignCollectionField.eager();
        int n = foreignCollectionField.maxEagerForeignCollectionLevel();
        object.foreignCollectionMaxEagerLevel = n != 1 ? n : foreignCollectionField.maxEagerLevel();
        object.foreignCollectionOrderColumnName = DatabaseFieldConfig.valueIfNotBlank(foreignCollectionField.orderColumnName());
        object.foreignCollectionOrderAscending = foreignCollectionField.orderAscending();
        object.foreignCollectionColumnName = DatabaseFieldConfig.valueIfNotBlank(foreignCollectionField.columnName());
        object2 = DatabaseFieldConfig.valueIfNotBlank(foreignCollectionField.foreignFieldName());
        if (object2 == null) {
            object.foreignCollectionForeignFieldName = DatabaseFieldConfig.valueIfNotBlank(DatabaseFieldConfig.valueIfNotBlank(foreignCollectionField.foreignColumnName()));
            return object;
        }
        object.foreignCollectionForeignFieldName = object2;
        return object;
    }

    private static String methodFromField(Field field, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(field.getName().substring(0, 1).toUpperCase());
        stringBuilder.append(field.getName().substring(1));
        return stringBuilder.toString();
    }

    private static String valueIfNotBlank(String string) {
        if (string != null && string.length() != 0) {
            return string;
        }
        return null;
    }

    public String getColumnDefinition() {
        return this.columnDefinition;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public DataPersister getDataPersister() {
        if (this.dataPersister == null) {
            return this.dataType.getDataPersister();
        }
        return this.dataPersister;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getForeignCollectionColumnName() {
        return this.foreignCollectionColumnName;
    }

    public String getForeignCollectionForeignFieldName() {
        return this.foreignCollectionForeignFieldName;
    }

    public int getForeignCollectionMaxEagerLevel() {
        return this.foreignCollectionMaxEagerLevel;
    }

    public String getForeignCollectionOrderColumnName() {
        return this.foreignCollectionOrderColumnName;
    }

    public String getForeignColumnName() {
        return this.foreignColumnName;
    }

    public DatabaseTableConfig<?> getForeignTableConfig() {
        return this.foreignTableConfig;
    }

    public String getFormat() {
        return this.format;
    }

    public String getGeneratedIdSequence() {
        return this.generatedIdSequence;
    }

    public String getIndexName(String string) {
        if (this.index && this.indexName == null) {
            this.indexName = this.findIndexName(string);
        }
        return this.indexName;
    }

    public int getMaxForeignAutoRefreshLevel() {
        return this.maxForeignAutoRefreshLevel;
    }

    public Class<? extends DataPersister> getPersisterClass() {
        return this.persisterClass;
    }

    public String getUniqueIndexName(String string) {
        if (this.uniqueIndex && this.uniqueIndexName == null) {
            this.uniqueIndexName = this.findIndexName(string);
        }
        return this.uniqueIndexName;
    }

    public Enum<?> getUnknownEnumValue() {
        return this.unknownEnumValue;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isAllowGeneratedIdInsert() {
        return this.allowGeneratedIdInsert;
    }

    public boolean isCanBeNull() {
        return this.canBeNull;
    }

    public boolean isForeign() {
        return this.foreign;
    }

    public boolean isForeignAutoCreate() {
        return this.foreignAutoCreate;
    }

    public boolean isForeignAutoRefresh() {
        return this.foreignAutoRefresh;
    }

    public boolean isForeignCollection() {
        return this.foreignCollection;
    }

    public boolean isForeignCollectionEager() {
        return this.foreignCollectionEager;
    }

    public boolean isForeignCollectionOrderAscending() {
        return this.foreignCollectionOrderAscending;
    }

    public boolean isGeneratedId() {
        return this.generatedId;
    }

    public boolean isId() {
        return this.id;
    }

    public boolean isIndex() {
        return this.index;
    }

    public boolean isPersisted() {
        return this.persisted;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public boolean isThrowIfNull() {
        return this.throwIfNull;
    }

    public boolean isUnique() {
        return this.unique;
    }

    public boolean isUniqueCombo() {
        return this.uniqueCombo;
    }

    public boolean isUniqueIndex() {
        return this.uniqueIndex;
    }

    public boolean isUseGetSet() {
        return this.useGetSet;
    }

    public boolean isVersion() {
        return this.version;
    }

    public void postProcess() {
        if (this.foreignColumnName != null) {
            this.foreignAutoRefresh = true;
        }
        if (this.foreignAutoRefresh && this.maxForeignAutoRefreshLevel == -1) {
            this.maxForeignAutoRefreshLevel = 2;
        }
    }

    public void setAllowGeneratedIdInsert(boolean bl) {
        this.allowGeneratedIdInsert = bl;
    }

    public void setCanBeNull(boolean bl) {
        this.canBeNull = bl;
    }

    public void setColumnDefinition(String string) {
        this.columnDefinition = string;
    }

    public void setColumnName(String string) {
        this.columnName = string;
    }

    public void setDataPersister(DataPersister dataPersister) {
        this.dataPersister = dataPersister;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public void setDefaultValue(String string) {
        this.defaultValue = string;
    }

    public void setFieldName(String string) {
        this.fieldName = string;
    }

    public void setForeign(boolean bl) {
        this.foreign = bl;
    }

    public void setForeignAutoCreate(boolean bl) {
        this.foreignAutoCreate = bl;
    }

    public void setForeignAutoRefresh(boolean bl) {
        this.foreignAutoRefresh = bl;
    }

    public void setForeignCollection(boolean bl) {
        this.foreignCollection = bl;
    }

    public void setForeignCollectionColumnName(String string) {
        this.foreignCollectionColumnName = string;
    }

    public void setForeignCollectionEager(boolean bl) {
        this.foreignCollectionEager = bl;
    }

    @Deprecated
    public void setForeignCollectionForeignColumnName(String string) {
        this.foreignCollectionForeignFieldName = string;
    }

    public void setForeignCollectionForeignFieldName(String string) {
        this.foreignCollectionForeignFieldName = string;
    }

    @Deprecated
    public void setForeignCollectionMaxEagerForeignCollectionLevel(int n) {
        this.foreignCollectionMaxEagerLevel = n;
    }

    public void setForeignCollectionMaxEagerLevel(int n) {
        this.foreignCollectionMaxEagerLevel = n;
    }

    public void setForeignCollectionOrderAscending(boolean bl) {
        this.foreignCollectionOrderAscending = bl;
    }

    @Deprecated
    public void setForeignCollectionOrderColumn(String string) {
        this.foreignCollectionOrderColumnName = string;
    }

    public void setForeignCollectionOrderColumnName(String string) {
        this.foreignCollectionOrderColumnName = string;
    }

    public void setForeignColumnName(String string) {
        this.foreignColumnName = string;
    }

    public void setForeignTableConfig(DatabaseTableConfig<?> databaseTableConfig) {
        this.foreignTableConfig = databaseTableConfig;
    }

    public void setFormat(String string) {
        this.format = string;
    }

    public void setGeneratedId(boolean bl) {
        this.generatedId = bl;
    }

    public void setGeneratedIdSequence(String string) {
        this.generatedIdSequence = string;
    }

    public void setId(boolean bl) {
        this.id = bl;
    }

    public void setIndex(boolean bl) {
        this.index = bl;
    }

    public void setIndexName(String string) {
        this.indexName = string;
    }

    @Deprecated
    public void setMaxEagerForeignCollectionLevel(int n) {
        this.foreignCollectionMaxEagerLevel = n;
    }

    public void setMaxForeignAutoRefreshLevel(int n) {
        this.maxForeignAutoRefreshLevel = n;
    }

    public void setPersisted(boolean bl) {
        this.persisted = bl;
    }

    public void setPersisterClass(Class<? extends DataPersister> class_) {
        this.persisterClass = class_;
    }

    public void setReadOnly(boolean bl) {
        this.readOnly = bl;
    }

    public void setThrowIfNull(boolean bl) {
        this.throwIfNull = bl;
    }

    public void setUnique(boolean bl) {
        this.unique = bl;
    }

    public void setUniqueCombo(boolean bl) {
        this.uniqueCombo = bl;
    }

    public void setUniqueIndex(boolean bl) {
        this.uniqueIndex = bl;
    }

    public void setUniqueIndexName(String string) {
        this.uniqueIndexName = string;
    }

    public void setUnknownEnumValue(Enum<?> enum_) {
        this.unknownEnumValue = enum_;
    }

    public void setUseGetSet(boolean bl) {
        this.useGetSet = bl;
    }

    public void setVersion(boolean bl) {
        this.version = bl;
    }

    public void setWidth(int n) {
        this.width = n;
    }
}
