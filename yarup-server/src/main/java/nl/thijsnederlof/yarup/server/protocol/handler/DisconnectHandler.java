package nl.thijsnederlof.yarup.server.protocol.handler;

import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;

@FunctionalInterface
public interface DisconnectHandler {

    void handleDisconnect(final Client client);
}
