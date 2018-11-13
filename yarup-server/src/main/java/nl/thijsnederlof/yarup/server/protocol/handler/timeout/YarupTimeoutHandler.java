package nl.thijsnederlof.yarup.server.protocol.handler.timeout;

import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;

public interface YarupTimeoutHandler {

    void handleTimeout(final Client client);
}
