// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import de.cisha.android.board.service.jsonparser.APIStatusCode;

public interface IErrorPresenter
{
    void showViewForErrorCode(final APIStatusCode p0, final IReloadAction p1);
    
    void showViewForErrorCode(final APIStatusCode p0, final IReloadAction p1, final ICancelAction p2);
    
    void showViewForErrorCode(final APIStatusCode p0, final IReloadAction p1, final boolean p2);
    
    public interface ICancelAction
    {
        void cancelPressed();
    }
    
    public interface IReloadAction
    {
        void performReload();
    }
}
