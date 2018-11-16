// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.util.List;
import de.cisha.chess.model.Move;
import de.cisha.android.board.analyze.model.OpeningMoveInformation;
import java.util.LinkedList;
import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import de.cisha.chess.model.position.Position;
import java.util.Random;

public class OpeningTreeServiceMock implements IOpeningTreeService
{
    private static OpeningTreeServiceMock _instance;
    private Random _random;
    
    private OpeningTreeServiceMock() {
        this._random = new Random();
    }
    
    public static OpeningTreeServiceMock getInstance() {
        if (OpeningTreeServiceMock._instance == null) {
            OpeningTreeServiceMock._instance = new OpeningTreeServiceMock();
        }
        return OpeningTreeServiceMock._instance;
    }
    
    @Override
    public void closeBook() {
    }
    
    @Override
    public OpeningPositionInformation getInformationForPosition(final Position position) {
        final LinkedList<OpeningMoveInformation> list = new LinkedList<OpeningMoveInformation>();
        final List<Move> allMoves = position.getAllMoves();
        for (int nextInt = this._random.nextInt(20), i = 0; i < Math.min(nextInt, allMoves.size()); ++i) {
            final float nextFloat = this._random.nextFloat();
            final float nextFloat2 = this._random.nextFloat();
            final float n = 1.0f - nextFloat;
            final float n2 = nextFloat2 * n;
            list.add(new OpeningMoveInformation(allMoves.get(i), this._random.nextInt(2500), nextFloat, n2, n - n2, this._random.nextInt(1000) + 2000, this._random.nextInt(1000) + 2000));
        }
        return new OpeningPositionInformation(list);
    }
}
