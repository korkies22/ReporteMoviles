// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import android.content.Context;
import android.content.Intent;
import de.cisha.android.board.registration.LoginActivity;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;

public class LandingFragment extends AbstractContentFragment
{
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690384);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }
    
    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }
    
    @Override
    public String getTrackingName() {
        return "LandingFragment";
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427359, viewGroup, false);
        inflate.findViewById(2131296558).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                LandingFragment.this.startActivity(new Intent((Context)LandingFragment.this.getActivity(), (Class)LoginActivity.class));
            }
        });
        return inflate;
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
}
