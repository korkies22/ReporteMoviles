/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import android.support.v4.app.Fragment;
import de.cisha.android.board.broadcast.model.TournamentHolder;

public interface TournamentDetailsHolder {
    public ContentFragmentSetter getContentFragmentSetter();

    public TournamentHolder getTournamentHolder();

    public static interface ContentFragmentSetter {
        public void setContentFragment(Fragment var1, boolean var2, String var3, String var4);
    }

}
