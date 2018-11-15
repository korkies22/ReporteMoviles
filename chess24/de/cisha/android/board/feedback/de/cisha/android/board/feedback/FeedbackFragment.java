/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 *  android.widget.Toast
 */
package de.cisha.android.board.feedback;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.user.User;
import de.cisha.chess.util.Logger;
import java.util.List;
import java.util.Set;

public class FeedbackFragment
extends AbstractContentFragment {
    private String createEmailBody() {
        Object object;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(Build.MANUFACTURER);
        charSequence.append(" ");
        charSequence.append(Build.MODEL);
        String string = charSequence.toString();
        charSequence = "";
        try {
            object = this.getActivity().getPackageManager().getPackageInfo((String)this.getActivity().getPackageName(), (int)0).versionName;
            charSequence = object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Logger.getInstance().debug(FeedbackFragment.class.getName(), PackageManager.NameNotFoundException.class.getName(), (Throwable)nameNotFoundException);
        }
        object = new StringBuilder();
        object.append(Build.VERSION.RELEASE);
        object.append(" (");
        object.append(Build.VERSION.SDK_INT);
        object.append(")");
        String string2 = object.toString();
        object = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        object = object != null ? object.getNickname() : "";
        String string3 = this.getActivity().getString(2131689983);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("- Device: ");
        stringBuilder.append(string);
        stringBuilder.append("\n- Operating system: ");
        stringBuilder.append(string2);
        stringBuilder.append("\n- Application version: ");
        stringBuilder.append((String)charSequence);
        stringBuilder.append("\n- User: ");
        stringBuilder.append((String)object);
        stringBuilder.append("\n- Language: ");
        stringBuilder.append(string3);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        if (context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131689978);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.FEEDBACK;
    }

    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }

    @Override
    public String getTrackingName() {
        return "Feedback";
    }

    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427440, viewGroup, false);
        ((TextView)layoutInflater.findViewById(2131296528)).setText((CharSequence)this.createEmailBody());
        return layoutInflater;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        view.findViewById(2131296527).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "Feedback", "sent");
                view = new Intent("android.intent.action.SENDTO", Uri.fromParts((String)"mailto", (String)FeedbackFragment.this.getString(2131689961), null));
                if (FeedbackFragment.isIntentAvailable((Context)FeedbackFragment.this.getActivity(), (Intent)view)) {
                    view.putExtra("android.intent.extra.SUBJECT", "Android App Support");
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\n\n");
                    stringBuilder.append(FeedbackFragment.this.createEmailBody());
                    view.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
                    FeedbackFragment.this.startActivity(Intent.createChooser((Intent)view, (CharSequence)"Send feedback"));
                    return;
                }
                Toast.makeText((Context)FeedbackFragment.this.getActivity(), (CharSequence)"No email app available", (int)1).show();
            }
        });
    }

    @Override
    public boolean showMenu() {
        return true;
    }

}
