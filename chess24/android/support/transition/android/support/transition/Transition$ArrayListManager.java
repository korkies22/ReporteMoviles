/*
 * Decompiled with CFR 0_134.
 */
package android.support.transition;

import android.support.transition.Transition;
import java.util.ArrayList;

private static class Transition.ArrayListManager {
    private Transition.ArrayListManager() {
    }

    static <T> ArrayList<T> add(ArrayList<T> arrayList, T t) {
        ArrayList<Object> arrayList2 = arrayList;
        if (arrayList == null) {
            arrayList2 = new ArrayList();
        }
        if (!arrayList2.contains(t)) {
            arrayList2.add(t);
        }
        return arrayList2;
    }

    static <T> ArrayList<T> remove(ArrayList<T> arrayList, T t) {
        ArrayList<T> arrayList2 = arrayList;
        if (arrayList != null) {
            arrayList.remove(t);
            arrayList2 = arrayList;
            if (arrayList.isEmpty()) {
                arrayList2 = null;
            }
        }
        return arrayList2;
    }
}
