// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import android.os.Handler;
import android.os.Looper;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.ArrayList;
import java.util.Iterator;
import de.cisha.chess.util.Logger;
import java.util.List;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import android.util.SparseArray;
import de.cisha.android.board.broadcast.model.ITournamentListService;

public class TournamentListServiceWithCache implements ITournamentListService
{
    private static final int CACHING_TIME_MILLIS = 600000;
    private SparseArray<TournamentInfo> _cachedResult;
    private ITournamentListService _service;
    private long _timeLastUpdate;
    
    public TournamentListServiceWithCache(final ITournamentListService service) {
        this._cachedResult = (SparseArray<TournamentInfo>)new SparseArray();
        this._service = service;
    }
    
    private boolean hasCachedPart(final int n, final int n2) {
        for (int i = n2; i < n2 + n; ++i) {
            if (this._cachedResult.get(i) == null) {
                return false;
            }
        }
        return true;
    }
    
    public void cachePart(final int n, final List<TournamentInfo> list) {
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("caching part start: ");
        sb.append(n);
        sb.append(" count: ");
        String string;
        if (list != null) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(list.size());
            string = sb2.toString();
        }
        else {
            string = "null";
        }
        sb.append(string);
        sb.append(" to cache");
        instance.debug("TournamentListServiceWithCache", sb.toString());
        int n2 = 0;
        synchronized (this._cachedResult) {
            final Iterator<TournamentInfo> iterator = list.iterator();
            while (iterator.hasNext()) {
                this._cachedResult.put(n + n2, (Object)iterator.next());
                ++n2;
            }
        }
    }
    
    public List<TournamentInfo> getCachedPart(final int n, final int n2) {
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("taking part start:");
        sb.append(n2);
        sb.append(" count:");
        sb.append(n);
        sb.append(" from cache");
        instance.debug("TournamentListServiceWithCache", sb.toString());
        final ArrayList<TournamentInfo> list = new ArrayList<TournamentInfo>(n);
        for (int i = n2; i < n2 + n; ++i) {
            final TournamentInfo tournamentInfo = (TournamentInfo)this._cachedResult.get(i);
            if (tournamentInfo != null) {
                list.add(tournamentInfo);
            }
        }
        return list;
    }
    
    @Override
    public void getTournaments(final int n, final int n2, final LoadCommandCallback<List<TournamentInfo>> loadCommandCallback) {
        final long timeLastUpdate = this._timeLastUpdate;
        final long currentTimeMillis = System.currentTimeMillis();
        boolean b = false;
        final boolean b2 = timeLastUpdate < currentTimeMillis - 600000L;
        if (b2 || !this.hasCachedPart(n2, n)) {
            b = true;
        }
        if (!b) {
            new Handler(Looper.getMainLooper()).postAtFrontOfQueue((Runnable)new Runnable() {
                @Override
                public void run() {
                    loadCommandCallback.loadingSucceded(TournamentListServiceWithCache.this.getCachedPart(n2, n));
                }
            });
            return;
        }
        final SparseArray<TournamentInfo> cachedResult = this._cachedResult;
        // monitorenter(cachedResult)
        Label_0107: {
            if (!b2) {
                break Label_0107;
            }
            while (true) {
                try {
                    this._cachedResult.clear();
                    // monitorexit(cachedResult)
                    this._timeLastUpdate = System.currentTimeMillis();
                    this._service.getTournaments(n, n2, new LoadCommandCallbackWrapper<List<TournamentInfo>>(loadCommandCallback) {
                        @Override
                        protected void succeded(final List<TournamentInfo> list) {
                            super.succeded(list);
                            TournamentListServiceWithCache.this.cachePart(n, list);
                        }
                    });
                    return;
                    // monitorexit(cachedResult)
                    throw;
                }
                finally {
                    continue;
                }
                break;
            }
        }
    }
}
