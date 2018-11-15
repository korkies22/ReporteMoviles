/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.patterns.text.CustomTextView;
import java.util.Iterator;
import java.util.List;

public class FormErrorView
extends LinearLayout {
    private CustomTextView _errorsText;

    public FormErrorView(Context context) {
        super(context);
        this.init();
    }

    public FormErrorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
        FormErrorView.inflate((Context)this.getContext(), (int)2131427439, (ViewGroup)this);
        this._errorsText = (CustomTextView)this.findViewById(2131296538);
        this._errorsText.setVisibility(8);
    }

    public void setErrors(List<LoadFieldError> object) {
        if (object != null && object.size() > 0) {
            this._errorsText.setVisibility(0);
            Object object2 = "";
            Iterator<LoadFieldError> iterator = object.iterator();
            object = object2;
            while (iterator.hasNext()) {
                object2 = iterator.next();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append("- ");
                stringBuilder.append(object2.getMessage());
                object = object2 = stringBuilder.toString();
                if (object2.endsWith("\n")) continue;
                object = new StringBuilder();
                object.append((String)object2);
                object.append("\n");
                object = object.toString();
            }
            object2 = object;
            if (object.endsWith("\n")) {
                object2 = object.substring(0, object.length() - 1);
            }
            this._errorsText.setText((CharSequence)object2);
            return;
        }
        this._errorsText.setVisibility(8);
    }
}
