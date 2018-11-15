/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Proxy;
import com.jcraft.jsch.SocketFactory;
import com.jcraft.jsch.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxySOCKS5
implements Proxy {
    private static int DEFAULTPORT = 1080;
    private InputStream in;
    private OutputStream out;
    private String passwd;
    private String proxy_host;
    private int proxy_port;
    private Socket socket;
    private String user;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ProxySOCKS5(String string) {
        String string2;
        int n;
        block6 : {
            int n2;
            n = n2 = DEFAULTPORT;
            string2 = string;
            if (string.indexOf(58) != -1) {
                try {
                    string2 = string.substring(0, string.indexOf(58));
                }
                catch (Exception exception) {
                    n = n2;
                    string2 = string;
                    break block6;
                }
                try {
                    n2 = n = Integer.parseInt(string.substring(string.indexOf(58) + 1));
                }
                catch (Exception exception) {}
                n = n2;
            }
        }
        this.proxy_host = string2;
        this.proxy_port = n;
    }

    public ProxySOCKS5(String string, int n) {
        this.proxy_host = string;
        this.proxy_port = n;
    }

    private void fill(InputStream inputStream, byte[] arrby, int n) throws JSchException, IOException {
        int n2;
        for (int i = 0; i < n; i += n2) {
            n2 = inputStream.read(arrby, i, n - i);
            if (n2 > 0) continue;
            throw new JSchException("ProxySOCKS5: stream is closed");
        }
    }

    public static int getDefaultPort() {
        return DEFAULTPORT;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        try {
            if (this.in != null) {
                this.in.close();
            }
            if (this.out != null) {
                this.out.close();
            }
            if (this.socket != null) {
                this.socket.close();
            }
        }
        catch (Exception exception) {}
        this.in = null;
        this.out = null;
        this.socket = null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void connect(SocketFactory var1_1, String var2_5, int var3_8, int var4_9) throws JSchException {
        block29 : {
            block30 : {
                block32 : {
                    if (var1_1 != null) ** GOTO lbl7
                    this.socket = Util.createSocket(this.proxy_host, this.proxy_port, var4_9);
                    this.in = this.socket.getInputStream();
                    this.out = this.socket.getOutputStream();
                    break block32;
lbl7: // 1 sources:
                    this.socket = var1_1.createSocket(this.proxy_host, this.proxy_port);
                    this.in = var1_1.getInputStream(this.socket);
                    this.out = var1_1.getOutputStream(this.socket);
                }
                if (var4_9 > 0) {
                    this.socket.setSoTimeout(var4_9);
                }
                this.socket.setTcpNoDelay(true);
                var1_1 = new byte[1024];
                var1_1[0] = 5;
                var1_1[1] = 2;
                var1_1[2] = 0;
                var1_1[3] = 2;
                this.out.write(var1_1, 0, 4);
                this.fill(this.in, var1_1, 2);
                var4_9 = var1_1[1] & 255;
                if (var4_9 == 0) ** GOTO lbl-1000
                if (var4_9 != 2) ** GOTO lbl-1000
                if (this.user == null || this.passwd == null) ** GOTO lbl-1000
                var1_1[0] = 1;
                var1_1[1] = (byte)this.user.length();
                System.arraycopy(Util.str2byte(this.user), 0, var1_1, 2, this.user.length());
                var5_10 = this.user.length() + 2;
                var4_9 = var5_10 + 1;
                var1_1[var5_10] = (byte)this.passwd.length();
                System.arraycopy(Util.str2byte(this.passwd), 0, var1_1, var4_9, this.passwd.length());
                var5_10 = this.passwd.length();
                this.out.write(var1_1, 0, var4_9 + var5_10);
                this.fill(this.in, var1_1, 2);
                var4_9 = var1_1[1];
                if (var4_9 != 0) lbl-1000: // 3 sources:
                {
                    var4_9 = 0;
                } else lbl-1000: // 2 sources:
                {
                    var4_9 = 1;
                }
                if (var4_9 != 0) ** GOTO lbl50
                this.socket.close();
                ** GOTO lbl73
                {
                    catch (Exception var1_4) {}
                }
lbl50: // 1 sources:
                var1_1[0] = 5;
                var1_1[1] = 1;
                var1_1[2] = 0;
                var2_5 = Util.str2byte((String)var2_5);
                var4_9 = ((CharSequence)var2_5).length;
                var1_1[3] = 3;
                var1_1[4] = (byte)var4_9;
                System.arraycopy(var2_5, 0, var1_1, 5, var4_9);
                var4_9 = 5 + var4_9;
                var5_10 = var4_9 + 1;
                var1_1[var4_9] = (byte)(var3_8 >>> 8);
                var1_1[var5_10] = (byte)(var3_8 & 255);
                this.out.write(var1_1, 0, var5_10 + 1);
                this.fill(this.in, var1_1, 4);
                var3_8 = var1_1[1];
                if (var3_8 == 0) break block29;
                this.socket.close();
                break block30;
lbl73: // 2 sources:
                try {
                    throw new JSchException("fail in SOCKS5 proxy");
                }
                catch (Exception var1_2) {}
                catch (Exception var2_6) {}
            }
            var2_5 = new StringBuilder();
            var2_5.append("ProxySOCKS5: server returns ");
            var2_5.append(var1_1[1]);
            throw new JSchException(var2_5.toString());
            try {
                if (this.socket != null) {
                    this.socket.close();
                }
            }
            catch (Exception var2_7) {}
            var2_5 = new StringBuilder();
            var2_5.append("ProxySOCKS5: ");
            var2_5.append(var1_2.toString());
            var2_5 = var2_5.toString();
            if (var1_2 instanceof Throwable == false) throw new JSchException((String)var2_5);
            throw new JSchException((String)var2_5, var1_2);
            catch (RuntimeException var1_3) {
                throw var1_3;
            }
        }
        var3_8 = var1_1[3] & 255;
        if (var3_8 == 1) {
            this.fill(this.in, var1_1, 6);
            return;
        }
        switch (var3_8) {
            case 4: {
                this.fill(this.in, var1_1, 18);
                return;
            }
            case 3: {
                this.fill(this.in, var1_1, 1);
                this.fill(this.in, var1_1, (var1_1[0] & 255) + 2);
                return;
            }
        }
    }

    @Override
    public InputStream getInputStream() {
        return this.in;
    }

    @Override
    public OutputStream getOutputStream() {
        return this.out;
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    public void setUserPasswd(String string, String string2) {
        this.user = string;
        this.passwd = string2;
    }
}
