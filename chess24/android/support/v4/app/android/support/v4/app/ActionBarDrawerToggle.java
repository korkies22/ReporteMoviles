/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Configuration
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.InsetDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.Window
 *  android.widget.ImageView
 */
package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ImageView;
import java.lang.reflect.Method;

@Deprecated
public class ActionBarDrawerToggle
implements DrawerLayout.DrawerListener {
    private static final int ID_HOME = 16908332;
    private static final String TAG = "ActionBarDrawerToggle";
    private static final int[] THEME_ATTRS = new int[]{16843531};
    private static final float TOGGLE_DRAWABLE_OFFSET = 0.33333334f;
    final Activity mActivity;
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    private Drawable mDrawerImage;
    private final int mDrawerImageResource;
    private boolean mDrawerIndicatorEnabled = true;
    private final DrawerLayout mDrawerLayout;
    private boolean mHasCustomUpIndicator;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private SetIndicatorInfo mSetIndicatorInfo;
    private SlideDrawable mSlider;

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, @DrawableRes int n, @StringRes int n2, @StringRes int n3) {
        this(activity, drawerLayout, ActionBarDrawerToggle.assumeMaterial((Context)activity) ^ true, n, n2, n3);
    }

    public ActionBarDrawerToggle(Activity object, DrawerLayout drawerLayout, boolean bl, @DrawableRes int n, @StringRes int n2, @StringRes int n3) {
        this.mActivity = object;
        this.mActivityImpl = object instanceof DelegateProvider ? ((DelegateProvider)object).getDrawerToggleDelegate() : null;
        this.mDrawerLayout = drawerLayout;
        this.mDrawerImageResource = n;
        this.mOpenDrawerContentDescRes = n2;
        this.mCloseDrawerContentDescRes = n3;
        this.mHomeAsUpIndicator = this.getThemeUpIndicator();
        this.mDrawerImage = ContextCompat.getDrawable((Context)object, n);
        this.mSlider = new SlideDrawable(this.mDrawerImage);
        object = this.mSlider;
        float f = bl ? 0.33333334f : 0.0f;
        object.setOffset(f);
    }

    private static boolean assumeMaterial(Context context) {
        if (context.getApplicationInfo().targetSdkVersion >= 21 && Build.VERSION.SDK_INT >= 21) {
            return true;
        }
        return false;
    }

    private Drawable getThemeUpIndicator() {
        if (this.mActivityImpl != null) {
            return this.mActivityImpl.getThemeUpIndicator();
        }
        if (Build.VERSION.SDK_INT >= 18) {
            Object object = this.mActivity.getActionBar();
            object = object != null ? object.getThemedContext() : this.mActivity;
            object = object.obtainStyledAttributes(null, THEME_ATTRS, 16843470, 0);
            Drawable drawable = object.getDrawable(0);
            object.recycle();
            return drawable;
        }
        TypedArray typedArray = this.mActivity.obtainStyledAttributes(THEME_ATTRS);
        Drawable drawable = typedArray.getDrawable(0);
        typedArray.recycle();
        return drawable;
    }

    private void setActionBarDescription(int n) {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarDescription(n);
            return;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(n);
                return;
            }
        } else {
            if (this.mSetIndicatorInfo == null) {
                this.mSetIndicatorInfo = new SetIndicatorInfo(this.mActivity);
            }
            if (this.mSetIndicatorInfo.mSetHomeAsUpIndicator != null) {
                try {
                    ActionBar actionBar = this.mActivity.getActionBar();
                    this.mSetIndicatorInfo.mSetHomeActionContentDescription.invoke((Object)actionBar, n);
                    actionBar.setSubtitle(actionBar.getSubtitle());
                    return;
                }
                catch (Exception exception) {
                    Log.w((String)TAG, (String)"Couldn't set content description via JB-MR2 API", (Throwable)exception);
                }
            }
        }
    }

    private void setActionBarUpIndicator(Drawable drawable, int n) {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarUpIndicator(drawable, n);
            return;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(drawable);
                actionBar.setHomeActionContentDescription(n);
                return;
            }
        } else {
            if (this.mSetIndicatorInfo == null) {
                this.mSetIndicatorInfo = new SetIndicatorInfo(this.mActivity);
            }
            if (this.mSetIndicatorInfo.mSetHomeAsUpIndicator != null) {
                try {
                    ActionBar actionBar = this.mActivity.getActionBar();
                    this.mSetIndicatorInfo.mSetHomeAsUpIndicator.invoke((Object)actionBar, new Object[]{drawable});
                    this.mSetIndicatorInfo.mSetHomeActionContentDescription.invoke((Object)actionBar, n);
                    return;
                }
                catch (Exception exception) {
                    Log.w((String)TAG, (String)"Couldn't set home-as-up indicator via JB-MR2 API", (Throwable)exception);
                    return;
                }
            }
            if (this.mSetIndicatorInfo.mUpIndicatorView != null) {
                this.mSetIndicatorInfo.mUpIndicatorView.setImageDrawable(drawable);
                return;
            }
            Log.w((String)TAG, (String)"Couldn't set home-as-up indicator");
        }
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = this.getThemeUpIndicator();
        }
        this.mDrawerImage = ContextCompat.getDrawable((Context)this.mActivity, this.mDrawerImageResource);
        this.syncState();
    }

    @Override
    public void onDrawerClosed(View view) {
        this.mSlider.setPosition(0.0f);
        if (this.mDrawerIndicatorEnabled) {
            this.setActionBarDescription(this.mOpenDrawerContentDescRes);
        }
    }

    @Override
    public void onDrawerOpened(View view) {
        this.mSlider.setPosition(1.0f);
        if (this.mDrawerIndicatorEnabled) {
            this.setActionBarDescription(this.mCloseDrawerContentDescRes);
        }
    }

    @Override
    public void onDrawerSlide(View view, float f) {
        float f2 = this.mSlider.getPosition();
        f = f > 0.5f ? Math.max(f2, Math.max(0.0f, f - 0.5f) * 2.0f) : Math.min(f2, f * 2.0f);
        this.mSlider.setPosition(f);
    }

    @Override
    public void onDrawerStateChanged(int n) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != null && menuItem.getItemId() == 16908332 && this.mDrawerIndicatorEnabled) {
            if (this.mDrawerLayout.isDrawerVisible(8388611)) {
                this.mDrawerLayout.closeDrawer(8388611);
            } else {
                this.mDrawerLayout.openDrawer(8388611);
            }
            return true;
        }
        return false;
    }

    public void setDrawerIndicatorEnabled(boolean bl) {
        if (bl != this.mDrawerIndicatorEnabled) {
            if (bl) {
                SlideDrawable slideDrawable = this.mSlider;
                int n = this.mDrawerLayout.isDrawerOpen(8388611) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes;
                this.setActionBarUpIndicator((Drawable)slideDrawable, n);
            } else {
                this.setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
            }
            this.mDrawerIndicatorEnabled = bl;
        }
    }

    public void setHomeAsUpIndicator(int n) {
        Drawable drawable = n != 0 ? ContextCompat.getDrawable((Context)this.mActivity, n) : null;
        this.setHomeAsUpIndicator(drawable);
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        if (drawable == null) {
            this.mHomeAsUpIndicator = this.getThemeUpIndicator();
            this.mHasCustomUpIndicator = false;
        } else {
            this.mHomeAsUpIndicator = drawable;
            this.mHasCustomUpIndicator = true;
        }
        if (!this.mDrawerIndicatorEnabled) {
            this.setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
        }
    }

    public void syncState() {
        if (this.mDrawerLayout.isDrawerOpen(8388611)) {
            this.mSlider.setPosition(1.0f);
        } else {
            this.mSlider.setPosition(0.0f);
        }
        if (this.mDrawerIndicatorEnabled) {
            SlideDrawable slideDrawable = this.mSlider;
            int n = this.mDrawerLayout.isDrawerOpen(8388611) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes;
            this.setActionBarUpIndicator((Drawable)slideDrawable, n);
        }
    }

    @Deprecated
    public static interface Delegate {
        @Nullable
        public Drawable getThemeUpIndicator();

        public void setActionBarDescription(@StringRes int var1);

        public void setActionBarUpIndicator(Drawable var1, @StringRes int var2);
    }

    @Deprecated
    public static interface DelegateProvider {
        @Nullable
        public Delegate getDrawerToggleDelegate();
    }

    private static class SetIndicatorInfo {
        Method mSetHomeActionContentDescription;
        Method mSetHomeAsUpIndicator;
        ImageView mUpIndicatorView;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        SetIndicatorInfo(Activity activity) {
            try {
                this.mSetHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
                this.mSetHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            activity = activity.findViewById(16908332);
            if (activity == null) {
                return;
            }
            ViewGroup viewGroup = (ViewGroup)activity.getParent();
            if (viewGroup.getChildCount() != 2) {
                return;
            }
            activity = viewGroup.getChildAt(0);
            viewGroup = viewGroup.getChildAt(1);
            if (activity.getId() == 16908332) {
                activity = viewGroup;
            }
            if (activity instanceof ImageView) {
                this.mUpIndicatorView = (ImageView)activity;
            }
        }
    }

    private class SlideDrawable
    extends InsetDrawable
    implements Drawable.Callback {
        private final boolean mHasMirroring;
        private float mOffset;
        private float mPosition;
        private final Rect mTmpRect;

        SlideDrawable(Drawable drawable) {
            boolean bl = false;
            super(drawable, 0);
            if (Build.VERSION.SDK_INT > 18) {
                bl = true;
            }
            this.mHasMirroring = bl;
            this.mTmpRect = new Rect();
        }

        public void draw(@NonNull Canvas canvas) {
            this.copyBounds(this.mTmpRect);
            canvas.save();
            int n = ViewCompat.getLayoutDirection(ActionBarDrawerToggle.this.mActivity.getWindow().getDecorView());
            int n2 = 1;
            n = n == 1 ? 1 : 0;
            if (n != 0) {
                n2 = -1;
            }
            int n3 = this.mTmpRect.width();
            float f = - this.mOffset;
            float f2 = n3;
            canvas.translate(f * f2 * this.mPosition * (float)n2, 0.0f);
            if (n != 0 && !this.mHasMirroring) {
                canvas.translate(f2, 0.0f);
                canvas.scale(-1.0f, 1.0f);
            }
            super.draw(canvas);
            canvas.restore();
        }

        public float getPosition() {
            return this.mPosition;
        }

        public void setOffset(float f) {
            this.mOffset = f;
            this.invalidateSelf();
        }

        public void setPosition(float f) {
            this.mPosition = f;
            this.invalidateSelf();
        }
    }

}
