// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.feedback;

import android.widget.Toast;
import android.net.Uri;
import de.cisha.android.board.service.ITrackingService;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import android.content.Intent;
import android.content.Context;
import de.cisha.android.board.user.User;
import de.cisha.android.board.service.ServiceProvider;
import android.os.Build.VERSION;
import android.content.pm.PackageManager.NameNotFoundException;
import de.cisha.chess.util.Logger;
import android.os.Build;
import de.cisha.android.board.AbstractContentFragment;

public class FeedbackFragment extends AbstractContentFragment
{
    private String createEmailBody() {
        final StringBuilder sb = new StringBuilder();
        sb.append(Build.MANUFACTURER);
        sb.append(" ");
        sb.append(Build.MODEL);
        final String string = sb.toString();
        String versionName = "";
        try {
            versionName = this.getActivity().getPackageManager().getPackageInfo(this.getActivity().getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException ex) {
            Logger.getInstance().debug(FeedbackFragment.class.getName(), PackageManager.NameNotFoundException.class.getName(), (Throwable)ex);
        }
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(Build.VERSION.RELEASE);
        sb2.append(" (");
        sb2.append(Build.VERSION.SDK_INT);
        sb2.append(")");
        final String string2 = sb2.toString();
        final User currentUserData = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        String nickname;
        if (currentUserData != null) {
            nickname = currentUserData.getNickname();
        }
        else {
            nickname = "";
        }
        final String string3 = this.getActivity().getString(2131689983);
        final StringBuilder sb3 = new StringBuilder();
        sb3.append("- Device: ");
        sb3.append(string);
        sb3.append("\n- Operating system: ");
        sb3.append(string2);
        sb3.append("\n- Application version: ");
        sb3.append(versionName);
        sb3.append("\n- User: ");
        sb3.append(nickname);
        sb3.append("\n- Language: ");
        sb3.append(string3);
        sb3.append("\n");
        return sb3.toString();
    }
    
    public static boolean isIntentAvailable(final Context context, final Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
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
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427440, viewGroup, false);
        ((TextView)inflate.findViewById(2131296528)).setText((CharSequence)this.createEmailBody());
        return inflate;
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        view.findViewById(2131296527).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "Feedback", "sent");
                final Intent intent = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", FeedbackFragment.this.getString(2131689961), (String)null));
                if (FeedbackFragment.isIntentAvailable((Context)FeedbackFragment.this.getActivity(), intent)) {
                    intent.putExtra("android.intent.extra.SUBJECT", "Android App Support");
                    final StringBuilder sb = new StringBuilder();
                    sb.append("\n\n");
                    sb.append(FeedbackFragment.this.createEmailBody());
                    intent.putExtra("android.intent.extra.TEXT", sb.toString());
                    FeedbackFragment.this.startActivity(Intent.createChooser(intent, (CharSequence)"Send feedback"));
                    return;
                }
                Toast.makeText((Context)FeedbackFragment.this.getActivity(), (CharSequence)"No email app available", 1).show();
            }
        });
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
}
