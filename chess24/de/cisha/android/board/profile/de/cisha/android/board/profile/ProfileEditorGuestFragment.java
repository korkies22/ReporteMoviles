/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.profile.GoogleAnalyticsSettingController;
import de.cisha.android.board.registration.LoginActivity;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import java.util.Arrays;
import java.util.Set;

public class ProfileEditorGuestFragment
extends AbstractContentFragment {
    GoogleAnalyticsSettingController _analyticsController;

    @Override
    public String getHeaderText(Resources resources) {
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
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        viewGroup = (ViewGroup)layoutInflater.inflate(2131427516, viewGroup, false);
        this._analyticsController = new GoogleAnalyticsSettingController(viewGroup, ServiceProvider.getInstance().getTrackingService());
        viewGroup.findViewById(2131296830).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                view = new Intent((Context)ProfileEditorGuestFragment.this.getActivity(), LoginActivity.class);
                view.setFlags(131072);
                ProfileEditorGuestFragment.this.startActivity((Intent)view);
            }
        });
        bundle = (ViewGroup)viewGroup.findViewById(2131296816);
        for (Integer n : Arrays.asList(2131689675, 2131689679, 2131689664, 2131689674, 2131689663)) {
            TextView textView = (TextView)layoutInflater.inflate(2131427442, (ViewGroup)bundle, false);
            textView.setText(n.intValue());
            bundle.addView((View)textView);
        }
        return viewGroup;
    }

    @Override
    public boolean showMenu() {
        return true;
    }

}
