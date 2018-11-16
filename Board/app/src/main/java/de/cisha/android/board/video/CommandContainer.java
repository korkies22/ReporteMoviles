// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video;

import java.util.Collection;
import java.util.Iterator;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.video.model.VideoGameHolder;
import de.cisha.android.board.view.BoardMarkingDisplay;
import java.util.ArrayList;
import de.cisha.android.board.video.command.VideoCommand;
import java.util.List;

public class CommandContainer
{
    private List<VideoCommand> _allCommands;
    private int _currentTime;
    private boolean _lastExecutionSucceded;
    
    public CommandContainer() {
        this._currentTime = -1;
        this._lastExecutionSucceded = true;
        this._allCommands = new ArrayList<VideoCommand>();
    }
    
    private boolean executeCommands(final int currentTime, final BoardMarkingDisplay boardMarkingDisplay, final VideoGameHolder videoGameHolder, final boolean b) {
        final ArrayList<VideoCommand> list = new ArrayList<VideoCommand>();
        if (currentTime > this._currentTime && this._lastExecutionSucceded && !b) {
            for (final VideoCommand videoCommand : this._allCommands) {
                if (videoCommand.getExecutionTime() > this._currentTime && videoCommand.getExecutionTime() <= currentTime) {
                    list.add(videoCommand);
                }
            }
        }
        else if (currentTime != this._currentTime || !this._lastExecutionSucceded || b) {
            boardMarkingDisplay.reset();
            for (final VideoCommand videoCommand2 : this._allCommands) {
                if (videoCommand2.getExecutionTime() <= currentTime) {
                    list.add(videoCommand2);
                }
            }
        }
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("Executing ");
        sb.append(list.size());
        sb.append(" commands until ");
        sb.append(currentTime);
        sb.append(" msecs");
        instance.debug("CommanndContainer", sb.toString());
        if (list.size() > 1) {
            videoGameHolder.setUpdateObservers(false);
            boardMarkingDisplay.setUpdateArrows(false);
        }
        else {
            videoGameHolder.setUpdateObservers(true);
            boardMarkingDisplay.setUpdateArrows(true);
        }
        final Iterator<Object> iterator3 = list.iterator();
        boolean b2 = false;
    Label_0306:
        while (true) {
            b2 = true;
            while (iterator3.hasNext()) {
                final VideoCommand videoCommand3 = iterator3.next();
                if (b2) {
                    if (b2 && videoCommand3.executeOn(videoGameHolder, boardMarkingDisplay)) {
                        continue Label_0306;
                    }
                    b2 = false;
                }
            }
            break;
        }
        if (list.size() > 1) {
            videoGameHolder.setUpdateObservers(true);
            boardMarkingDisplay.setUpdateArrows(true);
        }
        this._currentTime = currentTime;
        this._lastExecutionSucceded = true;
        return b2;
    }
    
    public void addCommand(final VideoCommand videoCommand) {
        this._allCommands.add(videoCommand);
    }
    
    public void addCommands(final Collection<VideoCommand> collection) {
        final Iterator<VideoCommand> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.addCommand(iterator.next());
        }
    }
    
    public boolean executeAllCommandsuntilCurrentPosition(final BoardMarkingDisplay boardMarkingDisplay, final VideoGameHolder videoGameHolder) {
        synchronized (this) {
            return this.executeCommands(this._currentTime, boardMarkingDisplay, videoGameHolder, true);
        }
    }
    
    public boolean executeCommandsUntil(final int n, final BoardMarkingDisplay boardMarkingDisplay, final VideoGameHolder videoGameHolder) {
        synchronized (this) {
            return this.executeCommands(n, boardMarkingDisplay, videoGameHolder, false);
        }
    }
}
