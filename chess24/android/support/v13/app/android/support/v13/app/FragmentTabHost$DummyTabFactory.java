/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.widget.TabHost
 *  android.widget.TabHost$TabContentFactory
 */
package android.support.v13.app;

import android.content.Context;
import android.support.v13.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;

static class FragmentTabHost.DummyTabFactory
implements TabHost.TabContentFactory {
    private final Context mContext;

    public FragmentTabHost.DummyTabFactory(Context context) {
        this.mContext = context;
    }

    public View createTabContent(String string) {
        string = new View(this.mContext);
        string.setMinimumWidth(0);
        string.setMinimumHeight(0);
        return string;
    }
}
