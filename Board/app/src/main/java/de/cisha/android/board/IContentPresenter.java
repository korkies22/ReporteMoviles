// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;

public interface IContentPresenter
{
    int getContentMaxHeight();
    
    int getContentMaxWidth();
    
    void popCurrentFragment();
    
    void showConversionDialog(final AbstractConversionDialogFragment p0, final ConversionContext p1);
    
    void showDialog(final AbstractDialogFragment p0);
    
    void showFragment(final AbstractContentFragment p0, final boolean p1, final boolean p2);
}
