/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417;

public final class PDF417ResultMetadata {
    private String fileId;
    private boolean lastSegment;
    private int[] optionalData;
    private int segmentIndex;

    public String getFileId() {
        return this.fileId;
    }

    public int[] getOptionalData() {
        return this.optionalData;
    }

    public int getSegmentIndex() {
        return this.segmentIndex;
    }

    public boolean isLastSegment() {
        return this.lastSegment;
    }

    public void setFileId(String string) {
        this.fileId = string;
    }

    public void setLastSegment(boolean bl) {
        this.lastSegment = bl;
    }

    public void setOptionalData(int[] arrn) {
        this.optionalData = arrn;
    }

    public void setSegmentIndex(int n) {
        this.segmentIndex = n;
    }
}
