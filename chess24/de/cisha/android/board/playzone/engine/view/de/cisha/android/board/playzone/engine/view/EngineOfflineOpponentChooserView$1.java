/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.text.method.LinkMovementMethod
 *  android.text.method.MovementMethod
 *  android.view.View
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.engine.view;

import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.playzone.engine.view.EngineOpponentChooserTimeSelectionView;
import de.cisha.android.board.playzone.engine.view.EngineOpponentView;
import de.cisha.android.board.view.ViewSelectionHelper;

class EngineOfflineOpponentChooserView
implements ViewSelectionHelper.ResourceSelectionAdapter<View> {
    EngineOfflineOpponentChooserView() {
    }

    @Override
    public View getClickableFrom(View view) {
        return view;
    }

    @Override
    public void onResourceSelected(int n) {
    }

    @Override
    public void selectView(View object, boolean bl) {
        if (object instanceof EngineOpponentView) {
            TextView textView = ((EngineOpponentView)((Object)object)).getViewDescriptionText();
            Object var4_4 = null;
            object = bl ? null : TextUtils.TruncateAt.END;
            textView.setEllipsize((TextUtils.TruncateAt)object);
            int n = bl ? 10 : 1;
            textView.setMaxLines(n);
            object = var4_4;
            if (bl) {
                object = LinkMovementMethod.getInstance();
            }
            textView.setMovementMethod((MovementMethod)object);
            return;
        }
        if (object instanceof EngineOpponentChooserTimeSelectionView) {
            object.setSelected(bl);
        }
    }
}
