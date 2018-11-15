/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.view;

import android.view.View;
import de.cisha.android.board.view.ViewSelectionHelper;

public static interface ViewSelectionHelper.ResourceSelectionAdapter<Type extends View> {
    public View getClickableFrom(Type var1);

    public void onResourceSelected(int var1);

    public void selectView(Type var1, boolean var2);
}
