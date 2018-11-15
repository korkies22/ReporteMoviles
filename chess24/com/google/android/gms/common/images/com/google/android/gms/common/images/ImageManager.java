/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.ActivityManager
 *  android.content.ComponentCallbacks
 *  android.content.ComponentCallbacks2
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Configuration
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 *  android.os.SystemClock
 *  android.util.Log
 *  android.widget.ImageView
 */
package com.google.android.gms.common.images;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.images.zza;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzabv;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    private static final Object zzaCG = new Object();
    private static HashSet<Uri> zzaCH = new HashSet();
    private static ImageManager zzaCI;
    private static ImageManager zzaCJ;
    private final Context mContext;
    private final Handler mHandler;
    private final ExecutorService zzaCK;
    private final zzb zzaCL;
    private final zzabv zzaCM;
    private final Map<com.google.android.gms.common.images.zza, ImageReceiver> zzaCN;
    private final Map<Uri, ImageReceiver> zzaCO;
    private final Map<Uri, Long> zzaCP;

    private ImageManager(Context context, boolean bl) {
        this.mContext = context.getApplicationContext();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.zzaCK = Executors.newFixedThreadPool(4);
        if (bl) {
            this.zzaCL = new zzb(this.mContext);
            if (zzs.zzyA()) {
                this.zzwJ();
            }
        } else {
            this.zzaCL = null;
        }
        this.zzaCM = new zzabv();
        this.zzaCN = new HashMap<com.google.android.gms.common.images.zza, ImageReceiver>();
        this.zzaCO = new HashMap<Uri, ImageReceiver>();
        this.zzaCP = new HashMap<Uri, Long>();
    }

    public static ImageManager create(Context context) {
        return ImageManager.zzg(context, false);
    }

    private Bitmap zza(zza.zza zza2) {
        if (this.zzaCL == null) {
            return null;
        }
        return (Bitmap)this.zzaCL.get(zza2);
    }

    public static ImageManager zzg(Context context, boolean bl) {
        if (bl) {
            if (zzaCJ == null) {
                zzaCJ = new ImageManager(context, true);
            }
            return zzaCJ;
        }
        if (zzaCI == null) {
            zzaCI = new ImageManager(context, false);
        }
        return zzaCI;
    }

    @TargetApi(value=14)
    private void zzwJ() {
        this.mContext.registerComponentCallbacks((ComponentCallbacks)new zze(this.zzaCL));
    }

    public void loadImage(ImageView imageView, int n) {
        this.zza(new zza.zzb(imageView, n));
    }

    public void loadImage(ImageView imageView, Uri uri) {
        this.zza(new zza.zzb(imageView, uri));
    }

    public void loadImage(ImageView object, Uri uri, int n) {
        object = new zza.zzb((ImageView)object, uri);
        object.zzcI(n);
        this.zza((com.google.android.gms.common.images.zza)object);
    }

    public void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        this.zza(new zza.zzc(onImageLoadedListener, uri));
    }

    public void loadImage(OnImageLoadedListener object, Uri uri, int n) {
        object = new zza.zzc((OnImageLoadedListener)object, uri);
        object.zzcI(n);
        this.zza((com.google.android.gms.common.images.zza)object);
    }

    public void zza(com.google.android.gms.common.images.zza zza2) {
        com.google.android.gms.common.internal.zzc.zzdn("ImageManager.loadImage() must be called in the main thread");
        new zzd(zza2).run();
    }

    @KeepName
    private final class ImageReceiver
    extends ResultReceiver {
        private final Uri mUri;
        private final ArrayList<com.google.android.gms.common.images.zza> zzaCQ;

        ImageReceiver(Uri uri) {
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
            this.zzaCQ = new ArrayList();
        }

        public void onReceiveResult(int n, Bundle bundle) {
            bundle = (ParcelFileDescriptor)bundle.getParcelable("com.google.android.gms.extra.fileDescriptor");
            ImageManager.this.zzaCK.execute(new zzc(this.mUri, (ParcelFileDescriptor)bundle));
        }

        public void zzb(com.google.android.gms.common.images.zza zza2) {
            com.google.android.gms.common.internal.zzc.zzdn("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zzaCQ.add(zza2);
        }

        public void zzc(com.google.android.gms.common.images.zza zza2) {
            com.google.android.gms.common.internal.zzc.zzdn("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zzaCQ.remove(zza2);
        }

        public void zzwL() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", (Parcelable)this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", (Parcelable)this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            ImageManager.this.mContext.sendBroadcast(intent);
        }
    }

    public static interface OnImageLoadedListener {
        public void onImageLoaded(Uri var1, Drawable var2, boolean var3);
    }

    @TargetApi(value=11)
    private static final class zza {
        static int zza(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    private static final class zzb
    extends LruCache<zza.zza, Bitmap> {
        public zzb(Context context) {
            super(zzb.zzaz(context));
        }

        @TargetApi(value=11)
        private static int zzaz(Context context) {
            ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
            int n = (context.getApplicationInfo().flags & 1048576) != 0 ? 1 : 0;
            n = n != 0 && zzs.zzyx() ? zza.zza(activityManager) : activityManager.getMemoryClass();
            return (int)(0.33f * (float)(1048576 * n));
        }

        @Override
        protected /* synthetic */ void entryRemoved(boolean bl, Object object, Object object2, Object object3) {
            this.zza(bl, (zza.zza)object, (Bitmap)object2, (Bitmap)object3);
        }

        @Override
        protected /* synthetic */ int sizeOf(Object object, Object object2) {
            return this.zza((zza.zza)object, (Bitmap)object2);
        }

        protected int zza(zza.zza zza2, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        protected void zza(boolean bl, zza.zza zza2, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(bl, zza2, bitmap, bitmap2);
        }
    }

    private final class zzc
    implements Runnable {
        private final Uri mUri;
        private final ParcelFileDescriptor zzaCS;

        public zzc(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.mUri = uri;
            this.zzaCS = parcelFileDescriptor;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            void var3_10;
            com.google.android.gms.common.internal.zzc.zzdo("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            ParcelFileDescriptor parcelFileDescriptor = this.zzaCS;
            boolean bl = false;
            boolean bl2 = false;
            Object var3_5 = null;
            Object object = null;
            if (parcelFileDescriptor != null) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFileDescriptor((FileDescriptor)this.zzaCS.getFileDescriptor());
                    bl = bl2;
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    String string = String.valueOf((Object)this.mUri);
                    StringBuilder stringBuilder = new StringBuilder(34 + String.valueOf(string).length());
                    stringBuilder.append("OOM while loading bitmap for uri: ");
                    stringBuilder.append(string);
                    Log.e((String)"ImageManager", (String)stringBuilder.toString(), (Throwable)outOfMemoryError);
                    bl = true;
                    CountDownLatch countDownLatch = object;
                }
                try {
                    this.zzaCS.close();
                }
                catch (IOException iOException) {
                    Log.e((String)"ImageManager", (String)"closed failed", (Throwable)iOException);
                }
            }
            object = new CountDownLatch(1);
            ImageManager.this.mHandler.post((Runnable)new zzf(this.mUri, (Bitmap)var3_10, bl, (CountDownLatch)object));
            try {
                object.await();
                return;
            }
            catch (InterruptedException interruptedException) {}
            String string = String.valueOf((Object)this.mUri);
            object = new StringBuilder(32 + String.valueOf(string).length());
            object.append("Latch interrupted while posting ");
            object.append(string);
            Log.w((String)"ImageManager", (String)object.toString());
        }
    }

    private final class zzd
    implements Runnable {
        private final com.google.android.gms.common.images.zza zzaCT;

        public zzd(com.google.android.gms.common.images.zza zza2) {
            this.zzaCT = zza2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            com.google.android.gms.common.internal.zzc.zzdn("LoadImageRunnable must be executed on the main thread");
            Object object = (ImageReceiver)((Object)ImageManager.this.zzaCN.get(this.zzaCT));
            if (object != null) {
                ImageManager.this.zzaCN.remove(this.zzaCT);
                object.zzc(this.zzaCT);
            }
            zza.zza zza2 = this.zzaCT.zzaCV;
            if (zza2.uri == null) {
                this.zzaCT.zza(ImageManager.this.mContext, ImageManager.this.zzaCM, true);
                return;
            }
            object = ImageManager.this.zza(zza2);
            if (object != null) {
                this.zzaCT.zza(ImageManager.this.mContext, (Bitmap)object, true);
                return;
            }
            object = (Long)ImageManager.this.zzaCP.get((Object)zza2.uri);
            if (object != null) {
                if (SystemClock.elapsedRealtime() - object.longValue() < 3600000L) {
                    this.zzaCT.zza(ImageManager.this.mContext, ImageManager.this.zzaCM, true);
                    return;
                }
                ImageManager.this.zzaCP.remove((Object)zza2.uri);
            }
            this.zzaCT.zza(ImageManager.this.mContext, ImageManager.this.zzaCM);
            Object object2 = (ImageReceiver)((Object)ImageManager.this.zzaCO.get((Object)zza2.uri));
            object = object2;
            if (object2 == null) {
                object = new ImageReceiver(zza2.uri);
                ImageManager.this.zzaCO.put(zza2.uri, object);
            }
            object.zzb(this.zzaCT);
            if (!(this.zzaCT instanceof zza.zzc)) {
                ImageManager.this.zzaCN.put(this.zzaCT, object);
            }
            object2 = zzaCG;
            synchronized (object2) {
                if (!zzaCH.contains((Object)zza2.uri)) {
                    zzaCH.add(zza2.uri);
                    object.zzwL();
                }
                return;
            }
        }
    }

    @TargetApi(value=14)
    private static final class zze
    implements ComponentCallbacks2 {
        private final zzb zzaCL;

        public zze(zzb zzb2) {
            this.zzaCL = zzb2;
        }

        public void onConfigurationChanged(Configuration configuration) {
        }

        public void onLowMemory() {
            this.zzaCL.evictAll();
        }

        public void onTrimMemory(int n) {
            if (n >= 60) {
                this.zzaCL.evictAll();
                return;
            }
            if (n >= 20) {
                this.zzaCL.trimToSize(this.zzaCL.size() / 2);
            }
        }
    }

    private final class zzf
    implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        private boolean zzaCU;
        private final CountDownLatch zzth;

        public zzf(Uri uri, Bitmap bitmap, boolean bl, CountDownLatch countDownLatch) {
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.zzaCU = bl;
            this.zzth = countDownLatch;
        }

        private void zza(ImageReceiver object, boolean bl) {
            object = ((ImageReceiver)((Object)object)).zzaCQ;
            int n = object.size();
            for (int i = 0; i < n; ++i) {
                com.google.android.gms.common.images.zza zza2 = (com.google.android.gms.common.images.zza)object.get(i);
                if (bl) {
                    zza2.zza(ImageManager.this.mContext, this.mBitmap, false);
                } else {
                    ImageManager.this.zzaCP.put(this.mUri, SystemClock.elapsedRealtime());
                    zza2.zza(ImageManager.this.mContext, ImageManager.this.zzaCM, false);
                }
                if (zza2 instanceof zza.zzc) continue;
                ImageManager.this.zzaCN.remove(zza2);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Object object;
            com.google.android.gms.common.internal.zzc.zzdn("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean bl = this.mBitmap != null;
            if (ImageManager.this.zzaCL != null) {
                if (this.zzaCU) {
                    ImageManager.this.zzaCL.evictAll();
                    System.gc();
                    this.zzaCU = false;
                    ImageManager.this.mHandler.post((Runnable)this);
                    return;
                }
                if (bl) {
                    ImageManager.this.zzaCL.put(new zza.zza(this.mUri), this.mBitmap);
                }
            }
            if ((object = (ImageReceiver)((Object)ImageManager.this.zzaCO.remove((Object)this.mUri))) != null) {
                this.zza((ImageReceiver)((Object)object), bl);
            }
            this.zzth.countDown();
            object = zzaCG;
            synchronized (object) {
                zzaCH.remove((Object)this.mUri);
                return;
            }
        }
    }

}
