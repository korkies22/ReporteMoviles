/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.DatePicker
 *  android.widget.DatePicker$OnDateChangedListener
 */
package de.cisha.android.ui.patterns.dialogs;

import android.view.View;
import android.widget.DatePicker;

class CustomDatePickerDialog
implements View.OnClickListener {
    CustomDatePickerDialog() {
    }

    public void onClick(View view) {
        if (CustomDatePickerDialog.this._listener != null) {
            if (CustomDatePickerDialog.this._datePicker != null) {
                CustomDatePickerDialog.this._datePicker.clearFocus();
            }
            CustomDatePickerDialog.this._listener.onDateChanged(CustomDatePickerDialog.this._datePicker, CustomDatePickerDialog.this._datePicker.getYear(), CustomDatePickerDialog.this._datePicker.getMonth(), CustomDatePickerDialog.this._datePicker.getDayOfMonth());
        }
        CustomDatePickerDialog.this.dismiss();
    }
}
