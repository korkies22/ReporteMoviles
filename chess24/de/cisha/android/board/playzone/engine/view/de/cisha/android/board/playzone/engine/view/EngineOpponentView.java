/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.engine.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.view.CouchImageView;
import de.cisha.chess.model.CishaUUID;

public class EngineOpponentView
extends RelativeLayout {
    private View _lockView;
    private TextView _opponentDescription;
    private CouchImageView _opponentImageView;
    private TextView _opponentName;
    private View _opponentOverlayImageView;
    private TextView _opponentTimeControl;
    private TextView _opponentTimeControlNoLimit;
    private TextView _playButton;
    private View _strengthIndicatorView;

    public EngineOpponentView(Context context) {
        super(context);
        this.initLayout(context);
    }

    public EngineOpponentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initLayout(context);
    }

    private void initLayout(Context context) {
        EngineOpponentView.inflate((Context)context, (int)2131427496, (ViewGroup)this);
        this._opponentName = (TextView)this.findViewById(2131296739);
        this._opponentImageView = (CouchImageView)this.findViewById(2131296735);
        this._opponentDescription = (TextView)this.findViewById(2131296733);
        this._opponentTimeControl = (TextView)this.findViewById(2131296741);
        this._opponentTimeControlNoLimit = (TextView)this.findViewById(2131296742);
        this._playButton = (TextView)this.findViewById(2131296740);
        this._strengthIndicatorView = this.findViewById(2131296730);
        this._lockView = this.findViewById(2131296738);
        this._opponentOverlayImageView = this.findViewById(2131296736);
        this.setTimeControl(null);
    }

    public TextView getViewDescriptionText() {
        return this._opponentDescription;
    }

    public TextView getViewPlayButton() {
        return this._playButton;
    }

    public void setDescriptionText(CharSequence charSequence) {
        this._opponentDescription.setText(charSequence);
    }

    public void setName(String string) {
        this._opponentName.setText((CharSequence)string);
    }

    public void setOpponentEnabled(boolean bl) {
        View view = this._opponentOverlayImageView;
        int n = bl ? 8 : 0;
        view.setVisibility(n);
        view = this._playButton;
        Context context = this.getContext();
        n = !bl ? 2131755178 : 2131755177;
        view.setTextAppearance(context, n);
        view = this._playButton;
        n = !bl ? 2131231010 : 2131231009;
        view.setBackgroundResource(n);
        this._playButton.setEnabled(bl);
    }

    public void setOpponentImage(EngineOpponent engineOpponent) {
        this._opponentImageView.setImageDrawable(this.getResources().getDrawable(engineOpponent._resId));
        this._strengthIndicatorView.setBackgroundColor(engineOpponent._color);
    }

    public void setOpponentImage(CishaUUID cishaUUID, int n) {
        this._opponentImageView.setCouchId(cishaUUID);
        this._strengthIndicatorView.setBackgroundColor(n);
    }

    public void setOpponentLock(boolean bl) {
        View view = this._lockView;
        int n = bl ? 0 : 8;
        view.setVisibility(n);
        this.setOpponentEnabled(bl ^ true);
    }

    public void setTimeControl(TimeControl object) {
        if (object == null) {
            this._opponentTimeControl.setVisibility(8);
            this._opponentTimeControlNoLimit.setVisibility(8);
            return;
        }
        if (object.hasTimeControl()) {
            TextView textView = this._opponentTimeControl;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object.getMinutes());
            stringBuilder.append(":00 ");
            if (object.getIncrement() > 0) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("+");
                stringBuilder2.append(object.getIncrement());
                object = stringBuilder2.toString();
            } else {
                object = "";
            }
            stringBuilder.append((String)object);
            textView.setText((CharSequence)stringBuilder.toString());
            this._opponentTimeControl.setVisibility(0);
            this._opponentTimeControlNoLimit.setVisibility(8);
            return;
        }
        this._opponentTimeControl.setVisibility(8);
        this._opponentTimeControlNoLimit.setVisibility(0);
    }

    public void setViewPlayButtonText(int n) {
        this._playButton.setText(n);
    }

    public static enum EngineOpponent {
        WEAK(2131231576, Color.rgb((int)132, (int)184, (int)25)),
        MEDIUM(2131231579, Color.rgb((int)255, (int)237, (int)12)),
        STRONG(2131231578, Color.rgb((int)226, (int)69, (int)14)),
        CUSTOM(2131231577, -7829368);
        
        private int _color;
        private int _resId;

        private EngineOpponent(int n2, int n3) {
            this._resId = n2;
            this._color = n3;
        }
    }

}
