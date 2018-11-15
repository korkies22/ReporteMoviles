/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package de.cisha.android.board.analyze;

import android.os.Handler;
import java.util.TimerTask;

class AbstractAnalyseFragment
extends TimerTask {
    AbstractAnalyseFragment() {
    }

    @Override
    public void run() {
        AbstractAnalyseFragment.this._mainThreadHandler.post(new Runnable(){

            @Override
            public void run() {
                if (!AbstractAnalyseFragment.this.advanceOneMove()) {
                    AbstractAnalyseFragment.this.stopAutoReplay();
                }
            }
        });
    }

}
