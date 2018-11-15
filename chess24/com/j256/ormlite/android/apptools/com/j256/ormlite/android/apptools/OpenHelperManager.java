/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package com.j256.ormlite.android.apptools;

import android.content.Context;
import android.content.res.Resources;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class OpenHelperManager {
    private static final String HELPER_CLASS_RESOURCE_NAME = "open_helper_classname";
    private static volatile OrmLiteSqliteOpenHelper helper;
    private static Class<? extends OrmLiteSqliteOpenHelper> helperClass;
    private static int instanceCount = 0;
    private static Logger logger;
    private static boolean wasClosed = false;

    static {
        logger = LoggerFactory.getLogger(OpenHelperManager.class);
    }

    private static OrmLiteSqliteOpenHelper constructHelper(Context object, Class<? extends OrmLiteSqliteOpenHelper> class_) {
        Object object2;
        try {
            object2 = class_.getConstructor(Context.class);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find public constructor that has a single (Context) argument for helper class ");
            stringBuilder.append(class_);
            throw new IllegalStateException(stringBuilder.toString(), exception);
        }
        try {
            object = object2.newInstance(object);
            return object;
        }
        catch (Exception exception) {
            object2 = new StringBuilder();
            object2.append("Could not construct instance of helper class ");
            object2.append(class_);
            throw new IllegalStateException(object2.toString(), exception);
        }
    }

    @Deprecated
    public static OrmLiteSqliteOpenHelper getHelper(Context object) {
        synchronized (OpenHelperManager.class) {
            if (helperClass == null) {
                if (object == null) {
                    throw new IllegalArgumentException("context argument is null");
                }
                OpenHelperManager.innerSetHelperClass(OpenHelperManager.lookupHelperClass(object.getApplicationContext(), object.getClass()));
            }
            object = OpenHelperManager.loadHelper((Context)object, helperClass);
            return object;
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
    public static <T extends OrmLiteSqliteOpenHelper> T getHelper(Context var0, Class<T> var1_2) {
        // MONITORENTER : com.j256.ormlite.android.apptools.OpenHelperManager.class
        if (var1_3 != null) ** GOTO lbl5
        throw new IllegalArgumentException("openHelperClass argument is null");
lbl5: // 1 sources:
        OpenHelperManager.innerSetHelperClass((Class<? extends OrmLiteSqliteOpenHelper>)var1_3);
        var0_1 = OpenHelperManager.loadHelper(var0 /* !! */ , var1_3);
        // MONITOREXIT : com.j256.ormlite.android.apptools.OpenHelperManager.class
        return var0_1;
    }

    private static void innerSetHelperClass(Class<? extends OrmLiteSqliteOpenHelper> class_) {
        if (class_ == null) {
            throw new IllegalStateException("Helper class was trying to be reset to null");
        }
        if (helperClass == null) {
            helperClass = class_;
            return;
        }
        if (helperClass != class_) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Helper class was ");
            stringBuilder.append(helperClass);
            stringBuilder.append(" but is trying to be reset to ");
            stringBuilder.append(class_);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    private static <T extends OrmLiteSqliteOpenHelper> T loadHelper(Context context, Class<T> class_) {
        if (helper == null) {
            if (wasClosed) {
                logger.info("helper was already closed and is being re-opened");
            }
            if (context == null) {
                throw new IllegalArgumentException("context argument is null");
            }
            helper = OpenHelperManager.constructHelper(context.getApplicationContext(), class_);
            logger.trace("zero instances, created helper {}", (Object)helper);
            BaseDaoImpl.clearAllInternalObjectCaches();
            DaoManager.clearDaoCache();
            instanceCount = 0;
        }
        logger.trace("returning helper {}, instance count = {} ", (Object)helper, (Object)(++instanceCount));
        return (T)((Object)helper);
    }

    private static Class<? extends OrmLiteSqliteOpenHelper> lookupHelperClass(Context object, Class<?> class_) {
        Object object2 = object.getResources();
        int n = object2.getIdentifier(HELPER_CLASS_RESOURCE_NAME, "string", object.getPackageName());
        if (n != 0) {
            object = object2.getString(n);
            try {
                class_ = Class.forName((String)object);
                return class_;
            }
            catch (Exception exception) {
                object2 = new StringBuilder();
                object2.append("Could not create helper instance for class ");
                object2.append((String)object);
                throw new IllegalStateException(object2.toString(), exception);
            }
        }
        for (object = class_; object != null; object = object.getSuperclass()) {
            object2 = object.getGenericSuperclass();
            if (object2 == null || !(object2 instanceof ParameterizedType) || (object2 = ((ParameterizedType)object2).getActualTypeArguments()) == null || ((Object)object2).length == 0) continue;
            for (Object object3 : object2) {
                if (!(object3 instanceof Class) || !OrmLiteSqliteOpenHelper.class.isAssignableFrom((Class<?>)(object3 = (Class)object3))) continue;
                return object3;
            }
        }
        object = new StringBuilder();
        object.append("Could not find OpenHelperClass because none of the generic parameters of class ");
        object.append(class_);
        object.append(" extends OrmLiteSqliteOpenHelper.  You should use getHelper(Context, Class) instead.");
        throw new IllegalStateException(object.toString());
    }

    @Deprecated
    public static void release() {
        OpenHelperManager.releaseHelper();
    }

    public static void releaseHelper() {
        synchronized (OpenHelperManager.class) {
            logger.trace("releasing helper {}, instance count = {}", (Object)helper, (Object)(--instanceCount));
            if (instanceCount <= 0) {
                if (helper != null) {
                    logger.trace("zero instances, closing helper {}", (Object)helper);
                    helper.close();
                    helper = null;
                    wasClosed = true;
                }
                if (instanceCount < 0) {
                    logger.error("too many calls to release helper, instance count = {}", instanceCount);
                }
            }
            return;
        }
    }

    public static void setHelper(OrmLiteSqliteOpenHelper ormLiteSqliteOpenHelper) {
        synchronized (OpenHelperManager.class) {
            helper = ormLiteSqliteOpenHelper;
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
    public static void setOpenHelperClass(Class<? extends OrmLiteSqliteOpenHelper> var0) {
        // MONITORENTER : com.j256.ormlite.android.apptools.OpenHelperManager.class
        if (var0 != null) ** GOTO lbl6
        OpenHelperManager.helperClass = null;
        return;
lbl6: // 1 sources:
        OpenHelperManager.innerSetHelperClass(var0);
        // MONITOREXIT : com.j256.ormlite.android.apptools.OpenHelperManager.class
        return;
    }
}
