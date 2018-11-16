// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.settings;

import android.view.View;
import android.content.Context;

public abstract class SettingsViewController
{
    private Context _context;
    
    public SettingsViewController(final Context context) {
        this._context = context;
    }
    
    protected Context getContext() {
        return this._context;
    }
    
    public abstract View getSettingsView();
}
