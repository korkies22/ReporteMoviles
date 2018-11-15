/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.command;

import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.android.board.view.BoardMarkingDisplay;

public interface VideoCommand {
    public boolean executeOn(VideoCommandDelegate var1, BoardMarkingDisplay var2);

    public long getExecutionTime();
}
