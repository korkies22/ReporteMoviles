/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Handler
 *  android.os.Looper
 *  android.widget.TextView
 */
package de.cisha.android.board;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.user.User;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class PremiumIndicatorManager
implements IProfileDataService.IUserDataChangedListener {
    private static PremiumIndicatorManager _instance;
    private IMembershipService _membershipService;
    private Set<TextView> _textViews;

    public PremiumIndicatorManager(IProfileDataService iProfileDataService, IMembershipService iMembershipService) {
        this._membershipService = iMembershipService;
        this._textViews = Collections.newSetFromMap(new WeakHashMap());
        iProfileDataService.addUserDataChangedListener(this);
    }

    public static PremiumIndicatorManager getInstance() {
        if (_instance == null) {
            _instance = new PremiumIndicatorManager(ServiceProvider.getInstance().getProfileDataService(), ServiceProvider.getInstance().getMembershipService());
        }
        return _instance;
    }

    private boolean isPremium() {
        if (this._membershipService.getMembershipLevel() == IMembershipService.MembershipLevel.MembershipLevelWebpremium) {
            return true;
        }
        return false;
    }

    public static void showPremiumIndicatorBesideTextView(boolean bl, TextView textView) {
        Drawable drawable = bl ? textView.getContext().getResources().getDrawable(2131231596) : null;
        Drawable[] arrdrawable = textView.getCompoundDrawables();
        textView.setCompoundDrawablesWithIntrinsicBounds(arrdrawable[0], arrdrawable[1], drawable, arrdrawable[3]);
    }

    public void addTextViewToIndicators(TextView textView) {
        this._textViews.add(textView);
        PremiumIndicatorManager.showPremiumIndicatorBesideTextView(this.isPremium(), textView);
    }

    @Override
    public void userDataChanged(final User user) {
        new Handler(Looper.getMainLooper()).post(new Runnable(){

            @Override
            public void run() {
                boolean bl = user != null && PremiumIndicatorManager.this.isPremium();
                Iterator iterator = PremiumIndicatorManager.this._textViews.iterator();
                while (iterator.hasNext()) {
                    PremiumIndicatorManager.showPremiumIndicatorBesideTextView(bl, (TextView)iterator.next());
                }
            }
        });
    }

}
