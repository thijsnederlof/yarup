package nl.thijsnederlof.yarup.client.protocol.message;

import lombok.RequiredArgsConstructor;
import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.client.protocol.connection.ConnectionManager;
import nl.thijsnederlof.yarup.client.protocol.handler.YarupConnectionAcceptHandler;
import nl.thijsnederlof.yarup.client.protocol.handler.YarupConnectionRejectHandler;
import nl.thijsnederlof.yarup.client.protocol.handler.YarupDisconnectHandler;
import nl.thijsnederlof.yarup.client.protocol.handler.YarupMessageHandler;

import static java.lang.String.format;
import static nl.thijsnederlof.common.util.Bytes.emptyByteArray;
import static nl.thijsnederlof.common.util.Connectionkey.parse;
import static nl.thijsnederlof.yarup.client.protocol.message.InboundMessageType.CONNECTION_ACCEPT;
import static nl.thijsnederlof.yarup.client.protocol.message.RejectReason.CUSTOM;
import static nl.thijsnederlof.yarup.client.protocol.message.RejectReason.CONNECTION_LIMIT_REACHED;

@RequiredArgsConstructor
public class MessagePipeline {

    private final AbstractYarupLogger log;

    private final ConnectionManager connectionManager;

    private final YarupConnectionAcceptHandler connectionAcceptHandler;

    private final YarupDisconnectHandler disconnectHandler;

    private final MessageFactory messageFactory;

    private final YarupMessageHandler messageHandler;

    private final YarupConnectionRejectHandler rejectHandler;

    public void processMessage(final String host, final int port, final Buffer buffer) {
        if (!buffer.validateCRC32Checksum()) {
            log.warn(format("Received message with invalid CRC32 check from %s:%d", host, port));
            return;
        }

        if (!this.connectionManager.isConnected() && !buffer.getMessageTpe().equals(CONNECTION_ACCEPT)) {
            log.debug(format("Received message from unconnected source %s:%d", host, port));
            return;
        }

        switch (buffer.getMessageTpe()) {
            case CONNECTION_ACCEPT:
                this.handleConnectAccept(host, port);
                return;
            case CONNECTION_REJECT_FULL:
                this.handConnectionRejectFull(host, port);
                return;
            case CONNECTION_REJECT_MESSAGE:
                this.handleConnectionReject(host, port, buffer);
                return;
        }

        if (!parse(host, port).equals(connectionManager.getServer().getConnectionDetails().getConnectionKey())) {
            log.warn(format("Received message with a valid CRC32 but from a different source than the server! %s:%d", host, port));
            return;
        }

        connectionManager.getServer().getConnectionDetails().updatelastMessageReceivedTimestamp();

        switch (buffer.getMessageTpe()) {
            case DISCONNECT:
                this.handleDisconnect(host, port);
                return;
            case HEARTBEAT:
                this.handleHeartbeat(host, port);
                return;
            case MESSAGE:
                this.handleMessage(host, port, buffer);
                return;
            case UNKNOWN_REQUEST:
                System.out.println("unknown request:" + buffer.messageByte());
                return;
            default:
                System.out.println("default:" + buffer.messageByte());
                log.warn("Received unknown message type!");
        }
    }

    private void handleMessage(final String host, final int port, final Buffer buffer) {
        this.messageHandler.handleMessage(this.connectionManager.getServer(), buffer.getPayload());
        log.debug(format("Processed message from %s:%d", host, port));
    }

    private void handleDisconnect(final String host, final int port) {
        this.disconnectHandler.handleDisconnect(this.connectionManager.getServer());
        this.connectionManager.disconnect();
        log.info(format("Disconnected from server %s:%d", host, port));
    }

    private void handleHeartbeat(final String host, final int port) {
        messageFactory.sendHeartbeat(this.connectionManager.getServer());
        log.debug(format("Received heartbeat check from %s:%d", host, port));
    }

    private void handConnectionRejectFull(final String host, final int port) {
        log.info(format("Server %s:%d rejected connection because it is not accepting new connections", host, port));
        this.rejectHandler.handleConnectionReject(CONNECTION_LIMIT_REACHED, emptyByteArray());
    }

    private void handleConnectionReject(final String host, final int port, final Buffer buffer) {
        this.rejectHandler.handleConnectionReject(CUSTOM, buffer.getPayload());
        log.info(format("Server %s:%d rejected connection with custom rule", host, port));
    }

    private void handleConnectAccept(final String host, final int port) {
        this.connectionManager.acceptConnection(host, port);
        this.connectionAcceptHandler.handleConnectionAccepted(this.connectionManager.getServer());
        log.debug(format("New connection accept connection from server %s:%d", host, port));
    }
}
