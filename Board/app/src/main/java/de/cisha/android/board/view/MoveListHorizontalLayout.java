// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import java.util.List;
import de.cisha.chess.model.MoveContainer;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import de.cisha.android.animation.RGBTransformation;
import android.view.View.OnClickListener;
import android.view.View;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;
import de.cisha.chess.model.Move;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.chess.model.MoveSelector;
import android.widget.LinearLayout;

public class MoveListHorizontalLayout extends LinearLayout implements MoveListView
{
    private static int _moveNo;
    private boolean _moveSelectionActivated;
    private MoveSelector _moveSelector;
    
    public MoveListHorizontalLayout(final Context context) {
        super(context);
        this._moveSelectionActivated = true;
    }
    
    public MoveListHorizontalLayout(final Context context, final AttributeSet set) {
        super(context, set);
        this._moveSelectionActivated = true;
    }
    
    private void addViewWithSAN(final Move move, final boolean b) {
        this.addViewWithSAN(move, b, null);
    }
    
    private void addViewWithSAN(final Move move, final boolean b, TextView textView) {
        if (textView == null) {
            textView = new TextView(this.getContext());
            final Typeface fromAsset = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/TREBUC.TTF");
            textView.setTextColor(Color.rgb(0, 0, 0));
            textView.setPadding(5, 3, 5, 3);
            textView.setTypeface(fromAsset);
            this.addView((View)textView);
        }
        final StringBuilder sb = new StringBuilder();
        String string;
        if (move.getPiece().getColor()) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(++MoveListHorizontalLayout._moveNo);
            sb2.append(". ");
            string = sb2.toString();
        }
        else {
            string = "";
        }
        sb.append(string);
        sb.append(move.getSAN());
        sb.append("   ");
        textView.setText((CharSequence)sb.toString());
        textView.setTextSize(15.0f);
        textView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (MoveListHorizontalLayout.this.isMoveSelectionActivated() && MoveListHorizontalLayout.this._moveSelector != null) {
                    MoveListHorizontalLayout.this._moveSelector.selectMove(move);
                }
            }
        });
        if (b) {
            final RGBTransformation rgbTransformation = new RGBTransformation(textView, 255, 0, 95, 0, 82, 0);
            rgbTransformation.setDuration(2000L);
            rgbTransformation.setFillAfter(true);
            rgbTransformation.setAnimationListener((Animation.AnimationListener)new Animation.AnimationListener() {
                public void onAnimationEnd(final Animation animation) {
                    textView.clearAnimation();
                }
                
                public void onAnimationRepeat(final Animation animation) {
                }
                
                public void onAnimationStart(final Animation animation) {
                }
            });
            textView.startAnimation((Animation)rgbTransformation);
        }
    }
    
    public void allMovesChanged(final MoveContainer moveContainer) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                MoveListHorizontalLayout._moveNo = 0;
                final List<Move> allMainLineMoves = moveContainer.getAllMainLineMoves();
                for (int i = 0; i < Math.min(MoveListHorizontalLayout.this.getChildCount(), allMainLineMoves.size()); ++i) {
                    MoveListHorizontalLayout.this.addViewWithSAN(allMainLineMoves.get(i), false, (TextView)MoveListHorizontalLayout.this.getChildAt(i));
                }
                for (int j = MoveListHorizontalLayout.this.getChildCount() - 1; j >= allMainLineMoves.size(); --j) {
                    MoveListHorizontalLayout.this.removeView(MoveListHorizontalLayout.this.getChildAt(j));
                }
                for (int k = MoveListHorizontalLayout.this.getChildCount(); k < allMainLineMoves.size(); ++k) {
                    MoveListHorizontalLayout.this.addViewWithSAN(allMainLineMoves.get(k), false);
                }
                MoveListHorizontalLayout.this.invalidate();
            }
        });
    }
    
    public boolean canSkipMoves() {
        return true;
    }
    
    public boolean isMoveSelectionActivated() {
        return this._moveSelectionActivated;
    }
    
    public void newMove(final Move move) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                MoveListHorizontalLayout.this.addViewWithSAN(move, true);
            }
        });
    }
    
    public void selectedMoveChanged(final Move move) {
    }
    
    public void setMoveSelector(final MoveSelector moveSelector) {
        this._moveSelector = moveSelector;
    }
    
    public void setNavigationSelectionEnabled(final boolean moveSelectionActivated) {
        this._moveSelectionActivated = moveSelectionActivated;
    }
}
