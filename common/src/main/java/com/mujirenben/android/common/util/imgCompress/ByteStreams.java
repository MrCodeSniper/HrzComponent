package com.mujirenben.android.common.util.imgCompress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.mujirenben.android.common.util.Preconditions.checkNotNull;

public class ByteStreams {

    private static final int BUF_SIZE = 0x1000; // 4K

    /**
     * Copies all bytes from the input stream to the output stream. Does not close or flush either
     * stream.
     *
     * @param from the input stream to read from
     * @param to the output stream to write to
     * @return the number of bytes copied
     * @throws IOException if an I/O error occurs
     */
    public static long copy(InputStream from, OutputStream to)
            throws IOException {
        checkNotNull(from);
        checkNotNull(to);
        byte[] buf = new byte[BUF_SIZE];
        long total = 0;
        while (true) {
            int r = from.read(buf);
            if (r == -1) {
                break;
            }
            to.write(buf, 0, r);
            total += r;
        }
        return total;
    }
}
