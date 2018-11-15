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
import de.cisha.android.board.service.ICouchImageService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.util.AsyncTaskCompatHelper;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.BitmapHelper;
import de.cisha.chess.util.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

class CouchImageService
extends LoadCommandCallbackWithTimeout<CouchUrl> {
    final /* synthetic */ CishaUUID val$couchId;
    final /* synthetic */ boolean val$forceLoad;
    final /* synthetic */ String val$revision;
    final /* synthetic */ int val$width;

    CouchImageService(String string, boolean bl, CishaUUID cishaUUID, int n) {
        this.val$revision = string;
        this.val$forceLoad = bl;
        this.val$couchId = cishaUUID;
        this.val$width = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject object) {
        object = CouchImageService.this.getCallbacksForCouchId(this.val$couchId);
        CouchImageService.this.removeCallbacksforCouchId(this.val$couchId);
        synchronized (object) {
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                ICouchImageService.CouchImageLoadCommandCallback couchImageLoadCommandCallback = (ICouchImageService.CouchImageLoadCommandCallback)iterator.next();
                if (couchImageLoadCommandCallback == null) continue;
                couchImageLoadCommandCallback.loadingFailed(aPIStatusCode, string, list, null);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void succeded(CouchUrl object) {
        if (object.getRevision().equals(this.val$revision) && !this.val$forceLoad) {
            CouchImageService.this._cache.setTimeOfUpdate(this.val$couchId);
            object = CouchImageService.this.getCallbacksForCouchId(this.val$couchId);
            CouchImageService.this.removeCallbacksforCouchId(this.val$couchId);
            synchronized (object) {
                Iterator iterator = object.iterator();
                do {
                    if (!iterator.hasNext()) {
                        return;
                    }
                    ((ICouchImageService.CouchImageLoadCommandCallback)iterator.next()).noUpdateNeeded();
                } while (true);
            }
        }
        AsyncTaskCompatHelper.executeOnExecutorPool(new AsyncTask<Void, Void, Bitmap>((CouchUrl)object){
            final /* synthetic */ CouchUrl val$result;
            {
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
                        CouchImageService.this._cache.makeCouchImageCacheEntry(1.this.val$couchId, this.val$result.getRevision(), 1.this.val$width, bitmap);
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
                List list = CouchImageService.this.getCallbacksForCouchId(1.this.val$couchId);
                CouchImageService.this.removeCallbacksforCouchId(1.this.val$couchId);
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
        }, new Void[0]);
    }

}
