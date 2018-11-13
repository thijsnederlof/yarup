package nl.thijsnederlof.yarup.server.protocol.handler.connect;

import nl.thijsnederlof.yarup.server.protocol.connection.model.AcceptConnectionResult;

public class AlwaysAcceptHandler implements YarupConnectHandler {

    @Override
    public AcceptConnectionResult acceptConnection(final String host, final int port, final byte[] message) {
        return new AcceptConnectionResult(true, null);
    }
}
