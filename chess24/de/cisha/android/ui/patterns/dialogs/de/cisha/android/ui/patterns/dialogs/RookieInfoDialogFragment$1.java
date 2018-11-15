/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.patterns.dialogs;

import de.cisha.android.ui.patterns.text.CustomTextView;

class RookieInfoDialogFragment
implements Runnable {
    final /* synthetic */ CharSequence val$text;

    RookieInfoDialogFragment(CharSequence charSequence) {
        this.val$text = charSequence;
    }

    @Override
    public void run() {
        RookieInfoDialogFragment.this._text = this.val$text;
        RookieInfoDialogFragment.this._textView.setText(this.val$text);
        CustomTextView customTextView = RookieInfoDialogFragment.this._textView;
        int n = this.val$text.length() > 0 ? 0 : 8;
        customTextView.setVisibility(n);
    }
}
