/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.tagmanager.zzau;
import com.google.android.gms.tagmanager.zzaw;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdc;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

class zzav
extends Thread
implements zzau {
    private static zzav zzbEo;
    private volatile boolean mClosed = false;
    private final Context mContext;
    private volatile boolean zzMS = false;
    private final LinkedBlockingQueue<Runnable> zzbEn = new LinkedBlockingQueue();
    private volatile zzaw zzbEp;

    private zzav(Context context) {
        super("GAThread");
        Context context2 = context;
        if (context != null) {
            context2 = context.getApplicationContext();
        }
        this.mContext = context2;
        this.start();
    }

    static zzav zzbI(Context context) {
        if (zzbEo == null) {
            zzbEo = new zzav(context);
        }
        return zzbEo;
    }

    private String zzg(Throwable throwable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        throwable.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        do {
            Object object;
            Throwable throwable22222;
            block5 : {
                boolean bl = this.mClosed;
                try {
                    object = this.zzbEn.take();
                    if (this.zzMS) continue;
                    object.run();
                }
                catch (Throwable throwable22222) {
                    break block5;
                }
                catch (InterruptedException interruptedException) {
                    zzbo.zzbd(interruptedException.toString());
                }
                continue;
            }
            object = String.valueOf(this.zzg(throwable22222));
            object = object.length() != 0 ? "Error on Google TagManager Thread: ".concat((String)object) : new String("Error on Google TagManager Thread: ");
            zzbo.e((String)object);
            zzbo.e("Google TagManager is shutting down.");
            this.zzMS = true;
        } while (true);
    }

    @Override
    public void zzhm(String string) {
        this.zzn(string, System.currentTimeMillis());
    }

    void zzn(final String string, final long l) {
        this.zzp(new Runnable(){

            @Override
            public void run() {
                if (zzav.this.zzbEp == null) {
                    zzdc zzdc2 = zzdc.zzPT();
                    zzdc2.zza(zzav.this.mContext, this);
                    zzav.this.zzbEp = zzdc2.zzPW();
                }
                zzav.this.zzbEp.zzg(l, string);
            }
        });
    }

    @Override
    public void zzp(Runnable runnable) {
        this.zzbEn.add(runnable);
    }

}
