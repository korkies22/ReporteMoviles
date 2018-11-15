/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 */
package android.support.design.internal;

import android.support.design.R;
import android.support.design.internal.NavigationMenuPresenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

private static class NavigationMenuPresenter.NormalViewHolder
extends NavigationMenuPresenter.ViewHolder {
    public NavigationMenuPresenter.NormalViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, View.OnClickListener onClickListener) {
        super(layoutInflater.inflate(R.layout.design_navigation_item, viewGroup, false));
        this.itemView.setOnClickListener(onClickListener);
    }
}
