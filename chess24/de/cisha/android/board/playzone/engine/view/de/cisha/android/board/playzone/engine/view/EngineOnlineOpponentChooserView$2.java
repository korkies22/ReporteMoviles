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
import de.cisha.android.board.playzone.engine.view.EngineOpponentView;
import de.cisha.android.board.view.ViewSelectionHelper;

class EngineOnlineOpponentChooserView
implements ViewSelectionHelper.ResourceSelectionAdapter<EngineOpponentView> {
    EngineOnlineOpponentChooserView() {
    }

    @Override
    public View getClickableFrom(EngineOpponentView engineOpponentView) {
        return engineOpponentView;
    }

    @Override
    public void onResourceSelected(int n) {
    }

    @Override
    public void selectView(EngineOpponentView engineOpponentView, boolean bl) {
        TextView textView = engineOpponentView.getViewDescriptionText();
        Object var5_4 = null;
        TextUtils.TruncateAt truncateAt = bl ? null : TextUtils.TruncateAt.END;
        textView.setEllipsize(truncateAt);
        truncateAt = engineOpponentView.getViewDescriptionText();
        int n = bl ? 10 : 1;
        truncateAt.setMaxLines(n);
        truncateAt = engineOpponentView.getViewDescriptionText();
        engineOpponentView = var5_4;
        if (bl) {
            engineOpponentView = LinkMovementMethod.getInstance();
        }
        truncateAt.setMovementMethod((MovementMethod)engineOpponentView);
    }
}
