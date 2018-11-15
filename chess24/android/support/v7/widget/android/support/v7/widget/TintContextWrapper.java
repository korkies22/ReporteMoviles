/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.TintResources;
import android.support.v7.widget.VectorEnabledTintResources;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class TintContextWrapper
extends ContextWrapper {
    private static final Object CACHE_LOCK = new Object();
    private static ArrayList<WeakReference<TintContextWrapper>> sCache;
    private final Resources mResources;
    private final Resources.Theme mTheme;

    private TintContextWrapper(@NonNull Context context) {
        super(context);
        if (VectorEnabledTintResources.shouldBeUsed()) {
            this.mResources = new VectorEnabledTintResources((Context)this, context.getResources());
            this.mTheme = this.mResources.newTheme();
            this.mTheme.setTo(context.getTheme());
            return;
        }
        this.mResources = new TintResources((Context)this, context.getResources());
        this.mTheme = null;
    }

    private static boolean shouldWrap(@NonNull Context context) {
        boolean bl = context instanceof TintContextWrapper;
        boolean bl2 = false;
        if (!bl && !(context.getResources() instanceof TintResources)) {
            if (context.getResources() instanceof VectorEnabledTintResources) {
                return false;
            }
            if (Build.VERSION.SDK_INT < 21 || VectorEnabledTintResources.shouldBeUsed()) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Context wrap(@NonNull Context object) {
        if (!TintContextWrapper.shouldWrap(object)) {
            return object;
        }
        Object object2 = CACHE_LOCK;
        synchronized (object2) {
            if (sCache == null) {
                sCache = new ArrayList();
            } else {
                Object object3;
                int n = sCache.size() - 1;
                do {
                    if (n < 0) break;
                    object3 = sCache.get(n);
                    if (object3 == null || object3.get() == null) {
                        sCache.remove(n);
                    }
                    --n;
                } while (true);
                for (n = TintContextWrapper.sCache.size() - 1; n >= 0; --n) {
                    object3 = sCache.get(n);
                    object3 = object3 != null ? (TintContextWrapper)((Object)object3.get()) : null;
                    if (object3 == null || object3.getBaseContext() != object) continue;
                    return object3;
                }
            }
            object = new TintContextWrapper((Context)object);
            sCache.add(new WeakReference<Context>((Context)object));
            return object;
        }
    }

    public AssetManager getAssets() {
        return this.mResources.getAssets();
    }

    public Resources getResources() {
        return this.mResources;
    }

    public Resources.Theme getTheme() {
        if (this.mTheme == null) {
            return super.getTheme();
        }
        return this.mTheme;
    }

    public void setTheme(int n) {
        if (this.mTheme == null) {
            super.setTheme(n);
            return;
        }
        this.mTheme.applyStyle(n, true);
    }
}
