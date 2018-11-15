/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.chess.engine.UCIEngine;

public interface IEngineService {
    public UCIEngine getDefaultSingleEngine();

    @Deprecated
    public UCIEngine getEngineWithName(String var1);
}
