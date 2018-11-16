// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import java.util.Iterator;
import android.widget.TextView;
import java.util.Arrays;
import android.content.Context;
import android.content.Intent;
import de.cisha.android.board.registration.LoginActivity;
import android.view.View.OnClickListener;
import de.cisha.android.board.service.ServiceProvider;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.android.board.AbstractContentFragment;

public class ProfileEditorGuestFragment extends AbstractContentFragment
{
    GoogleAnalyticsSettingController _analyticsController;
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690224);
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
        return "EditProfile";
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return false;
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, ViewGroup viewGroup, final Bundle bundle) {
        viewGroup = (ViewGroup)layoutInflater.inflate(2131427516, viewGroup, false);
        this._analyticsController = new GoogleAnalyticsSettingController(viewGroup, ServiceProvider.getInstance().getTrackingService());
        viewGroup.findViewById(2131296830).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final Intent intent = new Intent((Context)ProfileEditorGuestFragment.this.getActivity(), (Class)LoginActivity.class);
                intent.setFlags(131072);
                ProfileEditorGuestFragment.this.startActivity(intent);
            }
        });
        final ViewGroup viewGroup2 = (ViewGroup)viewGroup.findViewById(2131296816);
        for (final Integer n : Arrays.asList(2131689675, 2131689679, 2131689664, 2131689674, 2131689663)) {
            final TextView textView = (TextView)layoutInflater.inflate(2131427442, viewGroup2, false);
            textView.setText((int)n);
            viewGroup2.addView((View)textView);
        }
        return (View)viewGroup;
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
}
