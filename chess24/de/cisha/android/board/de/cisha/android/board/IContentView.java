/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.view.View
 */
package de.cisha.android.board;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import java.util.List;
import java.util.Set;

public interface IContentView {
    public String getHeaderText(Resources var1);

    public MenuItem getHighlightedMenuItem();

    public List<View> getLeftButtons(Context var1);

    public List<View> getRightButtons(Context var1);

    public Set<SettingsMenuCategory> getSettingsMenuCategories();

    public boolean isGrabMenuEnabled();

    public boolean onBackPressed();

    public boolean showMenu();
}
