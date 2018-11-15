/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.model.BoardMarks;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.MoveContainer;
import java.util.Map;

public class GameAndHighlights {
    public Game game;
    public Map<MoveContainer, BoardMarks> highlights;
}
