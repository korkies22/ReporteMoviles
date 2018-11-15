/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss.expanded.decoders;

final class CurrentParsingState {
    private State encoding = State.NUMERIC;
    private int position = 0;

    CurrentParsingState() {
    }

    int getPosition() {
        return this.position;
    }

    void incrementPosition(int n) {
        this.position += n;
    }

    boolean isAlpha() {
        if (this.encoding == State.ALPHA) {
            return true;
        }
        return false;
    }

    boolean isIsoIec646() {
        if (this.encoding == State.ISO_IEC_646) {
            return true;
        }
        return false;
    }

    boolean isNumeric() {
        if (this.encoding == State.NUMERIC) {
            return true;
        }
        return false;
    }

    void setAlpha() {
        this.encoding = State.ALPHA;
    }

    void setIsoIec646() {
        this.encoding = State.ISO_IEC_646;
    }

    void setNumeric() {
        this.encoding = State.NUMERIC;
    }

    void setPosition(int n) {
        this.position = n;
    }

    private static enum State {
        NUMERIC,
        ALPHA,
        ISO_IEC_646;
        

        private State() {
        }
    }

}
