/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.Adapter
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.ThemedSpinnerAdapter
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.AppCompatBackgroundHelper;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class AppCompatSpinner
extends Spinner
implements TintableBackgroundView {
    private static final int[] ATTRS_ANDROID_SPINNERMODE = new int[]{16843505};
    private static final int MAX_ITEMS_MEASURED = 15;
    private static final int MODE_DIALOG = 0;
    private static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final String TAG = "AppCompatSpinner";
    private final AppCompatBackgroundHelper mBackgroundTintHelper;
    private int mDropDownWidth;
    private ForwardingListener mForwardingListener;
    private DropdownPopup mPopup;
    private final Context mPopupContext;
    private final boolean mPopupSet;
    private SpinnerAdapter mTempAdapter;
    private final Rect mTempRect = new Rect();

    public AppCompatSpinner(Context context) {
        this(context, null);
    }

    public AppCompatSpinner(Context context, int n) {
        this(context, null, R.attr.spinnerStyle, n);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spinnerStyle);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, -1);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n, int n2) {
        this(context, attributeSet, n, n2, null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n, int n2, Resources.Theme object) {
        int n3;
        super(context, attributeSet, n);
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.Spinner, n, 0);
        this.mBackgroundTintHelper = new AppCompatBackgroundHelper((View)this);
        if (object != null) {
            this.mPopupContext = new ContextThemeWrapper(context, (Resources.Theme)object);
        } else {
            n3 = tintTypedArray.getResourceId(R.styleable.Spinner_popupTheme, 0);
            if (n3 != 0) {
                this.mPopupContext = new ContextThemeWrapper(context, n3);
            } else {
                object = Build.VERSION.SDK_INT < 23 ? context : null;
                this.mPopupContext = object;
            }
        }
        if (this.mPopupContext != null) {
            Object object2;
            int n4;
            block18 : {
                n4 = n2;
                if (n2 == -1) {
                    void var1_4;
                    block21 : {
                        block19 : {
                            block20 : {
                                object = context.obtainStyledAttributes(attributeSet, ATTRS_ANDROID_SPINNERMODE, n, 0);
                                n3 = n2;
                                object2 = object;
                                try {
                                    if (object.hasValue(0)) {
                                        object2 = object;
                                        n3 = object.getInt(0, 0);
                                    }
                                    n4 = n3;
                                    if (object == null) break block18;
                                    n2 = n3;
                                    break block19;
                                }
                                catch (Exception exception) {
                                    break block20;
                                }
                                catch (Throwable throwable) {
                                    object2 = null;
                                    break block21;
                                }
                                catch (Exception exception) {
                                    object = null;
                                }
                            }
                            object2 = object;
                            try {
                                void var9_15;
                                Log.i((String)TAG, (String)"Could not read android:spinnerMode", (Throwable)var9_15);
                                n4 = n2;
                                if (object == null) break block18;
                            }
                            catch (Throwable throwable) {
                                // empty catch block
                            }
                        }
                        object.recycle();
                        n4 = n2;
                        {
                            break block18;
                        }
                    }
                    if (object2 == null) throw var1_4;
                    object2.recycle();
                    throw var1_4;
                }
            }
            if (n4 == 1) {
                object = new DropdownPopup(this.mPopupContext, attributeSet, n);
                object2 = TintTypedArray.obtainStyledAttributes(this.mPopupContext, attributeSet, R.styleable.Spinner, n, 0);
                this.mDropDownWidth = object2.getLayoutDimension(R.styleable.Spinner_android_dropDownWidth, -2);
                object.setBackgroundDrawable(object2.getDrawable(R.styleable.Spinner_android_popupBackground));
                object.setPromptText(tintTypedArray.getString(R.styleable.Spinner_android_prompt));
                object2.recycle();
                this.mPopup = object;
                this.mForwardingListener = new ForwardingListener((View)this, (DropdownPopup)object){
                    final /* synthetic */ DropdownPopup val$popup;
                    {
                        this.val$popup = dropdownPopup;
                        super(view);
                    }

                    @Override
                    public ShowableListMenu getPopup() {
                        return this.val$popup;
                    }

                    @Override
                    public boolean onForwardingStarted() {
                        if (!AppCompatSpinner.this.mPopup.isShowing()) {
                            AppCompatSpinner.this.mPopup.show();
                        }
                        return true;
                    }
                };
            }
        }
        if ((object = tintTypedArray.getTextArray(R.styleable.Spinner_android_entries)) != null) {
            context = new ArrayAdapter(context, 17367048, (Object[])object);
            context.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.setAdapter((SpinnerAdapter)context);
        }
        tintTypedArray.recycle();
        this.mPopupSet = true;
        if (this.mTempAdapter != null) {
            this.setAdapter(this.mTempAdapter);
            this.mTempAdapter = null;
        }
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, n);
    }

    int compatMeasureContentWidth(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        int n = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int n2 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0);
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0);
        int n4 = Math.max(0, this.getSelectedItemPosition());
        int n5 = Math.min(spinnerAdapter.getCount(), n4 + 15);
        int n6 = Math.max(0, n4 - (15 - (n5 - n4)));
        n4 = 0;
        View view = null;
        while (n6 < n5) {
            int n7 = spinnerAdapter.getItemViewType(n6);
            int n8 = n;
            if (n7 != n) {
                view = null;
                n8 = n7;
            }
            if ((view = spinnerAdapter.getView(n6, view, (ViewGroup)this)).getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(n2, n3);
            n4 = Math.max(n4, view.getMeasuredWidth());
            ++n6;
            n = n8;
        }
        n6 = n4;
        if (drawable != null) {
            drawable.getPadding(this.mTempRect);
            n6 = n4 + (this.mTempRect.left + this.mTempRect.right);
        }
        return n6;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
    }

    public int getDropDownHorizontalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getHorizontalOffset();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownHorizontalOffset();
        }
        return 0;
    }

    public int getDropDownVerticalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getVerticalOffset();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownVerticalOffset();
        }
        return 0;
    }

    public int getDropDownWidth() {
        if (this.mPopup != null) {
            return this.mDropDownWidth;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownWidth();
        }
        return 0;
    }

    public Drawable getPopupBackground() {
        if (this.mPopup != null) {
            return this.mPopup.getBackground();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getPopupBackground();
        }
        return null;
    }

    public Context getPopupContext() {
        if (this.mPopup != null) {
            return this.mPopupContext;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return super.getPopupContext();
        }
        return null;
    }

    public CharSequence getPrompt() {
        if (this.mPopup != null) {
            return this.mPopup.getHintText();
        }
        return super.getPrompt();
    }

    @Nullable
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public ColorStateList getSupportBackgroundTintList() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    @Nullable
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPopup != null && this.mPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (this.mPopup != null && View.MeasureSpec.getMode((int)n) == Integer.MIN_VALUE) {
            this.setMeasuredDimension(Math.min(Math.max(this.getMeasuredWidth(), this.compatMeasureContentWidth(this.getAdapter(), this.getBackground())), View.MeasureSpec.getSize((int)n)), this.getMeasuredHeight());
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mForwardingListener != null && this.mForwardingListener.onTouch((View)this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean performClick() {
        if (this.mPopup != null) {
            if (!this.mPopup.isShowing()) {
                this.mPopup.show();
            }
            return true;
        }
        return super.performClick();
    }

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.mPopupSet) {
            this.mTempAdapter = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        if (this.mPopup != null) {
            Context context = this.mPopupContext == null ? this.getContext() : this.mPopupContext;
            this.mPopup.setAdapter(new DropDownAdapter(spinnerAdapter, context.getTheme()));
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(drawable);
        }
    }

    public void setBackgroundResource(@DrawableRes int n) {
        super.setBackgroundResource(n);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(n);
        }
    }

    public void setDropDownHorizontalOffset(int n) {
        if (this.mPopup != null) {
            this.mPopup.setHorizontalOffset(n);
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownHorizontalOffset(n);
        }
    }

    public void setDropDownVerticalOffset(int n) {
        if (this.mPopup != null) {
            this.mPopup.setVerticalOffset(n);
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownVerticalOffset(n);
        }
    }

    public void setDropDownWidth(int n) {
        if (this.mPopup != null) {
            this.mDropDownWidth = n;
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownWidth(n);
        }
    }

    public void setPopupBackgroundDrawable(Drawable drawable) {
        if (this.mPopup != null) {
            this.mPopup.setBackgroundDrawable(drawable);
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            super.setPopupBackgroundDrawable(drawable);
        }
    }

    public void setPopupBackgroundResource(@DrawableRes int n) {
        this.setPopupBackgroundDrawable(AppCompatResources.getDrawable(this.getPopupContext(), n));
    }

    public void setPrompt(CharSequence charSequence) {
        if (this.mPopup != null) {
            this.mPopup.setPromptText(charSequence);
            return;
        }
        super.setPrompt(charSequence);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(colorStateList);
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(mode);
        }
    }

    private static class DropDownAdapter
    implements ListAdapter,
    SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        public DropDownAdapter(@Nullable SpinnerAdapter spinnerAdapter, @Nullable Resources.Theme theme) {
            this.mAdapter = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter)spinnerAdapter;
            }
            if (theme != null) {
                if (Build.VERSION.SDK_INT >= 23 && spinnerAdapter instanceof android.widget.ThemedSpinnerAdapter) {
                    if ((spinnerAdapter = (android.widget.ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() != theme) {
                        spinnerAdapter.setDropDownViewTheme(theme);
                        return;
                    }
                } else if (spinnerAdapter instanceof ThemedSpinnerAdapter && (spinnerAdapter = (ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() == null) {
                    spinnerAdapter.setDropDownViewTheme(theme);
                }
            }
        }

        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        public int getCount() {
            if (this.mAdapter == null) {
                return 0;
            }
            return this.mAdapter.getCount();
        }

        public View getDropDownView(int n, View view, ViewGroup viewGroup) {
            if (this.mAdapter == null) {
                return null;
            }
            return this.mAdapter.getDropDownView(n, view, viewGroup);
        }

        public Object getItem(int n) {
            if (this.mAdapter == null) {
                return null;
            }
            return this.mAdapter.getItem(n);
        }

        public long getItemId(int n) {
            if (this.mAdapter == null) {
                return -1L;
            }
            return this.mAdapter.getItemId(n);
        }

        public int getItemViewType(int n) {
            return 0;
        }

        public View getView(int n, View view, ViewGroup viewGroup) {
            return this.getDropDownView(n, view, viewGroup);
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean hasStableIds() {
            if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
                return true;
            }
            return false;
        }

        public boolean isEmpty() {
            if (this.getCount() == 0) {
                return true;
            }
            return false;
        }

        public boolean isEnabled(int n) {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.isEnabled(n);
            }
            return true;
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    private class DropdownPopup
    extends ListPopupWindow {
        ListAdapter mAdapter;
        private CharSequence mHintText;
        private final Rect mVisibleRect;

        public DropdownPopup(Context context, AttributeSet attributeSet, int n) {
            super(context, attributeSet, n);
            this.mVisibleRect = new Rect();
            this.setAnchorView((View)AppCompatSpinner.this);
            this.setModal(true);
            this.setPromptPosition(0);
            this.setOnItemClickListener(new AdapterView.OnItemClickListener(AppCompatSpinner.this){
                final /* synthetic */ AppCompatSpinner val$this$0;
                {
                    this.val$this$0 = appCompatSpinner;
                }

                public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                    AppCompatSpinner.this.setSelection(n);
                    if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                        AppCompatSpinner.this.performItemClick(view, n, DropdownPopup.this.mAdapter.getItemId(n));
                    }
                    DropdownPopup.this.dismiss();
                }
            });
        }

        void computeContentWidth() {
            Drawable drawable = this.getBackground();
            int n = 0;
            if (drawable != null) {
                drawable.getPadding(AppCompatSpinner.this.mTempRect);
                n = ViewUtils.isLayoutRtl((View)AppCompatSpinner.this) ? AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).right : - AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).left;
            } else {
                drawable = AppCompatSpinner.this.mTempRect;
                AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).right = 0;
                drawable.left = 0;
            }
            int n2 = AppCompatSpinner.this.getPaddingLeft();
            int n3 = AppCompatSpinner.this.getPaddingRight();
            int n4 = AppCompatSpinner.this.getWidth();
            if (AppCompatSpinner.this.mDropDownWidth == -2) {
                int n5 = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter)this.mAdapter, this.getBackground());
                int n6 = AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).left - AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).right;
                int n7 = n5;
                if (n5 > n6) {
                    n7 = n6;
                }
                this.setContentWidth(Math.max(n7, n4 - n2 - n3));
            } else if (AppCompatSpinner.this.mDropDownWidth == -1) {
                this.setContentWidth(n4 - n2 - n3);
            } else {
                this.setContentWidth(AppCompatSpinner.this.mDropDownWidth);
            }
            n = ViewUtils.isLayoutRtl((View)AppCompatSpinner.this) ? (n += n4 - n3 - this.getWidth()) : (n += n2);
            this.setHorizontalOffset(n);
        }

        public CharSequence getHintText() {
            return this.mHintText;
        }

        boolean isVisibleToUser(View view) {
            if (ViewCompat.isAttachedToWindow(view) && view.getGlobalVisibleRect(this.mVisibleRect)) {
                return true;
            }
            return false;
        }

        @Override
        public void setAdapter(ListAdapter listAdapter) {
            super.setAdapter(listAdapter);
            this.mAdapter = listAdapter;
        }

        public void setPromptText(CharSequence charSequence) {
            this.mHintText = charSequence;
        }

        @Override
        public void show() {
            boolean bl = this.isShowing();
            this.computeContentWidth();
            this.setInputMethodMode(2);
            super.show();
            this.getListView().setChoiceMode(1);
            this.setSelection(AppCompatSpinner.this.getSelectedItemPosition());
            if (bl) {
                return;
            }
            ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
            if (viewTreeObserver != null) {
                final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

                    public void onGlobalLayout() {
                        if (!DropdownPopup.this.isVisibleToUser((View)AppCompatSpinner.this)) {
                            DropdownPopup.this.dismiss();
                            return;
                        }
                        DropdownPopup.this.computeContentWidth();
                        DropdownPopup.super.show();
                    }
                };
                viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
                this.setOnDismissListener(new PopupWindow.OnDismissListener(){

                    public void onDismiss() {
                        ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
                        if (viewTreeObserver != null) {
                            viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener);
                        }
                    }
                });
            }
        }

    }

}
