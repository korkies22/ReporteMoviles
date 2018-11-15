/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;
import java.util.Collection;

@RequiresApi(value=26)
static class ViewCompat.ViewCompatApi26Impl
extends ViewCompat.ViewCompatApi24Impl {
    ViewCompat.ViewCompatApi26Impl() {
    }

    @Override
    public void addKeyboardNavigationClusters(@NonNull View view, @NonNull Collection<View> collection, int n) {
        view.addKeyboardNavigationClusters(collection, n);
    }

    @Override
    public int getImportantForAutofill(@NonNull View view) {
        return view.getImportantForAutofill();
    }

    @Override
    public int getNextClusterForwardId(@NonNull View view) {
        return view.getNextClusterForwardId();
    }

    @Override
    public boolean hasExplicitFocusable(@NonNull View view) {
        return view.hasExplicitFocusable();
    }

    @Override
    public boolean isFocusedByDefault(@NonNull View view) {
        return view.isFocusedByDefault();
    }

    @Override
    public boolean isImportantForAutofill(@NonNull View view) {
        return view.isImportantForAutofill();
    }

    @Override
    public boolean isKeyboardNavigationCluster(@NonNull View view) {
        return view.isKeyboardNavigationCluster();
    }

    @Override
    public View keyboardNavigationClusterSearch(@NonNull View view, View view2, int n) {
        return view.keyboardNavigationClusterSearch(view2, n);
    }

    @Override
    public boolean restoreDefaultFocus(@NonNull View view) {
        return view.restoreDefaultFocus();
    }

    @Override
    public /* varargs */ void setAutofillHints(@NonNull View view, @Nullable String ... arrstring) {
        view.setAutofillHints(arrstring);
    }

    @Override
    public void setFocusedByDefault(@NonNull View view, boolean bl) {
        view.setFocusedByDefault(bl);
    }

    @Override
    public void setImportantForAutofill(@NonNull View view, int n) {
        view.setImportantForAutofill(n);
    }

    @Override
    public void setKeyboardNavigationCluster(@NonNull View view, boolean bl) {
        view.setKeyboardNavigationCluster(bl);
    }

    @Override
    public void setNextClusterForwardId(@NonNull View view, int n) {
        view.setNextClusterForwardId(n);
    }

    @Override
    public void setTooltipText(View view, CharSequence charSequence) {
        view.setTooltipText(charSequence);
    }
}
