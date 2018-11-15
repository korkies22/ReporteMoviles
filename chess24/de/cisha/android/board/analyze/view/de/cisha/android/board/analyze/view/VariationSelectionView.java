/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ScrollView
 *  android.widget.TextView
 */
package de.cisha.android.board.analyze.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.Piece;
import java.util.Iterator;
import java.util.List;

public class VariationSelectionView
extends ArrowBottomContainerView {
    private LinearLayout _mainView;
    private MoveSelector _moveSelector;
    private ScrollView _scroller;

    public VariationSelectionView(Context context, AttributeSet attributeSet) {
        super(context);
        this.init();
    }

    public VariationSelectionView(Context context, MoveContainer moveContainer) {
        super(context);
        this.init();
        this.showVariations(moveContainer);
    }

    private void init() {
        this._scroller = new ScrollView(this.getContext());
        this._mainView = new LinearLayout(this.getContext());
        this._mainView.setOrientation(1);
        this._scroller.addView((View)this._mainView);
        this.addView((View)this._scroller, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -1));
    }

    public void setMoveSelector(MoveSelector moveSelector) {
        this._moveSelector = moveSelector;
    }

    public void showVariations(MoveContainer object) {
        this._mainView.removeAllViews();
        Iterator<Move> iterator = object.getChildren().iterator();
        boolean bl = true;
        while (iterator.hasNext()) {
            final Move move = iterator.next();
            TextView textView = new TextView(this.getContext());
            textView.setTextSize(20.0f);
            textView.setPadding(10, 10, 10, 10);
            textView.setBackgroundResource(2131230814);
            textView.setTextColor(this.getResources().getColorStateList(2131099677));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(move.getMoveNumber());
            stringBuilder.append(". ");
            object = move.getPiece().getColor() ? "" : "... ";
            stringBuilder.append((String)object);
            stringBuilder.append(move.getFAN());
            textView.setText((CharSequence)stringBuilder.toString());
            textView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    for (int i = 0; i < VariationSelectionView.this._mainView.getChildCount(); ++i) {
                        VariationSelectionView.this._mainView.getChildAt(i).setSelected(false);
                    }
                    view.setSelected(true);
                    view.postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            if (VariationSelectionView.this._moveSelector != null) {
                                VariationSelectionView.this._moveSelector.selectMove(move);
                            }
                        }
                    }, 100L);
                }

            });
            if (bl) {
                textView.setSelected(true);
                bl = false;
            } else {
                object = new ImageView(this.getContext());
                object.setImageResource(2131231123);
                object.setScaleType(ImageView.ScaleType.FIT_XY);
                this._mainView.addView((View)object, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
            }
            this._mainView.addView((View)textView);
        }
        this.setVisibility(0);
    }

}
