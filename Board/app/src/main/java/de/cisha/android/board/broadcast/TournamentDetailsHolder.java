// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import android.support.v4.app.Fragment;
import de.cisha.android.board.broadcast.model.TournamentHolder;

public interface TournamentDetailsHolder
{
    ContentFragmentSetter getContentFragmentSetter();
    
    TournamentHolder getTournamentHolder();
    
    public interface ContentFragmentSetter
    {
        void setContentFragment(final Fragment p0, final boolean p1, final String p2, final String p3);
    }
}
