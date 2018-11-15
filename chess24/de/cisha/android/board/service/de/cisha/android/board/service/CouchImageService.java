/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import de.cisha.android.board.CouchUrl;
import de.cisha.android.board.service.CouchImageCache;
import de.cisha.android.board.service.ICouchImageService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONGetImageCouchUrlParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.util.AsyncTaskCompatHelper;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.BitmapHelper;
import de.cisha.chess.util.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

public class CouchImageService
implements ICouchImageService {
    private static final long MINIMAL_UPDATE_TIME_INTERVAL = 3600000L;
    private static Context _context;
    private static ICouchImageService _instance;
    private CouchImageCache _cache;
    private Map<CishaUUID, List<ICouchImageService.CouchImageLoadCommandCallback>> _requestedImages = new HashMap<CishaUUID, List<ICouchImageService.CouchImageLoadCommandCallback>>();

    private CouchImageService() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void addLoadCommandCallbackForImage(CishaUUID cishaUUID, ICouchImageService.CouchImageLoadCommandCallback couchImageLoadCommandCallback) {
        List<ICouchImageService.CouchImageLoadCommandCallback> list;
        List<ICouchImageService.CouchImageLoadCommandCallback> list2 = list = this._requestedImages.get(cishaUUID);
        if (list == null) {
            list2 = Collections.synchronizedList(new LinkedList());
            this._requestedImages.put(cishaUUID, list2);
        }
        synchronized (list2) {
            list2.add(couchImageLoadCommandCallback);
            return;
        }
    }

    private LoadCommandCallbackWithTimeout<CouchUrl> createCallbackForImage(CishaUUID cishaUUID, int n, boolean bl) {
        this._cache = CouchImageCache.getInstance(_context);
        return new LoadCommandCallbackWithTimeout<CouchUrl>(this._cache.getRevision(cishaUUID), bl, cishaUUID, n){
            final /* synthetic */ CishaUUID val$couchId;
            final /* synthetic */ boolean val$forceLoad;
            final /* synthetic */ String val$revision;
            final /* synthetic */ int val$width;
            {
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
                        Logger.getInstance().debug(CouchImageService.class.getName(), IOException.class.getName(), (Throwable)bitmap2);
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

        };
    }

    private List<ICouchImageService.CouchImageLoadCommandCallback> getCallbacksForCouchId(CishaUUID linkedList) {
        List<ICouchImageService.CouchImageLoadCommandCallback> list = this._requestedImages.get(linkedList);
        linkedList = list;
        if (list == null) {
            linkedList = new LinkedList();
        }
        return linkedList;
    }

    private void getCouchImageUrl(CishaUUID object, int n, int n2, LoadCommandCallback<CouchUrl> loadCommandCallback) {
        if (object != null) {
            GeneralJSONAPICommandLoader<CouchUrl> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<CouchUrl>();
            TreeMap<String, String> treeMap = new TreeMap<String, String>();
            treeMap.put("id", object.getUuid());
            object = new StringBuilder();
            object.append(n);
            object.append("");
            treeMap.put("width", object.toString());
            object = new StringBuilder();
            object.append(n2);
            object.append("");
            treeMap.put("height", object.toString());
            generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "mobileAPI/GetImageUrl", treeMap, new JSONGetImageCouchUrlParser(), true);
            return;
        }
        loadCommandCallback.loadingFailed(APIStatusCode.API_ERROR_NOT_SET, "Image Id was null", null, null);
    }

    public static ICouchImageService getInstance(Context context) {
        _context = context;
        if (_instance == null) {
            _instance = new CouchImageService();
        }
        return _instance;
    }

    private void removeCallbacksforCouchId(CishaUUID cishaUUID) {
        this._requestedImages.remove(cishaUUID);
    }

    @Override
    public Bitmap getCouchImage(CishaUUID cishaUUID, int n, int n2, ICouchImageService.CouchImageLoadCommandCallback couchImageLoadCommandCallback) {
        this._cache = CouchImageCache.getInstance(_context);
        Bitmap bitmap = this._cache.lookupForCouchId(cishaUUID, n);
        long l = System.currentTimeMillis();
        if (bitmap != null && l - this._cache.getLastTimeOfUpdate(cishaUUID) < 3600000L) {
            couchImageLoadCommandCallback.noUpdateNeeded();
            return bitmap;
        }
        this.addLoadCommandCallbackForImage(cishaUUID, couchImageLoadCommandCallback);
        boolean bl = bitmap == null;
        this.getCouchImageUrl(cishaUUID, n, n2, this.createCallbackForImage(cishaUUID, n, bl));
        return bitmap;
    }

}
