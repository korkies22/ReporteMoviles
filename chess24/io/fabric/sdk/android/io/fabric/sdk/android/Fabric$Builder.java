/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 */
package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.InitializationCallback;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public static class Fabric.Builder {
    private String appIdentifier;
    private String appInstallIdentifier;
    private final Context context;
    private boolean debuggable;
    private Handler handler;
    private InitializationCallback<Fabric> initializationCallback;
    private Kit[] kits;
    private Logger logger;
    private PriorityThreadPoolExecutor threadPoolExecutor;

    public Fabric.Builder(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.context = context;
    }

    public Fabric.Builder appIdentifier(String string) {
        if (string == null) {
            throw new IllegalArgumentException("appIdentifier must not be null.");
        }
        if (this.appIdentifier != null) {
            throw new IllegalStateException("appIdentifier already set.");
        }
        this.appIdentifier = string;
        return this;
    }

    public Fabric.Builder appInstallIdentifier(String string) {
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

    public Fabric.Builder debuggable(boolean bl) {
        this.debuggable = bl;
        return this;
    }

    @Deprecated
    public Fabric.Builder executorService(ExecutorService executorService) {
        return this;
    }

    @Deprecated
    public Fabric.Builder handler(Handler handler) {
        return this;
    }

    public Fabric.Builder initializationCallback(InitializationCallback<Fabric> initializationCallback) {
        if (initializationCallback == null) {
            throw new IllegalArgumentException("initializationCallback must not be null.");
        }
        if (this.initializationCallback != null) {
            throw new IllegalStateException("initializationCallback already set.");
        }
        this.initializationCallback = initializationCallback;
        return this;
    }

    public /* varargs */ Fabric.Builder kits(Kit ... arrkit) {
        if (this.kits != null) {
            throw new IllegalStateException("Kits already set.");
        }
        this.kits = arrkit;
        return this;
    }

    public Fabric.Builder logger(Logger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger must not be null.");
        }
        if (this.logger != null) {
            throw new IllegalStateException("Logger already set.");
        }
        this.logger = logger;
        return this;
    }

    public Fabric.Builder threadPoolExecutor(PriorityThreadPoolExecutor priorityThreadPoolExecutor) {
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
