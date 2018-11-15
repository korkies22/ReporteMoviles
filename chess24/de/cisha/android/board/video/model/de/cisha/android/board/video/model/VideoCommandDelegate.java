/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.model;

import de.cisha.chess.model.SEP;

public interface VideoCommandDelegate {
    public boolean addVideoMove(SEP var1, int var2);

    public boolean selectGame(int var1);

    public boolean selectMove(int var1);
}
