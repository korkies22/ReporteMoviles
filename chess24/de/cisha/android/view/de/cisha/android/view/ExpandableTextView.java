/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 */
package de.cisha.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import java.util.StringTokenizer;

public class ExpandableTextView
extends TextView {
    private static final String PRESTRING_EXPANDED = "- ";
    private static final String PRESTRING_SHORTENED = "+ ";
    private boolean _shortMode = true;
    private int _size = 8;
    private String _text = "waiting...";

    public ExpandableTextView(Context context) {
        super(context);
        this.init();
    }

    public ExpandableTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public ExpandableTextView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init();
    }

    private void init() {
        this._shortMode = true;
        this.setExpandableText(this._text);
        super.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ExpandableTextView.this.toggleMode();
            }
        });
    }

    private CharSequence shortText(String charSequence) {
        CharSequence charSequence2 = "";
        StringTokenizer stringTokenizer = new StringTokenizer((String)charSequence, " ");
        charSequence = charSequence2;
        for (int i = 0; stringTokenizer.hasMoreElements() && i < this._size; ++i) {
            charSequence2 = new StringBuilder();
            charSequence2.append((String)charSequence);
            charSequence2.append(stringTokenizer.nextToken());
            charSequence = charSequence2 = charSequence2.toString();
            if (!stringTokenizer.hasMoreElements()) continue;
            charSequence = new StringBuilder();
            charSequence.append((String)charSequence2);
            charSequence.append(" ");
            charSequence = charSequence.toString();
        }
        return charSequence;
    }

    public void clear() {
        this.init();
    }

    public void setExpandableText(String string) {
        this._text = string;
        if (this._shortMode) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PRESTRING_SHORTENED);
            stringBuilder.append((Object)this.shortText(string));
            super.setText((CharSequence)stringBuilder.toString());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PRESTRING_EXPANDED);
        stringBuilder.append(string);
        super.setText((CharSequence)stringBuilder.toString());
    }

    public void setShortMode(boolean bl) {
        this._shortMode = bl;
        this.setExpandableText(this._text);
    }

    public void setShortTextSize(int n) {
        this._size = n;
        this.setExpandableText(this._text);
    }

    public void toggleMode() {
        this.setShortMode(this._shortMode ^ true);
    }

}
