/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import de.cisha.android.board.CouchUrl;
import de.cisha.android.board.service.CouchImageCache;
import de.cisha.android.board.service.CouchImageService;
import de.cisha.android.board.service.ICouchImageService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.BitmapHelper;
import de.cisha.chess.util.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

class CouchImageService
extends AsyncTask<Void, Void, Bitmap> {
    final /* synthetic */ CouchUrl val$result;

    CouchImageService(CouchUrl couchUrl) {
        this.val$result = couchUrl;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected /* varargs */ Bitmap doInBackground(Void ... bitmap) {
        Bitmap bitmap2;
        block4 : {
            bitmap2 = bitmap = BitmapHelper.loadImageFromWeb(this.val$result.getUrl());
            if (bitmap == null) return bitmap2;
            try {
                1.this.this$0._cache.makeCouchImageCacheEntry(1.this.val$couchId, this.val$result.getRevision(), 1.this.val$width, bitmap);
                return bitmap;
            }
            catch (IOException iOException) {}
            break block4;
            catch (IOException iOException) {
                bitmap = null;
            }
        }
        Logger.getInstance().debug(de.cisha.android.board.service.CouchImageService.class.getName(), IOException.class.getName(), (Throwable)bitmap2);
        return bitmap;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onPostExecute(Bitmap bitmap) {
        List list = 1.this.this$0.getCallbacksForCouchId(1.this.val$couchId);
        1.this.this$0.removeCallbacksforCouchId(1.this.val$couchId);
        synchronized (list) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                ICouchImageService.CouchImageLoadCommandCallback couchImageLoadCommandCallback = (ICouchImageService.CouchImageLoadCommandCallback)iterator.next();
                if (bitmap != null) {
                    couchImageLoadCommandCallback.loadingSucceded(bitmap);
                    continue;
                }
                couchImageLoadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "failed to load bitmap", null, null);
            }
            return;
        }
    }
}
