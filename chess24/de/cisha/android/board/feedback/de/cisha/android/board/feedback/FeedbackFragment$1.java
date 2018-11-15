/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Toast
 */
package de.cisha.android.board.feedback;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;

class FeedbackFragment
implements View.OnClickListener {
    FeedbackFragment() {
    }

    public void onClick(View view) {
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "Feedback", "sent");
        view = new Intent("android.intent.action.SENDTO", Uri.fromParts((String)"mailto", (String)FeedbackFragment.this.getString(2131689961), null));
        if (de.cisha.android.board.feedback.FeedbackFragment.isIntentAvailable((Context)FeedbackFragment.this.getActivity(), (Intent)view)) {
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
}
