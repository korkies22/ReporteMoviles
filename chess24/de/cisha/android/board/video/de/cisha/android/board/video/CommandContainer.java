/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.video.command.VideoCommand;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.android.board.video.model.VideoGameHolder;
import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.chess.util.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CommandContainer {
    private List<VideoCommand> _allCommands = new ArrayList<VideoCommand>();
    private int _currentTime = -1;
    private boolean _lastExecutionSucceded = true;

    private boolean executeCommands(int n, BoardMarkingDisplay boardMarkingDisplay, VideoGameHolder videoGameHolder, boolean bl) {
        ArrayList<VideoCommand> arrayList = new ArrayList<VideoCommand>();
        if (n > this._currentTime && this._lastExecutionSucceded && !bl) {
            for (VideoCommand object2 : this._allCommands) {
                if (object2.getExecutionTime() <= (long)this._currentTime || object2.getExecutionTime() > (long)n) continue;
                arrayList.add(object2);
            }
        } else if (n != this._currentTime || !this._lastExecutionSucceded || bl) {
            boardMarkingDisplay.reset();
            for (VideoCommand videoCommand : this._allCommands) {
                if (videoCommand.getExecutionTime() > (long)n) continue;
                arrayList.add(videoCommand);
            }
        }
        Iterator iterator = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Executing ");
        stringBuilder.append(arrayList.size());
        stringBuilder.append(" commands until ");
        stringBuilder.append(n);
        stringBuilder.append(" msecs");
        iterator.debug("CommanndContainer", stringBuilder.toString());
        if (arrayList.size() > 1) {
            videoGameHolder.setUpdateObservers(false);
            boardMarkingDisplay.setUpdateArrows(false);
        } else {
            videoGameHolder.setUpdateObservers(true);
            boardMarkingDisplay.setUpdateArrows(true);
        }
        iterator = arrayList.iterator();
        block2 : do {
            bl = true;
            while (iterator.hasNext()) {
                VideoCommand videoCommand = (VideoCommand)iterator.next();
                if (!bl) continue;
                if (bl && videoCommand.executeOn(videoGameHolder, boardMarkingDisplay)) continue block2;
                bl = false;
            }
            break;
        } while (true);
        if (arrayList.size() > 1) {
            videoGameHolder.setUpdateObservers(true);
            boardMarkingDisplay.setUpdateArrows(true);
        }
        this._currentTime = n;
        this._lastExecutionSucceded = true;
        return bl;
    }

    public void addCommand(VideoCommand videoCommand) {
        this._allCommands.add(videoCommand);
    }

    public void addCommands(Collection<VideoCommand> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.addCommand((VideoCommand)object.next());
        }
    }

    public boolean executeAllCommandsuntilCurrentPosition(BoardMarkingDisplay boardMarkingDisplay, VideoGameHolder videoGameHolder) {
        synchronized (this) {
            boolean bl = this.executeCommands(this._currentTime, boardMarkingDisplay, videoGameHolder, true);
            return bl;
        }
    }

    public boolean executeCommandsUntil(int n, BoardMarkingDisplay boardMarkingDisplay, VideoGameHolder videoGameHolder) {
        synchronized (this) {
            boolean bl = this.executeCommands(n, boardMarkingDisplay, videoGameHolder, false);
            return bl;
        }
    }
}
