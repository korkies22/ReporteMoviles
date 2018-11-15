/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.ViewModel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ViewModelStore {
    private final HashMap<String, ViewModel> mMap = new HashMap();

    public final void clear() {
        Iterator<ViewModel> iterator = this.mMap.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().onCleared();
        }
        this.mMap.clear();
    }

    final ViewModel get(String string) {
        return this.mMap.get(string);
    }

    final void put(String string, ViewModel viewModel) {
        ViewModel viewModel2 = this.mMap.get(string);
        if (viewModel2 != null) {
            viewModel2.onCleared();
        }
        this.mMap.put(string, viewModel);
    }
}
