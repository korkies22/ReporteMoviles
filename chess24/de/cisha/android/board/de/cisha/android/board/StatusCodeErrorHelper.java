/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.modalfragments.GoPremiumWithListDialogFragment;
import de.cisha.android.board.modalfragments.RookieDialogFragmentNoInternet;
import de.cisha.android.board.modalfragments.RookieDialogFrament;
import de.cisha.android.board.registration.LoginActivity;
import de.cisha.android.board.service.IConfigService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.Iterator;
import java.util.List;

public class StatusCodeErrorHelper {
    public static String createErrorMessageFrom(List<LoadFieldError> object) {
        String string = "";
        Object object2 = string;
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                object2 = (LoadFieldError)object.next();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(" - ");
                stringBuilder.append(object2.getMessage());
                string = stringBuilder.toString();
            }
            int n = string.length() - 1;
            if (n <= 0) {
                n = 0;
            }
            object2 = string.substring(0, n);
        }
        return object2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static void handleErrorCode(FragmentActivity var0, APIStatusCode var1_1, IErrorPresenter.IReloadAction var2_2, View.OnClickListener var3_3) {
        if (var0 == null) return;
        if (var0.isFinishing()) {
            return;
        }
        switch (.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[var1_1.ordinal()]) {
            default: {
                var4_4 = var0.getString(2131689973);
                var2_2 = var4_4;
                if (ServiceProvider.getInstance().getConfigService().isDebugMode()) {
                    var2_2 = new StringBuilder();
                    var2_2.append(var4_4);
                    var2_2.append("\ninternal Code: ");
                    var2_2.append(var1_1.name());
                    var2_2 = var2_2.toString();
                    ** break;
                }
                ** GOTO lbl37
            }
            case 12: 
            case 13: {
                StatusCodeErrorHelper.showErrorDialog(var0, var0.getString(2131689970), var3_3);
                return;
            }
            case 7: {
                StatusCodeErrorHelper.showErrorDialog(var0, var0.getString(2131689972), var3_3);
                return;
            }
            case 5: 
            case 6: {
                var1_1 = new RookieDialogFragmentNoInternet();
                var1_1.setOnReloadButtonClickListener(new View.OnClickListener((IErrorPresenter.IReloadAction)var2_2, (RookieDialogFragmentNoInternet)var1_1){
                    final /* synthetic */ RookieDialogFragmentNoInternet val$fraggi;
                    final /* synthetic */ IErrorPresenter.IReloadAction val$reloadAction;
                    {
                        this.val$reloadAction = iReloadAction;
                        this.val$fraggi = rookieDialogFragmentNoInternet;
                    }

                    public void onClick(View view) {
                        if (this.val$reloadAction != null) {
                            this.val$reloadAction.performReload();
                            this.val$fraggi.dismiss();
                        }
                    }
                });
                var1_1.setOnCancelButtonClickListener(var3_3);
                var1_1.show(var0.getSupportFragmentManager(), "No Internet");
                return;
            }
            case 3: 
            case 4: {
                StatusCodeErrorHelper.logoutAndShowLoginActivity(var0);
                return;
            }
            case 2: {
                new GoPremiumWithListDialogFragment().show(var0.getSupportFragmentManager(), "premium");
                return;
            }
            case 1: {
                StatusCodeErrorHelper.logoutAndShowLoginActivity(var0);
                return;
            }
lbl37: // 2 sources:
            StatusCodeErrorHelper.showErrorDialog(var0, (String)var2_2, var3_3);
            case 8: 
            case 9: 
            case 10: 
            case 11: 
        }
    }

    private static void logoutAndShowLoginActivity(Activity activity) {
        ServiceProvider.getInstance().getLoginService().logOut(new ILoginService.LogoutCallback(){

            @Override
            public void logoutFailed(String string) {
            }

            @Override
            public void logoutSucceeded() {
            }
        });
        activity.startActivity(new Intent((Context)activity, LoginActivity.class));
        activity.finish();
    }

    private static void showErrorDialog(FragmentActivity fragmentActivity, String string, View.OnClickListener onClickListener) {
        RookieDialogFrament rookieDialogFrament = new RookieDialogFrament();
        rookieDialogFrament.setTitle(fragmentActivity.getString(2131689971));
        rookieDialogFrament.setText(string);
        rookieDialogFrament.setButtonTypes(RookieDialogFrament.RookieButtonType.CANCEL);
        rookieDialogFrament.setOnCancelButtonClickListener(onClickListener);
        rookieDialogFrament.show(fragmentActivity.getSupportFragmentManager(), "");
    }

}
