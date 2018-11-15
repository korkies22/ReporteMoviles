/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.chess.model;

import android.content.res.Resources;
import de.cisha.chess.R;
import de.cisha.chess.model.ClockSetting;

public static enum ClockSetting.GameClockType {
    BULLET(R.string.game_type_bullet),
    BLITZ(R.string.game_type_blitz),
    RAPID(R.string.game_type_rapid),
    STANDARD(R.string.game_type_standard),
    WITHOUT_TIME(R.string.game_type_no_time);
    
    public int _resStringId;

    private ClockSetting.GameClockType(int n2) {
        this._resStringId = n2;
    }

    public String getString(Resources resources) {
        return resources.getString(this._resStringId);
    }
}
