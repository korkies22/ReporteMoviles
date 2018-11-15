/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.ui.patterns.dialogs;

import android.view.View;

class CustomDatePickerDialog
implements View.OnClickListener {
    CustomDatePickerDialog() {
    }

    public void onClick(View view) {
        CustomDatePickerDialog.this.dismiss();
    }
}
