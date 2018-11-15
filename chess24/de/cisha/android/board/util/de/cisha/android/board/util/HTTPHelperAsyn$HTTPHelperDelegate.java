/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.util;

import de.cisha.android.board.util.HTTPHelperAsyn;

public static interface HTTPHelperAsyn.HTTPHelperDelegate {
    public void loadingFailed(int var1, String var2);

    public void urlLoaded(String var1);
}
