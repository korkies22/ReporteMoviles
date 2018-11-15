/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.ProgressBar
 *  android.widget.TextView
 */
package de.cisha.android.board.tactics.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.cisha.android.board.view.ToastView;

public class SolvedToastView
extends ToastView {
    private ImageView _image;
    private ProgressBar _progress;
    private boolean _solved;
    private TextView _text;

    public SolvedToastView(Context context) {
        super(context);
        this.init();
    }

    public SolvedToastView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        SolvedToastView.inflate((Context)this.getContext(), (int)2131427566, (ViewGroup)this);
        this._image = (ImageView)this.findViewById(2131297122);
        this._text = (TextView)this.findViewById(2131297124);
        this._progress = (ProgressBar)this.findViewById(2131297123);
        this._progress.setVisibility(4);
    }

    public boolean isSolved() {
        return this._solved;
    }

    public void setSolved(boolean bl) {
        this._solved = bl;
        ImageView imageView = this._image;
        int n = bl ? 2131231831 : 2131231832;
        imageView.setImageResource(n);
    }

    public void setText(CharSequence charSequence) {
        this._text.setText(charSequence);
    }

    public void showLoadingAnimation(boolean bl) {
        if (bl) {
            this._progress.setVisibility(0);
            return;
        }
        this._progress.setVisibility(4);
    }
}
