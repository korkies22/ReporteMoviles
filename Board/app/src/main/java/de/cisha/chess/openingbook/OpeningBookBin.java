// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.openingbook;

import java.util.ArrayList;
import java.util.List;
import de.cisha.chess.model.FEN;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.io.InputStream;

public class OpeningBookBin extends OpeningBook
{
    byte[] _bytes;
    
    public OpeningBookBin(final InputStream inputStream) {
        this.readStream(inputStream);
    }
    
    private BookMove[] getBookEntriesForKeyInternal(final long n) {
        final int n2 = 0;
        int n3 = this._bytes.length / 16;
        int n4 = -1;
        int n5 = 0;
        while (n5 != n3 && n4 == -1) {
            int n6 = (n5 + n3) / 2;
            final long keyAtIndex = this.getKeyAtIndex(n6);
            if ((n < keyAtIndex && n > 0L && keyAtIndex > 0L) || (n > 0L && keyAtIndex < 0L) || (n < 0L && keyAtIndex < 0L && n < keyAtIndex)) {
                n3 = n6;
            }
            else if ((n > keyAtIndex && n > 0L && keyAtIndex > 0L) || (n < 0L && keyAtIndex > 0L) || (n < 0L && keyAtIndex < 0L && n > keyAtIndex)) {
                if (n5 == n6) {
                    final int n7 = ++n6;
                    if (this.getKeyAtIndex(n3) == n) {
                        n4 = n3;
                        n6 = n7;
                    }
                }
                n5 = n6;
            }
            else {
                if (n != keyAtIndex) {
                    break;
                }
                n4 = n6;
            }
        }
        BookMove[] array2;
        if (n4 != -1) {
            int n8 = n4;
            int i;
            while (true) {
                i = n4;
                if (n8 <= 0) {
                    break;
                }
                i = n4;
                if (this.getKeyAtIndex(n8 - 1) != n) {
                    break;
                }
                --n8;
            }
            while (i < this._bytes.length / 16 - 1) {
                final int n9 = i + 1;
                if (this.getKeyAtIndex(n9) != n) {
                    break;
                }
                i = n9;
            }
            final int n10 = i - n8;
            final BookMove[] array = new BookMove[n10 + 1];
            int n11 = n2;
            while (true) {
                array2 = array;
                if (n11 > n10) {
                    break;
                }
                array[n11] = new BookMoveBin(this.getBytes((n11 + n8) * 16 + 8, 8));
                ++n11;
            }
        }
        else {
            array2 = new BookMove[0];
        }
        return array2;
    }
    
    private byte[] getBytes(final int n, final int n2) {
        return Arrays.copyOfRange(this._bytes, n, n2 + n);
    }
    
    private long getKeyAtIndex(final int n) {
        return this.longFromByte(this.getBytes(n * 16, 8));
    }
    
    private long longFromByte(final byte[] array) {
        if (array.length == 8) {
            final ByteBuffer allocate = ByteBuffer.allocate(8);
            allocate.put(array);
            allocate.flip();
            return allocate.getLong();
        }
        return 0L;
    }
    
    private void readStream(final InputStream p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: new             Ljava/io/ByteArrayOutputStream;
        //     3: dup            
        //     4: invokespecial   java/io/ByteArrayOutputStream.<init>:()V
        //     7: astore_2       
        //     8: new             Ljava/io/BufferedInputStream;
        //    11: dup            
        //    12: aload_1        
        //    13: invokespecial   java/io/BufferedInputStream.<init>:(Ljava/io/InputStream;)V
        //    16: astore_1       
        //    17: aload_1        
        //    18: invokevirtual   java/io/BufferedInputStream.available:()I
        //    21: ifle            43
        //    24: bipush          16
        //    26: newarray        B
        //    28: astore_3       
        //    29: aload_1        
        //    30: aload_3        
        //    31: invokevirtual   java/io/BufferedInputStream.read:([B)I
        //    34: pop            
        //    35: aload_2        
        //    36: aload_3        
        //    37: invokevirtual   java/io/ByteArrayOutputStream.write:([B)V
        //    40: goto            17
        //    43: aload_1        
        //    44: invokevirtual   java/io/BufferedInputStream.close:()V
        //    47: goto            75
        //    50: astore_2       
        //    51: goto            84
        //    54: astore_3       
        //    55: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //    58: ldc             Lde/cisha/chess/openingbook/OpeningBookBin;.class
        //    60: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //    63: ldc             Ljava/io/IOException;.class
        //    65: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //    68: aload_3        
        //    69: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
        //    72: goto            43
        //    75: aload_0        
        //    76: aload_2        
        //    77: invokevirtual   java/io/ByteArrayOutputStream.toByteArray:()[B
        //    80: putfield        de/cisha/chess/openingbook/OpeningBookBin._bytes:[B
        //    83: return         
        //    84: aload_1        
        //    85: invokevirtual   java/io/BufferedInputStream.close:()V
        //    88: aload_2        
        //    89: athrow         
        //    90: astore_1       
        //    91: goto            75
        //    94: astore_1       
        //    95: goto            88
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  17     40     54     75     Ljava/io/IOException;
        //  17     40     50     90     Any
        //  43     47     90     94     Ljava/io/IOException;
        //  55     72     50     90     Any
        //  84     88     94     98     Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 50, Size: 50
        //     at java.util.ArrayList.rangeCheck(Unknown Source)
        //     at java.util.ArrayList.get(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3303)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public List<BookMove> getBookEntriesForFEN(final FEN fen) {
        final BookMove[] bookEntriesForKeyInternal = this.getBookEntriesForKeyInternal(PolyglotBinBookHelper.getKeyFromFEN(fen));
        int i = 0;
        final ArrayList list = new ArrayList<BookMove>(bookEntriesForKeyInternal.length);
        while (i < bookEntriesForKeyInternal.length) {
            list.add(bookEntriesForKeyInternal[i]);
            ++i;
        }
        return (List<BookMove>)list;
    }
}
