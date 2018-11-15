/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.provider;

import android.support.v4.provider.FontsContractCompat;
import android.support.v4.provider.SelfDestructiveThread;
import android.support.v4.util.SimpleArrayMap;
import java.util.ArrayList;

static final class FontsContractCompat
implements SelfDestructiveThread.ReplyCallback<FontsContractCompat.TypefaceResult> {
    final /* synthetic */ String val$id;

    FontsContractCompat(String string) {
        this.val$id = string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onReply(FontsContractCompat.TypefaceResult typefaceResult) {
        ArrayList arrayList;
        Object object = sLock;
        synchronized (object) {
            arrayList = (ArrayList)sPendingReplies.get(this.val$id);
            if (arrayList == null) {
                return;
            }
            sPendingReplies.remove(this.val$id);
        }
        int n = 0;
        while (n < arrayList.size()) {
            ((SelfDestructiveThread.ReplyCallback)arrayList.get(n)).onReply(typefaceResult);
            ++n;
        }
        return;
    }
}
