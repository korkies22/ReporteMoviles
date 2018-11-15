/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;

public class SftpStatVFS {
    int atime;
    private long bavail;
    private long bfree;
    private long blocks;
    private long bsize;
    String[] extended = null;
    private long favail;
    private long ffree;
    private long files;
    private long flag;
    int flags = 0;
    private long frsize;
    private long fsid;
    int gid;
    int mtime;
    private long namemax;
    int permissions;
    long size;
    int uid;

    private SftpStatVFS() {
    }

    static SftpStatVFS getStatVFS(Buffer buffer) {
        SftpStatVFS sftpStatVFS = new SftpStatVFS();
        sftpStatVFS.bsize = buffer.getLong();
        sftpStatVFS.frsize = buffer.getLong();
        sftpStatVFS.blocks = buffer.getLong();
        sftpStatVFS.bfree = buffer.getLong();
        sftpStatVFS.bavail = buffer.getLong();
        sftpStatVFS.files = buffer.getLong();
        sftpStatVFS.ffree = buffer.getLong();
        sftpStatVFS.favail = buffer.getLong();
        sftpStatVFS.fsid = buffer.getLong();
        int n = (int)buffer.getLong();
        sftpStatVFS.namemax = buffer.getLong();
        long l = 0L;
        long l2 = (n & 1) != 0 ? 1L : 0L;
        long l3 = sftpStatVFS.flag = l2;
        l2 = l;
        if ((n & 2) != 0) {
            l2 = 2L;
        }
        sftpStatVFS.flag = l3 | l2;
        return sftpStatVFS;
    }

    public long getAvail() {
        return this.getFragmentSize() * this.getFreeBlocks() / 1024L;
    }

    public long getAvailBlocks() {
        return this.bavail;
    }

    public long getAvailForNonRoot() {
        return this.getFragmentSize() * this.getAvailBlocks() / 1024L;
    }

    public long getAvailINodes() {
        return this.favail;
    }

    public long getBlockSize() {
        return this.bsize;
    }

    public long getBlocks() {
        return this.blocks;
    }

    public int getCapacity() {
        return (int)(100L * (this.getBlocks() - this.getFreeBlocks()) / this.getBlocks());
    }

    public long getFileSystemID() {
        return this.fsid;
    }

    public long getFragmentSize() {
        return this.frsize;
    }

    public long getFreeBlocks() {
        return this.bfree;
    }

    public long getFreeINodes() {
        return this.ffree;
    }

    public long getINodes() {
        return this.files;
    }

    public long getMaximumFilenameLength() {
        return this.namemax;
    }

    public long getMountFlag() {
        return this.flag;
    }

    public long getSize() {
        return this.getFragmentSize() * this.getBlocks() / 1024L;
    }

    public long getUsed() {
        return this.getFragmentSize() * (this.getBlocks() - this.getFreeBlocks()) / 1024L;
    }
}
