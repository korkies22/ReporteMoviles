/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.user.Subscription;
import de.cisha.android.board.user.User;
import de.cisha.android.ui.patterns.text.CustomTextView;
import java.util.Date;

class ProfileEditorFragment
implements Runnable {
    ProfileEditorFragment() {
    }

    @Override
    public void run() {
        if (ProfileEditorFragment.this._user != null && ProfileEditorFragment.this._viewCreated) {
            Object object = ProfileEditorFragment.this._user;
            Date date = new Date();
            Subscription subscription = object.getSubscription(SubscriptionType.PREMIUM);
            boolean bl = true;
            boolean bl2 = subscription != null && subscription.getExpirationDate().after(date);
            if ((object = object.getSubscription(SubscriptionType.PREMIUM_MOBILE)) == null || !object.getExpirationDate().after(date)) {
                bl = false;
            }
            ProfileEditorFragment.this._mobilePremiumButton.setVisibility(8);
            ProfileEditorFragment.this._webPremiumButton.setVisibility(8);
            ProfileEditorFragment.this._mobilePremiumDateText.setVisibility(8);
            ProfileEditorFragment.this._mobilePremiumText.setVisibility(8);
            if (bl2) {
                ProfileEditorFragment.this._webPremiumButton.setVisibility(0);
                ProfileEditorFragment.this._mobilePremiumDateText.setText((CharSequence)ProfileEditorFragment.this.formatDate(subscription.getExpirationDate()));
                ProfileEditorFragment.this._mobilePremiumDateText.setVisibility(0);
                ProfileEditorFragment.this._mobilePremiumText.setVisibility(0);
                return;
            }
            if (bl) {
                ProfileEditorFragment.this._mobilePremiumButton.setText(2131689958);
                ProfileEditorFragment.this._mobilePremiumButton.setVisibility(0);
                ProfileEditorFragment.this._mobilePremiumDateText.setText((CharSequence)ProfileEditorFragment.this.formatDate(object.getExpirationDate()));
                ProfileEditorFragment.this._mobilePremiumDateText.setVisibility(0);
                ProfileEditorFragment.this._mobilePremiumText.setText(2131689960);
                ProfileEditorFragment.this._mobilePremiumText.setVisibility(0);
                return;
            }
            ProfileEditorFragment.this._mobilePremiumButton.setText(2131689957);
            ProfileEditorFragment.this._mobilePremiumButton.setVisibility(0);
            ProfileEditorFragment.this._mobilePremiumText.setText(2131689956);
            ProfileEditorFragment.this._mobilePremiumText.setVisibility(0);
        }
    }
}
