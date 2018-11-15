/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board;

import android.widget.TextView;
import de.cisha.android.board.user.User;
import java.util.Iterator;
import java.util.Set;

class PremiumIndicatorManager
implements Runnable {
    final /* synthetic */ User val$user;

    PremiumIndicatorManager(User user) {
        this.val$user = user;
    }

    @Override
    public void run() {
        boolean bl = this.val$user != null && PremiumIndicatorManager.this.isPremium();
        Iterator iterator = PremiumIndicatorManager.this._textViews.iterator();
        while (iterator.hasNext()) {
            de.cisha.android.board.PremiumIndicatorManager.showPremiumIndicatorBesideTextView(bl, (TextView)iterator.next());
        }
    }
}
