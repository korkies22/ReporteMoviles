/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package de.cisha.android.board;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BaseFragment
extends Fragment {
    private boolean _flagOnDestroyCalled;
    private boolean _flagOnStartCalled = false;
    private Handler _handlerMainThread = new Handler(Looper.getMainLooper());
    protected List<Runnable> _onStartRunnables = new LinkedList<Runnable>();

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
            Iterator<Runnable> iterator = this._onStartRunnables.iterator();
            while (iterator.hasNext()) {
                iterator.next().run();
            }
            this._onStartRunnables.clear();
            this._onStartRunnables = null;
        }
    }

    protected void runOnUiThreadBetweenStartAndDestroy(final Runnable runnable) {
        runnable = new Runnable(){

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
            runnable.run();
            return;
        }
        this._handlerMainThread.post(runnable);
    }

}
