/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.graphics.Typeface
 *  android.os.Handler
 */
package android.support.v4.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;

static final class FontsContractCompat
implements Runnable {
    final /* synthetic */ FontsContractCompat.FontRequestCallback val$callback;
    final /* synthetic */ Handler val$callerThreadHandler;
    final /* synthetic */ Context val$context;
    final /* synthetic */ FontRequest val$request;

    FontsContractCompat(Context context, FontRequest fontRequest, Handler handler, FontsContractCompat.FontRequestCallback fontRequestCallback) {
        this.val$context = context;
        this.val$request = fontRequest;
        this.val$callerThreadHandler = handler;
        this.val$callback = fontRequestCallback;
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
        int n;
        final Typeface typeface = android.support.v4.provider.FontsContractCompat.fetchFonts(this.val$context, null, this.val$request);
        {
            catch (PackageManager.NameNotFoundException nameNotFoundException) {}
        }
        if (typeface.getStatusCode() != 0) {
            switch (typeface.getStatusCode()) {
                default: {
                    this.val$callerThreadHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            4.this.val$callback.onTypefaceRequestFailed(-3);
                        }
                    });
                    return;
                }
                case 2: {
                    this.val$callerThreadHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            4.this.val$callback.onTypefaceRequestFailed(-3);
                        }
                    });
                    return;
                }
                case 1: 
            }
            this.val$callerThreadHandler.post(new Runnable(){

                @Override
                public void run() {
                    4.this.val$callback.onTypefaceRequestFailed(-2);
                }
            });
            return;
        }
        if ((typeface = typeface.getFonts()) != null && ((FontsContractCompat.FontInfo[])typeface).length != 0) {
            n = ((FontsContractCompat.FontInfo[])typeface).length;
        } else {
            this.val$callerThreadHandler.post(new Runnable(){

                @Override
                public void run() {
                    4.this.val$callback.onTypefaceRequestFailed(1);
                }
            });
            return;
            this.val$callerThreadHandler.post(new Runnable(){

                @Override
                public void run() {
                    4.this.val$callback.onTypefaceRequestFailed(-1);
                }
            });
            return;
        }
        for (int i = 0; i < n; ++i) {
            FontsContractCompat.FontInfo fontInfo = typeface[i];
            if (fontInfo.getResultCode() == 0) continue;
            i = fontInfo.getResultCode();
            if (i < 0) {
                this.val$callerThreadHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        4.this.val$callback.onTypefaceRequestFailed(-3);
                    }
                });
                return;
            }
            this.val$callerThreadHandler.post(new Runnable(){

                @Override
                public void run() {
                    4.this.val$callback.onTypefaceRequestFailed(i);
                }
            });
            return;
        }
        if ((typeface = android.support.v4.provider.FontsContractCompat.buildTypeface(this.val$context, null, (FontsContractCompat.FontInfo[])typeface)) == null) {
            this.val$callerThreadHandler.post(new Runnable(){

                @Override
                public void run() {
                    4.this.val$callback.onTypefaceRequestFailed(-3);
                }
            });
            return;
        }
        this.val$callerThreadHandler.post(new Runnable(){

            @Override
            public void run() {
                4.this.val$callback.onTypefaceRetrieved(typeface);
            }
        });
    }

}
