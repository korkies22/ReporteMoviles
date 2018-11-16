// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.view;

import java.util.Iterator;
import android.widget.ImageView.ScaleType;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import de.cisha.chess.model.Move;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import de.cisha.chess.model.MoveContainer;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.ScrollView;
import de.cisha.chess.model.MoveSelector;
import android.widget.LinearLayout;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;

public class VariationSelectionView extends ArrowBottomContainerView
{
    private LinearLayout _mainView;
    private MoveSelector _moveSelector;
    private ScrollView _scroller;
    
    public VariationSelectionView(final Context context, final AttributeSet set) {
        super(context);
        this.init();
    }
    
    public VariationSelectionView(final Context context, final MoveContainer moveContainer) {
        super(context);
        this.init();
        this.showVariations(moveContainer);
    }
    
    private void init() {
        this._scroller = new ScrollView(this.getContext());
        (this._mainView = new LinearLayout(this.getContext())).setOrientation(1);
        this._scroller.addView((View)this._mainView);
        this.addView((View)this._scroller, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -1));
    }
    
    public void setMoveSelector(final MoveSelector moveSelector) {
        this._moveSelector = moveSelector;
    }
    
    public void showVariations(final MoveContainer moveContainer) {
        this._mainView.removeAllViews();
        final Iterator<Move> iterator = moveContainer.getChildren().iterator();
        int n = 1;
        while (iterator.hasNext()) {
            final Move move = iterator.next();
            final TextView textView = new TextView(this.getContext());
            textView.setTextSize(20.0f);
            textView.setPadding(10, 10, 10, 10);
            textView.setBackgroundResource(2131230814);
            textView.setTextColor(this.getResources().getColorStateList(2131099677));
            final StringBuilder sb = new StringBuilder();
            sb.append(move.getMoveNumber());
            sb.append(". ");
            String s;
            if (move.getPiece().getColor()) {
                s = "";
            }
            else {
                s = "... ";
            }
            sb.append(s);
            sb.append(move.getFAN());
            textView.setText((CharSequence)sb.toString());
            textView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    for (int i = 0; i < VariationSelectionView.this._mainView.getChildCount(); ++i) {
                        VariationSelectionView.this._mainView.getChildAt(i).setSelected(false);
                    }
                    view.setSelected(true);
                    view.postDelayed((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (VariationSelectionView.this._moveSelector != null) {
                                VariationSelectionView.this._moveSelector.selectMove(move);
                            }
                        }
                    }, 100L);
                }
            });
            if (n != 0) {
                textView.setSelected(true);
                n = 0;
            }
            else {
                final ImageView imageView = new ImageView(this.getContext());
                imageView.setImageResource(2131231123);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                this._mainView.addView((View)imageView, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
            }
            this._mainView.addView((View)textView);
        }
        this.setVisibility(0);
    }
}
