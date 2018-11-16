// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine.view;

import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import java.util.LinkedList;
import java.util.HashMap;
import de.cisha.android.board.playzone.model.ChessClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View.OnClickListener;
import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.playzone.model.TimeControl;
import android.text.Html;
import android.text.method.MovementMethod;
import android.text.method.LinkMovementMethod;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import java.util.Iterator;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import de.cisha.android.board.view.ViewSelectionHelper;
import de.cisha.android.board.action.BoardAction;
import android.widget.CompoundButton;
import java.util.List;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import java.util.Map;
import android.view.ViewGroup;
import de.cisha.android.board.playzone.model.ClockListener;
import android.widget.LinearLayout;

public class EngineOnlineOpponentChooserView extends LinearLayout implements ClockListener
{
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
    
    public EngineOnlineOpponentChooserView(final Context context) {
        super(context);
        this.init();
    }
    
    public EngineOnlineOpponentChooserView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        LayoutInflater.from(this.getContext()).inflate(2131427498, (ViewGroup)this);
        (this._rateGameSwitch = (CompoundButton)this.findViewById(2131296746)).setChecked(true);
        this._rateGameSwitch.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                if (EngineOnlineOpponentChooserView.this._opponentViewsNotRated != null) {
                    final Iterator<EngineOpponentView> iterator = EngineOnlineOpponentChooserView.this._opponentViewsNotRated.iterator();
                    while (iterator.hasNext()) {
                        iterator.next().setOpponentEnabled(b ^ true);
                    }
                }
            }
        });
        this._engineContainerView = (ViewGroup)this.findViewById(2131296754);
        this._selectionAdapter = new ViewSelectionHelper.ResourceSelectionAdapter<EngineOpponentView>() {
            public View getClickableFrom(final EngineOpponentView engineOpponentView) {
                return (View)engineOpponentView;
            }
            
            @Override
            public void onResourceSelected(final int n) {
            }
            
            public void selectView(final EngineOpponentView engineOpponentView, final boolean b) {
                final TextView viewDescriptionText = engineOpponentView.getViewDescriptionText();
                final MovementMethod movementMethod = null;
                TextUtils.TruncateAt end;
                if (b) {
                    end = null;
                }
                else {
                    end = TextUtils.TruncateAt.END;
                }
                viewDescriptionText.setEllipsize(end);
                final TextView viewDescriptionText2 = engineOpponentView.getViewDescriptionText();
                int maxLines;
                if (b) {
                    maxLines = 10;
                }
                else {
                    maxLines = 1;
                }
                viewDescriptionText2.setMaxLines(maxLines);
                final TextView viewDescriptionText3 = engineOpponentView.getViewDescriptionText();
                MovementMethod instance = movementMethod;
                if (b) {
                    instance = LinkMovementMethod.getInstance();
                }
                viewDescriptionText3.setMovementMethod(instance);
            }
        };
    }
    
    private void updateOpponentView(final EngineOpponentView engineOpponentView, final OnlineEngineOpponent onlineEngineOpponent) {
        engineOpponentView.setName(onlineEngineOpponent.getName());
        engineOpponentView.setOpponentLock(onlineEngineOpponent.isLocked());
        engineOpponentView.setDescriptionText((CharSequence)Html.fromHtml(onlineEngineOpponent.getDescription()));
        final TimeControl timeControl = new TimeControl(onlineEngineOpponent.getTimeControlSeconds() / 60, onlineEngineOpponent.getTimeControlIncrementSeconds());
        engineOpponentView.setTimeControl(timeControl);
        engineOpponentView.setOpponentImage(new CishaUUID(onlineEngineOpponent.getProfilePictureCouchIdString(), true), onlineEngineOpponent.getColor());
        engineOpponentView.getViewPlayButton().setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (EngineOnlineOpponentChooserView.this._listener != null) {
                    EngineOnlineOpponentChooserView.this._listener.onOnlineEngineChosen(onlineEngineOpponent.getUuidString(), timeControl, EngineOnlineOpponentChooserView.this._rateGameSwitch.isChecked());
                }
            }
        });
    }
    
    public SwipeRefreshLayout getViewSwipeRefreshLayout() {
        return (SwipeRefreshLayout)this.findViewById(2131296755);
    }
    
    public void onClockChanged(final long n, final boolean b) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                final String formatTime = ChessClock.formatTime(n, false);
                if (b) {
                    EngineOnlineOpponentChooserView.this._textClockWhite = formatTime;
                }
                else {
                    EngineOnlineOpponentChooserView.this._textClockBlack = formatTime;
                }
                if (EngineOnlineOpponentChooserView.this._updateClockTextView != null) {
                    final TextView access.500 = EngineOnlineOpponentChooserView.this._updateClockTextView;
                    final StringBuilder sb = new StringBuilder();
                    sb.append(EngineOnlineOpponentChooserView.this._textClockWhite);
                    sb.append(" ");
                    sb.append(EngineOnlineOpponentChooserView.this._textClockBlack);
                    access.500.setText((CharSequence)sb.toString());
                }
            }
        });
    }
    
    public void onClockFlag(final boolean b) {
    }
    
    public void onClockStarted() {
    }
    
    public void onClockStopped() {
    }
    
    public void setOnlineEngineChooserListener(final OnlineEngineChooserListener listener) {
        this._listener = listener;
    }
    
    public void setOnlineOpponents(final List<OnlineEngineOpponent> list) {
        this._engineContainerView.removeAllViews();
        this._mapOpponentsView = new HashMap<OnlineEngineOpponent, EngineOpponentView>(list.size());
        final LinkedList<Object> list2 = new LinkedList<Object>();
        this._opponentViewsNotRated = new LinkedList<EngineOpponentView>();
        for (final OnlineEngineOpponent onlineEngineOpponent : list) {
            final View inflate = LayoutInflater.from(this.getContext()).inflate(2131427438, this._engineContainerView, false);
            final EngineOpponentView engineOpponentView = (EngineOpponentView)inflate.findViewById(2131296763);
            this._engineContainerView.addView(inflate, -1, -2);
            list2.add(engineOpponentView);
            this._mapOpponentsView.put(onlineEngineOpponent, engineOpponentView);
            this.updateOpponentView(engineOpponentView, onlineEngineOpponent);
            if (!onlineEngineOpponent.isRateGame()) {
                this._opponentViewsNotRated.add(engineOpponentView);
                engineOpponentView.setOpponentEnabled(this._rateGameSwitch.isChecked() ^ true);
            }
        }
        if (list2.size() > 0) {
            ViewSelectionHelper.initExclusiveSelectionForViewSet((ViewSelectionHelper.ResourceSelectionAdapter<Object>)this._selectionAdapter, list2.get(0), (Object[])list2.toArray(new EngineOpponentView[list2.size()]));
        }
        if (this._engineNameOfRunningGame != null) {
            this.setResumeGameEnabled(this._engineNameOfRunningGame, this._resumeAction);
        }
    }
    
    public void setResumeGameEnabled(final String engineNameOfRunningGame, final BoardAction resumeAction) {
        this._engineNameOfRunningGame = engineNameOfRunningGame;
        this._resumeAction = resumeAction;
        if (this._mapOpponentsView != null) {
            for (final Map.Entry<OnlineEngineOpponent, EngineOpponentView> entry : this._mapOpponentsView.entrySet()) {
                final EngineOpponentView engineOpponentView = entry.getValue();
                if (entry.getKey().getName().equals(engineNameOfRunningGame)) {
                    engineOpponentView.setBackgroundResource(2131231390);
                    engineOpponentView.getViewPlayButton().setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                        public void onClick(final View view) {
                            if (resumeAction != null) {
                                resumeAction.perform();
                            }
                        }
                    });
                    this._updateClockTextView = engineOpponentView.getViewPlayButton();
                }
                else {
                    engineOpponentView.setOpponentEnabled(false);
                }
            }
        }
    }
    
    public interface OnlineEngineChooserListener
    {
        void onOnlineEngineChosen(final String p0, final TimeControl p1, final boolean p2);
        
        void resumeEngineOnlineGame(final PlayzoneGameSessionInfo p0);
    }
}
