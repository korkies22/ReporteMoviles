/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.view.View
 */
package android.support.v4.app;

import android.graphics.Paint;
import android.support.v4.app.FragmentManagerImpl;
import android.view.View;

class FragmentManagerImpl.AnimateOnHWLayerIfNeededListener
implements Runnable {
    FragmentManagerImpl.AnimateOnHWLayerIfNeededListener() {
    }

    @Override
    public void run() {
        AnimateOnHWLayerIfNeededListener.this.mView.setLayerType(0, null);
    }
}
