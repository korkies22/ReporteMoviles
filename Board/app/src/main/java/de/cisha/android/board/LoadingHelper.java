// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import de.cisha.chess.util.Logger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LoadingHelper
{
    private LoadingHelperListener _listener;
    private List<Object> _loadables;
    
    public LoadingHelper(final LoadingHelperListener listener) {
        this._loadables = Collections.synchronizedList(new LinkedList<Object>());
        this._listener = listener;
    }
    
    public void addLoadable(final Object o) {
        // monitorenter(this)
        Label_0016: {
            if (o != null) {
                break Label_0016;
            }
            while (true) {
                try {
                    throw new IllegalArgumentException("loadable must not be null");
                    // iftrue(Label_0037:, !this._loadables.isEmpty())
                    this._listener.loadingStart();
                    // monitorexit(this)
                    Label_0037: {
                        break Label_0037;
                        throw;
                    }
                    this._loadables.add(o);
                }
                // monitorexit(this)
                finally {
                    continue;
                }
                break;
            }
        }
    }
    
    public void loadingCompleted(final Object o) {
        synchronized (this) {
            if (!this._loadables.remove(o)) {
                Logger.getInstance().error(this.getClass().getName(), "Error removing loadable", new IllegalArgumentException("Couldn't remove instance from list of loadables. Maybe it's already done or has not been added."));
            }
            else if (this._loadables.isEmpty() && this._listener != null) {
                this._listener.loadingStop(true);
            }
        }
    }
    
    public void loadingFailed(final Object o) {
        synchronized (this) {
            if (this._loadables.remove(o)) {
                if (this._listener != null) {
                    this._listener.loadingStop(false);
                }
                this._loadables.clear();
            }
        }
    }
    
    public interface LoadingHelperListener
    {
        void loadingStart();
        
        void loadingStop(final boolean p0);
    }
}
