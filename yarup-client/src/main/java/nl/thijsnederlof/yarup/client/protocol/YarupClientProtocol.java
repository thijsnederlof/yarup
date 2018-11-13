package nl.thijsnederlof.yarup.client.protocol;

import lombok.Getter;
import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.client.protocol.config.YarupClientProtocolConfig;
import nl.thijsnederlof.yarup.client.protocol.connection.ConnectionManager;
import nl.thijsnederlof.yarup.client.protocol.message.MessageFactory;
import nl.thijsnederlof.yarup.client.protocol.message.MessagePipeline;

import static java.lang.String.format;
import static nl.thijsnederlof.yarup.client.protocol.message.Buffer.buffer;

public class YarupClientProtocol {

    private final byte[] protocolId;

    private final MessageFactory messageFactory;

    @Getter
    private final ConnectionManager connectionManager;

    private final AbstractYarupLogger log;

    private final MessagePipeline messagePipeline;

    public YarupClientProtocol(final YarupClientProtocolConfig config) {
        this.protocolId = config.getProtocolId().getBytes();
        this.log = config.getLogger();

        this.messageFactory = new MessageFactory(
                config.getSendHandler(),
                this.protocolId,
                log
        );

        this.connectionManager = new ConnectionManager(
                this.messageFactory,
                config.getTimeoutThreshold(),
                config.getTimeoutHandler(),
                log
        );

        this.messagePipeline = new MessagePipeline(
                log,
                this.connectionManager,
                config.getConnectionAcceptHandler(),
                config.getDisconnectHandler(),
                this.messageFactory,
                config.getMessageHandler(),
                config.getConnectionRejectHandler()
        );
    }

    public void receiveMessage(final String host, final int port, final byte[] message) {
        this.messagePipeline.processMessage(host, port, buffer(protocolId, message));
    }

    public void connectToServer(final String host, final int port, final byte[] payload) {
        log.debug(format("Sending connection request to %s:%d", host, port));
        connectionManager.connect(host, port, payload);
    }

    public void connectToServer(final String host, final int port) {
        log.debug(format("Sending connection to %s:%d", host, port));
        messageFactory.sendConnectionRequest(host, port);
    }
}
