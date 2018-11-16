// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.view.ViewGroup;

public class SimpleDialogView extends ArrowBottomContainerView
{
    protected ViewGroup _bottom;
    private ViewGroup _content;
    protected TextView _title;
    
    public SimpleDialogView(final Context context) {
        super(context);
        this.init();
    }
    
    public SimpleDialogView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
        inflate(this.getContext(), R.layout.simple_dialog_view, (ViewGroup)this);
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
    
    public void setTitle(final CharSequence text) {
        this._title.setText(text);
    }
    
    protected void showTitle(final boolean b) {
        final TextView title = this._title;
        int visibility;
        if (b) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        title.setVisibility(visibility);
    }
}
