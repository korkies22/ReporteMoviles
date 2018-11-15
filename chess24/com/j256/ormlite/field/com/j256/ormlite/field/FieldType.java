/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.field;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.BaseForeignCollection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.EagerForeignCollection;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.LazyForeignCollection;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.DataPersisterManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.VoidType;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.mapped.MappedQueryForId;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableInfo;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class FieldType {
    private static boolean DEFAULT_VALUE_BOOLEAN = false;
    private static byte DEFAULT_VALUE_BYTE = 0;
    private static char DEFAULT_VALUE_CHAR = '\u0000';
    private static double DEFAULT_VALUE_DOUBLE = 0.0;
    private static float DEFAULT_VALUE_FLOAT = 0.0f;
    private static int DEFAULT_VALUE_INT = 0;
    private static long DEFAULT_VALUE_LONG = 0L;
    private static short DEFAULT_VALUE_SHORT = 0;
    public static final String FOREIGN_ID_FIELD_SUFFIX = "_id";
    private static final ThreadLocal<LevelCounters> threadLevelCounters = new ThreadLocal<LevelCounters>(){

        @Override
        protected LevelCounters initialValue() {
            return new LevelCounters();
        }
    };
    private final String columnName;
    private final ConnectionSource connectionSource;
    private DataPersister dataPersister;
    private Object dataTypeConfigObj;
    private Object defaultValue;
    private final Field field;
    private final DatabaseFieldConfig fieldConfig;
    private FieldConverter fieldConverter;
    private final Method fieldGetMethod;
    private final Method fieldSetMethod;
    private BaseDaoImpl<?, ?> foreignDao;
    private FieldType foreignFieldType;
    private FieldType foreignIdField;
    private TableInfo<?, ?> foreignTableInfo;
    private final String generatedIdSequence;
    private final boolean isGeneratedId;
    private final boolean isId;
    private MappedQueryForId<Object, Object> mappedQueryForId;
    private final Class<?> parentClass;
    private final String tableName;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public FieldType(ConnectionSource var1_1, String var2_8, Field var3_9, DatabaseFieldConfig var4_10, Class<?> var5_11) throws SQLException {
        super();
        this.connectionSource = var1_1;
        this.tableName = var2_8;
        var8_12 = var1_1.getDatabaseType();
        this.field = var3_9;
        this.parentClass = var5_11;
        var4_10.postProcess();
        var7_13 = var3_9.getType();
        if (var4_10.getDataPersister() == null) {
            var5_11 = var4_10.getPersisterClass();
            if (var5_11 != null && var5_11 != VoidType.class) {
                block46 : {
                    try {
                        var1_1 = var5_11.getDeclaredMethod("getSingleton", new Class[0]);
                    }
                    catch (Exception var1_5) {
                        var2_8 = new StringBuilder();
                        var2_8.append("Could not find getSingleton static method on class ");
                        var2_8.append(var5_11);
                        throw SqlExceptionUtil.create(var2_8.toString(), var1_5);
                    }
                    var1_1 = var1_1.invoke(null, new Object[0]);
                    if (var1_1 != null) break block46;
                    var1_1 = new StringBuilder();
                    var1_1.append("Static getSingleton method should not return null on class ");
                    var1_1.append(var5_11);
                    throw new SQLException(var1_1.toString());
                }
                try {
                    var1_1 = (DataPersister)var1_1;
                }
                catch (Exception var1_2) {
                    var2_8 = new StringBuilder();
                    var2_8.append("Could not cast result of static getSingleton method to DataPersister from class ");
                    var2_8.append(var5_11);
                    throw SqlExceptionUtil.create(var2_8.toString(), var1_2);
                }
                catch (Exception var1_3) {
                    var2_8 = new StringBuilder();
                    var2_8.append("Could not run getSingleton method on class ");
                    var2_8.append(var5_11);
                    throw SqlExceptionUtil.create(var2_8.toString(), var1_3);
                }
                catch (InvocationTargetException var1_4) {
                    var2_8 = new StringBuilder();
                    var2_8.append("Could not run getSingleton method on class ");
                    var2_8.append(var5_11);
                    throw SqlExceptionUtil.create(var2_8.toString(), var1_4.getTargetException());
                }
            }
            var1_1 = DataPersisterManager.lookupForField((Field)var3_9);
        } else {
            var1_1 = var5_11 = var4_10.getDataPersister();
            if (!var5_11.isValidForField((Field)var3_9)) {
                var1_1 = new StringBuilder();
                var1_1.append("Field class ");
                var1_1.append(var7_13.getName());
                var1_1.append(" for field ");
                var1_1.append(this);
                var1_1.append(" is not valid for type ");
                var1_1.append(var5_11);
                var2_8 = var5_11.getPrimaryClass();
                if (var2_8 == null) throw new IllegalArgumentException(var1_1.toString());
                var3_9 = new StringBuilder();
                var3_9.append(", maybe should be ");
                var3_9.append(var2_8);
                var1_1.append(var3_9.toString());
                throw new IllegalArgumentException(var1_1.toString());
            }
        }
        var5_11 = var4_10.getForeignColumnName();
        var6_15 = var3_9.getName();
        if (!var4_10.isForeign() && !var4_10.isForeignAutoRefresh() && var5_11 == null) {
            if (var4_10.isForeignCollection()) {
                if (var7_13 != Collection.class && !ForeignCollection.class.isAssignableFrom(var7_13)) {
                    var1_1 = new StringBuilder();
                    var1_1.append("Field class for '");
                    var1_1.append(var3_9.getName());
                    var1_1.append("' must be of class ");
                    var1_1.append(ForeignCollection.class.getSimpleName());
                    var1_1.append(" or Collection.");
                    throw new SQLException(var1_1.toString());
                }
                var7_14 = var3_9.getGenericType();
                if (!(var7_14 instanceof ParameterizedType)) {
                    var1_1 = new StringBuilder();
                    var1_1.append("Field class for '");
                    var1_1.append(var3_9.getName());
                    var1_1.append("' must be a parameterized Collection.");
                    throw new SQLException(var1_1.toString());
                }
                var5_11 = var6_15;
                if (((ParameterizedType)var7_14).getActualTypeArguments().length == 0) {
                    var1_1 = new StringBuilder();
                    var1_1.append("Field class for '");
                    var1_1.append(var3_9.getName());
                    var1_1.append("' must be a parameterized Collection with at least 1 type.");
                    throw new SQLException(var1_1.toString());
                }
            } else {
                var5_11 = var6_15;
                if (var1_1 == null) {
                    var5_11 = var6_15;
                    if (!var4_10.isForeignCollection()) {
                        if (byte[].class.isAssignableFrom(var7_13)) {
                            var1_1 = new StringBuilder();
                            var1_1.append("ORMLite does not know how to store ");
                            var1_1.append(var7_13);
                            var1_1.append(" for field '");
                            var1_1.append(var3_9.getName());
                            var1_1.append("'. byte[] fields must specify dataType=DataType.BYTE_ARRAY or SERIALIZABLE");
                            throw new SQLException(var1_1.toString());
                        }
                        if (Serializable.class.isAssignableFrom(var7_13)) {
                            var1_1 = new StringBuilder();
                            var1_1.append("ORMLite does not know how to store ");
                            var1_1.append(var7_13);
                            var1_1.append(" for field '");
                            var1_1.append(var3_9.getName());
                            var1_1.append("'.  Use another class, custom persister, or to serialize it use ");
                            var1_1.append("dataType=DataType.SERIALIZABLE");
                            throw new SQLException(var1_1.toString());
                        }
                        var1_1 = new StringBuilder();
                        var1_1.append("ORMLite does not know how to store ");
                        var1_1.append(var7_13);
                        var1_1.append(" for field ");
                        var1_1.append(var3_9.getName());
                        var1_1.append(". Use another class or a custom persister.");
                        throw new IllegalArgumentException(var1_1.toString());
                    }
                }
            }
        } else {
            if (var1_1 != null && var1_1.isPrimitive()) {
                var1_1 = new StringBuilder();
                var1_1.append("Field ");
                var1_1.append(this);
                var1_1.append(" is a primitive class ");
                var1_1.append(var7_13);
                var1_1.append(" but marked as foreign");
                throw new IllegalArgumentException(var1_1.toString());
            }
            if (var5_11 == null) {
                var5_11 = new StringBuilder();
                var5_11.append(var6_15);
                var5_11.append("_id");
                var5_11 = var5_11.toString();
            } else {
                var9_16 = new StringBuilder();
                var9_16.append(var6_15);
                var9_16.append("_");
                var9_16.append((String)var5_11);
                var5_11 = var9_16.toString();
            }
            if (ForeignCollection.class.isAssignableFrom(var7_13)) {
                var1_1 = new StringBuilder();
                var1_1.append("Field '");
                var1_1.append(var3_9.getName());
                var1_1.append("' in class ");
                var1_1.append(var7_13);
                var1_1.append("' should use the @");
                var1_1.append(ForeignCollectionField.class.getSimpleName());
                var1_1.append(" annotation not foreign=true");
                throw new SQLException(var1_1.toString());
            }
        }
        this.columnName = var4_10.getColumnName() == null ? var5_11 : var4_10.getColumnName();
        this.fieldConfig = var4_10;
        if (!var4_10.isId()) ** GOTO lbl160
        if (!var4_10.isGeneratedId() && var4_10.getGeneratedIdSequence() == null) {
            this.isId = true;
            this.isGeneratedId = false;
            this.generatedIdSequence = null;
        } else {
            var1_1 = new StringBuilder();
            var1_1.append("Must specify one of id, generatedId, and generatedIdSequence with ");
            var1_1.append(var3_9.getName());
            throw new IllegalArgumentException(var1_1.toString());
lbl160: // 1 sources:
            if (var4_10.isGeneratedId()) {
                if (var4_10.getGeneratedIdSequence() != null) {
                    var1_1 = new StringBuilder();
                    var1_1.append("Must specify one of id, generatedId, and generatedIdSequence with ");
                    var1_1.append(var3_9.getName());
                    throw new IllegalArgumentException(var1_1.toString());
                }
                this.isId = true;
                this.isGeneratedId = true;
                this.generatedIdSequence = var8_12.isIdSequenceNeeded() ? var8_12.generateIdSequenceName((String)var2_8, this) : null;
            } else if (var4_10.getGeneratedIdSequence() != null) {
                this.isId = true;
                this.isGeneratedId = true;
                var2_8 = var5_11 = var4_10.getGeneratedIdSequence();
                if (var8_12.isEntityNamesMustBeUpCase()) {
                    var2_8 = var5_11.toUpperCase();
                }
                this.generatedIdSequence = var2_8;
            } else {
                this.isId = false;
                this.isGeneratedId = false;
                this.generatedIdSequence = null;
            }
        }
        if (this.isId && (var4_10.isForeign() || var4_10.isForeignAutoRefresh())) {
            var1_1 = new StringBuilder();
            var1_1.append("Id field ");
            var1_1.append(var3_9.getName());
            var1_1.append(" cannot also be a foreign object");
            throw new IllegalArgumentException(var1_1.toString());
        }
        if (var4_10.isUseGetSet()) {
            this.fieldGetMethod = DatabaseFieldConfig.findGetMethod((Field)var3_9, true);
            this.fieldSetMethod = DatabaseFieldConfig.findSetMethod((Field)var3_9, true);
        } else {
            if (!var3_9.isAccessible()) {
                this.field.setAccessible(true);
            }
            this.fieldGetMethod = null;
            this.fieldSetMethod = null;
        }
        if (var4_10.isAllowGeneratedIdInsert() && !var4_10.isGeneratedId()) {
            var1_1 = new StringBuilder();
            var1_1.append("Field ");
            var1_1.append(var3_9.getName());
            var1_1.append(" must be a generated-id if allowGeneratedIdInsert = true");
            throw new IllegalArgumentException(var1_1.toString());
        }
        if (var4_10.isForeignAutoRefresh() && !var4_10.isForeign()) {
            var1_1 = new StringBuilder();
            var1_1.append("Field ");
            var1_1.append(var3_9.getName());
            var1_1.append(" must have foreign = true if foreignAutoRefresh = true");
            throw new IllegalArgumentException(var1_1.toString());
        }
        if (var4_10.isForeignAutoCreate() && !var4_10.isForeign()) {
            var1_1 = new StringBuilder();
            var1_1.append("Field ");
            var1_1.append(var3_9.getName());
            var1_1.append(" must have foreign = true if foreignAutoCreate = true");
            throw new IllegalArgumentException(var1_1.toString());
        }
        if (var4_10.getForeignColumnName() != null && !var4_10.isForeign()) {
            var1_1 = new StringBuilder();
            var1_1.append("Field ");
            var1_1.append(var3_9.getName());
            var1_1.append(" must have foreign = true if foreignColumnName is set");
            throw new IllegalArgumentException(var1_1.toString());
        }
        if (var4_10.isVersion() && (var1_1 == null || !var1_1.isValidForVersion())) {
            var1_1 = new StringBuilder();
            var1_1.append("Field ");
            var1_1.append(var3_9.getName());
            var1_1.append(" is not a valid type to be a version field");
            throw new IllegalArgumentException(var1_1.toString());
        }
        if (var4_10.getMaxForeignAutoRefreshLevel() > 0 && !var4_10.isForeignAutoRefresh()) {
            var1_1 = new StringBuilder();
            var1_1.append("Field ");
            var1_1.append(var3_9.getName());
            var1_1.append(" has maxForeignAutoRefreshLevel set but not foreignAutoRefresh is false");
            throw new IllegalArgumentException(var1_1.toString());
        }
        this.assignDataType(var8_12, (DataPersister)var1_1);
        return;
        catch (SecurityException var1_6) {}
        var1_7 = new StringBuilder();
        var1_7.append("Could not open access to field ");
        var1_7.append(var3_9.getName());
        var1_7.append(".  You may have to set useGetSet=true to fix.");
        throw new IllegalArgumentException(var1_7.toString());
    }

    private void assignDataType(DatabaseType object, DataPersister arrdataType) throws SQLException {
        this.dataPersister = arrdataType;
        if (arrdataType == null) {
            if (!this.fieldConfig.isForeign() && !this.fieldConfig.isForeignCollection()) {
                object = new StringBuilder();
                object.append("Data persister for field ");
                object.append(this);
                object.append(" is null but the field is not a foreign or foreignCollection");
                throw new SQLException(object.toString());
            }
            return;
        }
        this.fieldConverter = object.getFieldConverter((DataPersister)arrdataType);
        if (this.isGeneratedId && !arrdataType.isValidGeneratedType()) {
            object = new StringBuilder();
            object.append("Generated-id field '");
            object.append(this.field.getName());
            object.append("' in ");
            object.append(this.field.getDeclaringClass().getSimpleName());
            object.append(" can't be type ");
            object.append((Object)this.dataPersister.getSqlType());
            object.append(".  Must be one of: ");
            for (DataType dataType : DataType.values()) {
                DataPersister dataPersister = dataType.getDataPersister();
                if (dataPersister == null || !dataPersister.isValidGeneratedType()) continue;
                object.append((Object)dataType);
                object.append(' ');
            }
            throw new IllegalArgumentException(object.toString());
        }
        if (this.fieldConfig.isThrowIfNull() && !arrdataType.isPrimitive()) {
            object = new StringBuilder();
            object.append("Field ");
            object.append(this.field.getName());
            object.append(" must be a primitive if set with throwIfNull");
            throw new SQLException(object.toString());
        }
        if (this.isId && !arrdataType.isAppropriateId()) {
            object = new StringBuilder();
            object.append("Field '");
            object.append(this.field.getName());
            object.append("' is of data type ");
            object.append(arrdataType);
            object.append(" which cannot be the ID field");
            throw new SQLException(object.toString());
        }
        this.dataTypeConfigObj = arrdataType.makeConfigObject(this);
        object = this.fieldConfig.getDefaultValue();
        if (object == null) {
            this.defaultValue = null;
            return;
        }
        if (this.isGeneratedId) {
            arrdataType = new StringBuilder();
            arrdataType.append("Field '");
            arrdataType.append(this.field.getName());
            arrdataType.append("' cannot be a generatedId and have a default value '");
            arrdataType.append((String)object);
            arrdataType.append("'");
            throw new SQLException(arrdataType.toString());
        }
        this.defaultValue = this.fieldConverter.parseDefaultString(this, (String)object);
    }

    public static FieldType createFieldType(ConnectionSource connectionSource, String string, Field field, Class<?> class_) throws SQLException {
        DatabaseFieldConfig databaseFieldConfig = DatabaseFieldConfig.fromField(connectionSource.getDatabaseType(), string, field);
        if (databaseFieldConfig == null) {
            return null;
        }
        return new FieldType(connectionSource, string, field, databaseFieldConfig, class_);
    }

    private FieldType findForeignFieldType(Class<?> class_, Class<?> class_2, BaseDaoImpl<?, ?> object) throws SQLException {
        String string = this.fieldConfig.getForeignCollectionForeignFieldName();
        object = object.getTableInfo().getFieldTypes();
        int n = ((FieldType[])object).length;
        for (int i = 0; i < n; ++i) {
            FieldType fieldType = object[i];
            if (fieldType.getType() != class_2 || string != null && !fieldType.getField().getName().equals(string)) continue;
            if (!fieldType.fieldConfig.isForeign() && !fieldType.fieldConfig.isForeignAutoRefresh()) {
                object = new StringBuilder();
                object.append("Foreign collection object ");
                object.append(class_);
                object.append(" for field '");
                object.append(this.field.getName());
                object.append("' contains a field of class ");
                object.append(class_2);
                object.append(" but it's not foreign");
                throw new SQLException(object.toString());
            }
            return fieldType;
        }
        object = new StringBuilder();
        object.append("Foreign collection class ");
        object.append(class_.getName());
        object.append(" for field '");
        object.append(this.field.getName());
        object.append("' column-name does not contain a foreign field");
        if (string != null) {
            object.append(" named '");
            object.append(string);
            object.append('\'');
        }
        object.append(" of class ");
        object.append(class_2.getName());
        throw new SQLException(object.toString());
    }

    private boolean isFieldValueDefault(Object object) {
        if (object == null) {
            return true;
        }
        return object.equals(this.getJavaDefaultValueDefault());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void assignField(Object object, Object object2, boolean bl, ObjectCache objectCache) throws SQLException {
        Object object3 = object2;
        if (this.foreignIdField != null) {
            object3 = object2;
            if (object2 != null) {
                object3 = this.extractJavaFieldValue(object);
                if (object3 != null && object3.equals(object2)) {
                    return;
                }
                object3 = this.foreignDao.getObjectCache();
                object3 = object3 == null ? null : object3.get(this.getType(), object2);
                if (object3 == null) {
                    object3 = object2;
                    if (!bl) {
                        LevelCounters levelCounters = threadLevelCounters.get();
                        if (levelCounters.autoRefreshLevel == 0) {
                            levelCounters.autoRefreshLevelMax = this.fieldConfig.getMaxForeignAutoRefreshLevel();
                        }
                        if (levelCounters.autoRefreshLevel >= levelCounters.autoRefreshLevelMax) {
                            object3 = this.foreignTableInfo.createObject();
                            this.foreignIdField.assignField(object3, object2, false, objectCache);
                        } else {
                            if (this.mappedQueryForId == null) {
                                this.mappedQueryForId = MappedQueryForId.build(this.connectionSource.getDatabaseType(), this.foreignDao.getTableInfo(), this.foreignIdField);
                            }
                            ++levelCounters.autoRefreshLevel;
                            object3 = this.connectionSource.getReadOnlyConnection();
                            {
                                catch (Throwable throwable2) {
                                    --levelCounters.autoRefreshLevel;
                                    if (levelCounters.autoRefreshLevel > 0) throw throwable2;
                                    threadLevelCounters.remove();
                                    throw throwable2;
                                }
                            }
                            object2 = this.mappedQueryForId.execute((DatabaseConnection)object3, object2, objectCache);
                            this.connectionSource.releaseConnection((DatabaseConnection)object3);
                            --levelCounters.autoRefreshLevel;
                            object3 = object2;
                            if (levelCounters.autoRefreshLevel <= 0) {
                                threadLevelCounters.remove();
                                object3 = object2;
                            }
                            catch (Throwable throwable) {
                                this.connectionSource.releaseConnection((DatabaseConnection)object3);
                                throw throwable;
                            }
                        }
                    }
                }
            }
        }
        if (this.fieldSetMethod == null) {
            try {
                this.field.set(object, object3);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                object2 = new StringBuilder();
                object2.append("Could not assign object '");
                object2.append(object3);
                object2.append("' to field ");
                object2.append(this);
                throw SqlExceptionUtil.create(object2.toString(), illegalAccessException);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                object2 = new StringBuilder();
                object2.append("Could not assign object '");
                object2.append(object3);
                object2.append("' to field ");
                object2.append(this);
                throw SqlExceptionUtil.create(object2.toString(), illegalArgumentException);
            }
        }
        try {
            this.fieldSetMethod.invoke(object, object3);
            return;
        }
        catch (Exception exception) {
            object2 = new StringBuilder();
            object2.append("Could not call ");
            object2.append(this.fieldSetMethod);
            object2.append(" on object with '");
            object2.append(object3);
            object2.append("' for ");
            object2.append(this);
            throw SqlExceptionUtil.create(object2.toString(), exception);
        }
    }

    public Object assignIdValue(Object object, Number object2, ObjectCache objectCache) throws SQLException {
        if ((object2 = this.dataPersister.convertIdNumber((Number)object2)) == null) {
            object = new StringBuilder();
            object.append("Invalid class ");
            object.append(this.dataPersister);
            object.append(" for sequence-id ");
            object.append(this);
            throw new SQLException(object.toString());
        }
        this.assignField(object, object2, false, objectCache);
        return object2;
    }

    public <FT, FID> BaseForeignCollection<FT, FID> buildForeignCollection(Object eagerForeignCollection, FID FID) throws SQLException {
        if (this.foreignFieldType == null) {
            return null;
        }
        BaseDaoImpl<?, ?> baseDaoImpl = this.foreignDao;
        if (!this.fieldConfig.isForeignCollectionEager()) {
            return new LazyForeignCollection(baseDaoImpl, eagerForeignCollection, FID, this.foreignFieldType, this.fieldConfig.getForeignCollectionOrderColumnName(), this.fieldConfig.isForeignCollectionOrderAscending());
        }
        LevelCounters levelCounters = threadLevelCounters.get();
        if (levelCounters.foreignCollectionLevel == 0) {
            levelCounters.foreignCollectionLevelMax = this.fieldConfig.getForeignCollectionMaxEagerLevel();
        }
        if (levelCounters.foreignCollectionLevel >= levelCounters.foreignCollectionLevelMax) {
            return new LazyForeignCollection(baseDaoImpl, eagerForeignCollection, FID, this.foreignFieldType, this.fieldConfig.getForeignCollectionOrderColumnName(), this.fieldConfig.isForeignCollectionOrderAscending());
        }
        ++levelCounters.foreignCollectionLevel;
        try {
            eagerForeignCollection = new EagerForeignCollection(baseDaoImpl, eagerForeignCollection, FID, this.foreignFieldType, this.fieldConfig.getForeignCollectionOrderColumnName(), this.fieldConfig.isForeignCollectionOrderAscending());
            return eagerForeignCollection;
        }
        finally {
            --levelCounters.foreignCollectionLevel;
        }
    }

    public void configDaoInformation(ConnectionSource object, Class<?> object2) throws SQLException {
        DatabaseTableConfig<?> databaseTableConfig;
        Class<?> class_ = this.field.getType();
        DatabaseType databaseType = object.getDatabaseType();
        Object object3 = this.fieldConfig.getForeignColumnName();
        boolean bl = this.fieldConfig.isForeignAutoRefresh();
        MappedQueryForId mappedQueryForId = null;
        if (!bl && object3 == null) {
            if (this.fieldConfig.isForeign()) {
                if (this.dataPersister != null && this.dataPersister.isPrimitive()) {
                    object = new StringBuilder();
                    object.append("Field ");
                    object.append(this);
                    object.append(" is a primitive class ");
                    object.append(class_);
                    object.append(" but marked as foreign");
                    throw new IllegalArgumentException(object.toString());
                }
                object2 = this.fieldConfig.getForeignTableConfig();
                if (object2 != null) {
                    object2.extractFieldTypes((ConnectionSource)object);
                    object = (BaseDaoImpl)DaoManager.createDao((ConnectionSource)object, object2);
                } else {
                    object = (BaseDaoImpl)DaoManager.createDao((ConnectionSource)object, class_);
                }
                databaseTableConfig = object.getTableInfo();
                object2 = databaseTableConfig.getIdField();
                if (object2 == null) {
                    object = new StringBuilder();
                    object.append("Foreign field ");
                    object.append(class_);
                    object.append(" does not have id field");
                    throw new IllegalArgumentException(object.toString());
                }
                if (this.isForeignAutoCreate() && !object2.isGeneratedId()) {
                    object = new StringBuilder();
                    object.append("Field ");
                    object.append(this.field.getName());
                    object.append(", if foreignAutoCreate = true then class ");
                    object.append(class_.getSimpleName());
                    object.append(" must have id field with generatedId = true");
                    throw new IllegalArgumentException(object.toString());
                }
                object3 = null;
            } else if (this.fieldConfig.isForeignCollection()) {
                if (class_ != Collection.class && !ForeignCollection.class.isAssignableFrom(class_)) {
                    object = new StringBuilder();
                    object.append("Field class for '");
                    object.append(this.field.getName());
                    object.append("' must be of class ");
                    object.append(ForeignCollection.class.getSimpleName());
                    object.append(" or Collection.");
                    throw new SQLException(object.toString());
                }
                object3 = this.field.getGenericType();
                if (!(object3 instanceof ParameterizedType)) {
                    object = new StringBuilder();
                    object.append("Field class for '");
                    object.append(this.field.getName());
                    object.append("' must be a parameterized Collection.");
                    throw new SQLException(object.toString());
                }
                if (((Type[])(object3 = ((ParameterizedType)object3).getActualTypeArguments())).length == 0) {
                    object = new StringBuilder();
                    object.append("Field class for '");
                    object.append(this.field.getName());
                    object.append("' must be a parameterized Collection with at least 1 type.");
                    throw new SQLException(object.toString());
                }
                if (!(object3[0] instanceof Class)) {
                    object = new StringBuilder();
                    object.append("Field class for '");
                    object.append(this.field.getName());
                    object.append("' must be a parameterized Collection whose generic argument is an entity class not: ");
                    object.append(object3[0]);
                    throw new SQLException(object.toString());
                }
                object3 = (Class)object3[0];
                databaseTableConfig = this.fieldConfig.getForeignTableConfig();
                object = databaseTableConfig == null ? (BaseDaoImpl)DaoManager.createDao((ConnectionSource)object, object3) : (BaseDaoImpl)DaoManager.createDao((ConnectionSource)object, databaseTableConfig);
                object3 = this.findForeignFieldType((Class<?>)object3, (Class<?>)object2, (BaseDaoImpl<?, ?>)object);
                object2 = databaseTableConfig = null;
            } else {
                object3 = null;
                object2 = object = (databaseTableConfig = object3);
            }
        } else {
            object2 = this.fieldConfig.getForeignTableConfig();
            if (object2 == null) {
                object = (BaseDaoImpl)DaoManager.createDao((ConnectionSource)object, class_);
                databaseTableConfig = object.getTableInfo();
            } else {
                object2.extractFieldTypes((ConnectionSource)object);
                object = (BaseDaoImpl)DaoManager.createDao((ConnectionSource)object, object2);
                databaseTableConfig = object.getTableInfo();
            }
            if (object3 == null) {
                object3 = databaseTableConfig.getIdField();
                object2 = object3;
                if (object3 == null) {
                    object = new StringBuilder();
                    object.append("Foreign field ");
                    object.append(class_);
                    object.append(" does not have id field");
                    throw new IllegalArgumentException(object.toString());
                }
            } else {
                object2 = databaseTableConfig.getFieldTypeByColumnName((String)object3);
                if (object2 == null) {
                    object = new StringBuilder();
                    object.append("Foreign field ");
                    object.append(class_);
                    object.append(" does not have field named '");
                    object.append((String)object3);
                    object.append("'");
                    throw new IllegalArgumentException(object.toString());
                }
            }
            mappedQueryForId = MappedQueryForId.build(databaseType, databaseTableConfig, (FieldType)object2);
            object3 = null;
        }
        this.mappedQueryForId = mappedQueryForId;
        this.foreignTableInfo = databaseTableConfig;
        this.foreignFieldType = object3;
        this.foreignDao = object;
        this.foreignIdField = object2;
        if (this.foreignIdField != null) {
            this.assignDataType(databaseType, this.foreignIdField.getDataPersister());
        }
    }

    public Object convertJavaFieldToSqlArgValue(Object object) throws SQLException {
        if (object == null) {
            return null;
        }
        return this.fieldConverter.javaToSqlArg(this, object);
    }

    public Object convertStringToJavaField(String string, int n) throws SQLException {
        if (string == null) {
            return null;
        }
        return this.fieldConverter.resultStringToJava(this, string, n);
    }

    public <T> int createWithForeignDao(T t) throws SQLException {
        return this.foreignDao.create(t);
    }

    public boolean equals(Object object) {
        block4 : {
            boolean bl;
            block5 : {
                block7 : {
                    boolean bl2;
                    block6 : {
                        bl2 = false;
                        if (object == null) break block4;
                        if (object.getClass() != this.getClass()) {
                            return false;
                        }
                        object = (FieldType)object;
                        bl = bl2;
                        if (!this.field.equals(object.field)) break block5;
                        if (this.parentClass != null) break block6;
                        bl = bl2;
                        if (object.parentClass != null) break block5;
                        break block7;
                    }
                    bl = bl2;
                    if (!this.parentClass.equals(object.parentClass)) break block5;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public Object extractJavaFieldToSqlArgValue(Object object) throws SQLException {
        return this.convertJavaFieldToSqlArgValue(this.extractJavaFieldValue(object));
    }

    public Object extractJavaFieldValue(Object object) throws SQLException {
        FV FV = this.extractRawJavaFieldValue(object);
        object = FV;
        if (this.foreignIdField != null) {
            object = FV;
            if (FV != null) {
                object = this.foreignIdField.extractRawJavaFieldValue(FV);
            }
        }
        return object;
    }

    public <FV> FV extractRawJavaFieldValue(Object object) throws SQLException {
        if (this.fieldGetMethod == null) {
            try {
                object = this.field.get(object);
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not get field value for ");
                stringBuilder.append(this);
                throw SqlExceptionUtil.create(stringBuilder.toString(), exception);
            }
            return (FV)object;
        }
        try {
            object = this.fieldGetMethod.invoke(object, new Object[0]);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not call ");
            stringBuilder.append(this.fieldGetMethod);
            stringBuilder.append(" for ");
            stringBuilder.append(this);
            throw SqlExceptionUtil.create(stringBuilder.toString(), exception);
        }
        return (FV)object;
    }

    public Object generateId() {
        return this.dataPersister.generateId();
    }

    public String getColumnDefinition() {
        return this.fieldConfig.getColumnDefinition();
    }

    public String getColumnName() {
        return this.columnName;
    }

    public DataPersister getDataPersister() {
        return this.dataPersister;
    }

    public Object getDataTypeConfigObj() {
        return this.dataTypeConfigObj;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public Field getField() {
        return this.field;
    }

    public String getFieldName() {
        return this.field.getName();
    }

    public <FV> FV getFieldValueIfNotDefault(Object object) throws SQLException {
        if (this.isFieldValueDefault(object = this.extractJavaFieldValue(object))) {
            return null;
        }
        return (FV)object;
    }

    public FieldType getForeignIdField() {
        return this.foreignIdField;
    }

    public String getFormat() {
        return this.fieldConfig.getFormat();
    }

    public String getGeneratedIdSequence() {
        return this.generatedIdSequence;
    }

    public String getIndexName() {
        return this.fieldConfig.getIndexName(this.tableName);
    }

    public Object getJavaDefaultValueDefault() {
        if (this.field.getType() == Boolean.TYPE) {
            return DEFAULT_VALUE_BOOLEAN;
        }
        if (this.field.getType() != Byte.TYPE && this.field.getType() != Byte.class) {
            if (this.field.getType() != Character.TYPE && this.field.getType() != Character.class) {
                if (this.field.getType() != Short.TYPE && this.field.getType() != Short.class) {
                    if (this.field.getType() != Integer.TYPE && this.field.getType() != Integer.class) {
                        if (this.field.getType() != Long.TYPE && this.field.getType() != Long.class) {
                            if (this.field.getType() != Float.TYPE && this.field.getType() != Float.class) {
                                if (this.field.getType() != Double.TYPE && this.field.getType() != Double.class) {
                                    return null;
                                }
                                return DEFAULT_VALUE_DOUBLE;
                            }
                            return Float.valueOf(DEFAULT_VALUE_FLOAT);
                        }
                        return DEFAULT_VALUE_LONG;
                    }
                    return DEFAULT_VALUE_INT;
                }
                return DEFAULT_VALUE_SHORT;
            }
            return Character.valueOf(DEFAULT_VALUE_CHAR);
        }
        return DEFAULT_VALUE_BYTE;
    }

    public SqlType getSqlType() {
        return this.fieldConverter.getSqlType();
    }

    public String getTableName() {
        return this.tableName;
    }

    public Class<?> getType() {
        return this.field.getType();
    }

    public String getUniqueIndexName() {
        return this.fieldConfig.getUniqueIndexName(this.tableName);
    }

    public Enum<?> getUnknownEnumVal() {
        return this.fieldConfig.getUnknownEnumValue();
    }

    public int getWidth() {
        return this.fieldConfig.getWidth();
    }

    public int hashCode() {
        return this.field.hashCode();
    }

    public boolean isAllowGeneratedIdInsert() {
        return this.fieldConfig.isAllowGeneratedIdInsert();
    }

    public boolean isArgumentHolderRequired() {
        return this.dataPersister.isArgumentHolderRequired();
    }

    public boolean isCanBeNull() {
        return this.fieldConfig.isCanBeNull();
    }

    public boolean isComparable() throws SQLException {
        if (this.fieldConfig.isForeignCollection()) {
            return false;
        }
        if (this.dataPersister == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Internal error.  Data-persister is not configured for field.  Please post _full_ exception with associated data objects to mailing list: ");
            stringBuilder.append(this);
            throw new SQLException(stringBuilder.toString());
        }
        return this.dataPersister.isComparable();
    }

    public boolean isEscapedDefaultValue() {
        return this.dataPersister.isEscapedDefaultValue();
    }

    public boolean isEscapedValue() {
        return this.dataPersister.isEscapedValue();
    }

    public boolean isForeign() {
        return this.fieldConfig.isForeign();
    }

    public boolean isForeignAutoCreate() {
        return this.fieldConfig.isForeignAutoCreate();
    }

    public boolean isForeignCollection() {
        return this.fieldConfig.isForeignCollection();
    }

    public boolean isGeneratedId() {
        return this.isGeneratedId;
    }

    public boolean isGeneratedIdSequence() {
        if (this.generatedIdSequence != null) {
            return true;
        }
        return false;
    }

    public boolean isId() {
        return this.isId;
    }

    public boolean isObjectsFieldValueDefault(Object object) throws SQLException {
        return this.isFieldValueDefault(this.extractJavaFieldValue(object));
    }

    public boolean isReadOnly() {
        return this.fieldConfig.isReadOnly();
    }

    public boolean isSelfGeneratedId() {
        return this.dataPersister.isSelfGeneratedId();
    }

    public boolean isUnique() {
        return this.fieldConfig.isUnique();
    }

    public boolean isUniqueCombo() {
        return this.fieldConfig.isUniqueCombo();
    }

    public boolean isVersion() {
        return this.fieldConfig.isVersion();
    }

    public Object moveToNextValue(Object object) {
        if (this.dataPersister == null) {
            return null;
        }
        return this.dataPersister.moveToNextValue(object);
    }

    public <T> T resultToJava(DatabaseResults object, Map<String, Integer> object2) throws SQLException {
        Integer n;
        Integer n2 = n = object2.get(this.columnName);
        if (n == null) {
            n2 = object.findColumn(this.columnName);
            object2.put((String)this.columnName, (Integer)n2);
        }
        object2 = this.fieldConverter.resultToJava(this, (DatabaseResults)object, n2);
        if (this.fieldConfig.isForeign()) {
            if (object.wasNull(n2)) {
                return null;
            }
        } else if (this.dataPersister.isPrimitive()) {
            if (this.fieldConfig.isThrowIfNull() && object.wasNull(n2)) {
                object = new StringBuilder();
                object.append("Results value for primitive field '");
                object.append(this.field.getName());
                object.append("' was an invalid null value");
                throw new SQLException(object.toString());
            }
        } else if (!this.fieldConverter.isStreamType() && object.wasNull(n2)) {
            return null;
        }
        return (T)object2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append(":name=");
        stringBuilder.append(this.field.getName());
        stringBuilder.append(",class=");
        stringBuilder.append(this.field.getDeclaringClass().getSimpleName());
        return stringBuilder.toString();
    }

    private static class LevelCounters {
        int autoRefreshLevel;
        int autoRefreshLevelMax;
        int foreignCollectionLevel;
        int foreignCollectionLevelMax;

        private LevelCounters() {
        }
    }

}
