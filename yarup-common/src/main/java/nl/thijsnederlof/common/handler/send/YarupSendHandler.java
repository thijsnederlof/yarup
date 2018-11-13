package nl.thijsnederlof.common.handler.send;

@FunctionalInterface
public interface YarupSendHandler {

    void sendMessage(final String host, final int port, final byte[] message);
}
