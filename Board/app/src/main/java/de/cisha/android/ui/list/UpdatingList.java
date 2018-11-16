// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.list;

public interface UpdatingList
{
    void disableFooter();
    
    void disableHeader();
    
    void enableFooter();
    
    void enableHeader();
    
    void setListScrollListener(final UpdatingListListener p0);
    
    void updateFinished();
    
    void updateStarted();
    
    public interface UpdatingListListener
    {
        void footerReached();
        
        void headerReached();
    }
}
