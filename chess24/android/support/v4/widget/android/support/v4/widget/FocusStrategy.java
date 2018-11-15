/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 */
package android.support.v4.widget;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class FocusStrategy {
    FocusStrategy() {
    }

    private static boolean beamBeats(int n, @NonNull Rect rect, @NonNull Rect rect2, @NonNull Rect rect3) {
        boolean bl = FocusStrategy.beamsOverlap(n, rect, rect2);
        if (!FocusStrategy.beamsOverlap(n, rect, rect3)) {
            if (!bl) {
                return false;
            }
            if (!FocusStrategy.isToDirectionOf(n, rect, rect3)) {
                return true;
            }
            if (n != 17) {
                if (n == 66) {
                    return true;
                }
                if (FocusStrategy.majorAxisDistance(n, rect, rect2) < FocusStrategy.majorAxisDistanceToFarEdge(n, rect, rect3)) {
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static boolean beamsOverlap(int var0, @NonNull Rect var1_1, @NonNull Rect var2_2) {
        var5_3 = false;
        var4_4 = false;
        if (var0 != 17) {
            if (var0 != 33) {
                if (var0 != 66) {
                    if (var0 != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    } else {
                        ** GOTO lbl-1000
                    }
                }
            } else lbl-1000: // 3 sources:
            {
                var3_5 = var4_4;
                if (var2_2.right < var1_1.left) return var3_5;
                var3_5 = var4_4;
                if (var2_2.left > var1_1.right) return var3_5;
                return true;
            }
        }
        var3_6 = var5_3;
        if (var2_2.bottom < var1_1.top) return var3_6;
        var3_6 = var5_3;
        if (var2_2.top > var1_1.bottom) return var3_6;
        return true;
    }

    public static <L, T> T findNextFocusInAbsoluteDirection(@NonNull L l, @NonNull CollectionAdapter<L, T> collectionAdapter, @NonNull BoundsAdapter<T> boundsAdapter, @Nullable T t, @NonNull Rect rect, int n) {
        Rect rect2 = new Rect(rect);
        int n2 = 0;
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                    rect2.offset(0, - rect.height() + 1);
                } else {
                    rect2.offset(- rect.width() + 1, 0);
                }
            } else {
                rect2.offset(0, rect.height() + 1);
            }
        } else {
            rect2.offset(rect.width() + 1, 0);
        }
        T t2 = null;
        int n3 = collectionAdapter.size(l);
        Rect rect3 = new Rect();
        while (n2 < n3) {
            T t3 = collectionAdapter.get(l, n2);
            if (t3 != t) {
                boundsAdapter.obtainBounds(t3, rect3);
                if (FocusStrategy.isBetterCandidate(n, rect, rect3, rect2)) {
                    rect2.set(rect3);
                    t2 = t3;
                }
            }
            ++n2;
        }
        return t2;
    }

    public static <L, T> T findNextFocusInRelativeDirection(@NonNull L l, @NonNull CollectionAdapter<L, T> collectionAdapter, @NonNull BoundsAdapter<T> boundsAdapter, @Nullable T t, int n, boolean bl, boolean bl2) {
        int n2 = collectionAdapter.size(l);
        ArrayList<T> arrayList = new ArrayList<T>(n2);
        for (int i = 0; i < n2; ++i) {
            arrayList.add(collectionAdapter.get(l, i));
        }
        Collections.sort(arrayList, new SequentialComparator<T>(bl, boundsAdapter));
        switch (n) {
            default: {
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
            }
            case 2: {
                return FocusStrategy.getNextFocusable(t, arrayList, bl2);
            }
            case 1: 
        }
        return FocusStrategy.getPreviousFocusable(t, arrayList, bl2);
    }

    private static <T> T getNextFocusable(T t, ArrayList<T> arrayList, boolean bl) {
        int n = arrayList.size();
        int n2 = t == null ? -1 : arrayList.lastIndexOf(t);
        if (++n2 < n) {
            return arrayList.get(n2);
        }
        if (bl && n > 0) {
            return arrayList.get(0);
        }
        return null;
    }

    private static <T> T getPreviousFocusable(T t, ArrayList<T> arrayList, boolean bl) {
        int n = arrayList.size();
        int n2 = t == null ? n : arrayList.indexOf(t);
        if (--n2 >= 0) {
            return arrayList.get(n2);
        }
        if (bl && n > 0) {
            return arrayList.get(n - 1);
        }
        return null;
    }

    private static int getWeightedDistanceFor(int n, int n2) {
        return 13 * n * n + n2 * n2;
    }

    private static boolean isBetterCandidate(int n, @NonNull Rect rect, @NonNull Rect rect2, @NonNull Rect rect3) {
        boolean bl = FocusStrategy.isCandidate(rect, rect2, n);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (!FocusStrategy.isCandidate(rect, rect3, n)) {
            return true;
        }
        if (FocusStrategy.beamBeats(n, rect, rect2, rect3)) {
            return true;
        }
        if (FocusStrategy.beamBeats(n, rect, rect3, rect2)) {
            return false;
        }
        if (FocusStrategy.getWeightedDistanceFor(FocusStrategy.majorAxisDistance(n, rect, rect2), FocusStrategy.minorAxisDistance(n, rect, rect2)) < FocusStrategy.getWeightedDistanceFor(FocusStrategy.majorAxisDistance(n, rect, rect3), FocusStrategy.minorAxisDistance(n, rect, rect3))) {
            bl2 = true;
        }
        return bl2;
    }

    private static boolean isCandidate(@NonNull Rect rect, @NonNull Rect rect2, int n) {
        boolean bl;
        block27 : {
            boolean bl2;
            block26 : {
                block17 : {
                    boolean bl3;
                    block25 : {
                        boolean bl4;
                        block24 : {
                            block18 : {
                                boolean bl5;
                                block23 : {
                                    boolean bl6;
                                    block22 : {
                                        block19 : {
                                            boolean bl7;
                                            block21 : {
                                                boolean bl8;
                                                block20 : {
                                                    bl6 = false;
                                                    bl4 = false;
                                                    bl2 = false;
                                                    bl8 = false;
                                                    if (n == 17) break block17;
                                                    if (n == 33) break block18;
                                                    if (n == 66) break block19;
                                                    if (n != 130) {
                                                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                                                    }
                                                    if (rect.top < rect2.top) break block20;
                                                    bl7 = bl8;
                                                    if (rect.bottom > rect2.top) break block21;
                                                }
                                                bl7 = bl8;
                                                if (rect.bottom < rect2.bottom) {
                                                    bl7 = true;
                                                }
                                            }
                                            return bl7;
                                        }
                                        if (rect.left < rect2.left) break block22;
                                        bl5 = bl6;
                                        if (rect.right > rect2.left) break block23;
                                    }
                                    bl5 = bl6;
                                    if (rect.right < rect2.right) {
                                        bl5 = true;
                                    }
                                }
                                return bl5;
                            }
                            if (rect.bottom > rect2.bottom) break block24;
                            bl3 = bl4;
                            if (rect.top < rect2.bottom) break block25;
                        }
                        bl3 = bl4;
                        if (rect.top > rect2.top) {
                            bl3 = true;
                        }
                    }
                    return bl3;
                }
                if (rect.right > rect2.right) break block26;
                bl = bl2;
                if (rect.left < rect2.right) break block27;
            }
            bl = bl2;
            if (rect.left > rect2.left) {
                bl = true;
            }
        }
        return bl;
    }

    private static boolean isToDirectionOf(int n, @NonNull Rect rect, @NonNull Rect rect2) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                    if (rect.bottom <= rect2.top) {
                        bl4 = true;
                    }
                    return bl4;
                }
                bl4 = bl;
                if (rect.right <= rect2.left) {
                    bl4 = true;
                }
                return bl4;
            }
            bl4 = bl2;
            if (rect.top >= rect2.bottom) {
                bl4 = true;
            }
            return bl4;
        }
        bl4 = bl3;
        if (rect.left >= rect2.right) {
            bl4 = true;
        }
        return bl4;
    }

    private static int majorAxisDistance(int n, @NonNull Rect rect, @NonNull Rect rect2) {
        return Math.max(0, FocusStrategy.majorAxisDistanceRaw(n, rect, rect2));
    }

    private static int majorAxisDistanceRaw(int n, @NonNull Rect rect, @NonNull Rect rect2) {
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                    return rect2.top - rect.bottom;
                }
                return rect2.left - rect.right;
            }
            return rect.top - rect2.bottom;
        }
        return rect.left - rect2.right;
    }

    private static int majorAxisDistanceToFarEdge(int n, @NonNull Rect rect, @NonNull Rect rect2) {
        return Math.max(1, FocusStrategy.majorAxisDistanceToFarEdgeRaw(n, rect, rect2));
    }

    private static int majorAxisDistanceToFarEdgeRaw(int n, @NonNull Rect rect, @NonNull Rect rect2) {
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                    return rect2.bottom - rect.bottom;
                }
                return rect2.right - rect.right;
            }
            return rect.top - rect2.top;
        }
        return rect.left - rect2.left;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int minorAxisDistance(int n, @NonNull Rect rect, @NonNull Rect rect2) {
        if (n == 17) return Math.abs(rect.top + rect.height() / 2 - (rect2.top + rect2.height() / 2));
        if (n == 33) return Math.abs(rect.left + rect.width() / 2 - (rect2.left + rect2.width() / 2));
        if (n == 66) return Math.abs(rect.top + rect.height() / 2 - (rect2.top + rect2.height() / 2));
        if (n == 130) return Math.abs(rect.left + rect.width() / 2 - (rect2.left + rect2.width() / 2));
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    public static interface BoundsAdapter<T> {
        public void obtainBounds(T var1, Rect var2);
    }

    public static interface CollectionAdapter<T, V> {
        public V get(T var1, int var2);

        public int size(T var1);
    }

    private static class SequentialComparator<T>
    implements Comparator<T> {
        private final BoundsAdapter<T> mAdapter;
        private final boolean mIsLayoutRtl;
        private final Rect mTemp1 = new Rect();
        private final Rect mTemp2 = new Rect();

        SequentialComparator(boolean bl, BoundsAdapter<T> boundsAdapter) {
            this.mIsLayoutRtl = bl;
            this.mAdapter = boundsAdapter;
        }

        @Override
        public int compare(T t, T t2) {
            Rect rect = this.mTemp1;
            Rect rect2 = this.mTemp2;
            this.mAdapter.obtainBounds(t, rect);
            this.mAdapter.obtainBounds(t2, rect2);
            int n = rect.top;
            int n2 = rect2.top;
            int n3 = -1;
            if (n < n2) {
                return -1;
            }
            if (rect.top > rect2.top) {
                return 1;
            }
            if (rect.left < rect2.left) {
                if (this.mIsLayoutRtl) {
                    n3 = 1;
                }
                return n3;
            }
            if (rect.left > rect2.left) {
                if (this.mIsLayoutRtl) {
                    return -1;
                }
                return 1;
            }
            if (rect.bottom < rect2.bottom) {
                return -1;
            }
            if (rect.bottom > rect2.bottom) {
                return 1;
            }
            if (rect.right < rect2.right) {
                if (this.mIsLayoutRtl) {
                    n3 = 1;
                }
                return n3;
            }
            if (rect.right > rect2.right) {
                if (this.mIsLayoutRtl) {
                    return -1;
                }
                return 1;
            }
            return 0;
        }
    }

}
