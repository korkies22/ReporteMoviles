/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.patterns.dialogs;

import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;

public static interface OptionDialogFragment.OptionSelectionListener<Type extends OptionDialogFragment.Option> {
    public void onOptionSelected(Type var1);
}
