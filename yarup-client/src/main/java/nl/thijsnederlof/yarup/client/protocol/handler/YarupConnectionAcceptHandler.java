package nl.thijsnederlof.yarup.client.protocol.handler;

import nl.thijsnederlof.yarup.client.protocol.connection.model.Server;

@FunctionalInterface
public interface YarupConnectionAcceptHandler {

    void handleConnectionAccepted(final Server server);
}
