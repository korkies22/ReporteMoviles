/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package de.cisha.android.ui.patterns.dialogs;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BaseDialogFragment
extends DialogFragment {
    private boolean _flagIsDestroyed = false;
    private boolean _flagOnStartCalled = false;
    private Handler _mainThreadHandler = new Handler(Looper.getMainLooper());
    private List<Runnable> _runnableQueue = new LinkedList<Runnable>();

    private void exececuteRunnablesInQueue() {
        if (!this._runnableQueue.isEmpty()) {
            Iterator<Runnable> iterator = this._runnableQueue.iterator();
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
        this._mainThreadHandler.post(new Runnable(){

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
