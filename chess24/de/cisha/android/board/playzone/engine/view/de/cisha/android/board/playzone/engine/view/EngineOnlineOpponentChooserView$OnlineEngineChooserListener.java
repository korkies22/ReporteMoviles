/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine.view;

import de.cisha.android.board.playzone.engine.view.EngineOnlineOpponentChooserView;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.model.TimeControl;

public static interface EngineOnlineOpponentChooserView.OnlineEngineChooserListener {
    public void onOnlineEngineChosen(String var1, TimeControl var2, boolean var3);

    public void resumeEngineOnlineGame(PlayzoneGameSessionInfo var1);
}
