/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 */
package de.cisha.android.board.settings;

import android.content.Context;
import android.view.View;

public abstract class SettingsViewController {
    private Context _context;

    public SettingsViewController(Context context) {
        this._context = context;
    }

    protected Context getContext() {
        return this._context;
    }

    public abstract View getSettingsView();
}
