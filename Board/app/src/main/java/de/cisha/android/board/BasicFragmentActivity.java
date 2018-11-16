// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import java.util.Date;
import de.cisha.android.ui.patterns.buttons.CustomButtonAlternative;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.board.service.ITrackingService;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import de.cisha.android.ui.patterns.buttons.CustomButtonPositive;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.ServerAPIStatus;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.ServiceProvider;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class BasicFragmentActivity extends FragmentActivity
{
    protected volatile boolean _flagOnSaveInstanceStateCalled;
    protected DialogFragment _networkLoadingDialog;
    
    @Override
    protected void onResume() {
        super.onResume();
        this._flagOnSaveInstanceStateCalled = false;
    }
    
    @Override
    protected void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this._flagOnSaveInstanceStateCalled = true;
    }
    
    public boolean onSaveInstanceStateCalled() {
        return this._flagOnSaveInstanceStateCalled;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        this._flagOnSaveInstanceStateCalled = false;
        ServiceProvider.getInstance().getConfigService().checkServerBaseUrl(new LoadCommandCallback<ServerAPIStatus>() {
            @Override
            public void loadingCancelled() {
            }
            
            @Override
            public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
            }
            
            @Override
            public void loadingSucceded(final ServerAPIStatus serverAPIStatus) {
                if (serverAPIStatus != null && (serverAPIStatus.isDeprecated() || !serverAPIStatus.isValid())) {
                    final String string = BasicFragmentActivity.this.getString(2131689557);
                    final RookieInfoDialogFragment rookieInfoDialogFragment = new RookieInfoDialogFragment();
                    final CustomButtonPositive customButtonPositive = new CustomButtonPositive((Context)BasicFragmentActivity.this);
                    customButtonPositive.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                        public void onClick(final View view) {
                            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "Opened AppStore", "android");
                            rookieInfoDialogFragment.dismiss();
                            BasicFragmentActivity.this.openAppStore();
                        }
                    });
                    customButtonPositive.setText(2131689558);
                    rookieInfoDialogFragment.addButton(customButtonPositive);
                    rookieInfoDialogFragment.setTitle(string);
                    rookieInfoDialogFragment.setCancelable(false);
                    final CustomButtonAlternative customButtonAlternative = new CustomButtonAlternative((Context)BasicFragmentActivity.this);
                    customButtonAlternative.setText(2131689556);
                    if (!serverAPIStatus.isValid()) {
                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "API expiration dialog shown", "hasExpired");
                        rookieInfoDialogFragment.setText(BasicFragmentActivity.this.getString(2131689554));
                        customButtonAlternative.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                            public void onClick(final View view) {
                                rookieInfoDialogFragment.dismiss();
                                BasicFragmentActivity.this.finish();
                            }
                        });
                    }
                    else if (serverAPIStatus.isDeprecated()) {
                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "API expiration dialog shown", "willExpire");
                        final Date deprecationDate = serverAPIStatus.getDeprecationDate();
                        int n;
                        if (deprecationDate != null) {
                            n = (int)((deprecationDate.getTime() - new Date().getTime()) / 86400000L);
                        }
                        else {
                            n = 1;
                        }
                        rookieInfoDialogFragment.setText(BasicFragmentActivity.this.getString(2131689555, new Object[] { n }));
                        customButtonAlternative.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                            public void onClick(final View view) {
                                rookieInfoDialogFragment.dismiss();
                            }
                        });
                    }
                    rookieInfoDialogFragment.addButton(customButtonAlternative);
                    rookieInfoDialogFragment.show(BasicFragmentActivity.this.getSupportFragmentManager(), "update fragment");
                }
            }
        });
    }
    
    public void openAppStore() {
        final String packageName = this.getPackageName();
        while (true) {
            try {
                final StringBuilder sb = new StringBuilder();
                sb.append("market://details?id=");
                sb.append(packageName);
                this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                return;
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("http://play.google.com/store/apps/details?id=");
                sb2.append(packageName);
                this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb2.toString())));
            }
            catch (ActivityNotFoundException ex) {
                continue;
            }
            break;
        }
    }
}
