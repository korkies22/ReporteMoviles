/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ProgressBar
 */
package android.support.v4.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ContentLoadingProgressBar
extends ProgressBar {
    private static final int MIN_DELAY = 500;
    private static final int MIN_SHOW_TIME = 500;
    private final Runnable mDelayedHide = new Runnable(){

        @Override
        public void run() {
            ContentLoadingProgressBar.this.mPostedHide = false;
            ContentLoadingProgressBar.this.mStartTime = -1L;
            ContentLoadingProgressBar.this.setVisibility(8);
        }
    };
    private final Runnable mDelayedShow = new Runnable(){

        @Override
        public void run() {
            ContentLoadingProgressBar.this.mPostedShow = false;
            if (!ContentLoadingProgressBar.this.mDismissed) {
                ContentLoadingProgressBar.this.mStartTime = System.currentTimeMillis();
                ContentLoadingProgressBar.this.setVisibility(0);
            }
        }
    };
    boolean mDismissed = false;
    boolean mPostedHide = false;
    boolean mPostedShow = false;
    long mStartTime = -1L;

    public ContentLoadingProgressBar(@NonNull Context context) {
        this(context, null);
    }

    public ContentLoadingProgressBar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    private void removeCallbacks() {
        this.removeCallbacks(this.mDelayedHide);
        this.removeCallbacks(this.mDelayedShow);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void hide() {
        synchronized (this) {
            this.mDismissed = true;
            this.removeCallbacks(this.mDelayedShow);
            this.mPostedShow = false;
            long l = System.currentTimeMillis() - this.mStartTime;
            if (l < 500L && this.mStartTime != -1L) {
                if (!this.mPostedHide) {
                    this.postDelayed(this.mDelayedHide, 500L - l);
                    this.mPostedHide = true;
                }
            } else {
                this.setVisibility(8);
            }
            return;
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.removeCallbacks();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks();
    }

    public void show() {
        synchronized (this) {
            this.mStartTime = -1L;
            this.mDismissed = false;
            this.removeCallbacks(this.mDelayedHide);
            this.mPostedHide = false;
            if (!this.mPostedShow) {
                this.postDelayed(this.mDelayedShow, 500L);
                this.mPostedShow = true;
            }
            return;
        }
    }

}
