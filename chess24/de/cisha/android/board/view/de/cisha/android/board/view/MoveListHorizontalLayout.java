/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.graphics.Color
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.animation.RGBTransformation;
import de.cisha.android.board.view.MoveListView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.Piece;
import java.util.List;

public class MoveListHorizontalLayout
extends LinearLayout
implements MoveListView {
    private static int _moveNo;
    private boolean _moveSelectionActivated = true;
    private MoveSelector _moveSelector;

    public MoveListHorizontalLayout(Context context) {
        super(context);
    }

    public MoveListHorizontalLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void addViewWithSAN(Move move, boolean bl) {
        this.addViewWithSAN(move, bl, null);
    }

    private void addViewWithSAN(Move object, boolean bl, final TextView textView) {
        CharSequence charSequence;
        if (textView == null) {
            textView = new TextView(this.getContext());
            charSequence = Typeface.createFromAsset((AssetManager)this.getContext().getAssets(), (String)"fonts/TREBUC.TTF");
            textView.setTextColor(Color.rgb((int)0, (int)0, (int)0));
            textView.setPadding(5, 3, 5, 3);
            textView.setTypeface((Typeface)charSequence);
            this.addView((View)textView);
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (object.getPiece().getColor()) {
            int n;
            charSequence = new StringBuilder();
            _moveNo = n = _moveNo + 1;
            charSequence.append(n);
            charSequence.append(". ");
            charSequence = charSequence.toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(object.getSAN());
        stringBuilder.append("   ");
        textView.setText((CharSequence)stringBuilder.toString());
        textView.setTextSize(15.0f);
        textView.setOnClickListener(new View.OnClickListener((Move)object){
            final /* synthetic */ Move val$move;
            {
                this.val$move = move;
            }

            public void onClick(View view) {
                if (MoveListHorizontalLayout.this.isMoveSelectionActivated() && MoveListHorizontalLayout.this._moveSelector != null) {
                    MoveListHorizontalLayout.this._moveSelector.selectMove(this.val$move);
                }
            }
        });
        if (bl) {
            object = new RGBTransformation(textView, 255, 0, 95, 0, 82, 0);
            object.setDuration(2000L);
            object.setFillAfter(true);
            object.setAnimationListener(new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                    textView.clearAnimation();
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });
            textView.startAnimation((Animation)object);
        }
    }

    @Override
    public void allMovesChanged(final MoveContainer moveContainer) {
        this.post(new Runnable(){

            @Override
            public void run() {
                int n;
                _moveNo = 0;
                List<Move> list = moveContainer.getAllMainLineMoves();
                for (n = 0; n < Math.min(MoveListHorizontalLayout.this.getChildCount(), list.size()); ++n) {
                    TextView textView = (TextView)MoveListHorizontalLayout.this.getChildAt(n);
                    MoveListHorizontalLayout.this.addViewWithSAN(list.get(n), false, textView);
                }
                for (n = MoveListHorizontalLayout.this.getChildCount() - 1; n >= list.size(); --n) {
                    MoveListHorizontalLayout.this.removeView(MoveListHorizontalLayout.this.getChildAt(n));
                }
                for (n = MoveListHorizontalLayout.this.getChildCount(); n < list.size(); ++n) {
                    MoveListHorizontalLayout.this.addViewWithSAN(list.get(n), false);
                }
                MoveListHorizontalLayout.this.invalidate();
            }
        });
    }

    @Override
    public boolean canSkipMoves() {
        return true;
    }

    public boolean isMoveSelectionActivated() {
        return this._moveSelectionActivated;
    }

    @Override
    public void newMove(final Move move) {
        this.post(new Runnable(){

            @Override
            public void run() {
                MoveListHorizontalLayout.this.addViewWithSAN(move, true);
            }
        });
    }

    @Override
    public void selectedMoveChanged(Move move) {
    }

    @Override
    public void setMoveSelector(MoveSelector moveSelector) {
        this._moveSelector = moveSelector;
    }

    @Override
    public void setNavigationSelectionEnabled(boolean bl) {
        this._moveSelectionActivated = bl;
    }

}
