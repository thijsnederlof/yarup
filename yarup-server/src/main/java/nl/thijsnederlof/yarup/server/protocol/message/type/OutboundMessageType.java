package nl.thijsnederlof.yarup.server.protocol.message.type;

import lombok.Getter;

public enum OutboundMessageType {

    CONNECTION_ACCEPT((byte) 0),

    CONNECTION_REJECT_MESSAGE((byte) 10),
    CONNECTION_REJECT_FULL((byte) 11),

    HEARTBEAT((byte) 20),

    MESSAGE((byte) 30),

    DISCONNECT((byte) 40),

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