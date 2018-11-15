/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.Html
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.text.method.LinkMovementMethod
 *  android.text.method.MovementMethod
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.engine.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.engine.view.EngineOpponentView;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.view.ViewSelectionHelper;
import de.cisha.chess.model.CishaUUID;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EngineOnlineOpponentChooserView
extends LinearLayout
implements ClockListener {
    private ViewGroup _engineContainerView;
    private String _engineNameOfRunningGame;
    private OnlineEngineChooserListener _listener;
    private Map<OnlineEngineOpponent, EngineOpponentView> _mapOpponentsView;
    private List<EngineOpponentView> _opponentViewsNotRated;
    private CompoundButton _rateGameSwitch;
    private BoardAction _resumeAction;
    private ViewSelectionHelper.ResourceSelectionAdapter<EngineOpponentView> _selectionAdapter;
    private String _textClockBlack;
    private String _textClockWhite;
    private TextView _updateClockTextView;

    public EngineOnlineOpponentChooserView(Context context) {
        super(context);
        this.init();
    }

    public EngineOnlineOpponentChooserView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        LayoutInflater.from((Context)this.getContext()).inflate(2131427498, (ViewGroup)this);
        this._rateGameSwitch = (CompoundButton)this.findViewById(2131296746);
        this._rateGameSwitch.setChecked(true);
        this._rateGameSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                if (EngineOnlineOpponentChooserView.this._opponentViewsNotRated != null) {
                    object = EngineOnlineOpponentChooserView.this._opponentViewsNotRated.iterator();
                    while (object.hasNext()) {
                        ((EngineOpponentView)((Object)object.next())).setOpponentEnabled(bl ^ true);
                    }
                }
            }
        });
        this._engineContainerView = (ViewGroup)this.findViewById(2131296754);
        this._selectionAdapter = new ViewSelectionHelper.ResourceSelectionAdapter<EngineOpponentView>(){

            @Override
            public View getClickableFrom(EngineOpponentView engineOpponentView) {
                return engineOpponentView;
            }

            @Override
            public void onResourceSelected(int n) {
            }

            @Override
            public void selectView(EngineOpponentView engineOpponentView, boolean bl) {
                TextView textView = engineOpponentView.getViewDescriptionText();
                Object var5_4 = null;
                TextUtils.TruncateAt truncateAt = bl ? null : TextUtils.TruncateAt.END;
                textView.setEllipsize(truncateAt);
                truncateAt = engineOpponentView.getViewDescriptionText();
                int n = bl ? 10 : 1;
                truncateAt.setMaxLines(n);
                truncateAt = engineOpponentView.getViewDescriptionText();
                engineOpponentView = var5_4;
                if (bl) {
                    engineOpponentView = LinkMovementMethod.getInstance();
                }
                truncateAt.setMovementMethod((MovementMethod)engineOpponentView);
            }
        };
    }

    private void updateOpponentView(EngineOpponentView engineOpponentView, final OnlineEngineOpponent onlineEngineOpponent) {
        engineOpponentView.setName(onlineEngineOpponent.getName());
        engineOpponentView.setOpponentLock(onlineEngineOpponent.isLocked());
        engineOpponentView.setDescriptionText((CharSequence)Html.fromHtml((String)onlineEngineOpponent.getDescription()));
        final TimeControl timeControl = new TimeControl(onlineEngineOpponent.getTimeControlSeconds() / 60, onlineEngineOpponent.getTimeControlIncrementSeconds());
        engineOpponentView.setTimeControl(timeControl);
        engineOpponentView.setOpponentImage(new CishaUUID(onlineEngineOpponent.getProfilePictureCouchIdString(), true), onlineEngineOpponent.getColor());
        engineOpponentView.getViewPlayButton().setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (EngineOnlineOpponentChooserView.this._listener != null) {
                    EngineOnlineOpponentChooserView.this._listener.onOnlineEngineChosen(onlineEngineOpponent.getUuidString(), timeControl, EngineOnlineOpponentChooserView.this._rateGameSwitch.isChecked());
                }
            }
        });
    }

    public SwipeRefreshLayout getViewSwipeRefreshLayout() {
        return (SwipeRefreshLayout)this.findViewById(2131296755);
    }

    @Override
    public void onClockChanged(final long l, final boolean bl) {
        this.post(new Runnable(){

            @Override
            public void run() {
                String string = ChessClock.formatTime(l, false);
                if (bl) {
                    EngineOnlineOpponentChooserView.this._textClockWhite = string;
                } else {
                    EngineOnlineOpponentChooserView.this._textClockBlack = string;
                }
                if (EngineOnlineOpponentChooserView.this._updateClockTextView != null) {
                    string = EngineOnlineOpponentChooserView.this._updateClockTextView;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(EngineOnlineOpponentChooserView.this._textClockWhite);
                    stringBuilder.append(" ");
                    stringBuilder.append(EngineOnlineOpponentChooserView.this._textClockBlack);
                    string.setText((CharSequence)stringBuilder.toString());
                }
            }
        });
    }

    @Override
    public void onClockFlag(boolean bl) {
    }

    @Override
    public void onClockStarted() {
    }

    @Override
    public void onClockStopped() {
    }

    public void setOnlineEngineChooserListener(OnlineEngineChooserListener onlineEngineChooserListener) {
        this._listener = onlineEngineChooserListener;
    }

    public void setOnlineOpponents(List<OnlineEngineOpponent> object) {
        this._engineContainerView.removeAllViews();
        this._mapOpponentsView = new HashMap<OnlineEngineOpponent, EngineOpponentView>(object.size());
        LinkedList<EngineOpponentView> linkedList = new LinkedList<EngineOpponentView>();
        this._opponentViewsNotRated = new LinkedList<EngineOpponentView>();
        object = object.iterator();
        while (object.hasNext()) {
            OnlineEngineOpponent onlineEngineOpponent = (OnlineEngineOpponent)object.next();
            View view = LayoutInflater.from((Context)this.getContext()).inflate(2131427438, this._engineContainerView, false);
            EngineOpponentView engineOpponentView = (EngineOpponentView)view.findViewById(2131296763);
            this._engineContainerView.addView(view, -1, -2);
            linkedList.add(engineOpponentView);
            this._mapOpponentsView.put(onlineEngineOpponent, engineOpponentView);
            this.updateOpponentView(engineOpponentView, onlineEngineOpponent);
            if (onlineEngineOpponent.isRateGame()) continue;
            this._opponentViewsNotRated.add(engineOpponentView);
            engineOpponentView.setOpponentEnabled(this._rateGameSwitch.isChecked() ^ true);
        }
        if (linkedList.size() > 0) {
            ViewSelectionHelper.initExclusiveSelectionForViewSet(this._selectionAdapter, (View)((View)linkedList.get(0)), (View[])((View[])linkedList.toArray((T[])new EngineOpponentView[linkedList.size()])));
        }
        if (this._engineNameOfRunningGame != null) {
            this.setResumeGameEnabled(this._engineNameOfRunningGame, this._resumeAction);
        }
    }

    public void setResumeGameEnabled(String string, final BoardAction boardAction) {
        this._engineNameOfRunningGame = string;
        this._resumeAction = boardAction;
        if (this._mapOpponentsView != null) {
            for (Map.Entry<OnlineEngineOpponent, EngineOpponentView> entry : this._mapOpponentsView.entrySet()) {
                EngineOpponentView engineOpponentView = entry.getValue();
                if (entry.getKey().getName().equals(string)) {
                    engineOpponentView.setBackgroundResource(2131231390);
                    engineOpponentView.getViewPlayButton().setOnClickListener(new View.OnClickListener(){

                        public void onClick(View view) {
                            if (boardAction != null) {
                                boardAction.perform();
                            }
                        }
                    });
                    this._updateClockTextView = engineOpponentView.getViewPlayButton();
                    continue;
                }
                engineOpponentView.setOpponentEnabled(false);
            }
        }
    }

    public static interface OnlineEngineChooserListener {
        public void onOnlineEngineChosen(String var1, TimeControl var2, boolean var3);

        public void resumeEngineOnlineGame(PlayzoneGameSessionInfo var1);
    }

}
