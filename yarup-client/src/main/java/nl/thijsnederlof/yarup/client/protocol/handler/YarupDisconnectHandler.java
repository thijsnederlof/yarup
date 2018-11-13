package nl.thijsnederlof.yarup.client.protocol.handler;

import nl.thijsnederlof.yarup.client.protocol.connection.model.Server;

public interface YarupDisconnectHandler {

    void handleDisconnect(final Server server);
}
