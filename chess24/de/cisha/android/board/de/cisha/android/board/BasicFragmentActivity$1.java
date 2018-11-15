/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.View$OnClickListener
 *  org.json.JSONObject
 */
package de.cisha.android.board;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServerAPIStatus;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonAlternative;
import de.cisha.android.ui.patterns.buttons.CustomButtonPositive;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

class BasicFragmentActivity
implements LoadCommandCallback<ServerAPIStatus> {
    BasicFragmentActivity() {
    }

    @Override
    public void loadingCancelled() {
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
    }

    @Override
    public void loadingSucceded(ServerAPIStatus object) {
        if (object != null && (object.isDeprecated() || !object.isValid())) {
            Object object2 = BasicFragmentActivity.this.getString(2131689557);
            final RookieInfoDialogFragment rookieInfoDialogFragment = new RookieInfoDialogFragment();
            CustomButtonPositive customButtonPositive = new CustomButtonPositive((Context)BasicFragmentActivity.this);
            customButtonPositive.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "Opened AppStore", "android");
                    rookieInfoDialogFragment.dismiss();
                    BasicFragmentActivity.this.openAppStore();
                }
            });
            customButtonPositive.setText(2131689558);
            rookieInfoDialogFragment.addButton(customButtonPositive);
            rookieInfoDialogFragment.setTitle((CharSequence)object2);
            rookieInfoDialogFragment.setCancelable(false);
            object2 = new CustomButtonAlternative((Context)BasicFragmentActivity.this);
            object2.setText(2131689556);
            if (!object.isValid()) {
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "API expiration dialog shown", "hasExpired");
                rookieInfoDialogFragment.setText(BasicFragmentActivity.this.getString(2131689554));
                object2.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        rookieInfoDialogFragment.dismiss();
                        BasicFragmentActivity.this.finish();
                    }
                });
            } else if (object.isDeprecated()) {
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "API expiration dialog shown", "willExpire");
                object = object.getDeprecationDate();
                int n = object != null ? (int)((object.getTime() - new Date().getTime()) / 86400000L) : 1;
                rookieInfoDialogFragment.setText(BasicFragmentActivity.this.getString(2131689555, new Object[]{n}));
                object2.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        rookieInfoDialogFragment.dismiss();
                    }
                });
            }
            rookieInfoDialogFragment.addButton((CustomButton)((Object)object2));
            rookieInfoDialogFragment.show(BasicFragmentActivity.this.getSupportFragmentManager(), "update fragment");
        }
    }

}
