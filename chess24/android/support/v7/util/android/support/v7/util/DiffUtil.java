/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.BatchingListUpdateCallback;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DiffUtil {
    private static final Comparator<Snake> SNAKE_COMPARATOR = new Comparator<Snake>(){

        @Override
        public int compare(Snake snake, Snake snake2) {
            int n;
            int n2 = n = snake.x - snake2.x;
            if (n == 0) {
                n2 = snake.y - snake2.y;
            }
            return n2;
        }
    };

    private DiffUtil() {
    }

    public static DiffResult calculateDiff(Callback callback) {
        return DiffUtil.calculateDiff(callback, true);
    }

    public static DiffResult calculateDiff(Callback callback, boolean bl) {
        int n = callback.getOldListSize();
        int n2 = callback.getNewListSize();
        ArrayList<Snake> arrayList = new ArrayList<Snake>();
        ArrayList<Range> arrayList2 = new ArrayList<Range>();
        arrayList2.add(new Range(0, n, 0, n2));
        n = Math.abs(n - n2) + (n + n2);
        n2 = n * 2;
        int[] arrn = new int[n2];
        int[] arrn2 = new int[n2];
        ArrayList<Range> arrayList3 = new ArrayList<Range>();
        while (!arrayList2.isEmpty()) {
            Range range = (Range)arrayList2.remove(arrayList2.size() - 1);
            Snake snake = DiffUtil.diffPartial(callback, range.oldListStart, range.oldListEnd, range.newListStart, range.newListEnd, arrn, arrn2, n);
            if (snake != null) {
                if (snake.size > 0) {
                    arrayList.add(snake);
                }
                snake.x += range.oldListStart;
                snake.y += range.newListStart;
                Range range2 = arrayList3.isEmpty() ? new Range() : (Range)arrayList3.remove(arrayList3.size() - 1);
                range2.oldListStart = range.oldListStart;
                range2.newListStart = range.newListStart;
                if (snake.reverse) {
                    range2.oldListEnd = snake.x;
                    range2.newListEnd = snake.y;
                } else if (snake.removal) {
                    range2.oldListEnd = snake.x - 1;
                    range2.newListEnd = snake.y;
                } else {
                    range2.oldListEnd = snake.x;
                    range2.newListEnd = snake.y - 1;
                }
                arrayList2.add(range2);
                if (snake.reverse) {
                    if (snake.removal) {
                        range.oldListStart = snake.x + snake.size + 1;
                        range.newListStart = snake.y + snake.size;
                    } else {
                        range.oldListStart = snake.x + snake.size;
                        range.newListStart = snake.y + snake.size + 1;
                    }
                } else {
                    range.oldListStart = snake.x + snake.size;
                    range.newListStart = snake.y + snake.size;
                }
                arrayList2.add(range);
                continue;
            }
            arrayList3.add(range);
        }
        Collections.sort(arrayList, SNAKE_COMPARATOR);
        return new DiffResult(callback, arrayList, arrn, arrn2, bl);
    }

    private static Snake diffPartial(Callback object, int n, int n2, int n3, int n4, int[] arrn, int[] arrn2, int n5) {
        if ((n2 -= n) >= 1 && (n4 -= n3) >= 1) {
            int n6 = n2 - n4;
            int n7 = (n2 + n4 + 1) / 2;
            int n8 = n5 - n7 - 1;
            int n9 = n5 + n7 + 1;
            Arrays.fill(arrn, n8, n9, 0);
            Arrays.fill(arrn2, n8 + n6, n9 + n6, n2);
            n9 = n6 % 2 != 0 ? 1 : 0;
            for (int i = 0; i <= n7; ++i) {
                int n10;
                int n11;
                boolean bl;
                int n12;
                for (n12 = n11 = - i; n12 <= i; n12 += 2) {
                    if (n12 != n11 && (n12 == i || arrn[(n8 = n5 + n12) - 1] >= arrn[n8 + 1])) {
                        n8 = arrn[n5 + n12 - 1] + 1;
                        bl = true;
                    } else {
                        n8 = arrn[n5 + n12 + 1];
                        bl = false;
                    }
                    for (n10 = n8 - n12; n8 < n2 && n10 < n4 && object.areItemsTheSame(n + n8, n3 + n10); ++n8, ++n10) {
                    }
                    n10 = n5 + n12;
                    arrn[n10] = n8;
                    if (n9 == 0 || n12 < n6 - i + 1 || n12 > n6 + i - 1 || arrn[n10] < arrn2[n10]) continue;
                    object = new Snake();
                    object.x = arrn2[n10];
                    object.y = object.x - n12;
                    object.size = arrn[n10] - arrn2[n10];
                    object.removal = bl;
                    object.reverse = false;
                    return object;
                }
                bl = false;
                for (n12 = n11; n12 <= i; n12 += 2) {
                    int n13 = n12 + n6;
                    if (n13 != i + n6 && (n13 == n11 + n6 || arrn2[(n8 = n5 + n13) - 1] >= arrn2[n8 + 1])) {
                        n8 = arrn2[n5 + n13 + 1] - 1;
                        bl = true;
                    } else {
                        n8 = arrn2[n5 + n13 - 1];
                    }
                    for (n10 = n8 - n13; n8 > 0 && n10 > 0 && object.areItemsTheSame(n + n8 - 1, n3 + n10 - 1); --n8, --n10) {
                    }
                    n10 = n5 + n13;
                    arrn2[n10] = n8;
                    if (n9 == 0 && n13 >= n11 && n13 <= i && arrn[n10] >= arrn2[n10]) {
                        object = new Snake();
                        object.x = arrn2[n10];
                        object.y = object.x - n13;
                        object.size = arrn[n10] - arrn2[n10];
                        object.removal = bl;
                        object.reverse = true;
                        return object;
                    }
                    bl = false;
                }
            }
            throw new IllegalStateException("DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation.");
        }
        return null;
    }

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int var1, int var2);

        public abstract boolean areItemsTheSame(int var1, int var2);

        @Nullable
        public Object getChangePayload(int n, int n2) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    public static class DiffResult {
        private static final int FLAG_CHANGED = 2;
        private static final int FLAG_IGNORE = 16;
        private static final int FLAG_MASK = 31;
        private static final int FLAG_MOVED_CHANGED = 4;
        private static final int FLAG_MOVED_NOT_CHANGED = 8;
        private static final int FLAG_NOT_CHANGED = 1;
        private static final int FLAG_OFFSET = 5;
        private final Callback mCallback;
        private final boolean mDetectMoves;
        private final int[] mNewItemStatuses;
        private final int mNewListSize;
        private final int[] mOldItemStatuses;
        private final int mOldListSize;
        private final List<Snake> mSnakes;

        DiffResult(Callback callback, List<Snake> list, int[] arrn, int[] arrn2, boolean bl) {
            this.mSnakes = list;
            this.mOldItemStatuses = arrn;
            this.mNewItemStatuses = arrn2;
            Arrays.fill(this.mOldItemStatuses, 0);
            Arrays.fill(this.mNewItemStatuses, 0);
            this.mCallback = callback;
            this.mOldListSize = callback.getOldListSize();
            this.mNewListSize = callback.getNewListSize();
            this.mDetectMoves = bl;
            this.addRootSnake();
            this.findMatchingItems();
        }

        private void addRootSnake() {
            Snake snake = this.mSnakes.isEmpty() ? null : this.mSnakes.get(0);
            if (snake == null || snake.x != 0 || snake.y != 0) {
                snake = new Snake();
                snake.x = 0;
                snake.y = 0;
                snake.removal = false;
                snake.size = 0;
                snake.reverse = false;
                this.mSnakes.add(0, snake);
            }
        }

        private void dispatchAdditions(List<PostponedUpdate> object, ListUpdateCallback listUpdateCallback, int n, int n2, int n3) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onInserted(n, n2);
                return;
            }
            --n2;
            while (n2 >= 0) {
                block6 : {
                    Object object2;
                    block4 : {
                        int n4;
                        int n5;
                        block5 : {
                            object2 = this.mNewItemStatuses;
                            n5 = n3 + n2;
                            n4 = object2[n5] & 31;
                            if (n4 == 0) break block4;
                            if (n4 == 4 || n4 == 8) break block5;
                            if (n4 != 16) {
                                object = new StringBuilder();
                                object.append("unknown flag for pos ");
                                object.append(n5);
                                object.append(" ");
                                object.append(Long.toBinaryString(n4));
                                throw new IllegalStateException(object.toString());
                            }
                            object.add((PostponedUpdate)new PostponedUpdate(n5, n, false));
                            break block6;
                        }
                        int n6 = this.mNewItemStatuses[n5] >> 5;
                        listUpdateCallback.onMoved(DiffResult.removePostponedUpdate(object, (int)n6, (boolean)true).currentPos, n);
                        if (n4 != 4) break block6;
                        listUpdateCallback.onChanged(n, 1, this.mCallback.getChangePayload(n6, n5));
                        break block6;
                    }
                    listUpdateCallback.onInserted(n, 1);
                    object2 = object.iterator();
                    while (object2.hasNext()) {
                        PostponedUpdate postponedUpdate = (PostponedUpdate)object2.next();
                        ++postponedUpdate.currentPos;
                    }
                }
                --n2;
            }
        }

        private void dispatchRemovals(List<PostponedUpdate> object, ListUpdateCallback listUpdateCallback, int n, int n2, int n3) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onRemoved(n, n2);
                return;
            }
            --n2;
            while (n2 >= 0) {
                block6 : {
                    Object object2;
                    block4 : {
                        int n4;
                        int n5;
                        block5 : {
                            object2 = this.mOldItemStatuses;
                            n5 = n3 + n2;
                            n4 = object2[n5] & 31;
                            if (n4 == 0) break block4;
                            if (n4 == 4 || n4 == 8) break block5;
                            if (n4 != 16) {
                                object = new StringBuilder();
                                object.append("unknown flag for pos ");
                                object.append(n5);
                                object.append(" ");
                                object.append(Long.toBinaryString(n4));
                                throw new IllegalStateException(object.toString());
                            }
                            object.add((PostponedUpdate)new PostponedUpdate(n5, n + n2, true));
                            break block6;
                        }
                        int n6 = this.mOldItemStatuses[n5] >> 5;
                        object2 = DiffResult.removePostponedUpdate(object, n6, false);
                        listUpdateCallback.onMoved(n + n2, object2.currentPos - 1);
                        if (n4 != 4) break block6;
                        listUpdateCallback.onChanged(object2.currentPos - 1, 1, this.mCallback.getChangePayload(n5, n6));
                        break block6;
                    }
                    listUpdateCallback.onRemoved(n + n2, 1);
                    object2 = object.iterator();
                    while (object2.hasNext()) {
                        PostponedUpdate postponedUpdate = (PostponedUpdate)object2.next();
                        --postponedUpdate.currentPos;
                    }
                }
                --n2;
            }
        }

        private void findAddition(int n, int n2, int n3) {
            if (this.mOldItemStatuses[n - 1] != 0) {
                return;
            }
            this.findMatchingItem(n, n2, n3, false);
        }

        private boolean findMatchingItem(int n, int n2, int n3, boolean bl) {
            int n4;
            int n5;
            int n6;
            if (bl) {
                n6 = n2 - 1;
                n2 = n;
                n5 = n6;
            } else {
                n5 = n4 = n - 1;
                n6 = n2;
                n2 = n4;
            }
            while (n3 >= 0) {
                int[] arrn = this.mSnakes.get(n3);
                int n7 = arrn.x;
                int n8 = arrn.size;
                int n9 = arrn.y;
                int n10 = arrn.size;
                n4 = 4;
                if (bl) {
                    --n2;
                    while (n2 >= n7 + n8) {
                        if (this.mCallback.areItemsTheSame(n2, n5)) {
                            if (this.mCallback.areContentsTheSame(n2, n5)) {
                                n4 = 8;
                            }
                            this.mNewItemStatuses[n5] = n2 << 5 | 16;
                            this.mOldItemStatuses[n2] = n5 << 5 | n4;
                            return true;
                        }
                        --n2;
                    }
                } else {
                    for (n2 = n6 - 1; n2 >= n9 + n10; --n2) {
                        if (!this.mCallback.areItemsTheSame(n5, n2)) continue;
                        if (this.mCallback.areContentsTheSame(n5, n2)) {
                            n4 = 8;
                        }
                        arrn = this.mOldItemStatuses;
                        arrn[--n] = n2 << 5 | 16;
                        this.mNewItemStatuses[n2] = n << 5 | n4;
                        return true;
                    }
                }
                n2 = arrn.x;
                n6 = arrn.y;
                --n3;
            }
            return false;
        }

        private void findMatchingItems() {
            int n = this.mOldListSize;
            int n2 = this.mNewListSize;
            for (int i = this.mSnakes.size() - 1; i >= 0; --i) {
                int n3;
                Snake snake = this.mSnakes.get(i);
                int n4 = snake.x;
                int n5 = snake.size;
                int n6 = snake.y;
                int n7 = snake.size;
                if (this.mDetectMoves) {
                    do {
                        if (n <= n4 + n5) break;
                        this.findAddition(n, n2, i);
                        --n;
                    } while (true);
                    for (n3 = n2; n3 > n6 + n7; --n3) {
                        this.findRemoval(n, n3, i);
                    }
                }
                for (n2 = 0; n2 < snake.size; ++n2) {
                    n3 = snake.x + n2;
                    n6 = snake.y + n2;
                    n = this.mCallback.areContentsTheSame(n3, n6) ? 1 : 2;
                    this.mOldItemStatuses[n3] = n6 << 5 | n;
                    this.mNewItemStatuses[n6] = n3 << 5 | n;
                }
                n = snake.x;
                n2 = snake.y;
            }
        }

        private void findRemoval(int n, int n2, int n3) {
            if (this.mNewItemStatuses[n2 - 1] != 0) {
                return;
            }
            this.findMatchingItem(n, n2, n3, true);
        }

        private static PostponedUpdate removePostponedUpdate(List<PostponedUpdate> list, int n, boolean bl) {
            for (int i = list.size() - 1; i >= 0; --i) {
                PostponedUpdate postponedUpdate = list.get(i);
                if (postponedUpdate.posInOwnerList != n || postponedUpdate.removal != bl) continue;
                list.remove(i);
                while (i < list.size()) {
                    PostponedUpdate postponedUpdate2 = list.get(i);
                    int n2 = postponedUpdate2.currentPos;
                    n = bl ? 1 : -1;
                    postponedUpdate2.currentPos = n2 + n;
                    ++i;
                }
                return postponedUpdate;
            }
            return null;
        }

        public void dispatchUpdatesTo(ListUpdateCallback listUpdateCallback) {
            listUpdateCallback = listUpdateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback)listUpdateCallback : new BatchingListUpdateCallback(listUpdateCallback);
            ArrayList<PostponedUpdate> arrayList = new ArrayList<PostponedUpdate>();
            int n = this.mOldListSize;
            int n2 = this.mNewListSize;
            int n3 = this.mSnakes.size();
            --n3;
            while (n3 >= 0) {
                Snake snake = this.mSnakes.get(n3);
                int n4 = snake.size;
                int n5 = snake.x + n4;
                int n6 = snake.y + n4;
                if (n5 < n) {
                    this.dispatchRemovals(arrayList, listUpdateCallback, n5, n - n5, n5);
                }
                if (n6 < n2) {
                    this.dispatchAdditions(arrayList, listUpdateCallback, n5, n2 - n6, n6);
                }
                for (n = n4 - 1; n >= 0; --n) {
                    if ((this.mOldItemStatuses[snake.x + n] & 31) != 2) continue;
                    ((BatchingListUpdateCallback)listUpdateCallback).onChanged(snake.x + n, 1, this.mCallback.getChangePayload(snake.x + n, snake.y + n));
                }
                n = snake.x;
                n2 = snake.y;
                --n3;
            }
            listUpdateCallback.dispatchLastEvent();
        }

        public void dispatchUpdatesTo(RecyclerView.Adapter adapter) {
            this.dispatchUpdatesTo(new AdapterListUpdateCallback(adapter));
        }

        @VisibleForTesting
        List<Snake> getSnakes() {
            return this.mSnakes;
        }
    }

    public static abstract class ItemCallback<T> {
        public abstract boolean areContentsTheSame(T var1, T var2);

        public abstract boolean areItemsTheSame(T var1, T var2);

        public Object getChangePayload(T t, T t2) {
            return null;
        }
    }

    private static class PostponedUpdate {
        int currentPos;
        int posInOwnerList;
        boolean removal;

        public PostponedUpdate(int n, int n2, boolean bl) {
            this.posInOwnerList = n;
            this.currentPos = n2;
            this.removal = bl;
        }
    }

    static class Range {
        int newListEnd;
        int newListStart;
        int oldListEnd;
        int oldListStart;

        public Range() {
        }

        public Range(int n, int n2, int n3, int n4) {
            this.oldListStart = n;
            this.oldListEnd = n2;
            this.newListStart = n3;
            this.newListEnd = n4;
        }
    }

    static class Snake {
        boolean removal;
        boolean reverse;
        int size;
        int x;
        int y;

        Snake() {
        }
    }

}
