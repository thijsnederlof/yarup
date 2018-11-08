package nl.thijsnederlof.yarup.server.protocol.message;

import nl.thijsnederlof.yarup.server.protocol.handler.SendHandler;

public class MessageFactory {

    private final SendHandler sendHandler;

    public MessageFactory(final SendHandler sendHandler) {
        this.sendHandler = sendHandler;
    }

    public void sendMessage(final String host, final int port, final byte[] message) {
        sendHandler.sendMessage(host, port, message);
    }

    public void rejectConnectionCustom(final String host, final int port, final byte[] message) {
        sendHandler.sendMessage(host, port, message);
    }

    public void rejectConnectionFull(final String host, final int port) {
        sendHandler.sendMessage(host, port, new byte[] {});
    }
}
