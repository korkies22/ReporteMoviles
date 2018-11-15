/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common;

import java.util.List;

public final class DecoderResult {
    private final List<byte[]> byteSegments;
    private final String ecLevel;
    private Integer erasures;
    private Integer errorsCorrected;
    private int numBits;
    private Object other;
    private final byte[] rawBytes;
    private final int structuredAppendParity;
    private final int structuredAppendSequenceNumber;
    private final String text;

    public DecoderResult(byte[] arrby, String string, List<byte[]> list, String string2) {
        this(arrby, string, list, string2, -1, -1);
    }

    public DecoderResult(byte[] arrby, String string, List<byte[]> list, String string2, int n, int n2) {
        this.rawBytes = arrby;
        int n3 = arrby == null ? 0 : arrby.length * 8;
        this.numBits = n3;
        this.text = string;
        this.byteSegments = list;
        this.ecLevel = string2;
        this.structuredAppendParity = n2;
        this.structuredAppendSequenceNumber = n;
    }

    public List<byte[]> getByteSegments() {
        return this.byteSegments;
    }

    public String getECLevel() {
        return this.ecLevel;
    }

    public Integer getErasures() {
        return this.erasures;
    }

    public Integer getErrorsCorrected() {
        return this.errorsCorrected;
    }

    public int getNumBits() {
        return this.numBits;
    }

    public Object getOther() {
        return this.other;
    }

    public byte[] getRawBytes() {
        return this.rawBytes;
    }

    public int getStructuredAppendParity() {
        return this.structuredAppendParity;
    }

    public int getStructuredAppendSequenceNumber() {
        return this.structuredAppendSequenceNumber;
    }

    public String getText() {
        return this.text;
    }

    public boolean hasStructuredAppend() {
        if (this.structuredAppendParity >= 0 && this.structuredAppendSequenceNumber >= 0) {
            return true;
        }
        return false;
    }

    public void setErasures(Integer n) {
        this.erasures = n;
    }

    public void setErrorsCorrected(Integer n) {
        this.errorsCorrected = n;
    }

    public void setNumBits(int n) {
        this.numBits = n;
    }

    public void setOther(Object object) {
        this.other = object;
    }
}
