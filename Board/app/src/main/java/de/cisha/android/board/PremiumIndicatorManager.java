// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import java.util.Iterator;
import android.os.Handler;
import android.os.Looper;
import de.cisha.android.board.user.User;
import android.graphics.drawable.Drawable;
import de.cisha.android.board.service.ServiceProvider;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import android.widget.TextView;
import java.util.Set;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IProfileDataService;

public class PremiumIndicatorManager implements IUserDataChangedListener
{
    private static PremiumIndicatorManager _instance;
    private IMembershipService _membershipService;
    private Set<TextView> _textViews;
    
    public PremiumIndicatorManager(final IProfileDataService profileDataService, final IMembershipService membershipService) {
        this._membershipService = membershipService;
        this._textViews = Collections.newSetFromMap(new WeakHashMap<TextView, Boolean>());
        profileDataService.addUserDataChangedListener((IProfileDataService.IUserDataChangedListener)this);
    }
    
    public static PremiumIndicatorManager getInstance() {
        if (PremiumIndicatorManager._instance == null) {
            PremiumIndicatorManager._instance = new PremiumIndicatorManager(ServiceProvider.getInstance().getProfileDataService(), ServiceProvider.getInstance().getMembershipService());
        }
        return PremiumIndicatorManager._instance;
    }
    
    private boolean isPremium() {
        return this._membershipService.getMembershipLevel() == IMembershipService.MembershipLevel.MembershipLevelWebpremium;
    }
    
    public static void showPremiumIndicatorBesideTextView(final boolean b, final TextView textView) {
        Drawable drawable;
        if (b) {
            drawable = textView.getContext().getResources().getDrawable(2131231596);
        }
        else {
            drawable = null;
        }
        final Drawable[] compoundDrawables = textView.getCompoundDrawables();
        textView.setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], compoundDrawables[1], drawable, compoundDrawables[3]);
    }
    
    public void addTextViewToIndicators(final TextView textView) {
        this._textViews.add(textView);
        showPremiumIndicatorBesideTextView(this.isPremium(), textView);
    }
    
    @Override
    public void userDataChanged(final User user) {
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            @Override
            public void run() {
                final boolean b = user != null && PremiumIndicatorManager.this.isPremium();
                final Iterator<TextView> iterator = (Iterator<TextView>)PremiumIndicatorManager.this._textViews.iterator();
                while (iterator.hasNext()) {
                    PremiumIndicatorManager.showPremiumIndicatorBesideTextView(b, iterator.next());
                }
            }
        });
    }
}
