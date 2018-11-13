package nl.thijsnederlof.yarup.server.protocol.message;

import lombok.RequiredArgsConstructor;
import nl.thijsnederlof.common.handler.send.YarupSendHandler;
import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;

import java.util.Arrays;

import static java.lang.String.format;
import static nl.thijsnederlof.common.util.Bytes.concat;
import static nl.thijsnederlof.common.util.Bytes.concatBytes;
import static nl.thijsnederlof.yarup.server.protocol.message.type.OutboundMessageType.*;

@RequiredArgsConstructor
public class MessageFactory {

    private final YarupSendHandler sendHandler;

    private final byte[] protocolId;

    private final AbstractYarupLogger log;

    public void sendMessage(final Client client, final byte[] message) {
        final byte[] payload = concatBytes(protocolId, MESSAGE.getIndicator());

        sendHandler.sendMessage(client.getConnectionDetails().getHost(), client.getConnectionDetails().getPort(), concat(payload, message));
        log.debug(format("Message send to %s with payload %s", client.getConnectionDetails().getConnectionKey(), Arrays.toString(message)));
    }

    public void rejectConnectionCustom(final String host, final int port, final byte[] message) {
        final byte[] payload = concatBytes(protocolId, CONNECTION_REJECT_MESSAGE.getIndicator());

        sendHandler.sendMessage(host, port, concat(payload, message));
        log.debug(format("Connection reject message send to %s:%s with payload %s", host, port, Arrays.toString(message)));
    }

    public void rejectConnectionFull(final String host, final int port) {
        final byte[] payload = concatBytes(protocolId, CONNECTION_REJECT_FULL.getIndicator());

        sendHandler.sendMessage(host, port, payload);
        log.debug(format("Connection reject message send to %s:%s", host, port));
    }

    public void sendHeartbeat(final Client client) {
        final byte[] payload = concatBytes(protocolId, HEARTBEAT.getIndicator());

        sendHandler.sendMessage(client.getConnectionDetails().getHost(), client.getConnectionDetails().getPort(), payload);
        log.debug(format("Heartbeat message send to %s", client.getConnectionDetails().getConnectionKey()));
    }

    public void sendAcceptConnection(final Client client) {
        final byte[] payload = concatBytes(protocolId, CONNECTION_ACCEPT.getIndicator());

        sendHandler.sendMessage(client.getConnectionDetails().getHost(), client.getConnectionDetails().getPort(), payload);
        log.debug(format("Sending connect accept message send to %s", client.getConnectionDetails().getConnectionKey()));
    }
}
