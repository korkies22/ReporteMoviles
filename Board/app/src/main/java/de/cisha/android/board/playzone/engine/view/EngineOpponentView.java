// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine.view;

import android.graphics.Color;
import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.playzone.model.TimeControl;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.view.CouchImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.RelativeLayout;

public class EngineOpponentView extends RelativeLayout
{
    private View _lockView;
    private TextView _opponentDescription;
    private CouchImageView _opponentImageView;
    private TextView _opponentName;
    private View _opponentOverlayImageView;
    private TextView _opponentTimeControl;
    private TextView _opponentTimeControlNoLimit;
    private TextView _playButton;
    private View _strengthIndicatorView;
    
    public EngineOpponentView(final Context context) {
        super(context);
        this.initLayout(context);
    }
    
    public EngineOpponentView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initLayout(context);
    }
    
    private void initLayout(final Context context) {
        inflate(context, 2131427496, (ViewGroup)this);
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
    
    public void setDescriptionText(final CharSequence text) {
        this._opponentDescription.setText(text);
    }
    
    public void setName(final String text) {
        this._opponentName.setText((CharSequence)text);
    }
    
    public void setOpponentEnabled(final boolean enabled) {
        final View opponentOverlayImageView = this._opponentOverlayImageView;
        int visibility;
        if (enabled) {
            visibility = 8;
        }
        else {
            visibility = 0;
        }
        opponentOverlayImageView.setVisibility(visibility);
        final TextView playButton = this._playButton;
        final Context context = this.getContext();
        int n;
        if (!enabled) {
            n = 2131755178;
        }
        else {
            n = 2131755177;
        }
        playButton.setTextAppearance(context, n);
        final TextView playButton2 = this._playButton;
        int backgroundResource;
        if (!enabled) {
            backgroundResource = 2131231010;
        }
        else {
            backgroundResource = 2131231009;
        }
        playButton2.setBackgroundResource(backgroundResource);
        this._playButton.setEnabled(enabled);
    }
    
    public void setOpponentImage(final EngineOpponent engineOpponent) {
        this._opponentImageView.setImageDrawable(this.getResources().getDrawable(engineOpponent._resId));
        this._strengthIndicatorView.setBackgroundColor(engineOpponent._color);
    }
    
    public void setOpponentImage(final CishaUUID couchId, final int backgroundColor) {
        this._opponentImageView.setCouchId(couchId);
        this._strengthIndicatorView.setBackgroundColor(backgroundColor);
    }
    
    public void setOpponentLock(final boolean b) {
        final View lockView = this._lockView;
        int visibility;
        if (b) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        lockView.setVisibility(visibility);
        this.setOpponentEnabled(b ^ true);
    }
    
    public void setTimeControl(final TimeControl timeControl) {
        if (timeControl == null) {
            this._opponentTimeControl.setVisibility(8);
            this._opponentTimeControlNoLimit.setVisibility(8);
            return;
        }
        if (timeControl.hasTimeControl()) {
            final TextView opponentTimeControl = this._opponentTimeControl;
            final StringBuilder sb = new StringBuilder();
            sb.append(timeControl.getMinutes());
            sb.append(":00 ");
            String string;
            if (timeControl.getIncrement() > 0) {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("+");
                sb2.append(timeControl.getIncrement());
                string = sb2.toString();
            }
            else {
                string = "";
            }
            sb.append(string);
            opponentTimeControl.setText((CharSequence)sb.toString());
            this._opponentTimeControl.setVisibility(0);
            this._opponentTimeControlNoLimit.setVisibility(8);
            return;
        }
        this._opponentTimeControl.setVisibility(8);
        this._opponentTimeControlNoLimit.setVisibility(0);
    }
    
    public void setViewPlayButtonText(final int text) {
        this._playButton.setText(text);
    }
    
    public enum EngineOpponent
    {
        CUSTOM(2131231577, -7829368), 
        MEDIUM(2131231579, Color.rgb(255, 237, 12)), 
        STRONG(2131231578, Color.rgb(226, 69, 14)), 
        WEAK(2131231576, Color.rgb(132, 184, 25));
        
        private int _color;
        private int _resId;
        
        private EngineOpponent(final int resId, final int color) {
            this._resId = resId;
            this._color = color;
        }
    }
}
