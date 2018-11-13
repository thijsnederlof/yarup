package nl.thijsnederlof.common.util;

import java.util.Arrays;

public class CRC32 {

    // TODO: Replace with actual CRC32 logic check
    // Validates that the first 4 bytes are equal to the supplied protocolId
    public static boolean validate(final byte[] protocolId, final byte[] bytes) {
        return Arrays.equals(protocolId, bytes);
    }
}
