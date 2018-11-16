// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.list;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.graphics.Color;
import android.view.ViewGroup;
import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.view.View;
import android.widget.LinearLayout;

public class RefreshingListHeaderView extends LinearLayout
{
    private View _arrowImage;
    private View _progressBar;
    private State _state;
    private TextView _text;
    
    public RefreshingListHeaderView(final Context context) {
        super(context);
        this.init();
    }
    
    public RefreshingListHeaderView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
        this.setGravity(17);
        inflate(this.getContext(), R.layout.list_overscroll_header, (ViewGroup)this);
        this.setBackgroundColor(Color.rgb(185, 185, 185));
        this._text = (TextView)this.findViewById(R.id.smalltext);
        this._arrowImage = this.findViewById(R.id.refreshing_list_header_arrow);
        this._progressBar = this.findViewById(R.id.refreshing_list_header_progressbar);
        this.setState(State.PULLDOWN);
    }
    
    private void rotateArrow(final boolean b) {
        float n;
        if (b) {
            n = 0.0f;
        }
        else {
            n = 180.0f;
        }
        float n2;
        if (b) {
            n2 = 180.0f;
        }
        else {
            n2 = 0.0f;
        }
        final RotateAnimation rotateAnimation = new RotateAnimation(n, n2, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(300L);
        rotateAnimation.setFillEnabled(true);
        rotateAnimation.setFillAfter(true);
        this._arrowImage.startAnimation((Animation)rotateAnimation);
    }
    
    public void setState(final State state) {
        if (this._state != state) {
            this._state = state;
            this._arrowImage.setVisibility(0);
            this._text.setVisibility(0);
            switch (RefreshingListHeaderView.1..SwitchMap.de.cisha.android.ui.list.RefreshingListHeaderView.State[state.ordinal()]) {
                default: {}
                case 3: {
                    this._progressBar.setVisibility(0);
                    this._arrowImage.clearAnimation();
                    this._arrowImage.setVisibility(8);
                    this._text.setVisibility(0);
                    this._text.setText(R.string.refreshing_listview_text_while_refreshing);
                }
                case 2: {
                    this._text.setText(R.string.refreshing_listview_release_to_to_refresh_text);
                    this._progressBar.setVisibility(8);
                    this._arrowImage.setVisibility(0);
                    this.rotateArrow(true);
                }
                case 1: {
                    this._text.setText(R.string.refreshing_listview_pull_down_to_reload_text);
                    this._progressBar.setVisibility(8);
                    this._arrowImage.setVisibility(0);
                    this.rotateArrow(false);
                    break;
                }
            }
        }
    }
    
    public enum State
    {
        FINGER_UP, 
        PULLDOWN, 
        REFRESHING;
    }
}
