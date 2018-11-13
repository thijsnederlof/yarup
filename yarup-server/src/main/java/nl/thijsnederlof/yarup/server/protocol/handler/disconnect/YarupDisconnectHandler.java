package nl.thijsnederlof.yarup.server.protocol.handler.disconnect;

import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;

@FunctionalInterface
public interface YarupDisconnectHandler {

    void handleDisconnect(final Client client);
}
