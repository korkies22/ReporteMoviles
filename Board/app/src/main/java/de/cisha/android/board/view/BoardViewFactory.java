// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import de.cisha.chess.model.position.Position;
import android.view.View;
import java.lang.ref.WeakReference;
import de.cisha.android.board.RookiePromotionFragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;

public class BoardViewFactory extends AbstractBoardViewFactory<BoardView>
{
    private static BoardViewFactory _instance;
    
    public static BoardViewFactory getInstance() {
        if (BoardViewFactory._instance == null) {
            BoardViewFactory._instance = new BoardViewFactory();
        }
        return BoardViewFactory._instance;
    }
    
    @Override
    protected BoardView createBoardView(final Context context, final FragmentManager fragmentManager) {
        final BoardView boardView = new BoardView(context);
        boardView.setPromotionPresenter((BoardView.PromotionPresenter)new BoardView.PromotionPresenter() {
            private RookiePromotionFragment _fragment;
            private WeakReference<FragmentManager> _fragmentManagerRef = new WeakReference<FragmentManager>(fragmentManager);
            
            @Override
            public void dismissDialog() {
                if (this._fragment != null) {
                    this._fragment.dismiss();
                }
            }
            
            @Override
            public boolean isPromotionShown() {
                return this._fragment != null && this._fragment.isVisible();
            }
            
            @Override
            public void showPromotionDialog(final View pieceChooserView) {
                final FragmentManager fragmentManager = this._fragmentManagerRef.get();
                if (fragmentManager != null) {
                    (this._fragment = new RookiePromotionFragment()).setPieceChooserView(pieceChooserView);
                    this._fragment.show(fragmentManager, "");
                }
            }
        });
        return boardView;
    }
    
    @Override
    protected BoardView createBoardView(final Context context, final FragmentManager fragmentManager, final Position position) {
        return new BoardView(context, position);
    }
}
