/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.accessibility.AccessibilityEvent
 *  android.widget.EditText
 *  android.widget.TextView
 */
package android.support.design.widget;

import android.support.design.widget.CollapsingTextHelper;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;

private class TextInputLayout.TextInputAccessibilityDelegate
extends AccessibilityDelegateCompat {
    TextInputLayout.TextInputAccessibilityDelegate() {
    }

    @Override
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)TextInputLayout.class.getSimpleName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo((View)object, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.setClassName(TextInputLayout.class.getSimpleName());
        object = TextInputLayout.this.mCollapsingTextHelper.getText();
        if (!TextUtils.isEmpty((CharSequence)object)) {
            accessibilityNodeInfoCompat.setText((CharSequence)object);
        }
        if (TextInputLayout.this.mEditText != null) {
            accessibilityNodeInfoCompat.setLabelFor((View)TextInputLayout.this.mEditText);
        }
        object = TextInputLayout.this.mErrorView != null ? TextInputLayout.this.mErrorView.getText() : null;
        if (!TextUtils.isEmpty((CharSequence)object)) {
            accessibilityNodeInfoCompat.setContentInvalid(true);
            accessibilityNodeInfoCompat.setError((CharSequence)object);
        }
    }

    @Override
    public void onPopulateAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent((View)object, accessibilityEvent);
        object = TextInputLayout.this.mCollapsingTextHelper.getText();
        if (!TextUtils.isEmpty((CharSequence)object)) {
            accessibilityEvent.getText().add(object);
        }
    }
}
