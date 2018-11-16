// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONGetImageCouchUrlParser;
import java.util.TreeMap;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.util.AsyncTaskCompatHelper;
import de.cisha.chess.util.Logger;
import java.io.IOException;
import de.cisha.chess.util.BitmapHelper;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import java.util.Iterator;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.CouchUrl;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import java.util.Collections;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import de.cisha.chess.model.CishaUUID;
import java.util.Map;
import android.content.Context;

public class CouchImageService implements ICouchImageService
{
    private static final long MINIMAL_UPDATE_TIME_INTERVAL = 3600000L;
    private static Context _context;
    private static ICouchImageService _instance;
    private CouchImageCache _cache;
    private Map<CishaUUID, List<CouchImageLoadCommandCallback>> _requestedImages;
    
    private CouchImageService() {
        this._requestedImages = new HashMap<CishaUUID, List<CouchImageLoadCommandCallback>>();
    }
    
    private void addLoadCommandCallbackForImage(final CishaUUID cishaUUID, final CouchImageLoadCommandCallback couchImageLoadCommandCallback) {
        List<CouchImageLoadCommandCallback> synchronizedList;
        if ((synchronizedList = this._requestedImages.get(cishaUUID)) == null) {
            synchronizedList = Collections.synchronizedList(new LinkedList<CouchImageLoadCommandCallback>());
            this._requestedImages.put(cishaUUID, synchronizedList);
        }
        synchronized (synchronizedList) {
            synchronizedList.add(couchImageLoadCommandCallback);
        }
    }
    
    private LoadCommandCallbackWithTimeout<CouchUrl> createCallbackForImage(final CishaUUID cishaUUID, final int n, final boolean b) {
        this._cache = CouchImageCache.getInstance(CouchImageService._context);
        return new LoadCommandCallbackWithTimeout<CouchUrl>() {
            final /* synthetic */ String val.revision = CouchImageService.this._cache.getRevision(cishaUUID);
            
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, JSONObject access.100) {
                access.100 = (JSONObject)CouchImageService.this.getCallbacksForCouchId(cishaUUID);
                CouchImageService.this.removeCallbacksforCouchId(cishaUUID);
                synchronized (access.100) {
                    for (final CouchImageLoadCommandCallback couchImageLoadCommandCallback : access.100) {
                        if (couchImageLoadCommandCallback != null) {
                            couchImageLoadCommandCallback.loadingFailed(apiStatusCode, s, list, null);
                        }
                    }
                }
            }
            
            @Override
            protected void succeded(CouchUrl access.100) {
                if (access.100.getRevision().equals(this.val.revision)) {
                    if (!b) {
                        CouchImageService.this._cache.setTimeOfUpdate(cishaUUID);
                        access.100 = (CouchUrl)CouchImageService.this.getCallbacksForCouchId(cishaUUID);
                        CouchImageService.this.removeCallbacksforCouchId(cishaUUID);
                        synchronized (access.100) {
                            final Iterator<CouchImageLoadCommandCallback> iterator = ((List<CouchImageLoadCommandCallback>)access.100).iterator();
                            while (iterator.hasNext()) {
                                iterator.next().noUpdateNeeded();
                            }
                            return;
                        }
                    }
                }
                AsyncTaskCompatHelper.executeOnExecutorPool((android.os.AsyncTask<Void, Object, Object>)new AsyncTask<Void, Void, Bitmap>() {
                    protected Bitmap doInBackground(Void... array) {
                        try {
                            final Object loadImageFromWeb;
                            array = (Void[])(loadImageFromWeb = BitmapHelper.loadImageFromWeb(access.100.getUrl()));
                            if (array == null) {
                                return (Bitmap)loadImageFromWeb;
                            }
                            try {
                                CouchImageService.this._cache.makeCouchImageCacheEntry(cishaUUID, access.100.getRevision(), n, (Bitmap)(Object)array);
                                return (Bitmap)(Object)array;
                            }
                            catch (IOException ex) {}
                        }
                        catch (IOException ex) {
                            array = null;
                        }
                        final IOException ex;
                        Logger.getInstance().debug(CouchImageService.class.getName(), IOException.class.getName(), ex);
                        Object loadImageFromWeb = array;
                        return (Bitmap)loadImageFromWeb;
                    }
                    
                    protected void onPostExecute(final Bitmap bitmap) {
                        final List access.100 = CouchImageService.this.getCallbacksForCouchId(cishaUUID);
                        CouchImageService.this.removeCallbacksforCouchId(cishaUUID);
                        synchronized (access.100) {
                            for (final CouchImageLoadCommandCallback couchImageLoadCommandCallback : access.100) {
                                if (bitmap != null) {
                                    couchImageLoadCommandCallback.loadingSucceded(bitmap);
                                }
                                else {
                                    couchImageLoadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "failed to load bitmap", null, null);
                                }
                            }
                        }
                    }
                }, new Void[0]);
            }
        };
    }
    
    private List<CouchImageLoadCommandCallback> getCallbacksForCouchId(final CishaUUID cishaUUID) {
        List<CouchImageLoadCommandCallback> list;
        if ((list = this._requestedImages.get(cishaUUID)) == null) {
            list = new LinkedList<CouchImageLoadCommandCallback>();
        }
        return list;
    }
    
    private void getCouchImageUrl(final CishaUUID cishaUUID, final int n, final int n2, final LoadCommandCallback<CouchUrl> loadCommandCallback) {
        if (cishaUUID != null) {
            final GeneralJSONAPICommandLoader<CouchUrl> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<CouchUrl>();
            final TreeMap<String, String> treeMap = new TreeMap<String, String>();
            treeMap.put("id", cishaUUID.getUuid());
            final StringBuilder sb = new StringBuilder();
            sb.append(n);
            sb.append("");
            treeMap.put("width", sb.toString());
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(n2);
            sb2.append("");
            treeMap.put("height", sb2.toString());
            generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "mobileAPI/GetImageUrl", treeMap, new JSONGetImageCouchUrlParser(), true);
            return;
        }
        loadCommandCallback.loadingFailed(APIStatusCode.API_ERROR_NOT_SET, "Image Id was null", null, null);
    }
    
    public static ICouchImageService getInstance(final Context context) {
        CouchImageService._context = context;
        if (CouchImageService._instance == null) {
            CouchImageService._instance = new CouchImageService();
        }
        return CouchImageService._instance;
    }
    
    private void removeCallbacksforCouchId(final CishaUUID cishaUUID) {
        this._requestedImages.remove(cishaUUID);
    }
    
    @Override
    public Bitmap getCouchImage(final CishaUUID cishaUUID, final int n, final int n2, final CouchImageLoadCommandCallback couchImageLoadCommandCallback) {
        this._cache = CouchImageCache.getInstance(CouchImageService._context);
        final Bitmap lookupForCouchId = this._cache.lookupForCouchId(cishaUUID, n);
        final long currentTimeMillis = System.currentTimeMillis();
        if (lookupForCouchId != null && currentTimeMillis - this._cache.getLastTimeOfUpdate(cishaUUID) < 3600000L) {
            couchImageLoadCommandCallback.noUpdateNeeded();
            return lookupForCouchId;
        }
        this.addLoadCommandCallbackForImage(cishaUUID, couchImageLoadCommandCallback);
        this.getCouchImageUrl(cishaUUID, n, n2, this.createCallbackForImage(cishaUUID, n, lookupForCouchId == null));
        return lookupForCouchId;
    }
}
