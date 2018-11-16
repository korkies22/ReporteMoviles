// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import android.view.View.OnClickListener;
import de.cisha.android.ui.patterns.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.GregorianCalendar;
import android.os.Bundle;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.DatePicker;
import java.util.Date;
import java.util.Calendar;

public class CustomDatePickerDialog extends EmptyDialogFragment
{
    private Calendar _calendar;
    private Date _date;
    private DatePicker _datePicker;
    private DatePicker.OnDateChangedListener _listener;
    
    public DatePicker getDatePicker() {
        return this._datePicker;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle("Day of Birth");
        this._calendar = new GregorianCalendar();
        this.setTime(this._date);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, ViewGroup viewGroup, final Bundle bundle) {
        viewGroup = (ViewGroup)super.onCreateView(layoutInflater, viewGroup, bundle);
        layoutInflater.inflate(R.layout.custom_time_picker, viewGroup);
        return (View)viewGroup;
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        (this._datePicker = (DatePicker)view.findViewById(R.id.datePicker1)).init(this._calendar.get(1), this._calendar.get(2), this._calendar.get(5), (DatePicker.OnDateChangedListener)null);
        view.findViewById(R.id.dialog_cancel_button).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                CustomDatePickerDialog.this.dismiss();
            }
        });
        view.findViewById(R.id.dialog_save_button).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
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
    
    public void setOnDateChangedListener(final DatePicker.OnDateChangedListener listener) {
        this._listener = listener;
    }
    
    public void setTime(final Date date) {
        this._date = date;
        if (this._calendar != null && date != null) {
            this._calendar.setTime(date);
        }
    }
}
