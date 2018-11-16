// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine.view;

import de.cisha.android.view.PremiumButtonOverlayLayout;
import de.cisha.android.board.action.BoardAction;
import android.view.View.OnClickListener;
import android.text.method.MovementMethod;
import android.widget.TextView;
import android.text.method.LinkMovementMethod;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import de.cisha.android.board.view.ViewSelectionHelper;
import de.cisha.android.board.playzone.model.TimeControl;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.Random;
import android.content.Context;
import de.cisha.android.board.service.StoredGameInformation;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import android.widget.LinearLayout;

public class EngineOfflineOpponentChooserView extends LinearLayout
{
    private EngineOpponentChooserListener _listener;
    private StoredGameInformation _runningGameInfo;
    private int _selectedColor;
    
    public EngineOfflineOpponentChooserView(final Context context, final StoredGameInformation runningGameInfo) {
        super(context);
        this._runningGameInfo = runningGameInfo;
        this.init();
    }
    
    private boolean getPieceColor() {
        return this._selectedColor == 2131296732 || (this._selectedColor != 2131296729 && new Random().nextFloat() > 0.5);
    }
    
    private void init() {
        LayoutInflater.from(this.getContext()).inflate(2131427497, (ViewGroup)this);
        this.updateOpponentView((EngineOpponentView)this.findViewById(2131296759), EngineOpponentView.EngineOpponent.WEAK, 2131689965, 2131689964, 800, TimeControl.NO_TIME);
        this.updateOpponentView((EngineOpponentView)this.findViewById(2131296760), EngineOpponentView.EngineOpponent.MEDIUM, 2131689969, 2131689968, 1300, new TimeControl(15, 0));
        this.updateOpponentView((EngineOpponentView)this.findViewById(2131296761), EngineOpponentView.EngineOpponent.STRONG, 2131689967, 2131689966, 1800, new TimeControl(5, 0));
        ViewSelectionHelper.initExclusiveSelectionForViewSet((View)this, (ViewSelectionHelper.ResourceSelectionAdapter<View>)new ViewSelectionHelper.ResourceSelectionAdapter<View>() {
            @Override
            public View getClickableFrom(final View view) {
                return view;
            }
            
            @Override
            public void onResourceSelected(final int n) {
            }
            
            @Override
            public void selectView(final View view, final boolean selected) {
                if (view instanceof EngineOpponentView) {
                    final TextView viewDescriptionText = ((EngineOpponentView)view).getViewDescriptionText();
                    final MovementMethod movementMethod = null;
                    TextUtils.TruncateAt end;
                    if (selected) {
                        end = null;
                    }
                    else {
                        end = TextUtils.TruncateAt.END;
                    }
                    viewDescriptionText.setEllipsize(end);
                    int maxLines;
                    if (selected) {
                        maxLines = 10;
                    }
                    else {
                        maxLines = 1;
                    }
                    viewDescriptionText.setMaxLines(maxLines);
                    MovementMethod instance = movementMethod;
                    if (selected) {
                        instance = LinkMovementMethod.getInstance();
                    }
                    viewDescriptionText.setMovementMethod(instance);
                    return;
                }
                if (view instanceof EngineOpponentChooserTimeSelectionView) {
                    view.setSelected(selected);
                }
            }
        }, 2131296762, 2131296759, 2131296760, 2131296761, 2131296762);
        final EngineOpponentChooserTimeSelectionView engineOpponentChooserTimeSelectionView = (EngineOpponentChooserTimeSelectionView)this.findViewById(2131296762);
        engineOpponentChooserTimeSelectionView.getViewEngineOpponentView().getViewPlayButton().setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (EngineOfflineOpponentChooserView.this._listener != null) {
                    EngineOfflineOpponentChooserView.this._listener.onChoosen(engineOpponentChooserTimeSelectionView.getTimeControlChosen(), engineOpponentChooserTimeSelectionView.getSelectedElo().getRating(), EngineOfflineOpponentChooserView.this.getResources().getString(2131689963), EngineOfflineOpponentChooserView.this.getPieceColor());
                }
            }
        });
        if (this._runningGameInfo != null && this._runningGameInfo.getOpponentsName().equals(this.getResources().getString(2131689963))) {
            engineOpponentChooserTimeSelectionView.setBackgroundResource(2131231390);
            engineOpponentChooserTimeSelectionView.getViewEngineOpponentView().setViewPlayButtonText(2131690131);
            engineOpponentChooserTimeSelectionView.setTimeControl(this._runningGameInfo.getBaseTimeMinutes(), this._runningGameInfo.getIncrementSeconds());
            engineOpponentChooserTimeSelectionView.setTimeControlChecked(this._runningGameInfo.getBaseTimeMinutes() > 0 || this._runningGameInfo.getIncrementSeconds() > 0);
            engineOpponentChooserTimeSelectionView.setRating(this._runningGameInfo.getOpponentsRating().getRating());
        }
        this._selectedColor = 2131296731;
        ViewSelectionHelper.initExclusiveSelectionForViewSet((View)this, (ViewSelectionHelper.ResourceSelectionAdapter<View>)new ViewSelectionHelper.ResourceSelectionAdapter<View>() {
            @Override
            public View getClickableFrom(final View view) {
                return view;
            }
            
            @Override
            public void onResourceSelected(final int n) {
                EngineOfflineOpponentChooserView.this._selectedColor = n;
            }
            
            @Override
            public void selectView(View viewWithTag, final boolean b) {
                viewWithTag = viewWithTag.findViewWithTag((Object)"selection_indicator");
                int visibility;
                if (b) {
                    visibility = 0;
                }
                else {
                    visibility = 4;
                }
                viewWithTag.setVisibility(visibility);
            }
        }, this._selectedColor, 2131296732, 2131296729, 2131296731);
    }
    
    private void updateOpponentView(final EngineOpponentView engineOpponentView, final EngineOpponentView.EngineOpponent opponentImage, final int n, final int n2, final int n3, final TimeControl timeControl) {
        engineOpponentView.setOpponentImage(opponentImage);
        final String string = this.getResources().getString(n);
        engineOpponentView.setName(string);
        engineOpponentView.setDescriptionText(this.getResources().getText(n2));
        engineOpponentView.setTimeControl(timeControl);
        engineOpponentView.getViewPlayButton().setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (EngineOfflineOpponentChooserView.this._listener != null) {
                    EngineOfflineOpponentChooserView.this._listener.onChoosen(timeControl, n3, string, EngineOfflineOpponentChooserView.this.getPieceColor());
                }
            }
        });
        if (this._runningGameInfo != null && string.equals(this._runningGameInfo.getOpponentsName())) {
            engineOpponentView.setBackgroundResource(2131231390);
            engineOpponentView.setViewPlayButtonText(2131690131);
        }
    }
    
    public void setEngineOpponentChooserListener(final EngineOpponentChooserListener listener) {
        this._listener = listener;
    }
    
    public void setPremiumFeatureWallEnabled(final boolean premiumOverlayEnabled, final BoardAction overlayButtonAction) {
        final PremiumButtonOverlayLayout premiumButtonOverlayLayout = (PremiumButtonOverlayLayout)this.findViewById(2131296758);
        premiumButtonOverlayLayout.setPremiumOverlayEnabled(premiumOverlayEnabled);
        premiumButtonOverlayLayout.setOverlayButtonAction(overlayButtonAction);
    }
}
