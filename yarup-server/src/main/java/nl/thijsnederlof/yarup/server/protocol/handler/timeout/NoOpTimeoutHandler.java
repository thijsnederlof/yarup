package nl.thijsnederlof.yarup.server.protocol.handler.timeout;

import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;

public class NoOpTimeoutHandler implements YarupTimeoutHandler {

    @Override
    public void handleTimeout(Client client) {
        // Do nothing on timeout
    }
}
