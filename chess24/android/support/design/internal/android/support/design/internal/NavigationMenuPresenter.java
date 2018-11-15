/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
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

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.internal.NavigationMenuView;
import android.support.design.internal.ParcelableSparseArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class NavigationMenuPresenter
implements MenuPresenter {
    private static final String STATE_ADAPTER = "android:menu:adapter";
    private static final String STATE_HEADER = "android:menu:header";
    private static final String STATE_HIERARCHY = "android:menu:list";
    NavigationMenuAdapter mAdapter;
    private MenuPresenter.Callback mCallback;
    LinearLayout mHeaderLayout;
    ColorStateList mIconTintList;
    private int mId;
    Drawable mItemBackground;
    LayoutInflater mLayoutInflater;
    MenuBuilder mMenu;
    private NavigationMenuView mMenuView;
    final View.OnClickListener mOnClickListener = new View.OnClickListener(){

        public void onClick(View object) {
            object = (NavigationMenuItemView)object;
            NavigationMenuPresenter.this.setUpdateSuspended(true);
            object = object.getItemData();
            boolean bl = NavigationMenuPresenter.this.mMenu.performItemAction((MenuItem)object, NavigationMenuPresenter.this, 0);
            if (object != null && object.isCheckable() && bl) {
                NavigationMenuPresenter.this.mAdapter.setCheckedItem((MenuItemImpl)object);
            }
            NavigationMenuPresenter.this.setUpdateSuspended(false);
            NavigationMenuPresenter.this.updateMenuView(false);
        }
    };
    int mPaddingSeparator;
    private int mPaddingTopDefault;
    int mTextAppearance;
    boolean mTextAppearanceSet;
    ColorStateList mTextColor;

    public void addHeaderView(@NonNull View view) {
        this.mHeaderLayout.addView(view);
        this.mMenuView.setPadding(0, 0, 0, this.mMenuView.getPaddingBottom());
    }

    @Override
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public void dispatchApplyWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        int n = windowInsetsCompat.getSystemWindowInsetTop();
        if (this.mPaddingTopDefault != n) {
            this.mPaddingTopDefault = n;
            if (this.mHeaderLayout.getChildCount() == 0) {
                this.mMenuView.setPadding(0, this.mPaddingTopDefault, 0, this.mMenuView.getPaddingBottom());
            }
        }
        ViewCompat.dispatchApplyWindowInsets((View)this.mHeaderLayout, windowInsetsCompat);
    }

    @Override
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    public int getHeaderCount() {
        return this.mHeaderLayout.getChildCount();
    }

    public View getHeaderView(int n) {
        return this.mHeaderLayout.getChildAt(n);
    }

    @Override
    public int getId() {
        return this.mId;
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.mItemBackground;
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.mTextColor;
    }

    @Nullable
    public ColorStateList getItemTintList() {
        return this.mIconTintList;
    }

    @Override
    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.mMenuView == null) {
            this.mMenuView = (NavigationMenuView)this.mLayoutInflater.inflate(R.layout.design_navigation_menu, viewGroup, false);
            if (this.mAdapter == null) {
                this.mAdapter = new NavigationMenuAdapter();
            }
            this.mHeaderLayout = (LinearLayout)this.mLayoutInflater.inflate(R.layout.design_navigation_item_header, (ViewGroup)this.mMenuView, false);
            this.mMenuView.setAdapter(this.mAdapter);
        }
        return this.mMenuView;
    }

    public View inflateHeaderView(@LayoutRes int n) {
        View view = this.mLayoutInflater.inflate(n, (ViewGroup)this.mHeaderLayout, false);
        this.addHeaderView(view);
        return view;
    }

    @Override
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.mLayoutInflater = LayoutInflater.from((Context)context);
        this.mMenu = menuBuilder;
        this.mPaddingSeparator = context.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menuBuilder, bl);
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            SparseArray sparseArray = (parcelable = (Bundle)parcelable).getSparseParcelableArray(STATE_HIERARCHY);
            if (sparseArray != null) {
                this.mMenuView.restoreHierarchyState(sparseArray);
            }
            if ((sparseArray = parcelable.getBundle(STATE_ADAPTER)) != null) {
                this.mAdapter.restoreInstanceState((Bundle)sparseArray);
            }
            if ((parcelable = parcelable.getSparseParcelableArray(STATE_HEADER)) != null) {
                this.mHeaderLayout.restoreHierarchyState((SparseArray)parcelable);
            }
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        if (Build.VERSION.SDK_INT >= 11) {
            SparseArray sparseArray;
            Bundle bundle = new Bundle();
            if (this.mMenuView != null) {
                sparseArray = new SparseArray();
                this.mMenuView.saveHierarchyState(sparseArray);
                bundle.putSparseParcelableArray(STATE_HIERARCHY, sparseArray);
            }
            if (this.mAdapter != null) {
                bundle.putBundle(STATE_ADAPTER, this.mAdapter.createInstanceState());
            }
            if (this.mHeaderLayout != null) {
                sparseArray = new SparseArray();
                this.mHeaderLayout.saveHierarchyState(sparseArray);
                bundle.putSparseParcelableArray(STATE_HEADER, sparseArray);
            }
            return bundle;
        }
        return null;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    public void removeHeaderView(@NonNull View view) {
        this.mHeaderLayout.removeView(view);
        if (this.mHeaderLayout.getChildCount() == 0) {
            this.mMenuView.setPadding(0, this.mPaddingTopDefault, 0, this.mMenuView.getPaddingBottom());
        }
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
        this.mCallback = callback;
    }

    public void setCheckedItem(MenuItemImpl menuItemImpl) {
        this.mAdapter.setCheckedItem(menuItemImpl);
    }

    public void setId(int n) {
        this.mId = n;
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        this.mItemBackground = drawable;
        this.updateMenuView(false);
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
        this.updateMenuView(false);
    }

    public void setItemTextAppearance(@StyleRes int n) {
        this.mTextAppearance = n;
        this.mTextAppearanceSet = true;
        this.updateMenuView(false);
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.mTextColor = colorStateList;
        this.updateMenuView(false);
    }

    public void setUpdateSuspended(boolean bl) {
        if (this.mAdapter != null) {
            this.mAdapter.setUpdateSuspended(bl);
        }
    }

    @Override
    public void updateMenuView(boolean bl) {
        if (this.mAdapter != null) {
            this.mAdapter.update();
        }
    }

    private static class HeaderViewHolder
    extends ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    private class NavigationMenuAdapter
    extends RecyclerView.Adapter<ViewHolder> {
        private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
        private static final String STATE_CHECKED_ITEM = "android:menu:checked";
        private static final int VIEW_TYPE_HEADER = 3;
        private static final int VIEW_TYPE_NORMAL = 0;
        private static final int VIEW_TYPE_SEPARATOR = 2;
        private static final int VIEW_TYPE_SUBHEADER = 1;
        private MenuItemImpl mCheckedItem;
        private final ArrayList<NavigationMenuItem> mItems = new ArrayList();
        private boolean mUpdateSuspended;

        NavigationMenuAdapter() {
            this.prepareMenuItems();
        }

        private void appendTransparentIconIfMissing(int n, int n2) {
            while (n < n2) {
                ((NavigationMenuTextItem)this.mItems.get((int)n)).needsEmptyIcon = true;
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
                NavigationMenuItem navigationMenuItem = this.mItems.get(i);
                if (!(navigationMenuItem instanceof NavigationMenuTextItem)) continue;
                MenuItemImpl menuItemImpl = ((NavigationMenuTextItem)navigationMenuItem).getMenuItem();
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
            NavigationMenuItem navigationMenuItem = this.mItems.get(n);
            if (navigationMenuItem instanceof NavigationMenuSeparatorItem) {
                return 2;
            }
            if (navigationMenuItem instanceof NavigationMenuHeaderItem) {
                return 3;
            }
            if (navigationMenuItem instanceof NavigationMenuTextItem) {
                if (((NavigationMenuTextItem)navigationMenuItem).getMenuItem().hasSubMenu()) {
                    return 1;
                }
                return 0;
            }
            throw new RuntimeException("Unknown item type.");
        }

        @Override
        public void onBindViewHolder(ViewHolder object, int n) {
            switch (this.getItemViewType(n)) {
                default: {
                    return;
                }
                case 2: {
                    NavigationMenuSeparatorItem navigationMenuSeparatorItem = (NavigationMenuSeparatorItem)this.mItems.get(n);
                    object.itemView.setPadding(0, navigationMenuSeparatorItem.getPaddingTop(), 0, navigationMenuSeparatorItem.getPaddingBottom());
                    return;
                }
                case 1: {
                    ((TextView)object.itemView).setText(((NavigationMenuTextItem)this.mItems.get(n)).getMenuItem().getTitle());
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
            object = (NavigationMenuTextItem)this.mItems.get(n);
            navigationMenuItemView.setNeedsEmptyIcon(object.needsEmptyIcon);
            navigationMenuItemView.initialize(object.getMenuItem(), 0);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 3: {
                    return new HeaderViewHolder((View)NavigationMenuPresenter.this.mHeaderLayout);
                }
                case 2: {
                    return new SeparatorViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup);
                }
                case 1: {
                    return new SubheaderViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup);
                }
                case 0: 
            }
            return new NormalViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup, NavigationMenuPresenter.this.mOnClickListener);
        }

        @Override
        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder instanceof NormalViewHolder) {
                ((NavigationMenuItemView)viewHolder.itemView).recycle();
            }
        }

        public void restoreInstanceState(Bundle bundle) {
            int n;
            Object object;
            int n2 = 0;
            int n3 = bundle.getInt(STATE_CHECKED_ITEM, 0);
            if (n3 != 0) {
                this.mUpdateSuspended = true;
                int n4 = this.mItems.size();
                for (n = 0; n < n4; ++n) {
                    object = this.mItems.get(n);
                    if (!(object instanceof NavigationMenuTextItem) || (object = ((NavigationMenuTextItem)object).getMenuItem()) == null || object.getItemId() != n3) continue;
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
                    if (!(object instanceof NavigationMenuTextItem) || (object2 = ((NavigationMenuTextItem)object).getMenuItem()) == null || (object = object2.getActionView()) == null || (object2 = (ParcelableSparseArray)((Object)bundle.get(object2.getItemId()))) == null) continue;
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

    private static class NavigationMenuHeaderItem
    implements NavigationMenuItem {
        NavigationMenuHeaderItem() {
        }
    }

    private static interface NavigationMenuItem {
    }

    private static class NavigationMenuSeparatorItem
    implements NavigationMenuItem {
        private final int mPaddingBottom;
        private final int mPaddingTop;

        public NavigationMenuSeparatorItem(int n, int n2) {
            this.mPaddingTop = n;
            this.mPaddingBottom = n2;
        }

        public int getPaddingBottom() {
            return this.mPaddingBottom;
        }

        public int getPaddingTop() {
            return this.mPaddingTop;
        }
    }

    private static class NavigationMenuTextItem
    implements NavigationMenuItem {
        private final MenuItemImpl mMenuItem;
        boolean needsEmptyIcon;

        NavigationMenuTextItem(MenuItemImpl menuItemImpl) {
            this.mMenuItem = menuItemImpl;
        }

        public MenuItemImpl getMenuItem() {
            return this.mMenuItem;
        }
    }

    private static class NormalViewHolder
    extends ViewHolder {
        public NormalViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, View.OnClickListener onClickListener) {
            super(layoutInflater.inflate(R.layout.design_navigation_item, viewGroup, false));
            this.itemView.setOnClickListener(onClickListener);
        }
    }

    private static class SeparatorViewHolder
    extends ViewHolder {
        public SeparatorViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_separator, viewGroup, false));
        }
    }

    private static class SubheaderViewHolder
    extends ViewHolder {
        public SubheaderViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_subheader, viewGroup, false));
        }
    }

    private static abstract class ViewHolder
    extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

}
