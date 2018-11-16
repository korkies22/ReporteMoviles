// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.view;

import java.util.StringTokenizer;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;

public class ExpandableTextView extends TextView
{
    private static final String PRESTRING_EXPANDED = "- ";
    private static final String PRESTRING_SHORTENED = "+ ";
    private boolean _shortMode;
    private int _size;
    private String _text;
    
    public ExpandableTextView(final Context context) {
        super(context);
        this._text = "waiting...";
        this._size = 8;
        this._shortMode = true;
        this.init();
    }
    
    public ExpandableTextView(final Context context, final AttributeSet set) {
        super(context, set);
        this._text = "waiting...";
        this._size = 8;
        this._shortMode = true;
        this.init();
    }
    
    public ExpandableTextView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this._text = "waiting...";
        this._size = 8;
        this._shortMode = true;
        this.init();
    }
    
    private void init() {
        this._shortMode = true;
        this.setExpandableText(this._text);
        super.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ExpandableTextView.this.toggleMode();
            }
        });
    }
    
    private CharSequence shortText(String s) {
        final String s2 = "";
        final StringTokenizer stringTokenizer = new StringTokenizer(s, " ");
        int n = 0;
        s = s2;
        while (stringTokenizer.hasMoreElements() && n < this._size) {
            final StringBuilder sb = new StringBuilder();
            sb.append(s);
            sb.append(stringTokenizer.nextToken());
            final String s3 = s = sb.toString();
            if (stringTokenizer.hasMoreElements()) {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(s3);
                sb2.append(" ");
                s = sb2.toString();
            }
            ++n;
        }
        return s;
    }
    
    public void clear() {
        this.init();
    }
    
    public void setExpandableText(final String text) {
        this._text = text;
        if (this._shortMode) {
            final StringBuilder sb = new StringBuilder();
            sb.append("+ ");
            sb.append((Object)this.shortText(text));
            super.setText((CharSequence)sb.toString());
            return;
        }
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("- ");
        sb2.append(text);
        super.setText((CharSequence)sb2.toString());
    }
    
    public void setShortMode(final boolean shortMode) {
        this._shortMode = shortMode;
        this.setExpandableText(this._text);
    }
    
    public void setShortTextSize(final int size) {
        this._size = size;
        this.setExpandableText(this._text);
    }
    
    public void toggleMode() {
        this.setShortMode(this._shortMode ^ true);
    }
}
