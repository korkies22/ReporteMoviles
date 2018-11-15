/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.DatePicker
 *  android.widget.DatePicker$OnDateChangedListener
 */
package de.cisha.android.ui.patterns.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.dialogs.EmptyDialogFragment;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomDatePickerDialog
extends EmptyDialogFragment {
    private Calendar _calendar;
    private Date _date;
    private DatePicker _datePicker;
    private DatePicker.OnDateChangedListener _listener;

    public DatePicker getDatePicker() {
        return this._datePicker;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle("Day of Birth");
        this._calendar = new GregorianCalendar();
        this.setTime(this._date);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        viewGroup = (ViewGroup)super.onCreateView(layoutInflater, viewGroup, bundle);
        layoutInflater.inflate(R.layout.custom_time_picker, viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._datePicker = (DatePicker)view.findViewById(R.id.datePicker1);
        int n = this._calendar.get(1);
        int n2 = this._calendar.get(2);
        int n3 = this._calendar.get(5);
        this._datePicker.init(n, n2, n3, null);
        view.findViewById(R.id.dialog_cancel_button).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                CustomDatePickerDialog.this.dismiss();
            }
        });
        view.findViewById(R.id.dialog_save_button).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (CustomDatePickerDialog.this._listener != null) {
                    if (CustomDatePickerDialog.this._datePicker != null) {
                        CustomDatePickerDialog.this._datePicker.clearFocus();
                    }
                    CustomDatePickerDialog.this._listener.onDateChanged(CustomDatePickerDialog.this._datePicker, CustomDatePickerDialog.this._datePicker.getYear(), CustomDatePickerDialog.this._datePicker.getMonth(), CustomDatePickerDialog.this._datePicker.getDayOfMonth());
                }
                CustomDatePickerDialog.this.dismiss();
            }
        });
    }

    public void setOnDateChangedListener(DatePicker.OnDateChangedListener onDateChangedListener) {
        this._listener = onDateChangedListener;
    }

    public void setTime(Date date) {
        this._date = date;
        if (this._calendar != null && date != null) {
            this._calendar.setTime(date);
        }
    }

}
