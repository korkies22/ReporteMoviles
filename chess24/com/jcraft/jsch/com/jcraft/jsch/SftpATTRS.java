/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Util;
import java.util.Date;

public class SftpATTRS {
    public static final int SSH_FILEXFER_ATTR_ACMODTIME = 8;
    public static final int SSH_FILEXFER_ATTR_EXTENDED = Integer.MIN_VALUE;
    public static final int SSH_FILEXFER_ATTR_PERMISSIONS = 4;
    public static final int SSH_FILEXFER_ATTR_SIZE = 1;
    public static final int SSH_FILEXFER_ATTR_UIDGID = 2;
    static final int S_IEXEC = 64;
    static final int S_IFBLK = 24576;
    static final int S_IFCHR = 8192;
    static final int S_IFDIR = 16384;
    static final int S_IFIFO = 4096;
    static final int S_IFLNK = 40960;
    static final int S_IFMT = 61440;
    static final int S_IFREG = 32768;
    static final int S_IFSOCK = 49152;
    static final int S_IREAD = 256;
    static final int S_IRGRP = 32;
    static final int S_IROTH = 4;
    static final int S_IRUSR = 256;
    static final int S_ISGID = 1024;
    static final int S_ISUID = 2048;
    static final int S_ISVTX = 512;
    static final int S_IWGRP = 16;
    static final int S_IWOTH = 2;
    static final int S_IWRITE = 128;
    static final int S_IWUSR = 128;
    static final int S_IXGRP = 8;
    static final int S_IXOTH = 1;
    static final int S_IXUSR = 64;
    private static final int pmask = 4095;
    int atime;
    String[] extended = null;
    int flags = 0;
    int gid;
    int mtime;
    int permissions;
    long size;
    int uid;

    private SftpATTRS() {
    }

    static SftpATTRS getATTR(Buffer buffer) {
        int n;
        SftpATTRS sftpATTRS = new SftpATTRS();
        sftpATTRS.flags = buffer.getInt();
        if ((sftpATTRS.flags & 1) != 0) {
            sftpATTRS.size = buffer.getLong();
        }
        if ((sftpATTRS.flags & 2) != 0) {
            sftpATTRS.uid = buffer.getInt();
            sftpATTRS.gid = buffer.getInt();
        }
        if ((sftpATTRS.flags & 4) != 0) {
            sftpATTRS.permissions = buffer.getInt();
        }
        if ((sftpATTRS.flags & 8) != 0) {
            sftpATTRS.atime = buffer.getInt();
        }
        if ((sftpATTRS.flags & 8) != 0) {
            sftpATTRS.mtime = buffer.getInt();
        }
        if ((sftpATTRS.flags & Integer.MIN_VALUE) != 0 && (n = buffer.getInt()) > 0) {
            sftpATTRS.extended = new String[n * 2];
            for (int i = 0; i < n; ++i) {
                String[] arrstring = sftpATTRS.extended;
                int n2 = i * 2;
                arrstring[n2] = Util.byte2str(buffer.getString());
                sftpATTRS.extended[n2 + 1] = Util.byte2str(buffer.getString());
            }
        }
        return sftpATTRS;
    }

    private boolean isType(int n) {
        if ((this.flags & 4) != 0 && (this.permissions & 61440) == n) {
            return true;
        }
        return false;
    }

    void dump(Buffer buffer) {
        int n;
        buffer.putInt(this.flags);
        if ((this.flags & 1) != 0) {
            buffer.putLong(this.size);
        }
        if ((this.flags & 2) != 0) {
            buffer.putInt(this.uid);
            buffer.putInt(this.gid);
        }
        if ((this.flags & 4) != 0) {
            buffer.putInt(this.permissions);
        }
        if ((this.flags & 8) != 0) {
            buffer.putInt(this.atime);
        }
        if ((this.flags & 8) != 0) {
            buffer.putInt(this.mtime);
        }
        if ((this.flags & Integer.MIN_VALUE) != 0 && (n = this.extended.length / 2) > 0) {
            for (int i = 0; i < n; ++i) {
                String[] arrstring = this.extended;
                int n2 = i * 2;
                buffer.putString(Util.str2byte(arrstring[n2]));
                buffer.putString(Util.str2byte(this.extended[n2 + 1]));
            }
        }
    }

    public int getATime() {
        return this.atime;
    }

    public String getAtimeString() {
        return new Date((long)this.atime * 1000L).toString();
    }

    public String[] getExtended() {
        return this.extended;
    }

    public int getFlags() {
        return this.flags;
    }

    public int getGId() {
        return this.gid;
    }

    public int getMTime() {
        return this.mtime;
    }

    public String getMtimeString() {
        return new Date((long)this.mtime * 1000L).toString();
    }

    public int getPermissions() {
        return this.permissions;
    }

    public String getPermissionsString() {
        StringBuffer stringBuffer = new StringBuffer(10);
        if (this.isDir()) {
            stringBuffer.append('d');
        } else if (this.isLink()) {
            stringBuffer.append('l');
        } else {
            stringBuffer.append('-');
        }
        if ((this.permissions & 256) != 0) {
            stringBuffer.append('r');
        } else {
            stringBuffer.append('-');
        }
        if ((this.permissions & 128) != 0) {
            stringBuffer.append('w');
        } else {
            stringBuffer.append('-');
        }
        if ((this.permissions & 2048) != 0) {
            stringBuffer.append('s');
        } else if ((this.permissions & 64) != 0) {
            stringBuffer.append('x');
        } else {
            stringBuffer.append('-');
        }
        if ((this.permissions & 32) != 0) {
            stringBuffer.append('r');
        } else {
            stringBuffer.append('-');
        }
        if ((this.permissions & 16) != 0) {
            stringBuffer.append('w');
        } else {
            stringBuffer.append('-');
        }
        if ((this.permissions & 1024) != 0) {
            stringBuffer.append('s');
        } else if ((this.permissions & 8) != 0) {
            stringBuffer.append('x');
        } else {
            stringBuffer.append('-');
        }
        if ((this.permissions & 4) != 0) {
            stringBuffer.append('r');
        } else {
            stringBuffer.append('-');
        }
        if ((this.permissions & 2) != 0) {
            stringBuffer.append('w');
        } else {
            stringBuffer.append('-');
        }
        if ((this.permissions & 1) != 0) {
            stringBuffer.append('x');
        } else {
            stringBuffer.append('-');
        }
        return stringBuffer.toString();
    }

    public long getSize() {
        return this.size;
    }

    public int getUId() {
        return this.uid;
    }

    public boolean isBlk() {
        return this.isType(24576);
    }

    public boolean isChr() {
        return this.isType(8192);
    }

    public boolean isDir() {
        return this.isType(16384);
    }

    public boolean isFifo() {
        return this.isType(4096);
    }

    public boolean isLink() {
        return this.isType(40960);
    }

    public boolean isReg() {
        return this.isType(32768);
    }

    public boolean isSock() {
        return this.isType(49152);
    }

    int length() {
        int n = (this.flags & 1) != 0 ? 12 : 4;
        int n2 = n;
        if ((this.flags & 2) != 0) {
            n2 = n + 8;
        }
        n = n2;
        if ((this.flags & 4) != 0) {
            n = n2 + 4;
        }
        n2 = n;
        if ((this.flags & 8) != 0) {
            n2 = n + 8;
        }
        n = n2;
        if ((this.flags & Integer.MIN_VALUE) != 0) {
            int n3 = this.extended.length / 2;
            n = n2 += 4;
            if (n3 > 0) {
                int n4 = 0;
                do {
                    n = n2;
                    if (n4 >= n3) break;
                    String[] arrstring = this.extended;
                    n = n4 * 2;
                    n2 = n2 + 4 + arrstring[n].length() + 4 + this.extended[n + 1].length();
                    ++n4;
                } while (true);
            }
        }
        return n;
    }

    public void setACMODTIME(int n, int n2) {
        this.flags |= 8;
        this.atime = n;
        this.mtime = n2;
    }

    void setFLAGS(int n) {
        this.flags = n;
    }

    public void setPERMISSIONS(int n) {
        this.flags |= 4;
        this.permissions = n & 4095 | this.permissions & -4096;
    }

    public void setSIZE(long l) {
        this.flags |= 1;
        this.size = l;
    }

    public void setUIDGID(int n, int n2) {
        this.flags |= 2;
        this.uid = n;
        this.gid = n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getPermissionsString());
        stringBuilder.append(" ");
        stringBuilder.append(this.getUId());
        stringBuilder.append(" ");
        stringBuilder.append(this.getGId());
        stringBuilder.append(" ");
        stringBuilder.append(this.getSize());
        stringBuilder.append(" ");
        stringBuilder.append(this.getMtimeString());
        return stringBuilder.toString();
    }
}
