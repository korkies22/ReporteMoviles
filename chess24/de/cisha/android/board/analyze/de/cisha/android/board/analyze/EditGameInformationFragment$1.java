/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.DatePicker
 *  android.widget.DatePicker$OnDateChangedListener
 *  android.widget.EditText
 */
package de.cisha.android.board.analyze;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import de.cisha.android.ui.patterns.dialogs.CustomDatePickerDialog;
import de.cisha.android.ui.patterns.input.CustomEditDate;
import java.util.Date;
import java.util.GregorianCalendar;

class EditGameInformationFragment
implements View.OnClickListener {
    EditGameInformationFragment() {
    }

    public void onClick(View object) {
        EditGameInformationFragment.this._date.getEditText().requestFocus();
        object = new CustomDatePickerDialog();
        object.setOnDateChangedListener(new DatePicker.OnDateChangedListener(){

            public void onDateChanged(DatePicker object, int n, int n2, int n3) {
                object = new GregorianCalendar(n, n2, n3);
                EditGameInformationFragment.this._date.setDate(object.getTime());
            }
        });
        object.setTime(EditGameInformationFragment.this._date.getDate());
        object.show(EditGameInformationFragment.this.getFragmentManager(), null);
    }

}
