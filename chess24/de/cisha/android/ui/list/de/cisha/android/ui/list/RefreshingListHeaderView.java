/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.RotateAnimation
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.ui.list;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;

public class RefreshingListHeaderView
extends LinearLayout {
    private View _arrowImage;
    private View _progressBar;
    private State _state;
    private TextView _text;

    public RefreshingListHeaderView(Context context) {
        super(context);
        this.init();
    }

    public RefreshingListHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
        this.setGravity(17);
        RefreshingListHeaderView.inflate((Context)this.getContext(), (int)R.layout.list_overscroll_header, (ViewGroup)this);
        this.setBackgroundColor(Color.rgb((int)185, (int)185, (int)185));
        this._text = (TextView)this.findViewById(R.id.smalltext);
        this._arrowImage = this.findViewById(R.id.refreshing_list_header_arrow);
        this._progressBar = this.findViewById(R.id.refreshing_list_header_progressbar);
        this.setState(State.PULLDOWN);
    }

    private void rotateArrow(boolean bl) {
        float f = bl ? 0.0f : 180.0f;
        float f2 = bl ? 180.0f : 0.0f;
        RotateAnimation rotateAnimation = new RotateAnimation(f, f2, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(300L);
        rotateAnimation.setFillEnabled(true);
        rotateAnimation.setFillAfter(true);
        this._arrowImage.startAnimation((Animation)rotateAnimation);
    }

    public void setState(State state) {
        if (this._state != state) {
            this._state = state;
            this._arrowImage.setVisibility(0);
            this._text.setVisibility(0);
            switch (.$SwitchMap$de$cisha$android$ui$list$RefreshingListHeaderView$State[state.ordinal()]) {
                default: {
                    return;
                }
                case 3: {
                    this._progressBar.setVisibility(0);
                    this._arrowImage.clearAnimation();
                    this._arrowImage.setVisibility(8);
                    this._text.setVisibility(0);
                    this._text.setText(R.string.refreshing_listview_text_while_refreshing);
                    return;
                }
                case 2: {
                    this._text.setText(R.string.refreshing_listview_release_to_to_refresh_text);
                    this._progressBar.setVisibility(8);
                    this._arrowImage.setVisibility(0);
                    this.rotateArrow(true);
                    return;
                }
                case 1: 
            }
            this._text.setText(R.string.refreshing_listview_pull_down_to_reload_text);
            this._progressBar.setVisibility(8);
            this._arrowImage.setVisibility(0);
            this.rotateArrow(false);
        }
    }

    public static enum State {
        PULLDOWN,
        FINGER_UP,
        REFRESHING;
        

        private State() {
        }
    }

}
