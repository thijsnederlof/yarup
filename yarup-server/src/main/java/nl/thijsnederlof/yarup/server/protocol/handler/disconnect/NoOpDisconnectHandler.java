package nl.thijsnederlof.yarup.server.protocol.handler.disconnect;

import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;

public class NoOpDisconnectHandler implements YarupDisconnectHandler {

    @Override
    public void handleDisconnect(Client client) {
        // Do nothing on disconnect
    }
}
