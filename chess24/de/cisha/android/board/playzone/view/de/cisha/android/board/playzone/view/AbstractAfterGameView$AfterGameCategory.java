/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package de.cisha.android.board.playzone.view;

import android.content.Context;
import android.content.res.Resources;
import de.cisha.android.board.playzone.view.AbstractAfterGameView;

protected static enum AbstractAfterGameView.AfterGameCategory {
    REMATCH(2131690116, 2131296694, 2131231558, 2131231559, 2131231557),
    NEW_GAME(2131690115, 2131296693, 2131231555, 2131231556),
    ANALYZE(2131690114, 2131296690, 2131231551, 2131231552);
    
    private int _nameResourceId;
    private int _resId;
    private int _resIdItemDrawable;
    private int _resIdItemDrawableActive;
    private int _resIdItemDrawableDisabled = -1;

    private AbstractAfterGameView.AfterGameCategory(int n2, int n3, int n4, int n5) {
        this(n2, n3, n4, n5, -1);
    }

    private AbstractAfterGameView.AfterGameCategory(int n2, int n3, int n4, int n5, int n6) {
        this._nameResourceId = n2;
        this._resId = n3;
        this._resIdItemDrawable = n4;
        this._resIdItemDrawableActive = n5;
        this._resIdItemDrawableDisabled = n6;
    }

    public String getName(Context context) {
        return context.getResources().getString(this._nameResourceId);
    }

    public int getResId() {
        return this._resId;
    }

    public int getResIdItemDrawable() {
        return this._resIdItemDrawable;
    }

    public int getResIdItemDrawableActive() {
        return this._resIdItemDrawableActive;
    }

    public int getResIdItemDrawableDisabled() {
        if (this._resIdItemDrawableDisabled == -1) {
            return this._resIdItemDrawable;
        }
        return this._resIdItemDrawableDisabled;
    }
}
