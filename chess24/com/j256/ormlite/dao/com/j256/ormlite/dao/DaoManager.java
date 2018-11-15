/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DaoManager {
    private static Map<ClassConnectionSource, Dao<?, ?>> classMap;
    private static Map<Class<?>, DatabaseTableConfig<?>> configMap;
    private static Logger logger;
    private static Map<TableConfigConnectionSource, Dao<?, ?>> tableConfigMap;

    static {
        logger = LoggerFactory.getLogger(DaoManager.class);
    }

    public static void addCachedDatabaseConfigs(Collection<DatabaseTableConfig<?>> object) {
        synchronized (DaoManager.class) {
            HashMap<Object, Object> hashMap = configMap == null ? new HashMap() : new HashMap(configMap);
            object = object.iterator();
            while (object.hasNext()) {
                DatabaseTableConfig databaseTableConfig = (DatabaseTableConfig)object.next();
                hashMap.put(databaseTableConfig.getDataClass(), databaseTableConfig);
                logger.info("Loaded configuration for {}", databaseTableConfig.getDataClass());
            }
            configMap = hashMap;
            return;
        }
    }

    private static void addDaoToClassMap(ClassConnectionSource classConnectionSource, Dao<?, ?> dao) {
        if (classMap == null) {
            classMap = new HashMap();
        }
        classMap.put(classConnectionSource, dao);
    }

    private static void addDaoToTableMap(TableConfigConnectionSource tableConfigConnectionSource, Dao<?, ?> dao) {
        if (tableConfigMap == null) {
            tableConfigMap = new HashMap();
        }
        tableConfigMap.put(tableConfigConnectionSource, dao);
    }

    public static void clearCache() {
        synchronized (DaoManager.class) {
            if (configMap != null) {
                configMap.clear();
                configMap = null;
            }
            DaoManager.clearDaoCache();
            return;
        }
    }

    public static void clearDaoCache() {
        synchronized (DaoManager.class) {
            if (classMap != null) {
                classMap.clear();
                classMap = null;
            }
            if (tableConfigMap != null) {
                tableConfigMap.clear();
                tableConfigMap = null;
            }
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static <D extends Dao<T, ?>, T> D createDao(ConnectionSource var0, DatabaseTableConfig<T> var1_2) throws SQLException {
        // MONITORENTER : com.j256.ormlite.dao.DaoManager.class
        if (var0 /* !! */  != null) ** GOTO lbl5
        throw new IllegalArgumentException("connectionSource argument cannot be null");
lbl5: // 1 sources:
        var0_1 = DaoManager.doCreateDao(var0 /* !! */ , var1_3);
        // MONITOREXIT : com.j256.ormlite.dao.DaoManager.class
        return var0_1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static <D extends Dao<T, ?>, T> D createDao(ConnectionSource var0, Class<T> var1_3) throws SQLException {
        block11 : {
            block10 : {
                // MONITORENTER : com.j256.ormlite.dao.DaoManager.class
                if (var0 != null) ** GOTO lbl5
                throw new IllegalArgumentException("connectionSource argument cannot be null");
lbl5: // 1 sources:
                var2_4 /* !! */  = DaoManager.lookupDao(new ClassConnectionSource((ConnectionSource)var0, (Class<?>)var1_3));
                if (var2_4 /* !! */  == null) break block10;
                // MONITOREXIT : com.j256.ormlite.dao.DaoManager.class
                return (D)var2_4 /* !! */ ;
            }
            var2_4 /* !! */  = (Dao)DaoManager.createDaoFromConfig((ConnectionSource)var0, var1_3);
            if (var2_4 /* !! */  == null) break block11;
            return (D)var2_4 /* !! */ ;
        }
        var2_4 /* !! */  = var1_3.getAnnotation(DatabaseTable.class);
        if (var2_4 /* !! */  == null || var2_4 /* !! */ .daoClass() == Void.class || var2_4 /* !! */ .daoClass() == BaseDaoImpl.class) ** GOTO lbl44
        var5_5 = var2_4 /* !! */ .daoClass();
        var2_4 /* !! */  = new Object[]{var0, var1_3};
        var3_7 = var4_6 = DaoManager.findConstructor(var5_5, var2_4 /* !! */ );
        if (var4_6 != null) ** GOTO lbl35
        var2_4 /* !! */  = new Object[]{var0};
        var3_7 = var4_6 = DaoManager.findConstructor(var5_5, var2_4 /* !! */ );
        if (var4_6 == null) {
            var0 = new StringBuilder();
            var0.append("Could not find public constructor with ConnectionSource and optional Class parameters ");
            var0.append(var5_5);
            var0.append(".  Missing static on class?");
            throw new SQLException(var0.toString());
        }
lbl35: // 3 sources:
        try {
            var2_4 /* !! */  = (Dao)var3_7.newInstance(var2_4 /* !! */ );
            DaoManager.logger.debug("created dao for class {} from constructor", var1_3);
        }
        catch (Exception var0_1) {
            var1_3 = new StringBuilder();
            var1_3.append("Could not call the constructor in class ");
            var1_3.append(var5_5);
            throw SqlExceptionUtil.create(var1_3.toString(), var0_1);
        }
lbl44: // 1 sources:
        var2_4 /* !! */  = var0.getDatabaseType().extractDatabaseTableConfig((ConnectionSource)var0, var1_3);
        var2_4 /* !! */  = var2_4 /* !! */  == null ? BaseDaoImpl.createDao((ConnectionSource)var0, var1_3) : BaseDaoImpl.createDao((ConnectionSource)var0, var2_4 /* !! */ );
        DaoManager.logger.debug("created dao for class {} with reflection", var1_3);
        DaoManager.registerDao((ConnectionSource)var0, var2_4 /* !! */ );
        // MONITOREXIT : com.j256.ormlite.dao.DaoManager.class
        return (D)var2_4 /* !! */ ;
    }

    private static <D, T> D createDaoFromConfig(ConnectionSource connectionSource, Class<T> object) throws SQLException {
        if (configMap == null) {
            return null;
        }
        if ((object = configMap.get(object)) == null) {
            return null;
        }
        return DaoManager.doCreateDao(connectionSource, object);
    }

    private static <D extends Dao<T, ?>, T> D doCreateDao(ConnectionSource constructor, DatabaseTableConfig<T> object) throws SQLException {
        TableConfigConnectionSource tableConfigConnectionSource = new TableConfigConnectionSource((ConnectionSource)((Object)constructor), (DatabaseTableConfig<?>)object);
        Object object2 = DaoManager.lookupDao(tableConfigConnectionSource);
        if (object2 != null) {
            return (D)object2;
        }
        object2 = object.getDataClass();
        ClassConnectionSource classConnectionSource = new ClassConnectionSource((ConnectionSource)((Object)constructor), (Class<?>)object2);
        Object object3 = DaoManager.lookupDao(classConnectionSource);
        if (object3 != null) {
            DaoManager.addDaoToTableMap(tableConfigConnectionSource, object3);
            return (D)object3;
        }
        object3 = object.getDataClass().getAnnotation(DatabaseTable.class);
        if (object3 != null && object3.daoClass() != Void.class && object3.daoClass() != BaseDaoImpl.class) {
            object3 = object3.daoClass();
            Object[] arrobject = new Object[]{constructor, object};
            constructor = DaoManager.findConstructor(object3, arrobject);
            if (constructor == null) {
                constructor = new StringBuilder();
                constructor.append("Could not find public constructor with ConnectionSource, DatabaseTableConfig parameters in class ");
                constructor.append(object3);
                throw new SQLException(((StringBuilder)((Object)constructor)).toString());
            }
            try {
                constructor = (Dao)constructor.newInstance(arrobject);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                object.append("Could not call the constructor in class ");
                object.append(object3);
                throw SqlExceptionUtil.create(object.toString(), exception);
            }
        } else {
            constructor = BaseDaoImpl.createDao((ConnectionSource)((Object)constructor), object);
        }
        DaoManager.addDaoToTableMap(tableConfigConnectionSource, constructor);
        logger.debug("created dao for class {} from table config", object2);
        if (DaoManager.lookupDao(classConnectionSource) == null) {
            DaoManager.addDaoToClassMap(classConnectionSource, constructor);
        }
        return (D)constructor;
    }

    private static Constructor<?> findConstructor(Class<?> arrconstructor, Object[] arrobject) {
        for (Constructor<?> constructor : arrconstructor.getConstructors()) {
            int n;
            block2 : {
                Class<?>[] arrclass = constructor.getParameterTypes();
                if (arrclass.length != arrobject.length) continue;
                for (n = 0; n < arrclass.length; ++n) {
                    if (arrclass[n].isAssignableFrom(arrobject[n].getClass())) continue;
                    n = 0;
                    break block2;
                }
                n = 1;
            }
            if (n == 0) continue;
            return constructor;
        }
        return null;
    }

    private static <T> Dao<?, ?> lookupDao(ClassConnectionSource object) {
        if (classMap == null) {
            classMap = new HashMap();
        }
        if ((object = classMap.get(object)) == null) {
            return null;
        }
        return object;
    }

    private static <T> Dao<?, ?> lookupDao(TableConfigConnectionSource object) {
        if (tableConfigMap == null) {
            tableConfigMap = new HashMap();
        }
        if ((object = tableConfigMap.get(object)) == null) {
            return null;
        }
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static <D extends Dao<T, ?>, T> D lookupDao(ConnectionSource var0, DatabaseTableConfig<T> var1_2) {
        block3 : {
            // MONITORENTER : com.j256.ormlite.dao.DaoManager.class
            if (var0 != null) ** GOTO lbl5
            throw new IllegalArgumentException("connectionSource argument cannot be null");
lbl5: // 1 sources:
            if ((var0 = DaoManager.lookupDao(new TableConfigConnectionSource((ConnectionSource)var0, var1_2))) != null) break block3;
            // MONITOREXIT : com.j256.ormlite.dao.DaoManager.class
            return null;
        }
        // MONITOREXIT : com.j256.ormlite.dao.DaoManager.class
        return (D)var0;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static <D extends Dao<T, ?>, T> D lookupDao(ConnectionSource var0, Class<T> var1_2) {
        // MONITORENTER : com.j256.ormlite.dao.DaoManager.class
        if (var0 != null) ** GOTO lbl5
        throw new IllegalArgumentException("connectionSource argument cannot be null");
lbl5: // 1 sources:
        var0 = DaoManager.lookupDao(new ClassConnectionSource((ConnectionSource)var0, var1_2));
        // MONITOREXIT : com.j256.ormlite.dao.DaoManager.class
        return (D)var0;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static void registerDao(ConnectionSource var0, Dao<?, ?> var1_2) {
        // MONITORENTER : com.j256.ormlite.dao.DaoManager.class
        if (var0 != null) ** GOTO lbl5
        throw new IllegalArgumentException("connectionSource argument cannot be null");
lbl5: // 1 sources:
        DaoManager.addDaoToClassMap(new ClassConnectionSource(var0, var1_2.getDataClass()), var1_2);
        // MONITOREXIT : com.j256.ormlite.dao.DaoManager.class
        return;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static void registerDaoWithTableConfig(ConnectionSource var0, Dao<?, ?> var1_2) {
        block4 : {
            // MONITORENTER : com.j256.ormlite.dao.DaoManager.class
            if (var0 != null) ** GOTO lbl5
            throw new IllegalArgumentException("connectionSource argument cannot be null");
lbl5: // 1 sources:
            if (!(var1_2 instanceof BaseDaoImpl) || (var2_3 = ((BaseDaoImpl)var1_2).getTableConfig()) == null) break block4;
            DaoManager.addDaoToTableMap(new TableConfigConnectionSource(var0, var2_3), var1_2);
            // MONITOREXIT : com.j256.ormlite.dao.DaoManager.class
            return;
        }
        DaoManager.addDaoToClassMap(new ClassConnectionSource(var0, var1_2.getDataClass()), var1_2);
    }

    private static void removeDaoToClassMap(ClassConnectionSource classConnectionSource, Dao<?, ?> dao) {
        if (classMap != null) {
            classMap.remove(classConnectionSource);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static void unregisterDao(ConnectionSource var0, Dao<?, ?> var1_2) {
        // MONITORENTER : com.j256.ormlite.dao.DaoManager.class
        if (var0 != null) ** GOTO lbl5
        throw new IllegalArgumentException("connectionSource argument cannot be null");
lbl5: // 1 sources:
        DaoManager.removeDaoToClassMap(new ClassConnectionSource(var0, var1_2.getDataClass()), var1_2);
        // MONITOREXIT : com.j256.ormlite.dao.DaoManager.class
        return;
    }

    private static class ClassConnectionSource {
        Class<?> clazz;
        ConnectionSource connectionSource;

        public ClassConnectionSource(ConnectionSource connectionSource, Class<?> class_) {
            this.connectionSource = connectionSource;
            this.clazz = class_;
        }

        public boolean equals(Object object) {
            if (object != null) {
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                object = (ClassConnectionSource)object;
                if (!this.clazz.equals(object.clazz)) {
                    return false;
                }
                if (!this.connectionSource.equals(object.connectionSource)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            return 31 * (this.clazz.hashCode() + 31) + this.connectionSource.hashCode();
        }
    }

    private static class TableConfigConnectionSource {
        ConnectionSource connectionSource;
        DatabaseTableConfig<?> tableConfig;

        public TableConfigConnectionSource(ConnectionSource connectionSource, DatabaseTableConfig<?> databaseTableConfig) {
            this.connectionSource = connectionSource;
            this.tableConfig = databaseTableConfig;
        }

        public boolean equals(Object object) {
            if (object != null) {
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                object = (TableConfigConnectionSource)object;
                if (!this.tableConfig.equals(object.tableConfig)) {
                    return false;
                }
                if (!this.connectionSource.equals(object.connectionSource)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            return 31 * (this.tableConfig.hashCode() + 31) + this.connectionSource.hashCode();
        }
    }

}
