package nl.thijsnederlof.yarup.client.protocol.handler;

import nl.thijsnederlof.yarup.client.protocol.connection.model.Server;

public interface YarupMessageHandler {

    void handleMessage(final Server server, final byte[] message);
}
