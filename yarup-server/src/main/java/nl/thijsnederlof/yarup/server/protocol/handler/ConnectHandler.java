package nl.thijsnederlof.yarup.server.protocol.handler;

import nl.thijsnederlof.yarup.server.protocol.connection.model.AcceptConnectionResult;

@FunctionalInterface
public interface ConnectHandler {

    AcceptConnectionResult acceptConnection(final String host, final int port, final byte[] message);
}
