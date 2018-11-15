/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.AssetManager
 *  android.graphics.Color
 *  android.graphics.Typeface
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Interpolator
 *  android.view.animation.LinearInterpolator
 *  android.view.animation.RotateAnimation
 *  android.view.animation.Transformation
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.remote;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.TextView;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.view.TimeControlView;

public class PlayzoneRemoteFragment.PlayzoneWaitingScreen
extends FrameLayout {
    private View _chooseDifferentTimeButton;
    private View _networkSettingsButton;
    private View _playVsComputerButton;
    private PlayzoneRemoteFragment.WaitingScreenState _state;
    private View _stopButton;
    private View _stopButtonCircle;
    private View _stopButtonFrame;
    private TextView _text;
    private TimeControlView _timeControlView;
    private View _tryAgainButton;
    private WaitingAnimation _waitingAnimation;

    public PlayzoneRemoteFragment.PlayzoneWaitingScreen(Context context) {
        super(context);
        LayoutInflater.from((Context)context).inflate(2131427506, (ViewGroup)this, true);
        this.findViewById(2131296713).setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
            final /* synthetic */ PlayzoneRemoteFragment val$this$0;
            {
                this.val$this$0 = playzoneRemoteFragment;
            }

            public void onClick(View view) {
            }
        });
        TextView textView = (TextView)this.findViewById(2131296795);
        textView.setTypeface(Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/TREBUCBD.TTF"));
        this._waitingAnimation = new WaitingAnimation(textView);
        this._playVsComputerButton = this.findViewById(2131296791);
        this._chooseDifferentTimeButton = this.findViewById(2131296701);
        this._tryAgainButton = this.findViewById(2131296790);
        this._networkSettingsButton = this.findViewById(2131296789);
        this._text = (TextView)this.findViewById(2131296796);
        this._stopButtonFrame = this.findViewById(2131296794);
        this._timeControlView = (TimeControlView)this.findViewById(2131296797);
        this._tryAgainButton.setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
            final /* synthetic */ PlayzoneRemoteFragment val$this$0;
            {
                this.val$this$0 = playzoneRemoteFragment;
            }

            public void onClick(View view) {
                PlayzoneRemoteFragment.this.hideWaitingDialog();
                PlayzoneRemoteFragment.this.startPairing(PlayzoneRemoteFragment.this._choosenTimeControl);
            }
        });
        this._playVsComputerButton.setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
            final /* synthetic */ PlayzoneRemoteFragment val$this$0;
            {
                this.val$this$0 = playzoneRemoteFragment;
            }

            public void onClick(View view) {
                PlayzoneRemoteFragment.this.getContentPresenter().popCurrentFragment();
            }
        });
        this._chooseDifferentTimeButton.setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
            final /* synthetic */ PlayzoneRemoteFragment val$this$0;
            {
                this.val$this$0 = playzoneRemoteFragment;
            }

            public void onClick(View view) {
                PlayzoneRemoteFragment.this.getContentPresenter().popCurrentFragment();
            }
        });
        this._stopButton = this.findViewById(2131296792);
        this._stopButton.setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
            final /* synthetic */ PlayzoneRemoteFragment val$this$0;
            {
                this.val$this$0 = playzoneRemoteFragment;
            }

            public void onClick(View view) {
                PlayzoneRemoteFragment.this.stopButtonPressed();
                PlayzoneWaitingScreen.this.setState(PlayzoneRemoteFragment.WaitingScreenState.WAITINGSCREENSTATE_NO_OPPONENT_FOUND);
            }
        });
        this._networkSettingsButton.setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
            final /* synthetic */ PlayzoneRemoteFragment val$this$0;
            {
                this.val$this$0 = playzoneRemoteFragment;
            }

            public void onClick(View view) {
                PlayzoneRemoteFragment.this.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
            }
        });
        this._stopButtonCircle = this.findViewById(2131296793);
        this.setState(PlayzoneRemoteFragment.WaitingScreenState.WAITINGSCREENSTATE_SEARCHING);
    }

    private void setState(PlayzoneRemoteFragment.WaitingScreenState waitingScreenState) {
        this._state = waitingScreenState;
        switch (PlayzoneRemoteFragment.$SwitchMap$de$cisha$android$board$playzone$remote$PlayzoneRemoteFragment$WaitingScreenState[waitingScreenState.ordinal()]) {
            default: {
                return;
            }
            case 4: {
                this._tryAgainButton.setVisibility(8);
                this._playVsComputerButton.setVisibility(0);
                this._chooseDifferentTimeButton.setVisibility(8);
                this._timeControlView.setVisibility(8);
                this._stopButtonFrame.setVisibility(8);
                this._networkSettingsButton.setVisibility(0);
                this._text.setText(2131690159);
                this._waitingAnimation.cancel();
                return;
            }
            case 3: {
                this._tryAgainButton.setVisibility(0);
                this._playVsComputerButton.setVisibility(0);
                this._chooseDifferentTimeButton.setVisibility(0);
                this._timeControlView.setVisibility(0);
                this._stopButtonFrame.setVisibility(8);
                this._networkSettingsButton.setVisibility(8);
                this._text.setText(2131690160);
                this._waitingAnimation.cancel();
                return;
            }
            case 2: {
                this.setVisibility(0);
                this.bringToFront();
                this._tryAgainButton.setVisibility(8);
                this._playVsComputerButton.setVisibility(8);
                this._chooseDifferentTimeButton.setVisibility(8);
                this._networkSettingsButton.setVisibility(8);
                this._text.setText(2131690175);
                if (PlayzoneRemoteFragment.this._choosenTimeControl != null) {
                    this._timeControlView.setTimeControlValue(PlayzoneRemoteFragment.this._choosenTimeControl.getMinutes(), PlayzoneRemoteFragment.this._choosenTimeControl.getIncrement());
                }
                this._timeControlView.setVisibility(0);
                this._waitingAnimation.reset();
                this._stopButtonFrame.setVisibility(0);
                this._stopButtonCircle.startAnimation((Animation)this._waitingAnimation);
                return;
            }
            case 1: 
        }
        this.setVisibility(4);
    }

    public PlayzoneRemoteFragment.WaitingScreenState getState() {
        return this._state;
    }

    public void hide() {
        this.setState(PlayzoneRemoteFragment.WaitingScreenState.WAITINGSCREENSTATE_HIDDEN);
    }

    public void show(PlayzoneRemoteFragment.WaitingScreenState waitingScreenState) {
        this.setState(waitingScreenState);
    }

    private class WaitingAnimation
    extends RotateAnimation {
        private TextView _textView;

        public WaitingAnimation(TextView textView) {
            super(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
            this._textView = textView;
            this.setDuration(3000L);
            this.setRepeatCount(-1);
            this.setRepeatMode(1);
            this.setInterpolator((Interpolator)new LinearInterpolator());
        }

        protected void applyTransformation(float f, Transformation transformation) {
            super.applyTransformation(f, transformation);
            int n = Color.argb((int)244, (int)0, (int)0, (int)((int)(180.0 * ((Math.sin((double)f * 3.141592653589793 * 2.0) + 1.0) / 2.0)) + 20));
            this._textView.setTextColor(n);
        }
    }

}
