/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.annotation.VisibleForTesting;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.BatchingListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public static class DiffUtil.DiffResult {
    private static final int FLAG_CHANGED = 2;
    private static final int FLAG_IGNORE = 16;
    private static final int FLAG_MASK = 31;
    private static final int FLAG_MOVED_CHANGED = 4;
    private static final int FLAG_MOVED_NOT_CHANGED = 8;
    private static final int FLAG_NOT_CHANGED = 1;
    private static final int FLAG_OFFSET = 5;
    private final DiffUtil.Callback mCallback;
    private final boolean mDetectMoves;
    private final int[] mNewItemStatuses;
    private final int mNewListSize;
    private final int[] mOldItemStatuses;
    private final int mOldListSize;
    private final List<DiffUtil.Snake> mSnakes;

    DiffUtil.DiffResult(DiffUtil.Callback callback, List<DiffUtil.Snake> list, int[] arrn, int[] arrn2, boolean bl) {
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
        DiffUtil.Snake snake = this.mSnakes.isEmpty() ? null : this.mSnakes.get(0);
        if (snake == null || snake.x != 0 || snake.y != 0) {
            snake = new DiffUtil.Snake();
            snake.x = 0;
            snake.y = 0;
            snake.removal = false;
            snake.size = 0;
            snake.reverse = false;
            this.mSnakes.add(0, snake);
        }
    }

    private void dispatchAdditions(List<DiffUtil.PostponedUpdate> object, ListUpdateCallback listUpdateCallback, int n, int n2, int n3) {
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
                        n4 = n3 + n2;
                        n5 = object2[n4] & 31;
                        if (n5 == 0) break block4;
                        if (n5 == 4 || n5 == 8) break block5;
                        if (n5 != 16) {
                            object = new StringBuilder();
                            object.append("unknown flag for pos ");
                            object.append(n4);
                            object.append(" ");
                            object.append(Long.toBinaryString(n5));
                            throw new IllegalStateException(object.toString());
                        }
                        object.add((DiffUtil.PostponedUpdate)new DiffUtil.PostponedUpdate(n4, n, false));
                        break block6;
                    }
                    int n6 = this.mNewItemStatuses[n4] >> 5;
                    listUpdateCallback.onMoved(DiffUtil.DiffResult.removePostponedUpdate(object, (int)n6, (boolean)true).currentPos, n);
                    if (n5 != 4) break block6;
                    listUpdateCallback.onChanged(n, 1, this.mCallback.getChangePayload(n6, n4));
                    break block6;
                }
                listUpdateCallback.onInserted(n, 1);
                object2 = object.iterator();
                while (object2.hasNext()) {
                    DiffUtil.PostponedUpdate postponedUpdate = (DiffUtil.PostponedUpdate)object2.next();
                    ++postponedUpdate.currentPos;
                }
            }
            --n2;
        }
    }

    private void dispatchRemovals(List<DiffUtil.PostponedUpdate> object, ListUpdateCallback listUpdateCallback, int n, int n2, int n3) {
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
                        n4 = n3 + n2;
                        n5 = object2[n4] & 31;
                        if (n5 == 0) break block4;
                        if (n5 == 4 || n5 == 8) break block5;
                        if (n5 != 16) {
                            object = new StringBuilder();
                            object.append("unknown flag for pos ");
                            object.append(n4);
                            object.append(" ");
                            object.append(Long.toBinaryString(n5));
                            throw new IllegalStateException(object.toString());
                        }
                        object.add((DiffUtil.PostponedUpdate)new DiffUtil.PostponedUpdate(n4, n + n2, true));
                        break block6;
                    }
                    int n6 = this.mOldItemStatuses[n4] >> 5;
                    object2 = DiffUtil.DiffResult.removePostponedUpdate(object, n6, false);
                    listUpdateCallback.onMoved(n + n2, object2.currentPos - 1);
                    if (n5 != 4) break block6;
                    listUpdateCallback.onChanged(object2.currentPos - 1, 1, this.mCallback.getChangePayload(n4, n6));
                    break block6;
                }
                listUpdateCallback.onRemoved(n + n2, 1);
                object2 = object.iterator();
                while (object2.hasNext()) {
                    DiffUtil.PostponedUpdate postponedUpdate = (DiffUtil.PostponedUpdate)object2.next();
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
            n4 = n6;
        } else {
            n4 = n5 = n - 1;
            n6 = n2;
            n2 = n5;
        }
        while (n3 >= 0) {
            int[] arrn = this.mSnakes.get(n3);
            int n7 = arrn.x;
            int n8 = arrn.size;
            int n9 = arrn.y;
            int n10 = arrn.size;
            n5 = 4;
            if (bl) {
                --n2;
                while (n2 >= n7 + n8) {
                    if (this.mCallback.areItemsTheSame(n2, n4)) {
                        if (this.mCallback.areContentsTheSame(n2, n4)) {
                            n5 = 8;
                        }
                        this.mNewItemStatuses[n4] = n2 << 5 | 16;
                        this.mOldItemStatuses[n2] = n4 << 5 | n5;
                        return true;
                    }
                    --n2;
                }
            } else {
                for (n2 = n6 - 1; n2 >= n9 + n10; --n2) {
                    if (!this.mCallback.areItemsTheSame(n4, n2)) continue;
                    if (this.mCallback.areContentsTheSame(n4, n2)) {
                        n5 = 8;
                    }
                    arrn = this.mOldItemStatuses;
                    arrn[--n] = n2 << 5 | 16;
                    this.mNewItemStatuses[n2] = n << 5 | n5;
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
            DiffUtil.Snake snake = this.mSnakes.get(i);
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

    private static DiffUtil.PostponedUpdate removePostponedUpdate(List<DiffUtil.PostponedUpdate> list, int n, boolean bl) {
        for (int i = list.size() - 1; i >= 0; --i) {
            DiffUtil.PostponedUpdate postponedUpdate = list.get(i);
            if (postponedUpdate.posInOwnerList != n || postponedUpdate.removal != bl) continue;
            list.remove(i);
            while (i < list.size()) {
                DiffUtil.PostponedUpdate postponedUpdate2 = list.get(i);
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
        ArrayList<DiffUtil.PostponedUpdate> arrayList = new ArrayList<DiffUtil.PostponedUpdate>();
        int n = this.mOldListSize;
        int n2 = this.mNewListSize;
        int n3 = this.mSnakes.size();
        --n3;
        while (n3 >= 0) {
            DiffUtil.Snake snake = this.mSnakes.get(n3);
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
    List<DiffUtil.Snake> getSnakes() {
        return this.mSnakes;
    }
}
