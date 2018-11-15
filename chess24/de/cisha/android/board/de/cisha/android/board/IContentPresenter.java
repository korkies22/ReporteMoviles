/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;

public interface IContentPresenter {
    public int getContentMaxHeight();

    public int getContentMaxWidth();

    public void popCurrentFragment();

    public void showConversionDialog(AbstractConversionDialogFragment var1, ConversionContext var2);

    public void showDialog(AbstractDialogFragment var1);

    public void showFragment(AbstractContentFragment var1, boolean var2, boolean var3);
}
