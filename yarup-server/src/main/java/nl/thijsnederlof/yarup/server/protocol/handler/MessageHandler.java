package nl.thijsnederlof.yarup.server.protocol.handler;

import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;

@FunctionalInterface
public interface MessageHandler {

    void handleMessage(final Client client, final byte[] message);
}
