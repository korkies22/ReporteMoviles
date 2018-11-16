// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import java.util.Iterator;
import java.util.LinkedList;
import android.os.Looper;
import java.util.List;
import android.os.Handler;
import android.support.v4.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment
{
    private boolean _flagIsDestroyed;
    private boolean _flagOnStartCalled;
    private Handler _mainThreadHandler;
    private List<Runnable> _runnableQueue;
    
    public BaseDialogFragment() {
        this._flagIsDestroyed = false;
        this._flagOnStartCalled = false;
        this._mainThreadHandler = new Handler(Looper.getMainLooper());
        this._runnableQueue = new LinkedList<Runnable>();
    }
    
    private void exececuteRunnablesInQueue() {
        if (!this._runnableQueue.isEmpty()) {
            final Iterator<Runnable> iterator = this._runnableQueue.iterator();
            while (iterator.hasNext()) {
                iterator.next().run();
            }
            this._runnableQueue.clear();
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        this._flagIsDestroyed = true;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this.exececuteRunnablesInQueue();
        this._flagOnStartCalled = true;
    }
    
    protected void runOnUiThreadBetweenStartAndDestroy(final Runnable runnable) {
        this._mainThreadHandler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (BaseDialogFragment.this._flagOnStartCalled && !BaseDialogFragment.this._flagIsDestroyed) {
                    runnable.run();
                    return;
                }
                BaseDialogFragment.this._runnableQueue.add(runnable);
            }
        });
    }
}
