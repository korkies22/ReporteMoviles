/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.dialogs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;

public class SimpleDialogView
extends ArrowBottomContainerView {
    protected ViewGroup _bottom;
    private ViewGroup _content;
    protected TextView _title;

    public SimpleDialogView(Context context) {
        super(context);
        this.init();
    }

    public SimpleDialogView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
        SimpleDialogView.inflate((Context)this.getContext(), (int)R.layout.simple_dialog_view, (ViewGroup)this);
        this._title = (TextView)this.findViewById(R.id.simple_dialog_view_title);
        this._bottom = (ViewGroup)this.findViewById(R.id.simple_dialog_view_bottom);
        this._content = (ViewGroup)this.findViewById(R.id.simple_dialog_view_content);
        this.setDrawsArrow(false);
    }

    public ViewGroup getBottomViewGroup() {
        return this._bottom;
    }

    public ViewGroup getContentViewGroup() {
        return this._content;
    }

    public CharSequence getTitle() {
        return this._title.getText();
    }

    public void setTitle(CharSequence charSequence) {
        this._title.setText(charSequence);
    }

    protected void showTitle(boolean bl) {
        TextView textView = this._title;
        int n = bl ? 0 : 8;
        textView.setVisibility(n);
    }
}
