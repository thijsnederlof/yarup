package nl.thijsnederlof.common.util;

import lombok.NoArgsConstructor;

import static java.lang.System.arraycopy;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Bytes {

    public static int toInt(byte[] bytes, int offset) {
        int ret = 0;

        for (int i = 0; i < 4 && i + offset < bytes.length; i++) {
            ret <<= 8;
            ret |= (int) bytes[i] & 0xFF;
        }

        return ret;
    }

    public static byte[] emptyByteArray() {
        return new byte[]{};
    }

    public static byte[] concat(final byte[]... arrays) {
        int size = 0;
        for (final byte[] array : arrays) {
            size += array.length;
        }

        final byte[] result = new byte[size];

        int cursor = 0;
        for (final byte[] array : arrays) {
            arraycopy(array, 0, result, cursor, array.length);
            cursor = array.length;
        }

        return result;
    }

    public static byte[] concatBytes(final byte[] array, final byte... bytes) {
        return concat(array, bytes);
    }
}
