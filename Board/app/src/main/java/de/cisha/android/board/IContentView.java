// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import android.view.View;
import java.util.List;
import android.content.Context;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;

public interface IContentView
{
    String getHeaderText(final Resources p0);
    
    MenuItem getHighlightedMenuItem();
    
    List<View> getLeftButtons(final Context p0);
    
    List<View> getRightButtons(final Context p0);
    
    Set<SettingsMenuCategory> getSettingsMenuCategories();
    
    boolean isGrabMenuEnabled();
    
    boolean onBackPressed();
    
    boolean showMenu();
}
