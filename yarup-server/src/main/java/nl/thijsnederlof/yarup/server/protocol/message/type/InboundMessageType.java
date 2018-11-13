package nl.thijsnederlof.yarup.server.protocol.message.type;

public enum InboundMessageType {

    CONNECT_REQUEST((byte) 0),

    DISCONNECT((byte) 10),

    HEARTBEAT((byte) 20),

    MESSAGE((byte) 30),

    UNKNOWN_REQUEST((byte) 128);

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