/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.setup.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import de.cisha.android.board.setup.view.DragBoardView;
import de.cisha.android.board.view.AbstractBoardViewFactory;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.position.Position;

public class DragBoardViewFactory
extends AbstractBoardViewFactory<DragBoardView> {
    private static DragBoardViewFactory _instance;

    private DragBoardViewFactory() {
    }

    public static DragBoardViewFactory getInstance() {
        if (_instance == null) {
            _instance = new DragBoardViewFactory();
        }
        return _instance;
    }

    @Override
    protected DragBoardView createBoardView(Context context, FragmentManager fragmentManager) {
        return new DragBoardView(context);
    }

    @Override
    protected DragBoardView createBoardView(Context context, FragmentManager fragmentManager, Position position) {
        return new DragBoardView(context, position);
    }
}
