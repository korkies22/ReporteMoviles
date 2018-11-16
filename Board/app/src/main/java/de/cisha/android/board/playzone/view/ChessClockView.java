// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.view;

import de.cisha.chess.model.GameResult;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ChessClockView extends LinearLayout
{
    private boolean _activeClockColor;
    private ClockView _blackClock;
    private ViewGroup _blackClockSide;
    private TextView _result;
    private boolean _running;
    private boolean _showsMillis;
    private TextView _text1BlackMark;
    private TextView _text1WhiteMark;
    private TextView _text2BlackMark;
    private TextView _text2WhiteMark;
    private ClockFormat _timeFormatBlack;
    private ClockFormat _timeFormatWhite;
    private ClockView _whiteClock;
    private ViewGroup _whiteClockSide;
    
    public ChessClockView(final Context context) {
        super(context);
        this._showsMillis = true;
        this.init();
    }
    
    public ChessClockView(final Context context, final AttributeSet set) {
        super(context, set);
        this._showsMillis = true;
        this.init();
    }
    
    private ClockFormat fromMillis(final long n) {
        if (n < 20000L && this._showsMillis) {
            return ClockFormat.SS_MS;
        }
        if (n < 1200000L) {
            return ClockFormat.MM_SS;
        }
        return ClockFormat.HH_MM;
    }
    
    private boolean hasResult() {
        return this._result != null && !"".equals(this._result.getText());
    }
    
    private void init() {
        inflate(this.getContext(), 2131427491, (ViewGroup)this);
        this._whiteClock = (ClockView)this.findViewById(2131296703);
        this._blackClock = (ClockView)this.findViewById(2131296702);
        this._text1WhiteMark = (TextView)this.findViewById(2131296707);
        this._text2WhiteMark = (TextView)this.findViewById(2131296708);
        this._text1BlackMark = (TextView)this.findViewById(2131296705);
        this._text2BlackMark = (TextView)this.findViewById(2131296706);
        this._whiteClockSide = (ViewGroup)this.findViewById(2131296711);
        this._blackClockSide = (ViewGroup)this.findViewById(2131296704);
        this._result = (TextView)this.findViewById(2131296710);
        final Typeface fromAsset = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/DS-DIGI.TTF");
        this._result.setTypeface(fromAsset);
        this._text1WhiteMark.setTypeface(fromAsset);
        this._text2WhiteMark.setTypeface(fromAsset);
        this._text1BlackMark.setTypeface(fromAsset);
        this._text2BlackMark.setTypeface(fromAsset);
        this.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ChessClockView.this.toggleMode();
            }
        });
    }
    
    private void showClocks() {
        this._result.setVisibility(8);
        this._blackClockSide.setVisibility(0);
        this._whiteClockSide.setVisibility(0);
    }
    
    private void showResult() {
        if (this.hasResult()) {
            this._result.setVisibility(0);
            this._blackClockSide.setVisibility(8);
            this._whiteClockSide.setVisibility(8);
        }
    }
    
    public void setActiveClockMark(final boolean b) {
        this._activeClockColor = b;
        final TextView text1WhiteMark = this._text1WhiteMark;
        final Resources resources = this.getResources();
        final int n = 2131099802;
        int n2;
        if (b) {
            n2 = 2131099801;
        }
        else {
            n2 = 2131099802;
        }
        text1WhiteMark.setTextColor(resources.getColor(n2));
        final TextView text2WhiteMark = this._text2WhiteMark;
        final Resources resources2 = this.getResources();
        int n3;
        if (b) {
            n3 = 2131099801;
        }
        else {
            n3 = 2131099802;
        }
        text2WhiteMark.setTextColor(resources2.getColor(n3));
        final TextView text1BlackMark = this._text1BlackMark;
        final Resources resources3 = this.getResources();
        int n4;
        if (!b) {
            n4 = 2131099801;
        }
        else {
            n4 = 2131099802;
        }
        text1BlackMark.setTextColor(resources3.getColor(n4));
        final TextView text2BlackMark = this._text2BlackMark;
        final Resources resources4 = this.getResources();
        int n5 = n;
        if (!b) {
            n5 = 2131099801;
        }
        text2BlackMark.setTextColor(resources4.getColor(n5));
        if (this._running) {
            this._whiteClock.setFlashingOn(b);
            this._blackClock.setFlashingOn(b ^ true);
            return;
        }
        this._whiteClock.setFlashingOn(false);
        this._blackClock.setFlashingOn(false);
    }
    
    public void setGameResult(final GameResult gameResult) {
        if (gameResult != null && gameResult != GameResult.NO_RESULT) {
            final String shortDescription = gameResult.getShortDescription();
            String replace;
            if ((replace = shortDescription) != null) {
                replace = shortDescription;
                if (shortDescription.contains("-")) {
                    replace = shortDescription.replace("-", " - ");
                }
            }
            this._result.setText((CharSequence)replace);
            this.showResult();
            return;
        }
        this._result.setText((CharSequence)"");
        this.showClocks();
    }
    
    public void setRunning(final boolean running) {
        this._running = running;
        if (this._running) {
            this._whiteClock.setFlashingOn(this._activeClockColor);
            this._blackClock.setFlashingOn(this._activeClockColor ^ true);
            return;
        }
        this._whiteClock.setFlashingOn(false);
        this._blackClock.setFlashingOn(false);
    }
    
    public void setShowMilliseconds(final boolean showMilliseconds) {
        this._showsMillis = showMilliseconds;
        this._whiteClock.setShowMilliseconds(showMilliseconds);
        this._blackClock.setShowMilliseconds(showMilliseconds);
    }
    
    public void setTime(final boolean b, final long time) {
        ClockView clockView;
        if (b) {
            clockView = this._whiteClock;
        }
        else {
            clockView = this._blackClock;
        }
        clockView.setTime(time);
        ClockFormat clockFormat;
        if (b) {
            clockFormat = this._timeFormatWhite;
        }
        else {
            clockFormat = this._timeFormatBlack;
        }
        if (clockFormat != this.fromMillis(time)) {
            if (b) {
                final ClockFormat fromMillis = this.fromMillis(time);
                if (fromMillis != this._timeFormatWhite) {
                    this._timeFormatWhite = fromMillis;
                    this._text1WhiteMark.setText(this._timeFormatWhite._text1);
                    this._text2WhiteMark.setText(this._timeFormatWhite._text2);
                }
            }
            else {
                final ClockFormat fromMillis2 = this.fromMillis(time);
                if (fromMillis2 != this._timeFormatBlack) {
                    this._timeFormatBlack = fromMillis2;
                    this._text1BlackMark.setText(this._timeFormatBlack._text1);
                    this._text2BlackMark.setText(this._timeFormatBlack._text2);
                }
            }
        }
    }
    
    public void toggleMode() {
        if (!this.hasResult()) {
            this.showClocks();
            return;
        }
        if (this._result.getVisibility() == 8) {
            this.showResult();
            return;
        }
        this.showClocks();
    }
    
    private enum ClockFormat
    {
        HH_MM(2131689610, 2131689611), 
        MM_SS(2131689611, 2131689612), 
        SS_MS(2131689612, 2131689609);
        
        private int _text1;
        private int _text2;
        
        private ClockFormat(final int text1, final int text2) {
            this._text1 = text1;
            this._text2 = text2;
        }
    }
}
