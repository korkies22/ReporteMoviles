/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.mainmenu;

import de.cisha.android.board.mainmenu.AbstractMainMenuActivity;

protected static enum AbstractMainMenuActivity.ButtonType {
    NO(0),
    BACK(2131230867),
    SAVE(2131231653),
    SAVE_VALID(2131231653),
    SAVE_INVALID(2131231033),
    CLOSE(2131231037);
    
    private int _resId;

    private AbstractMainMenuActivity.ButtonType(int n2) {
        this._resId = n2;
    }

    static /* synthetic */ int access$000(AbstractMainMenuActivity.ButtonType buttonType) {
        return buttonType._resId;
    }
}
