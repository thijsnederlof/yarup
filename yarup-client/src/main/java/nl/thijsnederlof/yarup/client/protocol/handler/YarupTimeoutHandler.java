package nl.thijsnederlof.yarup.client.protocol.handler;

import nl.thijsnederlof.yarup.client.protocol.connection.model.Server;

public interface YarupTimeoutHandler {

    void handleTimeout(final Server server);
}
