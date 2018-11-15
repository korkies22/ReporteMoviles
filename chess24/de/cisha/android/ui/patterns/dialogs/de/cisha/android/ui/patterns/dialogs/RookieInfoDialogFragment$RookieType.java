/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.patterns.dialogs;

import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

public static enum RookieInfoDialogFragment.RookieType {
    NO_INTERNET(R.drawable.rookie_no_internet),
    NO_LOGIN(R.drawable.rookie_nologin),
    PROMOTION(R.drawable.rookie_promotiondialog),
    LOST_SERVER_CONNECTION(R.drawable.rookie_serverconnection),
    MORE(R.drawable.rookie_promotiondialog),
    INFO(R.drawable.rookie_info),
    RUNNING_GAME(R.drawable.rookie_running_game),
    RUNNING_ENGINE(R.drawable.rookie_running_engine);
    
    private int _imgResId;

    private RookieInfoDialogFragment.RookieType(int n2) {
        this._imgResId = n2;
    }

    static /* synthetic */ int access$000(RookieInfoDialogFragment.RookieType rookieType) {
        return rookieType._imgResId;
    }
}
