/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Adapter
 *  android.widget.BaseAdapter
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package uk.co.jasonfry.android.tools.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import uk.co.jasonfry.android.tools.ui.SwipeView;
import uk.co.jasonfry.android.tools.widget.BounceSwipeView;

public class PageView
extends BounceSwipeView {
    private Adapter mAdapter;
    private boolean mCarouselMode = false;
    private int mCurrentPage;
    private int mOffset;
    private SwipeView.OnPageChangedListener mOnPageChangedListener;

    public PageView(Context context) {
        super(context);
        this.initView();
    }

    public PageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initView();
    }

    public PageView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initView();
    }

    private void backwardsMove() {
        this.mCurrentPage = this.mCurrentPage > 0 ? --this.mCurrentPage : this.getAdapterPageCount() - 1;
        if (this.mCurrentPage > 0) {
            this.backwardsRearrange(this.mCurrentPage - 1);
            return;
        }
        this.backwardsRearrange(this.getAdapterPageCount() - 1);
    }

    private void backwardsRearrange(int n) {
        View view = this.getChildContainer().getChildAt(2);
        this.getChildContainer().removeViewAt(2);
        this.loadPage(n, 0, view);
        this.resetMargins();
    }

    private void emptyCarousel() {
        this.getChildContainer().removeAllViews();
    }

    private void fillCarousel(int n) {
        this.emptyCarousel();
        if (this.mAdapter.getCount() == 1) {
            this.loadPage(0, 0, null);
            return;
        }
        if (this.mAdapter.getCount() == 2) {
            if (!this.mCarouselMode) {
                this.loadPage(0, 0, null);
                this.loadPage(1, 1, null);
                return;
            }
            if (n == 0) {
                this.loadPage(1, 0, null);
                this.loadPage(0, 1, null);
                this.loadPage(1, 2, null);
                return;
            }
            this.loadPage(0, 0, null);
            this.loadPage(1, 1, null);
            this.loadPage(0, 2, null);
            return;
        }
        if (this.mAdapter.getCount() > 2) {
            if (n == 0 && this.mCarouselMode) {
                this.loadPage(this.mAdapter.getCount() - 1, 0, null);
                this.loadPage(0, 1, null);
                this.loadPage(1, 2, null);
            } else if (n == 0 && !this.mCarouselMode) {
                this.loadPage(0, 0, null);
                this.loadPage(1, 1, null);
                this.loadPage(2, 2, null);
            } else if (n == this.mAdapter.getCount() - 1 && this.mCarouselMode) {
                this.loadPage(n - 1, 0, null);
                this.loadPage(this.mAdapter.getCount() - 1, 1, null);
                this.loadPage(0, 2, null);
            } else if (n == this.mAdapter.getCount() - 1 && !this.mCarouselMode) {
                this.loadPage(this.mAdapter.getCount() - 3, 0, null);
                this.loadPage(this.mAdapter.getCount() - 2, 1, null);
                this.loadPage(this.mAdapter.getCount() - 1, 2, null);
            } else {
                this.loadPage(n - 1, 0, null);
                this.loadPage(n, 1, null);
                this.loadPage(n + 1, 2, null);
            }
            this.resetMargins();
        }
    }

    private void forwardsMove() {
        this.mCurrentPage = this.mCurrentPage < this.getAdapterPageCount() - 1 ? ++this.mCurrentPage : 0;
        if (this.mCurrentPage < this.getAdapterPageCount() - 1) {
            this.forwardsRearrange(this.mCurrentPage + 1);
            return;
        }
        this.forwardsRearrange(0);
    }

    private void forwardsRearrange(int n) {
        View view = this.getChildContainer().getChildAt(0);
        this.getChildContainer().removeViewAt(0);
        this.loadPage(n, 2, view);
        this.resetMargins();
    }

    private int getAdapterPageCount() {
        if (this.mAdapter != null) {
            if (this.mAdapter.getCount() == 2 && this.mCarouselMode) {
                return 4;
            }
            return this.mAdapter.getCount();
        }
        return -1;
    }

    private void initView() {
        this.setBounceEnabled(false);
    }

    private void loadPage(int n, int n2, View view) {
        int n3 = n;
        if (this.mAdapter.getCount() == 2) {
            n3 = n;
            if (n > 1) {
                n3 = n - 2;
            }
        }
        super.addView(this.mAdapter.getView(n3, view, (ViewGroup)this.getChildContainer()), n2);
    }

    private void notifiyAssignedOnPageChangedListener(int n) {
        if (this.mOnPageChangedListener != null) {
            if (this.mCarouselMode && this.mCurrentPage == 0 && n == 2) {
                this.mOnPageChangedListener.onPageChanged(this.mAdapter.getCount() - 1, this.mCurrentPage);
                return;
            }
            if (this.mCarouselMode && this.mCurrentPage == this.mAdapter.getCount() - 1 && n == 0) {
                this.mOnPageChangedListener.onPageChanged(0, this.mCurrentPage);
                return;
            }
            if (!this.mCarouselMode && this.mCurrentPage == 1 && n == 1) {
                this.mOnPageChangedListener.onPageChanged(0, 1);
                return;
            }
            if (!this.mCarouselMode && this.mCurrentPage == this.mAdapter.getCount() - 1 && n == this.mAdapter.getCount() - 1) {
                this.mOnPageChangedListener.onPageChanged(this.mCurrentPage, this.mAdapter.getCount() - 2);
                return;
            }
            if (n == 2) {
                this.mOnPageChangedListener.onPageChanged(this.mCurrentPage - 1, this.mCurrentPage);
                return;
            }
            this.mOnPageChangedListener.onPageChanged(this.mCurrentPage + 1, this.mCurrentPage);
        }
    }

    private void rearrangePages(int n, int n2, final boolean bl) {
        final int n3 = this.getAdapterPageCount();
        int n4 = 1;
        if (n3 > 1) {
            if (n2 >= n + 1) {
                if (!(this.mCarouselMode || this.mCurrentPage < this.getAdapterPageCount() - 2 && this.mCurrentPage > 0)) {
                    if (this.mCurrentPage <= 0) {
                        this.mCurrentPage = 1;
                        n3 = n4;
                    } else {
                        this.mCurrentPage = this.getAdapterPageCount() - 1;
                        n3 = 2;
                    }
                } else {
                    this.mCallScrollToPageInOnLayout = false;
                    this.scrollTo(this.getScrollX() - this.getPageWidth(), 0);
                    this.forwardsMove();
                    n3 = n4;
                }
            } else {
                n3 = n4;
                if (n2 <= n - 1) {
                    if (!(this.mCarouselMode || this.mCurrentPage > 1 && this.mCurrentPage < this.getAdapterPageCount() - 1)) {
                        if (this.mCurrentPage >= this.getAdapterPageCount() - 1) {
                            this.mCurrentPage = this.getAdapterPageCount() - 2;
                            n3 = n4;
                        } else {
                            this.mCurrentPage = 0;
                            n3 = 0;
                        }
                    } else {
                        this.mCallScrollToPageInOnLayout = false;
                        this.scrollTo(this.getScrollX() + this.getPageWidth(), 0);
                        this.backwardsMove();
                        n3 = n4;
                    }
                }
            }
            this.post(new Runnable(){

                @Override
                public void run() {
                    if (bl) {
                        PageView.super.smoothScrollToPage(n3);
                        return;
                    }
                    PageView.super.scrollToPage(n3);
                }
            });
        }
    }

    private void refill(int n) {
        if (this.mCurrentPage == 0) {
            if (n == this.getAdapterPageCount() - 1 || n <= this.mCurrentPage + 1) {
                this.fillCarousel(this.mCurrentPage);
                return;
            }
        } else if (this.mCurrentPage == this.getAdapterPageCount() - 1) {
            if (n >= this.mCurrentPage || n == 0) {
                this.fillCarousel(this.mCurrentPage);
                return;
            }
        } else if (n >= this.mCurrentPage - 1 && n <= this.mCurrentPage + 1) {
            this.fillCarousel(this.mCurrentPage);
        }
    }

    private void resetMargins() {
        if (this.mOffset > 0) {
            ((LinearLayout.LayoutParams)this.getChildContainer().getChildAt((int)0).getLayoutParams()).leftMargin = this.mOffset;
            ((LinearLayout.LayoutParams)this.getChildContainer().getChildAt((int)0).getLayoutParams()).rightMargin = 0;
            ((LinearLayout.LayoutParams)this.getChildContainer().getChildAt((int)1).getLayoutParams()).leftMargin = 0;
            ((LinearLayout.LayoutParams)this.getChildContainer().getChildAt((int)1).getLayoutParams()).rightMargin = 0;
            ((LinearLayout.LayoutParams)this.getChildContainer().getChildAt((int)2).getLayoutParams()).leftMargin = 0;
            ((LinearLayout.LayoutParams)this.getChildContainer().getChildAt((int)2).getLayoutParams()).rightMargin = this.mOffset;
        }
    }

    private void scrollToPage(int n, boolean bl) {
        if (!this.mCarouselMode && this.getCurrentPage() == this.getPageCount() - 1 && n >= this.getCurrentPage() || !this.mCarouselMode && this.getCurrentPage() == 0 && n <= 0) {
            this.doAtEdgeAnimation();
            return;
        }
        if (this.getCurrentPage() != n) {
            this.rearrangePages(this.getCurrentPage(), n, bl);
            this.notifiyAssignedOnPageChangedListener(n);
        }
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    public boolean getCarouselEnabled() {
        return this.mCarouselMode;
    }

    @Override
    public SwipeView.OnPageChangedListener getOnPageChangedListener() {
        return this.mOnPageChangedListener;
    }

    public int getRealCurrentPage() {
        return this.mCurrentPage;
    }

    public void itemAddedToAdapter(int n) {
        if (n <= this.mCurrentPage) {
            ++this.mCurrentPage;
        }
        if (this.mAdapter.getCount() > 1) {
            this.setBounceEnabled(false);
        }
        this.refill(n);
    }

    public void itemRemovedFromAdapter(int n) {
        if (n <= this.mCurrentPage && this.mCurrentPage != 0) {
            --this.mCurrentPage;
        }
        this.refill(n);
    }

    @Override
    public void scrollToPage(int n) {
        this.scrollToPage(n, false);
    }

    public void setAdapter(BaseAdapter baseAdapter) {
        this.setAdapter(baseAdapter, 0);
    }

    public void setAdapter(BaseAdapter baseAdapter, final int n) {
        this.mAdapter = baseAdapter;
        if (this.mAdapter != null) {
            this.mCurrentPage = n;
            this.fillCarousel(n);
            this.post(new Runnable(){

                @Override
                public void run() {
                    if (!PageView.this.mCarouselMode && n == 0) {
                        PageView.super.scrollToPage(0);
                        return;
                    }
                    if (!PageView.this.mCarouselMode && n == PageView.this.mAdapter.getCount() - 1) {
                        PageView.super.scrollToPage(2);
                        return;
                    }
                    PageView.super.scrollToPage(1);
                }
            });
            if (this.mAdapter.getCount() <= 1) {
                this.setBounceEnabled(true);
            }
        }
    }

    public void setCarouselEnabled(boolean bl) {
        this.mCarouselMode = bl;
        this.setBounceEnabled(bl ^ true);
    }

    @Override
    public void setOnPageChangedListener(SwipeView.OnPageChangedListener onPageChangedListener) {
        this.mOnPageChangedListener = onPageChangedListener;
    }

    @Override
    public int setPageWidth(int n) {
        this.mOffset = super.setPageWidth(n);
        return this.mOffset;
    }

    @Override
    public void smoothScrollToPage(int n) {
        this.scrollToPage(n, true);
    }

}
