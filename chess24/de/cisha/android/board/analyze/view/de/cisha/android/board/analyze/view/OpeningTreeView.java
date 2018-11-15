/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.TableLayout
 *  android.widget.TableRow
 *  android.widget.TableRow$LayoutParams
 *  android.widget.TextView
 */
package de.cisha.android.board.analyze.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.model.OpeningMoveInformation;
import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import de.cisha.android.board.analyze.view.OpeningMoveInformationView;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IOpeningTreeService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import de.cisha.android.view.BlurrView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObserver;
import java.util.List;

public class OpeningTreeView
extends BlurrView
implements PositionObserver {
    private OpeningTreeViewSourceAdapter _adapter;
    private IContentPresenter _contentPresenter;
    private FrameLayout _innerLayout;
    private MoveExecutor _moveExecutor;
    private TextView _noInformationText;
    private TableLayout _openingTreeTable;

    public OpeningTreeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public OpeningTreeView(Context context, IContentPresenter iContentPresenter) {
        super(context);
        this._contentPresenter = iContentPresenter;
        this.init();
    }

    private void init() {
        this._innerLayout = new FrameLayout(this.getContext());
        this.addView((View)this._innerLayout, -1, -1);
        this._noInformationText = new CustomTextView(this.getContext(), CustomTextViewTextStyle.TEXT, null);
        this._noInformationText.setText(2131689550);
        this._noInformationText.setPadding(10, 10, 10, 10);
        this._noInformationText.setVisibility(8);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2, 17);
        this._innerLayout.addView((View)this._noInformationText, (ViewGroup.LayoutParams)layoutParams);
        this._openingTreeTable = new TableLayout(this.getContext());
        this._openingTreeTable.setBackgroundColor(-1);
        if (this.isInEditMode()) {
            for (int i = 0; i < 5; ++i) {
            }
        }
        this._openingTreeTable.setColumnStretchable(4, true);
        this._innerLayout.addView((View)this._openingTreeTable, -1, -2);
        this.setBlurrRadius((int)(10.0f * this.getResources().getDisplayMetrics().density));
        this.setBlurrClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (OpeningTreeView.this._contentPresenter != null) {
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "premium screen shown", "opening tree");
                    OpeningTreeView.this._contentPresenter.showConversionDialog(null, ConversionContext.ANALYZE_TREE);
                }
            }
        });
    }

    private void setMoveInformation(final OpeningMoveInformation openingMoveInformation, OpeningMoveInformationView openingMoveInformationView) {
        openingMoveInformationView.setVisibility(0);
        openingMoveInformationView.setMoveInformation(openingMoveInformation);
        openingMoveInformationView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (OpeningTreeView.this._moveExecutor != null) {
                    OpeningTreeView.this._moveExecutor.doMoveInCurrentPosition(openingMoveInformation.getMove().getSEP());
                }
            }
        });
    }

    protected void finalize() throws Throwable {
        ServiceProvider.getInstance().getOpeningTreeService().closeBook();
        Object.super.finalize();
    }

    @Override
    public void positionChanged(final Position position, Move move) {
        this.post(new Runnable(){

            @Override
            public void run() {
                OpeningTreeView.this.setOpeningTreeInformation(position);
            }
        });
    }

    public void setAdapter(OpeningTreeViewSourceAdapter openingTreeViewSourceAdapter) {
        this._adapter = openingTreeViewSourceAdapter;
    }

    public void setMoveExecutor(MoveExecutor moveExecutor) {
        this._moveExecutor = moveExecutor;
    }

    public void setOpeningTreeInformation(Position position) {
        int n;
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-1, 2);
        layoutParams.span = 5;
        if (this._adapter != null) {
            int n2;
            Object object;
            int n3;
            int n4;
            List<OpeningMoveInformation> list = this._adapter.getInformation(position).getMoveInformation();
            int n5 = list.size();
            int n6 = this._openingTreeTable.getChildCount();
            if (n5 == 0) {
                this._noInformationText.setVisibility(0);
            } else {
                this._noInformationText.setVisibility(8);
            }
            int n7 = 1;
            n = n4 = (n3 = 0);
            do {
                n2 = n4;
                if (n3 >= n6) break;
                n2 = n4;
                if (n7 == 0) break;
                object = this._openingTreeTable.getChildAt(n3);
                object.setVisibility(0);
                n4 = n < n5 ? 1 : 0;
                n2 = n;
                if (object instanceof OpeningMoveInformationView) {
                    n2 = n;
                    if (n4 != 0) {
                        this.setMoveInformation(list.get(n), (OpeningMoveInformationView)((Object)object));
                        n2 = n + 1;
                    }
                }
                n = n3++;
                n7 = n4;
                n4 = n;
                n = n2;
            } while (true);
            do {
                if (n2 >= n6) break;
                this._openingTreeTable.getChildAt(n2).setVisibility(8);
                ++n2;
            } while (true);
            for (n3 = n; n3 < n5; ++n3) {
                object = new OpeningMoveInformationView(this.getContext());
                this._openingTreeTable.addView(object);
                this.setMoveInformation(list.get(n3), (OpeningMoveInformationView)((Object)object));
                object = new LinearLayout(this.getContext());
                object.setBackgroundColor(Color.rgb((int)236, (int)236, (int)236));
                object.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                this._openingTreeTable.addView(object);
            }
        }
        n = ServiceProvider.getInstance().getMembershipService().getNumberOfOpeningTreeMovesAvailable();
        if (position.getMoveNumber() > n) {
            this.setBlurrActive(true);
            return;
        }
        this.setBlurrActive(false);
    }

    public static interface OpeningTreeViewSourceAdapter {
        public OpeningPositionInformation getInformation(Position var1);
    }

}
