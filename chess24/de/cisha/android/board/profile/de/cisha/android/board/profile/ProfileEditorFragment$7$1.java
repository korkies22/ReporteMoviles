/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.DatePicker
 *  android.widget.DatePicker$OnDateChangedListener
 */
package de.cisha.android.board.profile;

import android.widget.DatePicker;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.user.User;
import de.cisha.android.ui.patterns.input.CustomEditDate;
import java.util.Date;
import java.util.GregorianCalendar;

class ProfileEditorFragment
implements DatePicker.OnDateChangedListener {
    ProfileEditorFragment() {
    }

    public void onDateChanged(DatePicker object, int n, int n2, int n3) {
        object = new GregorianCalendar(n, n2, n3);
        7.this.this$0._user.setBirthday(object.getTime());
        7.this.this$0._dateField.setDate(object.getTime());
    }
}
