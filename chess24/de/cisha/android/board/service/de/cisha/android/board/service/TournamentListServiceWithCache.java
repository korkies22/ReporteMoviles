/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.SparseArray
 */
package de.cisha.android.board.service;

import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.chess.util.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TournamentListServiceWithCache
implements ITournamentListService {
    private static final int CACHING_TIME_MILLIS = 600000;
    private SparseArray<TournamentInfo> _cachedResult = new SparseArray();
    private ITournamentListService _service;
    private long _timeLastUpdate;

    public TournamentListServiceWithCache(ITournamentListService iTournamentListService) {
        this._service = iTournamentListService;
    }

    private boolean hasCachedPart(int n, int n2) {
        for (int i = n2; i < n2 + n; ++i) {
            if (this._cachedResult.get(i) != null) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cachePart(int n, List<TournamentInfo> object) {
        CharSequence charSequence;
        Object object2 = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("caching part start: ");
        stringBuilder.append(n);
        stringBuilder.append(" count: ");
        if (object != null) {
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append(object.size());
            charSequence = charSequence.toString();
        } else {
            charSequence = "null";
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" to cache");
        object2.debug("TournamentListServiceWithCache", stringBuilder.toString());
        int n2 = 0;
        charSequence = this._cachedResult;
        synchronized (charSequence) {
            object = object.iterator();
            while (object.hasNext()) {
                object2 = (TournamentInfo)object.next();
                this._cachedResult.put(n + n2, object2);
                ++n2;
            }
            return;
        }
    }

    public List<TournamentInfo> getCachedPart(int n, int n2) {
        Object object = Logger.getInstance();
        Object object2 = new StringBuilder();
        object2.append("taking part start:");
        object2.append(n2);
        object2.append(" count:");
        object2.append(n);
        object2.append(" from cache");
        object.debug("TournamentListServiceWithCache", object2.toString());
        object = new ArrayList(n);
        for (int i = n2; i < n2 + n; ++i) {
            object2 = (TournamentInfo)this._cachedResult.get(i);
            if (object2 == null) continue;
            object.add(object2);
        }
        return object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void getTournaments(final int n, final int n2, final LoadCommandCallback<List<TournamentInfo>> loadCommandCallbackWrapper) {
        long l = this._timeLastUpdate;
        long l2 = System.currentTimeMillis();
        boolean bl = false;
        boolean bl2 = l < l2 - 600000L;
        if (bl2 || !this.hasCachedPart(n2, n)) {
            bl = true;
        }
        if (!bl) {
            new Handler(Looper.getMainLooper()).postAtFrontOfQueue(new Runnable(){

                @Override
                public void run() {
                    loadCommandCallbackWrapper.loadingSucceded(TournamentListServiceWithCache.this.getCachedPart(n2, n));
                }
            });
            return;
        }
        SparseArray<TournamentInfo> sparseArray = this._cachedResult;
        // MONITORENTER : sparseArray
        if (bl2) {
            this._cachedResult.clear();
            // MONITOREXIT : sparseArray
        }
        this._timeLastUpdate = System.currentTimeMillis();
        loadCommandCallbackWrapper = new LoadCommandCallbackWrapper<List<TournamentInfo>>(loadCommandCallbackWrapper){

            @Override
            protected void succeded(List<TournamentInfo> list) {
                super.succeded(list);
                TournamentListServiceWithCache.this.cachePart(n, list);
            }
        };
        this._service.getTournaments(n, n2, (LoadCommandCallback<List<TournamentInfo>>)loadCommandCallbackWrapper);
        return;
        catch (Throwable throwable) {
            throw throwable;
        }
    }

}
