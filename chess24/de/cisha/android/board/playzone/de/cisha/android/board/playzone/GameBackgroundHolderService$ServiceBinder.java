/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 */
package de.cisha.android.board.playzone;

import android.os.Binder;
import de.cisha.android.board.playzone.GameBackgroundHolderService;

public class GameBackgroundHolderService.ServiceBinder
extends Binder {
    public GameBackgroundHolderService getService() {
        return GameBackgroundHolderService.this;
    }
}
