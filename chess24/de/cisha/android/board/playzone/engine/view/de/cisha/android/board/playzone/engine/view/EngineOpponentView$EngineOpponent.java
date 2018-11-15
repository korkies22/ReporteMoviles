/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package de.cisha.android.board.playzone.engine.view;

import android.graphics.Color;
import de.cisha.android.board.playzone.engine.view.EngineOpponentView;

public static enum EngineOpponentView.EngineOpponent {
    WEAK(2131231576, Color.rgb((int)132, (int)184, (int)25)),
    MEDIUM(2131231579, Color.rgb((int)255, (int)237, (int)12)),
    STRONG(2131231578, Color.rgb((int)226, (int)69, (int)14)),
    CUSTOM(2131231577, -7829368);
    
    private int _color;
    private int _resId;

    private EngineOpponentView.EngineOpponent(int n2, int n3) {
        this._resId = n2;
        this._color = n3;
    }

    static /* synthetic */ int access$000(EngineOpponentView.EngineOpponent engineOpponent) {
        return engineOpponent._resId;
    }

    static /* synthetic */ int access$100(EngineOpponentView.EngineOpponent engineOpponent) {
        return engineOpponent._color;
    }
}
