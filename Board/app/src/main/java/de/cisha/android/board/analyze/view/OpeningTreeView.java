// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.view;

import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import java.util.List;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TableRow.LayoutParams;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.Position;
import de.cisha.android.board.analyze.model.OpeningMoveInformation;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout.LayoutParams;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TableLayout;
import android.widget.TextView;
import de.cisha.chess.model.MoveExecutor;
import android.widget.FrameLayout;
import de.cisha.android.board.IContentPresenter;
import de.cisha.chess.model.position.PositionObserver;
import de.cisha.android.view.BlurrView;

public class OpeningTreeView extends BlurrView implements PositionObserver
{
    private OpeningTreeViewSourceAdapter _adapter;
    private IContentPresenter _contentPresenter;
    private FrameLayout _innerLayout;
    private MoveExecutor _moveExecutor;
    private TextView _noInformationText;
    private TableLayout _openingTreeTable;
    
    public OpeningTreeView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    public OpeningTreeView(final Context context, final IContentPresenter contentPresenter) {
        super(context);
        this._contentPresenter = contentPresenter;
        this.init();
    }
    
    private void init() {
        this.addView((View)(this._innerLayout = new FrameLayout(this.getContext())), -1, -1);
        (this._noInformationText = new CustomTextView(this.getContext(), CustomTextViewTextStyle.TEXT, null)).setText(2131689550);
        this._noInformationText.setPadding(10, 10, 10, 10);
        this._noInformationText.setVisibility(8);
        this._innerLayout.addView((View)this._noInformationText, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2, 17));
        (this._openingTreeTable = new TableLayout(this.getContext())).setBackgroundColor(-1);
        if (this.isInEditMode()) {
            for (int i = 0; i < 5; ++i) {}
        }
        this._openingTreeTable.setColumnStretchable(4, true);
        this._innerLayout.addView((View)this._openingTreeTable, -1, -2);
        this.setBlurrRadius((int)(10.0f * this.getResources().getDisplayMetrics().density));
        this.setBlurrClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (OpeningTreeView.this._contentPresenter != null) {
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "premium screen shown", "opening tree");
                    OpeningTreeView.this._contentPresenter.showConversionDialog(null, ConversionContext.ANALYZE_TREE);
                }
            }
        });
    }
    
    private void setMoveInformation(final OpeningMoveInformation moveInformation, final OpeningMoveInformationView openingMoveInformationView) {
        openingMoveInformationView.setVisibility(0);
        openingMoveInformationView.setMoveInformation(moveInformation);
        openingMoveInformationView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (OpeningTreeView.this._moveExecutor != null) {
                    OpeningTreeView.this._moveExecutor.doMoveInCurrentPosition(moveInformation.getMove().getSEP());
                }
            }
        });
    }
    
    protected void finalize() throws Throwable {
        ServiceProvider.getInstance().getOpeningTreeService().closeBook();
        super.finalize();
    }
    
    @Override
    public void positionChanged(final Position position, final Move move) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                OpeningTreeView.this.setOpeningTreeInformation(position);
            }
        });
    }
    
    public void setAdapter(final OpeningTreeViewSourceAdapter adapter) {
        this._adapter = adapter;
    }
    
    public void setMoveExecutor(final MoveExecutor moveExecutor) {
        this._moveExecutor = moveExecutor;
    }
    
    public void setOpeningTreeInformation(final Position position) {
        final TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-1, 2);
        layoutParams.span = 5;
        if (this._adapter != null) {
            final List<OpeningMoveInformation> moveInformation = this._adapter.getInformation(position).getMoveInformation();
            final int size = moveInformation.size();
            final int childCount = this._openingTreeTable.getChildCount();
            if (size == 0) {
                this._noInformationText.setVisibility(0);
            }
            else {
                this._noInformationText.setVisibility(8);
            }
            int n = 1;
            int n2 = 0;
            int n4;
            int n3 = n4 = n2;
            int n5;
            while (true) {
                n5 = n3;
                if (n2 >= childCount) {
                    break;
                }
                n5 = n3;
                if (n == 0) {
                    break;
                }
                final View child = this._openingTreeTable.getChildAt(n2);
                child.setVisibility(0);
                final boolean b = n4 < size;
                int n6 = n4;
                if (child instanceof OpeningMoveInformationView) {
                    n6 = n4;
                    if (b) {
                        this.setMoveInformation(moveInformation.get(n4), (OpeningMoveInformationView)child);
                        n6 = n4 + 1;
                    }
                }
                final int n7 = n2;
                ++n2;
                n = (b ? 1 : 0);
                n3 = n7;
                n4 = n6;
            }
            int i;
            while (true) {
                i = n4;
                if (n5 >= childCount) {
                    break;
                }
                this._openingTreeTable.getChildAt(n5).setVisibility(8);
                ++n5;
            }
            while (i < size) {
                final OpeningMoveInformationView openingMoveInformationView = new OpeningMoveInformationView(this.getContext());
                this._openingTreeTable.addView((View)openingMoveInformationView);
                this.setMoveInformation(moveInformation.get(i), openingMoveInformationView);
                final LinearLayout linearLayout = new LinearLayout(this.getContext());
                ((View)linearLayout).setBackgroundColor(Color.rgb(236, 236, 236));
                ((View)linearLayout).setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                this._openingTreeTable.addView((View)linearLayout);
                ++i;
            }
        }
        if (position.getMoveNumber() > ServiceProvider.getInstance().getMembershipService().getNumberOfOpeningTreeMovesAvailable()) {
            this.setBlurrActive(true);
            return;
        }
        this.setBlurrActive(false);
    }
    
    public interface OpeningTreeViewSourceAdapter
    {
        OpeningPositionInformation getInformation(final Position p0);
    }
}
