/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.chess.util.Logger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LoadingHelper {
    private LoadingHelperListener _listener;
    private List<Object> _loadables = Collections.synchronizedList(new LinkedList());

    public LoadingHelper(LoadingHelperListener loadingHelperListener) {
        this._listener = loadingHelperListener;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void addLoadable(Object var1_1) {
        // MONITORENTER : this
        if (var1_1 != null) ** GOTO lbl5
        throw new IllegalArgumentException("loadable must not be null");
lbl5: // 1 sources:
        if (this._loadables.isEmpty()) {
            this._listener.loadingStart();
        }
        this._loadables.add(var1_1);
        // MONITOREXIT : this
        return;
    }

    public void loadingCompleted(Object object) {
        synchronized (this) {
            if (!this._loadables.remove(object)) {
                object = new IllegalArgumentException("Couldn't remove instance from list of loadables. Maybe it's already done or has not been added.");
                Logger.getInstance().error(this.getClass().getName(), "Error removing loadable", (Throwable)object);
            } else if (this._loadables.isEmpty() && this._listener != null) {
                this._listener.loadingStop(true);
            }
            return;
        }
    }

    public void loadingFailed(Object object) {
        synchronized (this) {
            if (this._loadables.remove(object)) {
                if (this._listener != null) {
                    this._listener.loadingStop(false);
                }
                this._loadables.clear();
            }
            return;
        }
    }

    public static interface LoadingHelperListener {
        public void loadingStart();

        public void loadingStop(boolean var1);
    }

}
