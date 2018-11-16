// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

public class RookiePromotionFragment extends RookieInfoDialogFragment
{
    private View _chooserView;
    
    @Override
    protected RookieType getRookieType() {
        return RookieType.PROMOTION;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setCancelable(true);
        this.setTitle("choose your piece");
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.getContentContainerView().addView(this._chooserView);
        return onCreateView;
    }
    
    public void setPieceChooserView(final View chooserView) {
        this._chooserView = chooserView;
    }
}
