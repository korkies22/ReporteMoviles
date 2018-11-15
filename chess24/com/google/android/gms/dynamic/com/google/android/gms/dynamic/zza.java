/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zzf;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate> {
    private T zzaQd;
    private Bundle zzaQe;
    private LinkedList<zza> zzaQf;
    private final zzf<T> zzaQg = new zzf<T>(){

        @Override
        public void zza(T object) {
            zza.this.zzaQd = object;
            object = zza.this.zzaQf.iterator();
            while (object.hasNext()) {
                ((zza)object.next()).zzb(zza.this.zzaQd);
            }
            zza.this.zzaQf.clear();
            zza.this.zzaQe = null;
        }
    };

    private void zza(Bundle bundle, zza zza2) {
        if (this.zzaQd != null) {
            zza2.zzb((LifecycleDelegate)this.zzaQd);
            return;
        }
        if (this.zzaQf == null) {
            this.zzaQf = new LinkedList();
        }
        this.zzaQf.add(zza2);
        if (bundle != null) {
            if (this.zzaQe == null) {
                this.zzaQe = (Bundle)bundle.clone();
            } else {
                this.zzaQe.putAll(bundle);
            }
        }
        this.zza(this.zzaQg);
    }

    @VisibleForTesting
    static void zza(FrameLayout frameLayout, GoogleApiAvailability googleApiAvailability) {
        Context context = frameLayout.getContext();
        int n = googleApiAvailability.isGooglePlayServicesAvailable(context);
        String string = zzh.zzi(context, n);
        String string2 = zzh.zzk(context, n);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView((View)linearLayout);
        frameLayout = new TextView(frameLayout.getContext());
        frameLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.setText((CharSequence)string);
        linearLayout.addView((View)frameLayout);
        frameLayout = googleApiAvailability.zzb(context, n, null);
        if (frameLayout != null) {
            googleApiAvailability = new Button(context);
            googleApiAvailability.setId(16908313);
            googleApiAvailability.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
            googleApiAvailability.setText((CharSequence)string2);
            linearLayout.addView((View)googleApiAvailability);
            googleApiAvailability.setOnClickListener(new View.OnClickListener((Intent)frameLayout){
                final /* synthetic */ Intent zzaQm;
                {
                    this.zzaQm = intent;
                }

                public void onClick(View view) {
                    try {
                        Context.this.startActivity(this.zzaQm);
                        return;
                    }
                    catch (ActivityNotFoundException activityNotFoundException) {
                        Log.e((String)"DeferredLifecycleHelper", (String)"Failed to start resolution intent", (Throwable)activityNotFoundException);
                        return;
                    }
                }
            });
        }
    }

    public static void zzb(FrameLayout frameLayout) {
        zza.zza(frameLayout, GoogleApiAvailability.getInstance());
    }

    private void zzgk(int n) {
        while (!this.zzaQf.isEmpty() && this.zzaQf.getLast().getState() >= n) {
            this.zzaQf.removeLast();
        }
    }

    public void onCreate(final Bundle bundle) {
        this.zza(bundle, new zza(){

            @Override
            public int getState() {
                return 1;
            }

            @Override
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzaQd.onCreate(bundle);
            }
        });
    }

    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        this.zza(bundle, new zza(){

            @Override
            public int getState() {
                return 2;
            }

            @Override
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                frameLayout.removeAllViews();
                frameLayout.addView(zza.this.zzaQd.onCreateView(layoutInflater, viewGroup, bundle));
            }
        });
        if (this.zzaQd == null) {
            this.zza(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.zzaQd != null) {
            this.zzaQd.onDestroy();
            return;
        }
        this.zzgk(1);
    }

    public void onDestroyView() {
        if (this.zzaQd != null) {
            this.zzaQd.onDestroyView();
            return;
        }
        this.zzgk(2);
    }

    public void onInflate(final Activity activity, final Bundle bundle, final Bundle bundle2) {
        this.zza(bundle2, new zza(){

            @Override
            public int getState() {
                return 0;
            }

            @Override
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzaQd.onInflate(activity, bundle, bundle2);
            }
        });
    }

    public void onLowMemory() {
        if (this.zzaQd != null) {
            this.zzaQd.onLowMemory();
        }
    }

    public void onPause() {
        if (this.zzaQd != null) {
            this.zzaQd.onPause();
            return;
        }
        this.zzgk(5);
    }

    public void onResume() {
        this.zza(null, new zza(){

            @Override
            public int getState() {
                return 5;
            }

            @Override
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzaQd.onResume();
            }
        });
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (this.zzaQd != null) {
            this.zzaQd.onSaveInstanceState(bundle);
            return;
        }
        if (this.zzaQe != null) {
            bundle.putAll(this.zzaQe);
        }
    }

    public void onStart() {
        this.zza(null, new zza(){

            @Override
            public int getState() {
                return 4;
            }

            @Override
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzaQd.onStart();
            }
        });
    }

    public void onStop() {
        if (this.zzaQd != null) {
            this.zzaQd.onStop();
            return;
        }
        this.zzgk(4);
    }

    public T zzAY() {
        return this.zzaQd;
    }

    protected void zza(FrameLayout frameLayout) {
        zza.zzb(frameLayout);
    }

    protected abstract void zza(zzf<T> var1);

    private static interface zza {
        public int getState();

        public void zzb(LifecycleDelegate var1);
    }

}
