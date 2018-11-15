/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.animation.Interpolator
 */
package android.support.v7.widget.helper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.support.v7.widget.helper.ItemTouchUIUtilImpl;
import android.view.View;
import android.view.animation.Interpolator;
import java.util.List;

public static abstract class ItemTouchHelper.Callback {
    private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
    public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
    public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
    private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000L;
    static final int RELATIVE_DIR_FLAGS = 3158064;
    private static final Interpolator sDragScrollInterpolator = new Interpolator(){

        public float getInterpolation(float f) {
            return f * f * f * f * f;
        }
    };
    private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator(){

        public float getInterpolation(float f) {
            return f * f * f * f * (f -= 1.0f) + 1.0f;
        }
    };
    private static final ItemTouchUIUtil sUICallback = Build.VERSION.SDK_INT >= 21 ? new ItemTouchUIUtilImpl.Api21Impl() : new ItemTouchUIUtilImpl.BaseImpl();
    private int mCachedMaxScrollSpeed = -1;

    public static int convertToRelativeDirection(int n, int n2) {
        int n3 = n & 789516;
        if (n3 == 0) {
            return n;
        }
        n &= ~ n3;
        if (n2 == 0) {
            return n | n3 << 2;
        }
        n2 = n3 << 1;
        return n | -789517 & n2 | (n2 & 789516) << 2;
    }

    public static ItemTouchUIUtil getDefaultUIUtil() {
        return sUICallback;
    }

    private int getMaxDragScroll(RecyclerView recyclerView) {
        if (this.mCachedMaxScrollSpeed == -1) {
            this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
        }
        return this.mCachedMaxScrollSpeed;
    }

    public static int makeFlag(int n, int n2) {
        return n2 << n * 8;
    }

    public static int makeMovementFlags(int n, int n2) {
        int n3 = ItemTouchHelper.Callback.makeFlag(0, n2 | n);
        n2 = ItemTouchHelper.Callback.makeFlag(1, n2);
        return ItemTouchHelper.Callback.makeFlag(2, n) | (n2 | n3);
    }

    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        return true;
    }

    public RecyclerView.ViewHolder chooseDropTarget(RecyclerView.ViewHolder viewHolder, List<RecyclerView.ViewHolder> list, int n, int n2) {
        int n3 = viewHolder.itemView.getWidth();
        int n4 = viewHolder.itemView.getHeight();
        int n5 = n - viewHolder.itemView.getLeft();
        int n6 = n2 - viewHolder.itemView.getTop();
        int n7 = list.size();
        RecyclerView.ViewHolder viewHolder2 = null;
        int n8 = -1;
        for (int i = 0; i < n7; ++i) {
            int n9;
            RecyclerView.ViewHolder viewHolder3 = list.get(i);
            RecyclerView.ViewHolder viewHolder4 = viewHolder2;
            int n10 = n8;
            if (n5 > 0) {
                n9 = viewHolder3.itemView.getRight() - (n + n3);
                viewHolder4 = viewHolder2;
                n10 = n8;
                if (n9 < 0) {
                    viewHolder4 = viewHolder2;
                    n10 = n8;
                    if (viewHolder3.itemView.getRight() > viewHolder.itemView.getRight()) {
                        n9 = Math.abs(n9);
                        viewHolder4 = viewHolder2;
                        n10 = n8;
                        if (n9 > n8) {
                            viewHolder4 = viewHolder3;
                            n10 = n9;
                        }
                    }
                }
            }
            viewHolder2 = viewHolder4;
            n8 = n10;
            if (n5 < 0) {
                n9 = viewHolder3.itemView.getLeft() - n;
                viewHolder2 = viewHolder4;
                n8 = n10;
                if (n9 > 0) {
                    viewHolder2 = viewHolder4;
                    n8 = n10;
                    if (viewHolder3.itemView.getLeft() < viewHolder.itemView.getLeft()) {
                        n9 = Math.abs(n9);
                        viewHolder2 = viewHolder4;
                        n8 = n10;
                        if (n9 > n10) {
                            viewHolder2 = viewHolder3;
                            n8 = n9;
                        }
                    }
                }
            }
            viewHolder4 = viewHolder2;
            n10 = n8;
            if (n6 < 0) {
                n9 = viewHolder3.itemView.getTop() - n2;
                viewHolder4 = viewHolder2;
                n10 = n8;
                if (n9 > 0) {
                    viewHolder4 = viewHolder2;
                    n10 = n8;
                    if (viewHolder3.itemView.getTop() < viewHolder.itemView.getTop()) {
                        n9 = Math.abs(n9);
                        viewHolder4 = viewHolder2;
                        n10 = n8;
                        if (n9 > n8) {
                            viewHolder4 = viewHolder3;
                            n10 = n9;
                        }
                    }
                }
            }
            viewHolder2 = viewHolder4;
            n8 = n10;
            if (n6 <= 0) continue;
            n9 = viewHolder3.itemView.getBottom() - (n2 + n4);
            viewHolder2 = viewHolder4;
            n8 = n10;
            if (n9 >= 0) continue;
            viewHolder2 = viewHolder4;
            n8 = n10;
            if (viewHolder3.itemView.getBottom() <= viewHolder.itemView.getBottom()) continue;
            n9 = Math.abs(n9);
            viewHolder2 = viewHolder4;
            n8 = n10;
            if (n9 <= n10) continue;
            n8 = n9;
            viewHolder2 = viewHolder3;
        }
        return viewHolder2;
    }

    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        sUICallback.clearView(viewHolder.itemView);
    }

    public int convertToAbsoluteDirection(int n, int n2) {
        int n3 = n & 3158064;
        if (n3 == 0) {
            return n;
        }
        n &= ~ n3;
        if (n2 == 0) {
            return n | n3 >> 2;
        }
        n2 = n3 >> 1;
        return n | -3158065 & n2 | (n2 & 3158064) >> 2;
    }

    final int getAbsoluteMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return this.convertToAbsoluteDirection(this.getMovementFlags(recyclerView, viewHolder), ViewCompat.getLayoutDirection((View)recyclerView));
    }

    public long getAnimationDuration(RecyclerView object, int n, float f, float f2) {
        if ((object = object.getItemAnimator()) == null) {
            if (n == 8) {
                return 200L;
            }
            return 250L;
        }
        if (n == 8) {
            return object.getMoveDuration();
        }
        return object.getRemoveDuration();
    }

    public int getBoundingBoxMargin() {
        return 0;
    }

    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        return 0.5f;
    }

    public abstract int getMovementFlags(RecyclerView var1, RecyclerView.ViewHolder var2);

    public float getSwipeEscapeVelocity(float f) {
        return f;
    }

    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return 0.5f;
    }

    public float getSwipeVelocityThreshold(float f) {
        return f;
    }

    boolean hasDragFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if ((this.getAbsoluteMovementFlags(recyclerView, viewHolder) & 16711680) != 0) {
            return true;
        }
        return false;
    }

    boolean hasSwipeFlag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if ((this.getAbsoluteMovementFlags(recyclerView, viewHolder) & 65280) != 0) {
            return true;
        }
        return false;
    }

    public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int n, int n2, int n3, long l) {
        n3 = this.getMaxDragScroll(recyclerView);
        int n4 = Math.abs(n2);
        int n5 = (int)Math.signum(n2);
        float f = n4;
        float f2 = 1.0f;
        f = Math.min(1.0f, f * 1.0f / (float)n);
        n = (int)((float)(n5 * n3) * sDragViewScrollCapInterpolator.getInterpolation(f));
        if (l <= 2000L) {
            f2 = (float)l / 2000.0f;
        }
        n = (int)((float)n * sDragScrollInterpolator.getInterpolation(f2));
        if (n == 0) {
            if (n2 > 0) {
                return 1;
            }
            return -1;
        }
        return n;
    }

    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    public boolean isLongPressDragEnabled() {
        return true;
    }

    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int n, boolean bl) {
        sUICallback.onDraw(canvas, recyclerView, viewHolder.itemView, f, f2, n, bl);
    }

    public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int n, boolean bl) {
        sUICallback.onDrawOver(canvas, recyclerView, viewHolder.itemView, f, f2, n, bl);
    }

    void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List<ItemTouchHelper.RecoverAnimation> list, int n, float f, float f2) {
        int n2;
        int n3 = list.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ItemTouchHelper.RecoverAnimation recoverAnimation = list.get(n2);
            recoverAnimation.update();
            int n4 = canvas.save();
            this.onChildDraw(canvas, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
            canvas.restoreToCount(n4);
        }
        if (viewHolder != null) {
            n2 = canvas.save();
            this.onChildDraw(canvas, recyclerView, viewHolder, f, f2, n, true);
            canvas.restoreToCount(n2);
        }
    }

    void onDrawOver(Canvas object, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List<ItemTouchHelper.RecoverAnimation> list, int n, float f, float f2) {
        int n2;
        int n3 = list.size();
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            ItemTouchHelper.RecoverAnimation recoverAnimation = list.get(n2);
            int n5 = object.save();
            this.onChildDrawOver((Canvas)object, recyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
            object.restoreToCount(n5);
        }
        if (viewHolder != null) {
            n2 = object.save();
            this.onChildDrawOver((Canvas)object, recyclerView, viewHolder, f, f2, n, true);
            object.restoreToCount(n2);
        }
        n2 = n4;
        for (n = n3 - 1; n >= 0; --n) {
            object = list.get(n);
            if (object.mEnded && !object.mIsPendingCleanup) {
                list.remove(n);
                continue;
            }
            if (object.mEnded) continue;
            n2 = 1;
        }
        if (n2 != 0) {
            recyclerView.invalidate();
        }
    }

    public abstract boolean onMove(RecyclerView var1, RecyclerView.ViewHolder var2, RecyclerView.ViewHolder var3);

    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int n, RecyclerView.ViewHolder viewHolder2, int n2, int n3, int n4) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof ItemTouchHelper.ViewDropHandler) {
            ((ItemTouchHelper.ViewDropHandler)((Object)layoutManager)).prepareForDrop(viewHolder.itemView, viewHolder2.itemView, n3, n4);
            return;
        }
        if (layoutManager.canScrollHorizontally()) {
            if (layoutManager.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                recyclerView.scrollToPosition(n2);
            }
            if (layoutManager.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                recyclerView.scrollToPosition(n2);
            }
        }
        if (layoutManager.canScrollVertically()) {
            if (layoutManager.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                recyclerView.scrollToPosition(n2);
            }
            if (layoutManager.getDecoratedBottom(viewHolder2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                recyclerView.scrollToPosition(n2);
            }
        }
    }

    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int n) {
        if (viewHolder != null) {
            sUICallback.onSelected(viewHolder.itemView);
        }
    }

    public abstract void onSwiped(RecyclerView.ViewHolder var1, int var2);

}
