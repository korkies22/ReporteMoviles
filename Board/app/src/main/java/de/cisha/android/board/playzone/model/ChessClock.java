// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.Set;
import de.cisha.chess.model.ClockSetting;

public class ChessClock
{
    public static final long SHOW_MILLISECONDS = 20000L;
    private static final long WAITTIME_LONG = 490L;
    private static final long WAITTIME_SHORT = 47L;
    private boolean _activeColor;
    private ClockSetting _clockSettings;
    protected boolean _countUpwards;
    private long _lastTimeMeasure;
    private Set<ClockListener> _listeners;
    private int _oneTimeOffsetSinceReconnect;
    private long _remainingRunningTimeBlack;
    private long _remainingRunningTimeWhite;
    private long _startTimeMillisBlack;
    private long _startTimeMillisWhite;
    private long _timeLastColorSwitch;
    private TimerThread _timeThread;
    
    public ChessClock(final ClockSetting clockSettings) {
        this(false);
        this._clockSettings = clockSettings;
        if (clockSettings == null || clockSettings.isEmpty()) {
            this._countUpwards = true;
        }
        this.updateClockSetting();
    }
    
    public ChessClock(final boolean countUpwards) {
        this._countUpwards = false;
        this._clockSettings = new ClockSetting(0, 0);
        this.updateClockSetting();
        this._activeColor = true;
        this._countUpwards = countUpwards;
        this._listeners = Collections.newSetFromMap(new WeakHashMap<ClockListener, Boolean>());
    }
    
    public static String formatTime(long n, final boolean b) {
        if (n <= 0L) {
            n = 0L;
        }
        final long n2 = n / 3600000L;
        final long n3 = n / 1000L % 60L;
        final long n4 = n / 60000L % 60L;
        final long n5 = n / 10L % 100L;
        if (b) {
            if (n < 0L) {
                return "00.00";
            }
            final StringBuilder sb = new StringBuilder();
            sb.append(n2);
            sb.append(":");
            String s;
            if (n4 < 10L) {
                s = "0";
            }
            else {
                s = "";
            }
            sb.append(s);
            sb.append(n4);
            sb.append(":");
            String s2;
            if (n3 < 10L) {
                s2 = "0";
            }
            else {
                s2 = "";
            }
            sb.append(s2);
            sb.append(n3);
            return sb.toString();
        }
        else {
            if (n < 0L) {
                return "00.00";
            }
            if (n < 20000L) {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                String s3;
                if (n3 < 10L) {
                    s3 = "0";
                }
                else {
                    s3 = "";
                }
                sb2.append(s3);
                sb2.append(n3);
                sb2.append(".");
                String s4;
                if (n5 < 10L) {
                    s4 = "0";
                }
                else {
                    s4 = "";
                }
                sb2.append(s4);
                sb2.append(n5);
                return sb2.toString();
            }
            if (n > 1200000L) {
                final StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(n2);
                sb3.append(":");
                String s5;
                if (n4 < 10L) {
                    s5 = "0";
                }
                else {
                    s5 = "";
                }
                sb3.append(s5);
                sb3.append(n4);
                return sb3.toString();
            }
            final StringBuilder sb4 = new StringBuilder();
            String s6;
            if (n4 < 10L) {
                s6 = "0";
            }
            else {
                s6 = "";
            }
            sb4.append(s6);
            sb4.append(n4);
            sb4.append(":");
            String s7;
            if (n3 < 10L) {
                s7 = "0";
            }
            else {
                s7 = "";
            }
            sb4.append(s7);
            sb4.append(n3);
            return sb4.toString();
        }
    }
    
    private void setToInitialTime(final int n) {
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
        this._lastTimeMeasure = System.currentTimeMillis() - n;
        this._timeLastColorSwitch = this._lastTimeMeasure;
    }
    
    private void updateClock(final boolean b, long n) {
        if (n <= 0L) {
            n = 0L;
        }
        this.setRemainingTime(b, n);
        synchronized (this._listeners) {
            final Iterator<ClockListener> iterator = this._listeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onClockChanged(n, b);
            }
            if (n == 0L && !this._countUpwards && this._timeThread != null) {
                this._timeThread.running = false;
                final Iterator<ClockListener> iterator2 = this._listeners.iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().onClockFlag(b);
                }
            }
        }
    }
    
    private void updateClockSetting() {
        this.setToInitialTime(this._oneTimeOffsetSinceReconnect = this._clockSettings.getTimeGoneSinceLastMove());
    }
    
    public void addOnClockListener(final ClockListener clockListener) {
        synchronized (this._listeners) {
            this._listeners.add(clockListener);
            // monitorexit(this._listeners)
            this.fireUpdate();
        }
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
    
    public long getStartTimeMillis(final boolean b) {
        if (b) {
            return this._startTimeMillisWhite;
        }
        return this._startTimeMillisBlack;
    }
    
    public long getTimeMillis(final boolean b) {
        if (b) {
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
        return this._timeThread != null && this._timeThread.running;
    }
    
    public void removeAllClockListeners() {
        synchronized (this._listeners) {
            this._listeners.clear();
        }
    }
    
    public void removeClockListener(final ClockListener clockListener) {
        synchronized (this._listeners) {
            this._listeners.remove(clockListener);
        }
    }
    
    public void reset() {
        if (this.isRunning()) {
            this._oneTimeOffsetSinceReconnect = (int)(System.currentTimeMillis() - this._timeLastColorSwitch);
        }
        else {
            this._oneTimeOffsetSinceReconnect = this._clockSettings.getTimeGoneSinceLastMove();
        }
        this.setToInitialTime(this._oneTimeOffsetSinceReconnect);
    }
    
    public int setActiveColor(final boolean b) {
        return this.setActiveColor(b, true);
    }
    
    public int setActiveColor(final boolean activeColor, final boolean b) {
        if (this._activeColor != activeColor) {
            this._activeColor = activeColor;
            if (this.isRunning() && b) {
                this.setRemainingTime(activeColor ^ true, this.getTimeMillis(activeColor ^ true) + this._clockSettings.getIncrementPerMove(activeColor ^ true));
            }
            final long currentTimeMillis = System.currentTimeMillis();
            final int n = (int)(currentTimeMillis - this._timeLastColorSwitch);
            this._lastTimeMeasure = currentTimeMillis;
            this._timeLastColorSwitch = currentTimeMillis;
            return n;
        }
        return 0;
    }
    
    protected void setRemainingTime(final boolean b, final long n) {
        if (b) {
            this._remainingRunningTimeWhite = n;
            return;
        }
        this._remainingRunningTimeBlack = n;
    }
    
    public void start() {
        if (this._timeThread == null || !this._timeThread.running) {
            this._lastTimeMeasure = System.currentTimeMillis() - this._oneTimeOffsetSinceReconnect;
            this._timeLastColorSwitch = this._lastTimeMeasure;
            this._oneTimeOffsetSinceReconnect = 0;
            (this._timeThread = new TimerThread()).start();
            final Iterator<ClockListener> iterator = this._listeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onClockStarted();
            }
        }
    }
    
    public void stop() {
        if (this._timeThread != null) {
            this._timeThread.running = false;
            final Iterator<ClockListener> iterator = this._listeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onClockStopped();
            }
        }
        this._timeThread = null;
    }
    
    private class TimerThread extends Thread
    {
        public boolean running;
        
        protected TimerThread() {
            this.running = false;
            this.setName("clockthread");
        }
        
        @Override
        public void run() {
            this.running = true;
            while (true) {
                if (!this.running) {
                    return;
                }
                final long timeMillis = ChessClock.this.getTimeMillis(ChessClock.this._activeColor);
                final long currentTimeMillis = System.currentTimeMillis();
                long n = currentTimeMillis - ChessClock.this._lastTimeMeasure;
                if (ChessClock.this._countUpwards) {
                    n = -n;
                }
                ChessClock.this._lastTimeMeasure = currentTimeMillis;
                ChessClock.this.updateClock(ChessClock.this._activeColor, timeMillis - n);
                final ChessClock this.0 = ChessClock.this;
                final boolean access.000 = ChessClock.this._activeColor;
                long n2;
                if (ChessClock.this._activeColor) {
                    n2 = ChessClock.this._remainingRunningTimeBlack;
                }
                else {
                    n2 = ChessClock.this._remainingRunningTimeWhite;
                }
                this.0.updateClock(access.000 ^ true, n2);
                long n3;
                if (timeMillis > 20000L) {
                    n3 = 490L;
                }
                else {
                    n3 = 47L;
                }
                try {
                    Thread.sleep(n3);
                    continue;
                }
                catch (InterruptedException ex) {}
            }
        }
    }
}
