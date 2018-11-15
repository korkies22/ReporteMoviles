/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.text.method.LinkMovementMethod
 *  android.text.method.MovementMethod
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.engine.view;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.engine.model.EngineOpponentChooserListener;
import de.cisha.android.board.playzone.engine.view.EngineOpponentChooserTimeSelectionView;
import de.cisha.android.board.playzone.engine.view.EngineOpponentView;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.StoredGameInformation;
import de.cisha.android.board.view.ViewSelectionHelper;
import de.cisha.android.view.PremiumButtonOverlayLayout;
import de.cisha.chess.model.Rating;
import java.util.Random;

public class EngineOfflineOpponentChooserView
extends LinearLayout {
    private EngineOpponentChooserListener _listener;
    private StoredGameInformation _runningGameInfo;
    private int _selectedColor;

    public EngineOfflineOpponentChooserView(Context context, StoredGameInformation storedGameInformation) {
        super(context);
        this._runningGameInfo = storedGameInformation;
        this.init();
    }

    private boolean getPieceColor() {
        if (this._selectedColor == 2131296732) {
            return true;
        }
        if (this._selectedColor == 2131296729) {
            return false;
        }
        if ((double)new Random().nextFloat() > 0.5) {
            return true;
        }
        return false;
    }

    private void init() {
        LayoutInflater.from((Context)this.getContext()).inflate(2131427497, (ViewGroup)this);
        this.updateOpponentView((EngineOpponentView)this.findViewById(2131296759), EngineOpponentView.EngineOpponent.WEAK, 2131689965, 2131689964, 800, TimeControl.NO_TIME);
        this.updateOpponentView((EngineOpponentView)this.findViewById(2131296760), EngineOpponentView.EngineOpponent.MEDIUM, 2131689969, 2131689968, 1300, new TimeControl(15, 0));
        this.updateOpponentView((EngineOpponentView)this.findViewById(2131296761), EngineOpponentView.EngineOpponent.STRONG, 2131689967, 2131689966, 1800, new TimeControl(5, 0));
        ViewSelectionHelper.initExclusiveSelectionForViewSet((View)this, new ViewSelectionHelper.ResourceSelectionAdapter<View>(){

            @Override
            public View getClickableFrom(View view) {
                return view;
            }

            @Override
            public void onResourceSelected(int n) {
            }

            @Override
            public void selectView(View object, boolean bl) {
                if (object instanceof EngineOpponentView) {
                    TextView textView = ((EngineOpponentView)((Object)object)).getViewDescriptionText();
                    Object var4_4 = null;
                    object = bl ? null : TextUtils.TruncateAt.END;
                    textView.setEllipsize((TextUtils.TruncateAt)object);
                    int n = bl ? 10 : 1;
                    textView.setMaxLines(n);
                    object = var4_4;
                    if (bl) {
                        object = LinkMovementMethod.getInstance();
                    }
                    textView.setMovementMethod((MovementMethod)object);
                    return;
                }
                if (object instanceof EngineOpponentChooserTimeSelectionView) {
                    object.setSelected(bl);
                }
            }
        }, 2131296762, 2131296759, 2131296760, 2131296761, 2131296762);
        final EngineOpponentChooserTimeSelectionView engineOpponentChooserTimeSelectionView = (EngineOpponentChooserTimeSelectionView)this.findViewById(2131296762);
        engineOpponentChooserTimeSelectionView.getViewEngineOpponentView().getViewPlayButton().setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (EngineOfflineOpponentChooserView.this._listener != null) {
                    EngineOfflineOpponentChooserView.this._listener.onChoosen(engineOpponentChooserTimeSelectionView.getTimeControlChosen(), engineOpponentChooserTimeSelectionView.getSelectedElo().getRating(), EngineOfflineOpponentChooserView.this.getResources().getString(2131689963), EngineOfflineOpponentChooserView.this.getPieceColor());
                }
            }
        });
        if (this._runningGameInfo != null && this._runningGameInfo.getOpponentsName().equals(this.getResources().getString(2131689963))) {
            engineOpponentChooserTimeSelectionView.setBackgroundResource(2131231390);
            engineOpponentChooserTimeSelectionView.getViewEngineOpponentView().setViewPlayButtonText(2131690131);
            engineOpponentChooserTimeSelectionView.setTimeControl(this._runningGameInfo.getBaseTimeMinutes(), this._runningGameInfo.getIncrementSeconds());
            boolean bl = this._runningGameInfo.getBaseTimeMinutes() > 0 || this._runningGameInfo.getIncrementSeconds() > 0;
            engineOpponentChooserTimeSelectionView.setTimeControlChecked(bl);
            engineOpponentChooserTimeSelectionView.setRating(this._runningGameInfo.getOpponentsRating().getRating());
        }
        this._selectedColor = 2131296731;
        ViewSelectionHelper.initExclusiveSelectionForViewSet((View)this, new ViewSelectionHelper.ResourceSelectionAdapter<View>(){

            @Override
            public View getClickableFrom(View view) {
                return view;
            }

            @Override
            public void onResourceSelected(int n) {
                EngineOfflineOpponentChooserView.this._selectedColor = n;
            }

            @Override
            public void selectView(View view, boolean bl) {
                view = view.findViewWithTag((Object)"selection_indicator");
                int n = bl ? 0 : 4;
                view.setVisibility(n);
            }
        }, this._selectedColor, 2131296732, 2131296729, 2131296731);
    }

    private void updateOpponentView(EngineOpponentView engineOpponentView, EngineOpponentView.EngineOpponent object, int n, int n2, final int n3, final TimeControl timeControl) {
        engineOpponentView.setOpponentImage((EngineOpponentView.EngineOpponent)((Object)object));
        object = this.getResources().getString(n);
        engineOpponentView.setName((String)object);
        engineOpponentView.setDescriptionText(this.getResources().getText(n2));
        engineOpponentView.setTimeControl(timeControl);
        engineOpponentView.getViewPlayButton().setOnClickListener(new View.OnClickListener((String)object){
            final /* synthetic */ String val$engineName;
            {
                this.val$engineName = string;
            }

            public void onClick(View view) {
                if (EngineOfflineOpponentChooserView.this._listener != null) {
                    EngineOfflineOpponentChooserView.this._listener.onChoosen(timeControl, n3, this.val$engineName, EngineOfflineOpponentChooserView.this.getPieceColor());
                }
            }
        });
        if (this._runningGameInfo != null && object.equals(this._runningGameInfo.getOpponentsName())) {
            engineOpponentView.setBackgroundResource(2131231390);
            engineOpponentView.setViewPlayButtonText(2131690131);
        }
    }

    public void setEngineOpponentChooserListener(EngineOpponentChooserListener engineOpponentChooserListener) {
        this._listener = engineOpponentChooserListener;
    }

    public void setPremiumFeatureWallEnabled(boolean bl, BoardAction boardAction) {
        PremiumButtonOverlayLayout premiumButtonOverlayLayout = (PremiumButtonOverlayLayout)this.findViewById(2131296758);
        premiumButtonOverlayLayout.setPremiumOverlayEnabled(bl);
        premiumButtonOverlayLayout.setOverlayButtonAction(boardAction);
    }

}
