/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

public class RookiePromotionFragment
extends RookieInfoDialogFragment {
    private View _chooserView;

    @Override
    protected RookieInfoDialogFragment.RookieType getRookieType() {
        return RookieInfoDialogFragment.RookieType.PROMOTION;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setCancelable(true);
        this.setTitle("choose your piece");
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.getContentContainerView().addView(this._chooserView);
        return layoutInflater;
    }

    public void setPieceChooserView(View view) {
        this._chooserView = view;
    }
}
