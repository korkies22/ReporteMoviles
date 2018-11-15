/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.internal.zzaaw;
import com.google.android.gms.internal.zzaax;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

@TargetApi(value=11)
public final class zzaay
extends Fragment
implements zzaax {
    private static WeakHashMap<Activity, WeakReference<zzaay>> zzaBt = new WeakHashMap();
    private int zzJh = 0;
    private Map<String, zzaaw> zzaBu = new ArrayMap<String, zzaaw>();
    private Bundle zzaBv;

    private void zzb(final String string, final @NonNull zzaaw zzaaw2) {
        if (this.zzJh > 0) {
            new Handler(Looper.getMainLooper()).post(new Runnable(){

                @Override
                public void run() {
                    if (zzaay.this.zzJh >= 1) {
                        zzaaw zzaaw22 = zzaaw2;
                        Bundle bundle = zzaay.this.zzaBv != null ? zzaay.this.zzaBv.getBundle(string) : null;
                        zzaaw22.onCreate(bundle);
                    }
                    if (zzaay.this.zzJh >= 2) {
                        zzaaw2.onStart();
                    }
                    if (zzaay.this.zzJh >= 3) {
                        zzaaw2.onStop();
                    }
                    if (zzaay.this.zzJh >= 4) {
                        zzaaw2.onDestroy();
                    }
                }
            });
        }
    }

    public static zzaay zzt(Activity activity) {
        WeakReference<zzaay> weakReference;
        block7 : {
            block6 : {
                zzaay zzaay2;
                weakReference = zzaBt.get((Object)activity);
                if (weakReference != null && (weakReference = (zzaay)weakReference.get()) != null) {
                    return weakReference;
                }
                try {
                    zzaay2 = (zzaay)activity.getFragmentManager().findFragmentByTag("LifecycleFragmentImpl");
                    if (zzaay2 == null) break block6;
                    weakReference = zzaay2;
                }
                catch (ClassCastException classCastException) {
                    throw new IllegalStateException("Fragment with tag LifecycleFragmentImpl is not a LifecycleFragmentImpl", classCastException);
                }
                if (!zzaay2.isRemoving()) break block7;
            }
            weakReference = new zzaay();
            activity.getFragmentManager().beginTransaction().add((Fragment)weakReference, "LifecycleFragmentImpl").commitAllowingStateLoss();
        }
        zzaBt.put(activity, new WeakReference<Object>(weakReference));
        return weakReference;
    }

    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string, fileDescriptor, printWriter, arrstring);
        Iterator<zzaaw> iterator = this.zzaBu.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().dump(string, fileDescriptor, printWriter, arrstring);
        }
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        Iterator<zzaaw> iterator = this.zzaBu.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onActivityResult(n, n2, intent);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzJh = 1;
        this.zzaBv = bundle;
        for (Map.Entry<String, zzaaw> entry : this.zzaBu.entrySet()) {
            void object;
            zzaaw zzaaw2 = entry.getValue();
            if (bundle != null) {
                Bundle bundle2 = bundle.getBundle(entry.getKey());
            } else {
                Object var2_6 = null;
            }
            zzaaw2.onCreate((Bundle)object);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.zzJh = 4;
        Iterator<zzaaw> iterator = this.zzaBu.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onDestroy();
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        for (Map.Entry<String, zzaaw> entry : this.zzaBu.entrySet()) {
            Bundle bundle2 = new Bundle();
            entry.getValue().onSaveInstanceState(bundle2);
            bundle.putBundle(entry.getKey(), bundle2);
        }
    }

    public void onStart() {
        super.onStart();
        this.zzJh = 2;
        Iterator<zzaaw> iterator = this.zzaBu.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onStart();
        }
    }

    public void onStop() {
        super.onStop();
        this.zzJh = 3;
        Iterator<zzaaw> iterator = this.zzaBu.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onStop();
        }
    }

    @Override
    public <T extends zzaaw> T zza(String string, Class<T> class_) {
        return (T)((zzaaw)class_.cast(this.zzaBu.get(string)));
    }

    @Override
    public void zza(String string, @NonNull zzaaw object) {
        if (!this.zzaBu.containsKey(string)) {
            this.zzaBu.put(string, (zzaaw)object);
            this.zzb(string, (zzaaw)object);
            return;
        }
        object = new StringBuilder(59 + String.valueOf(string).length());
        object.append("LifecycleCallback with tag ");
        object.append(string);
        object.append(" already added to this fragment.");
        throw new IllegalArgumentException(object.toString());
    }

    @Override
    public Activity zzwo() {
        return this.getActivity();
    }

}
