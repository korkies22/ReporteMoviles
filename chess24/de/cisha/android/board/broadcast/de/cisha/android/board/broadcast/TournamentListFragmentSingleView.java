/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.TournamentListFragment;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import java.util.TreeSet;

public class TournamentListFragmentSingleView
extends TournamentListFragment {
    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        TreeSet<SettingsMenuCategory> treeSet = new TreeSet<SettingsMenuCategory>();
        treeSet.add(SettingsMenuCategory.ANALYTICS);
        treeSet.add(SettingsMenuCategory.BOARD);
        return treeSet;
    }

    @Override
    public boolean showMenu() {
        return false;
    }
}
