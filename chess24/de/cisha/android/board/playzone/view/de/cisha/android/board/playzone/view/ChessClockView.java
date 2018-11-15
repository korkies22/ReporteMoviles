/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.playzone.view.ClockView;
import de.cisha.chess.model.GameResult;

public class ChessClockView
extends LinearLayout {
    private boolean _activeClockColor;
    private ClockView _blackClock;
    private ViewGroup _blackClockSide;
    private TextView _result;
    private boolean _running;
    private boolean _showsMillis = true;
    private TextView _text1BlackMark;
    private TextView _text1WhiteMark;
    private TextView _text2BlackMark;
    private TextView _text2WhiteMark;
    private ClockFormat _timeFormatBlack;
    private ClockFormat _timeFormatWhite;
    private ClockView _whiteClock;
    private ViewGroup _whiteClockSide;

    public ChessClockView(Context context) {
        super(context);
        this.init();
    }

    public ChessClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private ClockFormat fromMillis(long l) {
        if (l < 20000L && this._showsMillis) {
            return ClockFormat.SS_MS;
        }
        if (l < 1200000L) {
            return ClockFormat.MM_SS;
        }
        return ClockFormat.HH_MM;
    }

    private boolean hasResult() {
        if (this._result != null && !"".equals(this._result.getText())) {
            return true;
        }
        return false;
    }

    private void init() {
        ChessClockView.inflate((Context)this.getContext(), (int)2131427491, (ViewGroup)this);
        this._whiteClock = (ClockView)this.findViewById(2131296703);
        this._blackClock = (ClockView)this.findViewById(2131296702);
        this._text1WhiteMark = (TextView)this.findViewById(2131296707);
        this._text2WhiteMark = (TextView)this.findViewById(2131296708);
        this._text1BlackMark = (TextView)this.findViewById(2131296705);
        this._text2BlackMark = (TextView)this.findViewById(2131296706);
        this._whiteClockSide = (ViewGroup)this.findViewById(2131296711);
        this._blackClockSide = (ViewGroup)this.findViewById(2131296704);
        this._result = (TextView)this.findViewById(2131296710);
        Typeface typeface = Typeface.createFromAsset((AssetManager)this.getContext().getAssets(), (String)"fonts/DS-DIGI.TTF");
        this._result.setTypeface(typeface);
        this._text1WhiteMark.setTypeface(typeface);
        this._text2WhiteMark.setTypeface(typeface);
        this._text1BlackMark.setTypeface(typeface);
        this._text2BlackMark.setTypeface(typeface);
        this.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
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

    public void setActiveClockMark(boolean bl) {
        this._activeClockColor = bl;
        TextView textView = this._text1WhiteMark;
        Resources resources = this.getResources();
        int n = 2131099802;
        int n2 = bl ? 2131099801 : 2131099802;
        textView.setTextColor(resources.getColor(n2));
        textView = this._text2WhiteMark;
        resources = this.getResources();
        n2 = bl ? 2131099801 : 2131099802;
        textView.setTextColor(resources.getColor(n2));
        textView = this._text1BlackMark;
        resources = this.getResources();
        n2 = !bl ? 2131099801 : 2131099802;
        textView.setTextColor(resources.getColor(n2));
        textView = this._text2BlackMark;
        resources = this.getResources();
        n2 = n;
        if (!bl) {
            n2 = 2131099801;
        }
        textView.setTextColor(resources.getColor(n2));
        if (this._running) {
            this._whiteClock.setFlashingOn(bl);
            this._blackClock.setFlashingOn(bl ^ true);
            return;
        }
        this._whiteClock.setFlashingOn(false);
        this._blackClock.setFlashingOn(false);
    }

    public void setGameResult(GameResult object) {
        if (object != null && object != GameResult.NO_RESULT) {
            String string = object.getShortDescription();
            object = string;
            if (string != null) {
                object = string;
                if (string.contains("-")) {
                    object = string.replace("-", " - ");
                }
            }
            this._result.setText((CharSequence)object);
            this.showResult();
            return;
        }
        this._result.setText((CharSequence)"");
        this.showClocks();
    }

    public void setRunning(boolean bl) {
        this._running = bl;
        if (this._running) {
            this._whiteClock.setFlashingOn(this._activeClockColor);
            this._blackClock.setFlashingOn(this._activeClockColor ^ true);
            return;
        }
        this._whiteClock.setFlashingOn(false);
        this._blackClock.setFlashingOn(false);
    }

    public void setShowMilliseconds(boolean bl) {
        this._showsMillis = bl;
        this._whiteClock.setShowMilliseconds(bl);
        this._blackClock.setShowMilliseconds(bl);
    }

    public void setTime(boolean bl, long l) {
        Object object = bl ? this._whiteClock : this._blackClock;
        object.setTime(l);
        object = bl ? this._timeFormatWhite : this._timeFormatBlack;
        if (object != this.fromMillis(l)) {
            if (bl) {
                object = this.fromMillis(l);
                if (object != this._timeFormatWhite) {
                    this._timeFormatWhite = object;
                    this._text1WhiteMark.setText(this._timeFormatWhite._text1);
                    this._text2WhiteMark.setText(this._timeFormatWhite._text2);
                    return;
                }
            } else {
                object = this.fromMillis(l);
                if (object != this._timeFormatBlack) {
                    this._timeFormatBlack = object;
                    this._text1BlackMark.setText(this._timeFormatBlack._text1);
                    this._text2BlackMark.setText(this._timeFormatBlack._text2);
                }
            }
        }
    }

    public void toggleMode() {
        if (this.hasResult()) {
            if (this._result.getVisibility() == 8) {
                this.showResult();
                return;
            }
            this.showClocks();
            return;
        }
        this.showClocks();
    }

    private static enum ClockFormat {
        HH_MM(2131689610, 2131689611),
        MM_SS(2131689611, 2131689612),
        SS_MS(2131689612, 2131689609);
        
        private int _text1;
        private int _text2;

        private ClockFormat(int n2, int n3) {
            this._text1 = n2;
            this._text2 = n3;
        }
    }

}
