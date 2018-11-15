/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.AutoCompleteTextView
 */
package android.support.v7.widget;

import android.support.v7.widget.SearchView;
import android.widget.AutoCompleteTextView;
import java.lang.reflect.Method;

private static class SearchView.AutoCompleteTextViewReflector {
    private Method doAfterTextChanged;
    private Method doBeforeTextChanged;
    private Method ensureImeVisible;
    private Method showSoftInputUnchecked;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    SearchView.AutoCompleteTextViewReflector() {
        try {
            this.doBeforeTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
            this.doBeforeTextChanged.setAccessible(true);
        }
        catch (NoSuchMethodException noSuchMethodException) {}
        try {
            this.doAfterTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
            this.doAfterTextChanged.setAccessible(true);
        }
        catch (NoSuchMethodException noSuchMethodException) {}
        try {
            this.ensureImeVisible = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
            this.ensureImeVisible.setAccessible(true);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void doAfterTextChanged(AutoCompleteTextView autoCompleteTextView) {
        if (this.doAfterTextChanged == null) return;
        try {
            this.doAfterTextChanged.invoke((Object)autoCompleteTextView, new Object[0]);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void doBeforeTextChanged(AutoCompleteTextView autoCompleteTextView) {
        if (this.doBeforeTextChanged == null) return;
        try {
            this.doBeforeTextChanged.invoke((Object)autoCompleteTextView, new Object[0]);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void ensureImeVisible(AutoCompleteTextView autoCompleteTextView, boolean bl) {
        if (this.ensureImeVisible == null) return;
        try {
            this.ensureImeVisible.invoke((Object)autoCompleteTextView, bl);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}
