// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.setup.view;

import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.position.Position;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import de.cisha.android.board.view.AbstractBoardViewFactory;

public class DragBoardViewFactory extends AbstractBoardViewFactory<DragBoardView>
{
    private static DragBoardViewFactory _instance;
    
    public static DragBoardViewFactory getInstance() {
        if (DragBoardViewFactory._instance == null) {
            DragBoardViewFactory._instance = new DragBoardViewFactory();
        }
        return DragBoardViewFactory._instance;
    }
    
    @Override
    protected DragBoardView createBoardView(final Context context, final FragmentManager fragmentManager) {
        return new DragBoardView(context);
    }
    
    @Override
    protected DragBoardView createBoardView(final Context context, final FragmentManager fragmentManager, final Position position) {
        return new DragBoardView(context, position);
    }
}
