/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.View
 */
package android.support.v4.widget;

import android.support.annotation.RequiresApi;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@RequiresApi(value=16)
static class SlidingPaneLayout.SlidingPanelLayoutImplJB
extends SlidingPaneLayout.SlidingPanelLayoutImplBase {
    private Method mGetDisplayList;
    private Field mRecreateDisplayList;

    SlidingPaneLayout.SlidingPanelLayoutImplJB() {
        try {
            this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", null);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)SlidingPaneLayout.TAG, (String)"Couldn't fetch getDisplayList method; dimming won't work right.", (Throwable)noSuchMethodException);
        }
        try {
            this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
            this.mRecreateDisplayList.setAccessible(true);
            return;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.e((String)SlidingPaneLayout.TAG, (String)"Couldn't fetch mRecreateDisplayList field; dimming will be slow.", (Throwable)noSuchFieldException);
            return;
        }
    }

    @Override
    public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
        if (this.mGetDisplayList != null && this.mRecreateDisplayList != null) {
            try {
                this.mRecreateDisplayList.setBoolean((Object)view, true);
                this.mGetDisplayList.invoke((Object)view, null);
            }
            catch (Exception exception) {
                Log.e((String)SlidingPaneLayout.TAG, (String)"Error refreshing display list state", (Throwable)exception);
            }
            super.invalidateChildRegion(slidingPaneLayout, view);
            return;
        }
        view.invalidate();
    }
}
