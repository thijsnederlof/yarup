package nl.thijsnederlof.yarup.client.protocol.message;

import lombok.Getter;

public enum OutboundMessageType {

    CONNECTION_REQUEST((byte) 0),

    DISCONNECT((byte) 10),

    HEARTBEAT((byte) 20),

    MESSAGE((byte) 30),

    UNKNOWN_REQUEST((byte) 128);

    @Getter
    private final byte indicator;

    public static OutboundMessageType getMessageType(final byte value) {
        for (final OutboundMessageType messageType : OutboundMessageType.values()) {
            if (messageType.indicator == value) {
                return messageType;
            }
        }

        return OutboundMessageType.UNKNOWN_REQUEST;
    }

    OutboundMessageType(final byte indicator) {
        this.indicator = indicator;
    }
}
