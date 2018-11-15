/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 */
package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.ResultReceiver;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.images.zza;
import com.google.android.gms.common.internal.zzc;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

@KeepName
private final class ImageManager.ImageReceiver
extends ResultReceiver {
    private final Uri mUri;
    private final ArrayList<zza> zzaCQ;

    ImageManager.ImageReceiver(Uri uri) {
        super(new Handler(Looper.getMainLooper()));
        this.mUri = uri;
        this.zzaCQ = new ArrayList();
    }

    static /* synthetic */ ArrayList zza(ImageManager.ImageReceiver imageReceiver) {
        return imageReceiver.zzaCQ;
    }

    public void onReceiveResult(int n, Bundle bundle) {
        bundle = (ParcelFileDescriptor)bundle.getParcelable("com.google.android.gms.extra.fileDescriptor");
        ImageManager.this.zzaCK.execute(new ImageManager.zzc(ImageManager.this, this.mUri, (ParcelFileDescriptor)bundle));
    }

    public void zzb(zza zza2) {
        zzc.zzdn("ImageReceiver.addImageRequest() must be called in the main thread");
        this.zzaCQ.add(zza2);
    }

    public void zzc(zza zza2) {
        zzc.zzdn("ImageReceiver.removeImageRequest() must be called in the main thread");
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
