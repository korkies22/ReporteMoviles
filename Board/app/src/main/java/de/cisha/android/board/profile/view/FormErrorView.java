// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import java.util.Iterator;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.ui.patterns.text.CustomTextView;
import android.widget.LinearLayout;

public class FormErrorView extends LinearLayout
{
    private CustomTextView _errorsText;
    
    public FormErrorView(final Context context) {
        super(context);
        this.init();
    }
    
    public FormErrorView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
        inflate(this.getContext(), 2131427439, (ViewGroup)this);
        (this._errorsText = (CustomTextView)this.findViewById(2131296538)).setVisibility(8);
    }
    
    public void setErrors(final List<LoadFieldError> list) {
        if (list != null && list.size() > 0) {
            this._errorsText.setVisibility(0);
            final String s = "";
            final Iterator<LoadFieldError> iterator = list.iterator();
            String s2 = s;
            while (iterator.hasNext()) {
                final LoadFieldError loadFieldError = iterator.next();
                final StringBuilder sb = new StringBuilder();
                sb.append(s2);
                sb.append("- ");
                sb.append(loadFieldError.getMessage());
                final String s3 = s2 = sb.toString();
                if (!s3.endsWith("\n")) {
                    final StringBuilder sb2 = new StringBuilder();
                    sb2.append(s3);
                    sb2.append("\n");
                    s2 = sb2.toString();
                }
            }
            String substring = s2;
            if (s2.endsWith("\n")) {
                substring = s2.substring(0, s2.length() - 1);
            }
            this._errorsText.setText((CharSequence)substring);
            return;
        }
        this._errorsText.setVisibility(8);
    }
}
