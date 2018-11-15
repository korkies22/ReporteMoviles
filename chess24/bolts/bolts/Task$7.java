/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.Continuation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

static final class Task
implements Continuation<Void, List<TResult>> {
    final /* synthetic */ Collection val$tasks;

    Task(Collection collection) {
        this.val$tasks = collection;
    }

    @Override
    public List<TResult> then(bolts.Task<Void> object) throws Exception {
        if (this.val$tasks.size() == 0) {
            return Collections.emptyList();
        }
        object = new ArrayList();
        Iterator iterator = this.val$tasks.iterator();
        while (iterator.hasNext()) {
            object.add(((bolts.Task)iterator.next()).getResult());
        }
        return object;
    }
}
