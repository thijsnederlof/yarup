package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common;

import nl.thijsnederlof.yarup.server.protocol.message.Buffer;

public interface MessageValidator {

    boolean validate(final String host, final int port, final Buffer buffer);
}
