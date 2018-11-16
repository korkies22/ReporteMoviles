// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import java.util.Iterator;
import android.os.Looper;
import java.util.LinkedList;
import java.util.List;
import android.os.Handler;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment
{
    private boolean _flagOnDestroyCalled;
    private boolean _flagOnStartCalled;
    private Handler _handlerMainThread;
    protected List<Runnable> _onStartRunnables;
    
    public BaseFragment() {
        this._flagOnStartCalled = false;
        this._onStartRunnables = new LinkedList<Runnable>();
        this._handlerMainThread = new Handler(Looper.getMainLooper());
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        this._flagOnDestroyCalled = true;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this._flagOnStartCalled = true;
        if (this._onStartRunnables != null) {
            final Iterator<Runnable> iterator = this._onStartRunnables.iterator();
            while (iterator.hasNext()) {
                iterator.next().run();
            }
            this._onStartRunnables.clear();
            this._onStartRunnables = null;
        }
    }
    
    protected void runOnUiThreadBetweenStartAndDestroy(final Runnable runnable) {
        final Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                if (!BaseFragment.this._flagOnDestroyCalled) {
                    if (!BaseFragment.this._flagOnStartCalled && BaseFragment.this._onStartRunnables != null) {
                        BaseFragment.this._onStartRunnables.add(runnable);
                        return;
                    }
                    runnable.run();
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable2.run();
            return;
        }
        this._handlerMainThread.post((Runnable)runnable2);
    }
}
