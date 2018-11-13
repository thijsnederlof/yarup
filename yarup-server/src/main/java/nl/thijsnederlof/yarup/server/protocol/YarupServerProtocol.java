package nl.thijsnederlof.yarup.server.protocol;

import lombok.Getter;
import nl.thijsnederlof.yarup.server.protocol.config.YarupServerProtocolConfig;
import nl.thijsnederlof.yarup.server.protocol.connection.ClientManager;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;
import nl.thijsnederlof.yarup.server.protocol.message.MessageFactory;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.MessagePipeline;

public class YarupServerProtocol {

    @Getter
    private final ClientManager clientManager;

    private final MessagePipeline pipeline;

    private final byte[] protocolId;

    public YarupServerProtocol(final YarupServerProtocolConfig config) {

        this.protocolId = config.getProtocolId().getBytes();

        final MessageFactory messageFactory = new MessageFactory(
                config.getSendHandler(),
                this.protocolId,
                config.getLogger()
        );

        this.clientManager = new ClientManager(
                config.getMaxConnectionAmount(),
                config.getTimeoutThreshold(),
                config.getHeartbeatThreshold(),
                config.getDisconnectHandler(),
                messageFactory,
                config.getTimeoutHandler(),
                config.getLogger()
        );

        this.pipeline = new MessagePipeline(
                config.getLogger(),
                clientManager,
                config.getConnectHandler(),
                messageFactory,
                config.getMessageHandler(),
                config.getDisconnectHandler()
        );

        config.getLogger().debug("YarupProtocol initialization done");
    }

    public void receiveMessage(final String host, final int port, final byte[] message) {
        this.pipeline.processMessage(host, port, new Buffer(protocolId, message));
    }
}