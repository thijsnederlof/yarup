package nl.thijsnederlof.yarup.client.protocol.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Arrays.copyOfRange;
import static nl.thijsnederlof.common.util.CRC32.validate;

@RequiredArgsConstructor
public class Buffer {

    private final byte[] protocolId;

    private final byte[] message;

    public static Buffer buffer(final byte[] protocolIdLength, final byte[] bytes) {
        return new Buffer(protocolIdLength, bytes);
    }

    public byte[] getProtcolId() {
        return copyOfRange(message, 0, protocolId.length);
    }

    public InboundMessageType getMessageTpe() {
        return InboundMessageType.getMessageType(message[protocolId.length]);
    }

    public byte messageByte() {
        return message[protocolId.length];
    }

    public byte[] getPayload() {
        return copyOfRange(message, protocolId.length + 1, message.length);
    }

    public boolean validateCRC32Checksum() {
        return validate(protocolId, copyOfRange(message, 0, protocolId.length));
    }
}
