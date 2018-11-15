/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.chess.model.ClockSetting;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class ChessClock {
    public static final long SHOW_MILLISECONDS = 20000L;
    private static final long WAITTIME_LONG = 490L;
    private static final long WAITTIME_SHORT = 47L;
    private boolean _activeColor;
    private ClockSetting _clockSettings = new ClockSetting(0, 0);
    protected boolean _countUpwards = false;
    private long _lastTimeMeasure;
    private Set<ClockListener> _listeners;
    private int _oneTimeOffsetSinceReconnect;
    private long _remainingRunningTimeBlack;
    private long _remainingRunningTimeWhite;
    private long _startTimeMillisBlack;
    private long _startTimeMillisWhite;
    private long _timeLastColorSwitch;
    private TimerThread _timeThread;

    public ChessClock(ClockSetting clockSetting) {
        this(false);
        this._clockSettings = clockSetting;
        if (clockSetting == null || clockSetting.isEmpty()) {
            this._countUpwards = true;
        }
        this.updateClockSetting();
    }

    public ChessClock(boolean bl) {
        this.updateClockSetting();
        this._activeColor = true;
        this._countUpwards = bl;
        this._listeners = Collections.newSetFromMap(new WeakHashMap());
    }

    public static String formatTime(long l, boolean bl) {
        if (l <= 0L) {
            l = 0L;
        }
        long l2 = l / 3600000L;
        long l3 = l / 1000L % 60L;
        long l4 = l / 60000L % 60L;
        long l5 = l / 10L % 100L;
        if (bl) {
            if (l < 0L) {
                return "00.00";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(l2);
            stringBuilder.append(":");
            String string = l4 < 10L ? "0" : "";
            stringBuilder.append(string);
            stringBuilder.append(l4);
            stringBuilder.append(":");
            string = l3 < 10L ? "0" : "";
            stringBuilder.append(string);
            stringBuilder.append(l3);
            return stringBuilder.toString();
        }
        if (l < 0L) {
            return "00.00";
        }
        if (l < 20000L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            String string = l3 < 10L ? "0" : "";
            stringBuilder.append(string);
            stringBuilder.append(l3);
            stringBuilder.append(".");
            string = l5 < 10L ? "0" : "";
            stringBuilder.append(string);
            stringBuilder.append(l5);
            return stringBuilder.toString();
        }
        if (l > 1200000L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(l2);
            stringBuilder.append(":");
            String string = l4 < 10L ? "0" : "";
            stringBuilder.append(string);
            stringBuilder.append(l4);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string = l4 < 10L ? "0" : "";
        stringBuilder.append(string);
        stringBuilder.append(l4);
        stringBuilder.append(":");
        string = l3 < 10L ? "0" : "";
        stringBuilder.append(string);
        stringBuilder.append(l3);
        return stringBuilder.toString();
    }

    private void setToInitialTime(int n) {
        this._startTimeMillisWhite = this._clockSettings.getBase(true);
        this._startTimeMillisBlack = this._clockSettings.getBase(false);
        if (this._clockSettings.getIncrementPerMove(true) > 0) {
            this._startTimeMillisWhite = Math.max(this._startTimeMillisWhite, 10000L);
            this._clockSettings.setBase(true, (int)this._startTimeMillisWhite);
        }
        if (this._clockSettings.getIncrementPerMove(false) > 0) {
            this._startTimeMillisBlack = Math.max(this._startTimeMillisBlack, 10000L);
            this._clockSettings.setBase(false, (int)this._startTimeMillisBlack);
        }
        this._remainingRunningTimeBlack = this._startTimeMillisBlack;
        this._remainingRunningTimeWhite = this._startTimeMillisWhite;
        this._timeLastColorSwitch = this._lastTimeMeasure = System.currentTimeMillis() - (long)n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateClock(boolean bl, long l) {
        if (l <= 0L) {
            l = 0L;
        }
        this.setRemainingTime(bl, l);
        Set<ClockListener> set = this._listeners;
        synchronized (set) {
            Iterator<ClockListener> iterator = this._listeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onClockChanged(l, bl);
            }
            if (l == 0L && !this._countUpwards && this._timeThread != null) {
                this._timeThread.running = false;
                iterator = this._listeners.iterator();
                while (iterator.hasNext()) {
                    iterator.next().onClockFlag(bl);
                }
            }
            return;
        }
    }

    private void updateClockSetting() {
        this._oneTimeOffsetSinceReconnect = this._clockSettings.getTimeGoneSinceLastMove();
        this.setToInitialTime(this._oneTimeOffsetSinceReconnect);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addOnClockListener(ClockListener clockListener) {
        Set<ClockListener> set = this._listeners;
        synchronized (set) {
            this._listeners.add(clockListener);
        }
        this.fireUpdate();
    }

    protected void fireUpdate() {
        this.updateClock(true, this._remainingRunningTimeWhite);
        this.updateClock(false, this._remainingRunningTimeBlack);
    }

    public boolean getActiveColor() {
        return this._activeColor;
    }

    public ClockSetting getClockSettings() {
        return this._clockSettings;
    }

    public long getStartTimeMillis(boolean bl) {
        if (bl) {
            return this._startTimeMillisWhite;
        }
        return this._startTimeMillisBlack;
    }

    public long getTimeMillis(boolean bl) {
        if (bl) {
            return this._remainingRunningTimeWhite;
        }
        return this._remainingRunningTimeBlack;
    }

    public long getTimeSinceLastMove() {
        if (this.isRunning()) {
            return System.currentTimeMillis() - this._timeLastColorSwitch;
        }
        return 0L;
    }

    public boolean isRunning() {
        if (this._timeThread != null && this._timeThread.running) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeAllClockListeners() {
        Set<ClockListener> set = this._listeners;
        synchronized (set) {
            this._listeners.clear();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeClockListener(ClockListener clockListener) {
        Set<ClockListener> set = this._listeners;
        synchronized (set) {
            this._listeners.remove(clockListener);
            return;
        }
    }

    public void reset() {
        this._oneTimeOffsetSinceReconnect = this.isRunning() ? (int)(System.currentTimeMillis() - this._timeLastColorSwitch) : this._clockSettings.getTimeGoneSinceLastMove();
        this.setToInitialTime(this._oneTimeOffsetSinceReconnect);
    }

    public int setActiveColor(boolean bl) {
        return this.setActiveColor(bl, true);
    }

    public int setActiveColor(boolean bl, boolean bl2) {
        if (this._activeColor != bl) {
            this._activeColor = bl;
            if (this.isRunning() && bl2) {
                this.setRemainingTime(bl ^ true, this.getTimeMillis(bl ^ true) + (long)this._clockSettings.getIncrementPerMove(bl ^ true));
            }
            long l = System.currentTimeMillis();
            int n = (int)(l - this._timeLastColorSwitch);
            this._lastTimeMeasure = l;
            this._timeLastColorSwitch = l;
            return n;
        }
        return 0;
    }

    protected void setRemainingTime(boolean bl, long l) {
        if (bl) {
            this._remainingRunningTimeWhite = l;
            return;
        }
        this._remainingRunningTimeBlack = l;
    }

    public void start() {
        if (this._timeThread == null || !this._timeThread.running) {
            this._timeLastColorSwitch = this._lastTimeMeasure = System.currentTimeMillis() - (long)this._oneTimeOffsetSinceReconnect;
            this._oneTimeOffsetSinceReconnect = 0;
            this._timeThread = new TimerThread();
            this._timeThread.start();
            Iterator<ClockListener> iterator = this._listeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onClockStarted();
            }
        }
    }

    public void stop() {
        if (this._timeThread != null) {
            this._timeThread.running = false;
            Iterator<ClockListener> iterator = this._listeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onClockStopped();
            }
        }
        this._timeThread = null;
    }

    private class TimerThread
    extends Thread {
        public boolean running = false;

        protected TimerThread() {
            this.setName("clockthread");
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            this.running = true;
            while (this.running) {
                long l;
                long l2 = ChessClock.this.getTimeMillis(ChessClock.this._activeColor);
                long l3 = System.currentTimeMillis();
                long l4 = l = l3 - ChessClock.this._lastTimeMeasure;
                if (ChessClock.this._countUpwards) {
                    l4 = - l;
                }
                ChessClock.this._lastTimeMeasure = l3;
                ChessClock.this.updateClock(ChessClock.this._activeColor, l2 - l4);
                ChessClock chessClock = ChessClock.this;
                boolean bl = ChessClock.this._activeColor;
                l4 = ChessClock.this._activeColor ? ChessClock.this._remainingRunningTimeBlack : ChessClock.this._remainingRunningTimeWhite;
                chessClock.updateClock(bl ^ true, l4);
                l4 = l2 > 20000L ? 490L : 47L;
                try {
                    Thread.sleep(l4);
                }
                catch (InterruptedException interruptedException) {
                }
            }
        }
    }

}
