/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public final class zzabm
extends Fragment
implements zzaax {
    private static WeakHashMap<FragmentActivity, WeakReference<zzabm>> zzaBt = new WeakHashMap();
    private int zzJh = 0;
    private Map<String, zzaaw> zzaBu = new ArrayMap<String, zzaaw>();
    private Bundle zzaBv;

    public static zzabm zza(FragmentActivity fragmentActivity) {
        WeakReference<zzabm> weakReference;
        block7 : {
            block6 : {
                zzabm zzabm2;
                weakReference = zzaBt.get(fragmentActivity);
                if (weakReference != null && (weakReference = (zzabm)weakReference.get()) != null) {
                    return weakReference;
                }
                try {
                    zzabm2 = (zzabm)fragmentActivity.getSupportFragmentManager().findFragmentByTag("SupportLifecycleFragmentImpl");
                    if (zzabm2 == null) break block6;
                    weakReference = zzabm2;
                }
                catch (ClassCastException classCastException) {
                    throw new IllegalStateException("Fragment with tag SupportLifecycleFragmentImpl is not a SupportLifecycleFragmentImpl", classCastException);
                }
                if (!zzabm2.isRemoving()) break block7;
            }
            weakReference = new zzabm();
            fragmentActivity.getSupportFragmentManager().beginTransaction().add((Fragment)((Object)weakReference), "SupportLifecycleFragmentImpl").commitAllowingStateLoss();
        }
        zzaBt.put(fragmentActivity, new WeakReference<Object>(weakReference));
        return weakReference;
    }

    private void zzb(final String string, final @NonNull zzaaw zzaaw2) {
        if (this.zzJh > 0) {
            new Handler(Looper.getMainLooper()).post(new Runnable(){

                @Override
                public void run() {
                    if (zzabm.this.zzJh >= 1) {
                        zzaaw zzaaw22 = zzaaw2;
                        Bundle bundle = zzabm.this.zzaBv != null ? zzabm.this.zzaBv.getBundle(string) : null;
                        zzaaw22.onCreate(bundle);
                    }
                    if (zzabm.this.zzJh >= 2) {
                        zzaaw2.onStart();
                    }
                    if (zzabm.this.zzJh >= 3) {
                        zzaaw2.onStop();
                    }
                    if (zzabm.this.zzJh >= 4) {
                        zzaaw2.onDestroy();
                    }
                }
            });
        }
    }

    @Override
    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string, fileDescriptor, printWriter, arrstring);
        Iterator<zzaaw> iterator = this.zzaBu.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().dump(string, fileDescriptor, printWriter, arrstring);
        }
    }

    @Override
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        Iterator<zzaaw> iterator = this.zzaBu.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onActivityResult(n, n2, intent);
        }
    }

    @Override
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.zzJh = 4;
        Iterator<zzaaw> iterator = this.zzaBu.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onDestroy();
        }
    }

    @Override
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

    @Override
    public void onStart() {
        super.onStart();
        this.zzJh = 2;
        Iterator<zzaaw> iterator = this.zzaBu.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onStart();
        }
    }

    @Override
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
    public /* synthetic */ Activity zzwo() {
        return this.zzws();
    }

    public FragmentActivity zzws() {
        return this.getActivity();
    }

}
