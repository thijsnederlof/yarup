package nl.thijsnederlof.yarup.server.protocol.handler.message;

import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;

@FunctionalInterface
public interface YarupMessageHandler {

    void handleMessage(final Client client, final byte[] message);
}
