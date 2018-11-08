package nl.thijsnederlof.common.util;

import static nl.thijsnederlof.common.util.Bytes.toInt;

public class CRC32 {

    // TODO: Replace with actual CRC32 logic check
    // Validates that the first 4 bytes are equal to the supplied int protocolId
    public static boolean validate(final int protocolId, final byte[] bytes) {
        return protocolId == toInt(bytes, 0);
    }
}
