/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.analyze.model.OpeningMoveInformation;
import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import de.cisha.android.board.service.IOpeningTreeService;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.Position;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class OpeningTreeServiceMock
implements IOpeningTreeService {
    private static OpeningTreeServiceMock _instance;
    private Random _random = new Random();

    private OpeningTreeServiceMock() {
    }

    public static OpeningTreeServiceMock getInstance() {
        if (_instance == null) {
            _instance = new OpeningTreeServiceMock();
        }
        return _instance;
    }

    @Override
    public void closeBook() {
    }

    @Override
    public OpeningPositionInformation getInformationForPosition(Position object) {
        LinkedList<OpeningMoveInformation> linkedList = new LinkedList<OpeningMoveInformation>();
        object = object.getAllMoves();
        int n = this._random.nextInt(20);
        for (int i = 0; i < Math.min(n, object.size()); ++i) {
            float f = this._random.nextFloat();
            float f2 = this._random.nextFloat();
            float f3 = 1.0f - f;
            linkedList.add(new OpeningMoveInformation((Move)object.get(i), this._random.nextInt(2500), f, f2, f3 - (f2 *= f3), this._random.nextInt(1000) + 2000, this._random.nextInt(1000) + 2000));
        }
        return new OpeningPositionInformation(linkedList);
    }
}
