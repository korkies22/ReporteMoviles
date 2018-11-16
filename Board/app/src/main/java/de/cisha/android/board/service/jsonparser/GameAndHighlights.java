// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.model.BoardMarks;
import de.cisha.chess.model.MoveContainer;
import java.util.Map;
import de.cisha.chess.model.Game;

public class GameAndHighlights
{
    public Game game;
    public Map<MoveContainer, BoardMarks> highlights;
}
