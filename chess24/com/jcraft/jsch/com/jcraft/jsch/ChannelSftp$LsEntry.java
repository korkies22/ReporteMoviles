/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;

public class ChannelSftp.LsEntry
implements Comparable {
    private SftpATTRS attrs;
    private String filename;
    private String longname;

    ChannelSftp.LsEntry(String string, String string2, SftpATTRS sftpATTRS) {
        this.setFilename(string);
        this.setLongname(string2);
        this.setAttrs(sftpATTRS);
    }

    public int compareTo(Object object) throws ClassCastException {
        if (object instanceof ChannelSftp.LsEntry) {
            return this.filename.compareTo(((ChannelSftp.LsEntry)object).getFilename());
        }
        throw new ClassCastException("a decendent of LsEntry must be given.");
    }

    public SftpATTRS getAttrs() {
        return this.attrs;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getLongname() {
        return this.longname;
    }

    void setAttrs(SftpATTRS sftpATTRS) {
        this.attrs = sftpATTRS;
    }

    void setFilename(String string) {
        this.filename = string;
    }

    void setLongname(String string) {
        this.longname = string;
    }

    public String toString() {
        return this.longname;
    }
}
