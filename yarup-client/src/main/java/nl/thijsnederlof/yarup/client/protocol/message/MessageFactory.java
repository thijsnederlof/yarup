package nl.thijsnederlof.yarup.client.protocol.message;

import lombok.RequiredArgsConstructor;
import nl.thijsnederlof.common.handler.send.YarupSendHandler;
import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.client.protocol.connection.model.Server;

import static java.lang.String.format;
import static nl.thijsnederlof.common.util.Bytes.concat;
import static nl.thijsnederlof.common.util.Bytes.concatBytes;
import static nl.thijsnederlof.yarup.client.protocol.message.OutboundMessageType.CONNECTION_REQUEST;
import static nl.thijsnederlof.yarup.client.protocol.message.OutboundMessageType.HEARTBEAT;
import static nl.thijsnederlof.yarup.client.protocol.message.OutboundMessageType.MESSAGE;

@RequiredArgsConstructor
public class MessageFactory {

    private final YarupSendHandler sendHandler;

    private final byte[] protocolId;

    private final AbstractYarupLogger log;

    public void sendConnectionRequest(final String host, final int port, final byte[] message) {
        final byte[] header = concatBytes(protocolId, CONNECTION_REQUEST.getIndicator());

        this.sendHandler.sendMessage(host, port, concat(header, message));
        log.debug(format("Connection request with payload send to %s:%d", host, port));
    }

    public void sendConnectionRequest(final String host, final int port) {
        final byte[] header = concatBytes(protocolId, CONNECTION_REQUEST.getIndicator());

        this.sendHandler.sendMessage(host, port, header);
        log.debug(format("Connection request send to %s:%d", host, port));
    }

    public void sendMessage(final Server server, final byte[] message) {
        final byte[] header = concatBytes(protocolId, MESSAGE.getIndicator());

        this.sendHandler.sendMessage(server.getConnectionDetails().getHost(), server.getConnectionDetails().getPort(), concat(header, message));
        log.debug(format("Message send to server at %s", server.getConnectionDetails().getConnectionKey()));
    }

    public void sendHeartbeat(final Server server) {
        final byte[] header = concatBytes(protocolId, HEARTBEAT.getIndicator());

        this.sendHandler.sendMessage(server.getConnectionDetails().getHost(), server.getConnectionDetails().getPort(), header);
        log.debug(format("Heartbeat response send to %s", server.getConnectionDetails().getConnectionKey()));
    }
}
