package nl.thijsnederlof.yarup.server.protocol.connection.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.thijsnederlof.common.connection.ConnectionDetails;
import nl.thijsnederlof.yarup.server.protocol.message.MessageFactory;

@EqualsAndHashCode
public class Client {

    @Getter
    private final long id;

    @Getter
    private final ConnectionDetails connectionDetails;

    private final MessageFactory messageFactory;

    public Client(final MessageFactory messageFactory, final String host, final int port, final long id) {
        this.messageFactory = messageFactory;
        this.id = id;
        this.connectionDetails = new ConnectionDetails(host, port);
    }

    public void sendMessage(final byte[] message) {
        messageFactory.sendMessage(this, message);
    }
}