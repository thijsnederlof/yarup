package nl.thijsnederlof.yarup.server.protocol.handler;

@FunctionalInterface
public interface SendHandler {

    void sendMessage(final String host, final int port, final byte[] message);
}
