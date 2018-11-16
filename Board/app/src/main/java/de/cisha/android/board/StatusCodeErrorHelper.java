// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import de.cisha.android.board.modalfragments.RookieDialogFrament;
import android.content.Context;
import android.content.Intent;
import de.cisha.android.board.registration.LoginActivity;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.modalfragments.GoPremiumWithListDialogFragment;
import android.app.Activity;
import android.view.View;
import de.cisha.android.board.modalfragments.RookieDialogFragmentNoInternet;
import de.cisha.android.board.service.ServiceProvider;
import android.view.View.OnClickListener;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import android.support.v4.app.FragmentActivity;
import java.util.Iterator;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;

public class StatusCodeErrorHelper
{
    public static String createErrorMessageFrom(final List<LoadFieldError> list) {
        String substring;
        String string = substring = "";
        if (list != null) {
            for (final LoadFieldError loadFieldError : list) {
                final StringBuilder sb = new StringBuilder();
                sb.append(string);
                sb.append(" - ");
                sb.append(loadFieldError.getMessage());
                string = sb.toString();
            }
            int n = string.length() - 1;
            if (n <= 0) {
                n = 0;
            }
            substring = string.substring(0, n);
        }
        return substring;
    }
    
    public static void handleErrorCode(final FragmentActivity fragmentActivity, final APIStatusCode apiStatusCode, final IErrorPresenter.IReloadAction reloadAction, final View.OnClickListener onCancelButtonClickListener) {
        if (fragmentActivity == null) {
            return;
        }
        if (fragmentActivity.isFinishing()) {
            return;
        }
        switch (StatusCodeErrorHelper.3..SwitchMap.de.cisha.android.board.service.jsonparser.APIStatusCode[apiStatusCode.ordinal()]) {
            default: {
                String s = fragmentActivity.getString(2131689973);
                if (ServiceProvider.getInstance().getConfigService().isDebugMode()) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(s);
                    sb.append("\ninternal Code: ");
                    sb.append(apiStatusCode.name());
                    s = sb.toString();
                }
                showErrorDialog(fragmentActivity, s, onCancelButtonClickListener);
            }
            case 12:
            case 13: {
                showErrorDialog(fragmentActivity, fragmentActivity.getString(2131689970), onCancelButtonClickListener);
            }
            case 7: {
                showErrorDialog(fragmentActivity, fragmentActivity.getString(2131689972), onCancelButtonClickListener);
            }
            case 5:
            case 6: {
                final RookieDialogFragmentNoInternet rookieDialogFragmentNoInternet = new RookieDialogFragmentNoInternet();
                rookieDialogFragmentNoInternet.setOnReloadButtonClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                        if (reloadAction != null) {
                            reloadAction.performReload();
                            rookieDialogFragmentNoInternet.dismiss();
                        }
                    }
                });
                rookieDialogFragmentNoInternet.setOnCancelButtonClickListener(onCancelButtonClickListener);
                rookieDialogFragmentNoInternet.show(fragmentActivity.getSupportFragmentManager(), "No Internet");
            }
            case 3:
            case 4: {
                logoutAndShowLoginActivity(fragmentActivity);
            }
            case 2: {
                new GoPremiumWithListDialogFragment().show(fragmentActivity.getSupportFragmentManager(), "premium");
            }
            case 1: {
                logoutAndShowLoginActivity(fragmentActivity);
            }
            case 8:
            case 9:
            case 10:
            case 11: {}
        }
    }
    
    private static void logoutAndShowLoginActivity(final Activity activity) {
        ServiceProvider.getInstance().getLoginService().logOut((ILoginService.LogoutCallback)new ILoginService.LogoutCallback() {
            @Override
            public void logoutFailed(final String s) {
            }
            
            @Override
            public void logoutSucceeded() {
            }
        });
        activity.startActivity(new Intent((Context)activity, (Class)LoginActivity.class));
        activity.finish();
    }
    
    private static void showErrorDialog(final FragmentActivity fragmentActivity, final String text, final View.OnClickListener onCancelButtonClickListener) {
        final RookieDialogFrament rookieDialogFrament = new RookieDialogFrament();
        rookieDialogFrament.setTitle(fragmentActivity.getString(2131689971));
        rookieDialogFrament.setText(text);
        rookieDialogFrament.setButtonTypes(RookieDialogFrament.RookieButtonType.CANCEL);
        rookieDialogFrament.setOnCancelButtonClickListener(onCancelButtonClickListener);
        rookieDialogFrament.show(fragmentActivity.getSupportFragmentManager(), "");
    }
}
