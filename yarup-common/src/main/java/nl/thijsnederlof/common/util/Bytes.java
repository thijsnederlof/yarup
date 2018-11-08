package nl.thijsnederlof.common.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Bytes {

    public static int toInt(byte[] bytes, int offset) {
        int ret = 0;

        for (int i=0; i<4 && i+offset<bytes.length; i++) {
            ret <<= 8;
            ret |= (int)bytes[i] & 0xFF;
        }

        return ret;
    }
}
