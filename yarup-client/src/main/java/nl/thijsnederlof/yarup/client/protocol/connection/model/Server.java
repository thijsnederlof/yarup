package nl.thijsnederlof.yarup.client.protocol.connection.model;

import lombok.Getter;
import nl.thijsnederlof.common.connection.ConnectionDetails;
import nl.thijsnederlof.yarup.client.protocol.message.MessageFactory;

public class Server {

    @Getter
    private final ConnectionDetails connectionDetails;

    private final MessageFactory messageFactory;

    public Server(final MessageFactory messageFactory, final String host, final int port) {
        this.messageFactory = messageFactory;
        this.connectionDetails = new ConnectionDetails(host, port);
    }

    public void sendMessage(final byte[] message) {
        this.messageFactory.sendMessage(this, message);
    }

    public void reconnect(final byte[] message) {
        this.messageFactory.sendConnectionRequest(connectionDetails.getHost(), connectionDetails.getPort(), message);
    }
}
