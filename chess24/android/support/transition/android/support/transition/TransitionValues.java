/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.transition;

import android.support.transition.Transition;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransitionValues {
    final ArrayList<Transition> mTargetedTransitions = new ArrayList();
    public final Map<String, Object> values = new HashMap<String, Object>();
    public View view;

    public boolean equals(Object object) {
        if (object instanceof TransitionValues) {
            View view = this.view;
            object = (TransitionValues)object;
            if (view == object.view && this.values.equals(object.values)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return 31 * this.view.hashCode() + this.values.hashCode();
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("TransitionValues@");
        charSequence.append(Integer.toHexString(this.hashCode()));
        charSequence.append(":\n");
        charSequence = charSequence.toString();
        Object object = new StringBuilder();
        object.append((String)charSequence);
        object.append("    view = ");
        object.append((Object)this.view);
        object.append("\n");
        charSequence = object.toString();
        object = new StringBuilder();
        object.append((String)charSequence);
        object.append("    values:");
        charSequence = object.toString();
        for (String string : this.values.keySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("    ");
            stringBuilder.append(string);
            stringBuilder.append(": ");
            stringBuilder.append(this.values.get(string));
            stringBuilder.append("\n");
            charSequence = stringBuilder.toString();
        }
        return charSequence;
    }
}
