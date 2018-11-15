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
 */
package de.cisha.android.board;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.registration.LoginActivity;
import java.util.Set;

public class LandingFragment
extends AbstractContentFragment {
    @Override
    public String getHeaderText(Resources resources) {
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
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427359, viewGroup, false);
        layoutInflater.findViewById(2131296558).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                LandingFragment.this.startActivity(new Intent((Context)LandingFragment.this.getActivity(), LoginActivity.class));
            }
        });
        return layoutInflater;
    }

    @Override
    public boolean showMenu() {
        return true;
    }

}
