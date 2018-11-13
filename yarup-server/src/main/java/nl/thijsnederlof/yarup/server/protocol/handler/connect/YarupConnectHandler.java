package nl.thijsnederlof.yarup.server.protocol.handler.connect;

import nl.thijsnederlof.yarup.server.protocol.connection.model.AcceptConnectionResult;

@FunctionalInterface
public interface YarupConnectHandler {

    AcceptConnectionResult acceptConnection(final String host, final int port, final byte[] message);
}
