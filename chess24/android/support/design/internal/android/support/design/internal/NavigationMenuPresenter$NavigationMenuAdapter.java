/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.Bundle
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package android.support.design.internal;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.internal.ParcelableSparseArray;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

private class NavigationMenuPresenter.NavigationMenuAdapter
extends RecyclerView.Adapter<NavigationMenuPresenter.ViewHolder> {
    private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
    private static final String STATE_CHECKED_ITEM = "android:menu:checked";
    private static final int VIEW_TYPE_HEADER = 3;
    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_SEPARATOR = 2;
    private static final int VIEW_TYPE_SUBHEADER = 1;
    private MenuItemImpl mCheckedItem;
    private final ArrayList<NavigationMenuPresenter.NavigationMenuItem> mItems = new ArrayList();
    private boolean mUpdateSuspended;

    NavigationMenuPresenter.NavigationMenuAdapter() {
        this.prepareMenuItems();
    }

    private void appendTransparentIconIfMissing(int n, int n2) {
        while (n < n2) {
            ((NavigationMenuPresenter.NavigationMenuTextItem)this.mItems.get((int)n)).needsEmptyIcon = true;
            ++n;
        }
    }

    private void prepareMenuItems() {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:296)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public Bundle createInstanceState() {
        Bundle bundle = new Bundle();
        if (this.mCheckedItem != null) {
            bundle.putInt(STATE_CHECKED_ITEM, this.mCheckedItem.getItemId());
        }
        SparseArray sparseArray = new SparseArray();
        int n = this.mItems.size();
        for (int i = 0; i < n; ++i) {
            NavigationMenuPresenter.NavigationMenuItem navigationMenuItem = this.mItems.get(i);
            if (!(navigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuTextItem)) continue;
            MenuItemImpl menuItemImpl = ((NavigationMenuPresenter.NavigationMenuTextItem)navigationMenuItem).getMenuItem();
            navigationMenuItem = menuItemImpl != null ? menuItemImpl.getActionView() : null;
            if (navigationMenuItem == null) continue;
            ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
            navigationMenuItem.saveHierarchyState((SparseArray)parcelableSparseArray);
            sparseArray.put(menuItemImpl.getItemId(), (Object)parcelableSparseArray);
        }
        bundle.putSparseParcelableArray(STATE_ACTION_VIEWS, sparseArray);
        return bundle;
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    @Override
    public long getItemId(int n) {
        return n;
    }

    @Override
    public int getItemViewType(int n) {
        NavigationMenuPresenter.NavigationMenuItem navigationMenuItem = this.mItems.get(n);
        if (navigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuSeparatorItem) {
            return 2;
        }
        if (navigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuHeaderItem) {
            return 3;
        }
        if (navigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
            if (((NavigationMenuPresenter.NavigationMenuTextItem)navigationMenuItem).getMenuItem().hasSubMenu()) {
                return 1;
            }
            return 0;
        }
        throw new RuntimeException("Unknown item type.");
    }

    @Override
    public void onBindViewHolder(NavigationMenuPresenter.ViewHolder object, int n) {
        switch (this.getItemViewType(n)) {
            default: {
                return;
            }
            case 2: {
                NavigationMenuPresenter.NavigationMenuSeparatorItem navigationMenuSeparatorItem = (NavigationMenuPresenter.NavigationMenuSeparatorItem)this.mItems.get(n);
                object.itemView.setPadding(0, navigationMenuSeparatorItem.getPaddingTop(), 0, navigationMenuSeparatorItem.getPaddingBottom());
                return;
            }
            case 1: {
                ((TextView)object.itemView).setText(((NavigationMenuPresenter.NavigationMenuTextItem)this.mItems.get(n)).getMenuItem().getTitle());
                return;
            }
            case 0: 
        }
        NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView)object.itemView;
        navigationMenuItemView.setIconTintList(NavigationMenuPresenter.this.mIconTintList);
        if (NavigationMenuPresenter.this.mTextAppearanceSet) {
            navigationMenuItemView.setTextAppearance(NavigationMenuPresenter.this.mTextAppearance);
        }
        if (NavigationMenuPresenter.this.mTextColor != null) {
            navigationMenuItemView.setTextColor(NavigationMenuPresenter.this.mTextColor);
        }
        object = NavigationMenuPresenter.this.mItemBackground != null ? NavigationMenuPresenter.this.mItemBackground.getConstantState().newDrawable() : null;
        ViewCompat.setBackground((View)navigationMenuItemView, (Drawable)object);
        object = (NavigationMenuPresenter.NavigationMenuTextItem)this.mItems.get(n);
        navigationMenuItemView.setNeedsEmptyIcon(object.needsEmptyIcon);
        navigationMenuItemView.initialize(object.getMenuItem(), 0);
    }

    @Override
    public NavigationMenuPresenter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        switch (n) {
            default: {
                return null;
            }
            case 3: {
                return new NavigationMenuPresenter.HeaderViewHolder((View)NavigationMenuPresenter.this.mHeaderLayout);
            }
            case 2: {
                return new NavigationMenuPresenter.SeparatorViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup);
            }
            case 1: {
                return new NavigationMenuPresenter.SubheaderViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup);
            }
            case 0: 
        }
        return new NavigationMenuPresenter.NormalViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup, NavigationMenuPresenter.this.mOnClickListener);
    }

    @Override
    public void onViewRecycled(NavigationMenuPresenter.ViewHolder viewHolder) {
        if (viewHolder instanceof NavigationMenuPresenter.NormalViewHolder) {
            ((NavigationMenuItemView)viewHolder.itemView).recycle();
        }
    }

    public void restoreInstanceState(Bundle bundle) {
        Object object;
        int n;
        int n2 = 0;
        int n3 = bundle.getInt(STATE_CHECKED_ITEM, 0);
        if (n3 != 0) {
            this.mUpdateSuspended = true;
            int n4 = this.mItems.size();
            for (n = 0; n < n4; ++n) {
                object = this.mItems.get(n);
                if (!(object instanceof NavigationMenuPresenter.NavigationMenuTextItem) || (object = ((NavigationMenuPresenter.NavigationMenuTextItem)object).getMenuItem()) == null || object.getItemId() != n3) continue;
                this.setCheckedItem((MenuItemImpl)object);
                break;
            }
            this.mUpdateSuspended = false;
            this.prepareMenuItems();
        }
        if ((bundle = bundle.getSparseParcelableArray(STATE_ACTION_VIEWS)) != null) {
            n3 = this.mItems.size();
            for (n = n2; n < n3; ++n) {
                Object object2;
                object = this.mItems.get(n);
                if (!(object instanceof NavigationMenuPresenter.NavigationMenuTextItem) || (object2 = ((NavigationMenuPresenter.NavigationMenuTextItem)object).getMenuItem()) == null || (object = object2.getActionView()) == null || (object2 = (ParcelableSparseArray)((Object)bundle.get(object2.getItemId()))) == null) continue;
                object.restoreHierarchyState((SparseArray)object2);
            }
        }
    }

    public void setCheckedItem(MenuItemImpl menuItemImpl) {
        if (this.mCheckedItem != menuItemImpl) {
            if (!menuItemImpl.isCheckable()) {
                return;
            }
            if (this.mCheckedItem != null) {
                this.mCheckedItem.setChecked(false);
            }
            this.mCheckedItem = menuItemImpl;
            menuItemImpl.setChecked(true);
            return;
        }
    }

    public void setUpdateSuspended(boolean bl) {
        this.mUpdateSuspended = bl;
    }

    public void update() {
        this.prepareMenuItems();
        this.notifyDataSetChanged();
    }
}
