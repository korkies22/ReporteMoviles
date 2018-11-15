/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.settings;

import android.view.View;

class SpeedChooserController
implements View.OnClickListener {
    SpeedChooserController() {
    }

    public void onClick(View view) {
        SpeedChooserController.this.goToNextSpeedLevel();
    }
}
