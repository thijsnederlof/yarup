package nl.thijsnederlof.yarup.client.protocol.message;

import lombok.Getter;

public enum InboundMessageType {

    CONNECTION_ACCEPT((byte) 0),

    CONNECTION_REJECT_MESSAGE((byte) 10),
    CONNECTION_REJECT_FULL((byte) 11),

    HEARTBEAT((byte) 20),

    MESSAGE((byte) 30),

    DISCONNECT((byte) 40),

    UNKNOWN_REQUEST((byte) 128);

    @Getter
    private final byte indicator;

    public static InboundMessageType getMessageType(final byte value) {
        for (final InboundMessageType messageType : InboundMessageType.values()) {
            if (messageType.indicator == value) {
                return messageType;
            }
        }

        return InboundMessageType.UNKNOWN_REQUEST;
    }

    InboundMessageType(final byte indicator) {
        this.indicator = indicator;
    }
}
