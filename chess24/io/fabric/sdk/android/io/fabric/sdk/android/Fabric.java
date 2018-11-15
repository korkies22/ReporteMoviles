/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 */
package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.FabricKitsFinder;
import io.fabric.sdk.android.InitializationCallback;
import io.fabric.sdk.android.InitializationTask;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitGroup;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.Onboarding;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fabric {
    static final boolean DEFAULT_DEBUGGABLE = false;
    static final Logger DEFAULT_LOGGER = new DefaultLogger();
    static final String ROOT_DIR = ".Fabric";
    public static final String TAG = "Fabric";
    static volatile Fabric singleton;
    private WeakReference<Activity> activity;
    private ActivityLifecycleManager activityLifecycleManager;
    private final Context context;
    final boolean debuggable;
    private final ExecutorService executorService;
    private final IdManager idManager;
    private final InitializationCallback<Fabric> initializationCallback;
    private AtomicBoolean initialized;
    private final InitializationCallback<?> kitInitializationCallback;
    private final Map<Class<? extends Kit>, Kit> kits;
    final Logger logger;
    private final Handler mainHandler;

    Fabric(Context context, Map<Class<? extends Kit>, Kit> map, PriorityThreadPoolExecutor priorityThreadPoolExecutor, Handler handler, Logger logger, boolean bl, InitializationCallback initializationCallback, IdManager idManager, Activity activity) {
        this.context = context;
        this.kits = map;
        this.executorService = priorityThreadPoolExecutor;
        this.mainHandler = handler;
        this.logger = logger;
        this.debuggable = bl;
        this.initializationCallback = initializationCallback;
        this.initialized = new AtomicBoolean(false);
        this.kitInitializationCallback = this.createKitInitializationCallback(map.size());
        this.idManager = idManager;
        this.setCurrentActivity(activity);
    }

    private static void addToKitMap(Map<Class<? extends Kit>, Kit> map, Collection<? extends Kit> object) {
        object = object.iterator();
        while (object.hasNext()) {
            Kit kit = (Kit)object.next();
            map.put(kit.getClass(), kit);
            if (!(kit instanceof KitGroup)) continue;
            Fabric.addToKitMap(map, ((KitGroup)((Object)kit)).getKits());
        }
    }

    private static Activity extractActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity)context;
        }
        return null;
    }

    public static <T extends Kit> T getKit(Class<T> class_) {
        return (T)Fabric.singleton().kits.get(class_);
    }

    private static Map<Class<? extends Kit>, Kit> getKitMap(Collection<? extends Kit> collection) {
        HashMap<Class<? extends Kit>, Kit> hashMap = new HashMap<Class<? extends Kit>, Kit>(collection.size());
        Fabric.addToKitMap(hashMap, collection);
        return hashMap;
    }

    public static Logger getLogger() {
        if (singleton == null) {
            return DEFAULT_LOGGER;
        }
        return Fabric.singleton.logger;
    }

    private void init() {
        this.activityLifecycleManager = new ActivityLifecycleManager(this.context);
        this.activityLifecycleManager.registerCallbacks(new ActivityLifecycleManager.Callbacks(){

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Fabric.this.setCurrentActivity(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }
        });
        this.initializeKits(this.context);
    }

    public static boolean isDebuggable() {
        if (singleton == null) {
            return false;
        }
        return Fabric.singleton.debuggable;
    }

    public static boolean isInitialized() {
        if (singleton != null && Fabric.singleton.initialized.get()) {
            return true;
        }
        return false;
    }

    private static void setFabric(Fabric fabric) {
        singleton = fabric;
        fabric.init();
    }

    static Fabric singleton() {
        if (singleton == null) {
            throw new IllegalStateException("Must Initialize Fabric before using singleton()");
        }
        return singleton;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static /* varargs */ Fabric with(Context context, Kit ... arrkit) {
        if (singleton != null) return singleton;
        synchronized (Fabric.class) {
            if (singleton != null) return singleton;
            Fabric.setFabric(new Builder(context).kits(arrkit).build());
            return singleton;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Fabric with(Fabric fabric) {
        if (singleton != null) return singleton;
        synchronized (Fabric.class) {
            if (singleton != null) return singleton;
            Fabric.setFabric(fabric);
            return singleton;
        }
    }

    void addAnnotatedDependencies(Map<Class<? extends Kit>, Kit> map, Kit kit) {
        Class<?>[] arrclass = kit.dependsOnAnnotation;
        if (arrclass != null) {
            for (Class<?> class_ : arrclass.value()) {
                if (class_.isInterface()) {
                    for (Kit kit2 : map.values()) {
                        if (!class_.isAssignableFrom(kit2.getClass())) continue;
                        kit.initializationTask.addDependency(kit2.initializationTask);
                    }
                    continue;
                }
                if (map.get(class_) == null) {
                    throw new UnmetDependencyException("Referenced Kit was null, does the kit exist?");
                }
                kit.initializationTask.addDependency(map.get(class_).initializationTask);
            }
        }
    }

    InitializationCallback<?> createKitInitializationCallback(final int n) {
        return new InitializationCallback(){
            final CountDownLatch kitInitializedLatch;
            {
                this.kitInitializedLatch = new CountDownLatch(n);
            }

            @Override
            public void failure(Exception exception) {
                Fabric.this.initializationCallback.failure(exception);
            }

            public void success(Object object) {
                this.kitInitializedLatch.countDown();
                if (this.kitInitializedLatch.getCount() == 0L) {
                    Fabric.this.initialized.set(true);
                    Fabric.this.initializationCallback.success(Fabric.this);
                }
            }
        };
    }

    public ActivityLifecycleManager getActivityLifecycleManager() {
        return this.activityLifecycleManager;
    }

    public String getAppIdentifier() {
        return this.idManager.getAppIdentifier();
    }

    public String getAppInstallIdentifier() {
        return this.idManager.getAppInstallIdentifier();
    }

    public Activity getCurrentActivity() {
        if (this.activity != null) {
            return (Activity)this.activity.get();
        }
        return null;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    public Collection<Kit> getKits() {
        return this.kits.values();
    }

    Future<Map<String, KitInfo>> getKitsFinderFuture(Context object) {
        object = new FabricKitsFinder(object.getPackageCodePath());
        return this.getExecutorService().submit(object);
    }

    public Handler getMainHandler() {
        return this.mainHandler;
    }

    public String getVersion() {
        return "1.4.2.22";
    }

    void initializeKits(Context object) {
        Object object2 = this.getKitsFinderFuture((Context)object);
        Object object3 = this.getKits();
        object2 = new Onboarding((Future<Map<String, KitInfo>>)object2, (Collection<Kit>)object3);
        object3 = new ArrayList<Kit>((Collection<Kit>)object3);
        Collections.sort(object3);
        object2.injectParameters((Context)object, this, InitializationCallback.EMPTY, this.idManager);
        Object object4 = object3.iterator();
        while (object4.hasNext()) {
            ((Kit)object4.next()).injectParameters((Context)object, this, this.kitInitializationCallback, this.idManager);
        }
        object2.initialize();
        if (Fabric.getLogger().isLoggable(TAG, 3)) {
            object = new StringBuilder("Initializing ");
            object.append(this.getIdentifier());
            object.append(" [Version: ");
            object.append(this.getVersion());
            object.append("], with the following kits:\n");
        } else {
            object = null;
        }
        object3 = object3.iterator();
        while (object3.hasNext()) {
            object4 = (Kit)object3.next();
            object4.initializationTask.addDependency(object2.initializationTask);
            this.addAnnotatedDependencies(this.kits, (Kit)object4);
            object4.initialize();
            if (object == null) continue;
            object.append(object4.getIdentifier());
            object.append(" [Version: ");
            object.append(object4.getVersion());
            object.append("]\n");
        }
        if (object != null) {
            Fabric.getLogger().d(TAG, object.toString());
        }
    }

    public Fabric setCurrentActivity(Activity activity) {
        this.activity = new WeakReference<Activity>(activity);
        return this;
    }

    public static class Builder {
        private String appIdentifier;
        private String appInstallIdentifier;
        private final Context context;
        private boolean debuggable;
        private Handler handler;
        private InitializationCallback<Fabric> initializationCallback;
        private Kit[] kits;
        private Logger logger;
        private PriorityThreadPoolExecutor threadPoolExecutor;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context;
        }

        public Builder appIdentifier(String string) {
            if (string == null) {
                throw new IllegalArgumentException("appIdentifier must not be null.");
            }
            if (this.appIdentifier != null) {
                throw new IllegalStateException("appIdentifier already set.");
            }
            this.appIdentifier = string;
            return this;
        }

        public Builder appInstallIdentifier(String string) {
            if (string == null) {
                throw new IllegalArgumentException("appInstallIdentifier must not be null.");
            }
            if (this.appInstallIdentifier != null) {
                throw new IllegalStateException("appInstallIdentifier already set.");
            }
            this.appInstallIdentifier = string;
            return this;
        }

        public Fabric build() {
            if (this.threadPoolExecutor == null) {
                this.threadPoolExecutor = PriorityThreadPoolExecutor.create();
            }
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
            if (this.logger == null) {
                this.logger = this.debuggable ? new DefaultLogger(3) : new DefaultLogger();
            }
            if (this.appIdentifier == null) {
                this.appIdentifier = this.context.getPackageName();
            }
            if (this.initializationCallback == null) {
                this.initializationCallback = InitializationCallback.EMPTY;
            }
            Map map = this.kits == null ? new HashMap() : Fabric.getKitMap(Arrays.asList(this.kits));
            Context context = this.context.getApplicationContext();
            IdManager idManager = new IdManager(context, this.appIdentifier, this.appInstallIdentifier, map.values());
            return new Fabric(context, map, this.threadPoolExecutor, this.handler, this.logger, this.debuggable, this.initializationCallback, idManager, Fabric.extractActivity(this.context));
        }

        public Builder debuggable(boolean bl) {
            this.debuggable = bl;
            return this;
        }

        @Deprecated
        public Builder executorService(ExecutorService executorService) {
            return this;
        }

        @Deprecated
        public Builder handler(Handler handler) {
            return this;
        }

        public Builder initializationCallback(InitializationCallback<Fabric> initializationCallback) {
            if (initializationCallback == null) {
                throw new IllegalArgumentException("initializationCallback must not be null.");
            }
            if (this.initializationCallback != null) {
                throw new IllegalStateException("initializationCallback already set.");
            }
            this.initializationCallback = initializationCallback;
            return this;
        }

        public /* varargs */ Builder kits(Kit ... arrkit) {
            if (this.kits != null) {
                throw new IllegalStateException("Kits already set.");
            }
            this.kits = arrkit;
            return this;
        }

        public Builder logger(Logger logger) {
            if (logger == null) {
                throw new IllegalArgumentException("Logger must not be null.");
            }
            if (this.logger != null) {
                throw new IllegalStateException("Logger already set.");
            }
            this.logger = logger;
            return this;
        }

        public Builder threadPoolExecutor(PriorityThreadPoolExecutor priorityThreadPoolExecutor) {
            if (priorityThreadPoolExecutor == null) {
                throw new IllegalArgumentException("PriorityThreadPoolExecutor must not be null.");
            }
            if (this.threadPoolExecutor != null) {
                throw new IllegalStateException("PriorityThreadPoolExecutor already set.");
            }
            this.threadPoolExecutor = priorityThreadPoolExecutor;
            return this;
        }
    }

}
