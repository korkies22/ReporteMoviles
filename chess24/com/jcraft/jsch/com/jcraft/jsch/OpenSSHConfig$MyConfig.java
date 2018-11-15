/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ConfigRepository;
import com.jcraft.jsch.OpenSSHConfig;
import com.jcraft.jsch.Util;
import java.util.Hashtable;
import java.util.Vector;

class OpenSSHConfig.MyConfig
implements ConfigRepository.Config {
    private Vector _configs = new Vector();
    private String host;

    OpenSSHConfig.MyConfig(String string) {
        this.host = string;
        this._configs.addElement(OpenSSHConfig.this.config.get(""));
        byte[] arrby = Util.str2byte(string);
        if (OpenSSHConfig.this.hosts.size() > 1) {
            for (int i = 1; i < OpenSSHConfig.this.hosts.size(); ++i) {
                String[] arrstring = ((String)OpenSSHConfig.this.hosts.elementAt(i)).split("[ \t]");
                for (int j = 0; j < arrstring.length; ++j) {
                    boolean bl;
                    string = arrstring[j].trim();
                    if (string.startsWith("!")) {
                        string = string.substring(1).trim();
                        bl = true;
                    } else {
                        bl = false;
                    }
                    if (Util.glob(Util.str2byte(string), arrby)) {
                        if (bl) continue;
                        this._configs.addElement(OpenSSHConfig.this.config.get((String)OpenSSHConfig.this.hosts.elementAt(i)));
                        continue;
                    }
                    if (!bl) continue;
                    this._configs.addElement(OpenSSHConfig.this.config.get((String)OpenSSHConfig.this.hosts.elementAt(i)));
                }
            }
        }
    }

    private String find(String arrstring) {
        Object object = arrstring;
        if (keymap.get(arrstring) != null) {
            object = (String)keymap.get(arrstring);
        }
        String string = object.toUpperCase();
        arrstring = null;
        for (int i = 0; i < this._configs.size(); ++i) {
            Vector vector = (Vector)this._configs.elementAt(i);
            int n = 0;
            do {
                object = arrstring;
                if (n >= vector.size()) break;
                object = (String[])vector.elementAt(n);
                if (object[0].toUpperCase().equals(string)) {
                    object = object[1];
                    break;
                }
                ++n;
            } while (true);
            if (object != null) {
                return object;
            }
            arrstring = object;
        }
        return arrstring;
    }

    private String[] multiFind(String object) {
        String[] arrstring = object.toUpperCase();
        object = new Vector();
        for (int i = 0; i < this._configs.size(); ++i) {
            Vector vector = (Vector)this._configs.elementAt(i);
            for (int j = 0; j < vector.size(); ++j) {
                Object object2 = (String[])vector.elementAt(j);
                if (!object2[0].toUpperCase().equals(arrstring) || (object2 = object2[1]) == null) continue;
                object.remove(object2);
                object.addElement(object2);
            }
        }
        arrstring = new String[object.size()];
        object.toArray(arrstring);
        return arrstring;
    }

    @Override
    public String getHostname() {
        return this.find("Hostname");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int getPort() {
        String string = this.find("Port");
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }

    @Override
    public String getUser() {
        return this.find("User");
    }

    @Override
    public String getValue(String string) {
        if (!string.equals("compression.s2c") && !string.equals("compression.c2s")) {
            return this.find(string);
        }
        if ((string = this.find(string)) != null && !string.equals("no")) {
            return "zlib@openssh.com,zlib,none";
        }
        return "none,zlib@openssh.com,zlib";
    }

    @Override
    public String[] getValues(String string) {
        return this.multiFind(string);
    }
}
