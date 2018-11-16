// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import java.util.TreeSet;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;

public class TournamentListFragmentSingleView extends TournamentListFragment
{
    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        final TreeSet<SettingsMenuCategory> set = new TreeSet<SettingsMenuCategory>();
        set.add(SettingsMenuCategory.ANALYTICS);
        set.add(SettingsMenuCategory.BOARD);
        return set;
    }
    
    @Override
    public boolean showMenu() {
        return false;
    }
}
