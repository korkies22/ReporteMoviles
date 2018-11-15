/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import de.cisha.android.board.RookiePromotionFragment;
import de.cisha.android.board.view.AbstractBoardViewFactory;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.position.Position;
import java.lang.ref.WeakReference;

public class BoardViewFactory
extends AbstractBoardViewFactory<BoardView> {
    private static BoardViewFactory _instance;

    private BoardViewFactory() {
    }

    public static BoardViewFactory getInstance() {
        if (_instance == null) {
            _instance = new BoardViewFactory();
        }
        return _instance;
    }

    @Override
    protected BoardView createBoardView(Context object, final FragmentManager fragmentManager) {
        object = new BoardView((Context)object);
        object.setPromotionPresenter(new BoardView.PromotionPresenter(){
            private RookiePromotionFragment _fragment;
            private WeakReference<FragmentManager> _fragmentManagerRef;
            {
                this._fragmentManagerRef = new WeakReference<FragmentManager>(fragmentManager);
            }

            @Override
            public void dismissDialog() {
                if (this._fragment != null) {
                    this._fragment.dismiss();
                }
            }

            @Override
            public boolean isPromotionShown() {
                if (this._fragment != null && this._fragment.isVisible()) {
                    return true;
                }
                return false;
            }

            @Override
            public void showPromotionDialog(View view) {
                FragmentManager fragmentManager2 = (FragmentManager)this._fragmentManagerRef.get();
                if (fragmentManager2 != null) {
                    this._fragment = new RookiePromotionFragment();
                    this._fragment.setPieceChooserView(view);
                    this._fragment.show(fragmentManager2, "");
                }
            }
        });
        return object;
    }

    @Override
    protected BoardView createBoardView(Context context, FragmentManager fragmentManager, Position position) {
        return new BoardView(context, position);
    }

}
