/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.DatePicker
 *  android.widget.DatePicker$OnDateChangedListener
 */
package de.cisha.android.board.analyze;

import android.widget.DatePicker;
import de.cisha.android.board.analyze.EditGameInformationFragment;
import de.cisha.android.ui.patterns.input.CustomEditDate;
import java.util.Date;
import java.util.GregorianCalendar;

class EditGameInformationFragment
implements DatePicker.OnDateChangedListener {
    EditGameInformationFragment() {
    }

    public void onDateChanged(DatePicker object, int n, int n2, int n3) {
        object = new GregorianCalendar(n, n2, n3);
        1.this.this$0._date.setDate(object.getTime());
    }
}
