package nl.thijsnederlof.yarup.client.protocol.connection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.client.protocol.message.MessageFactory;
import nl.thijsnederlof.yarup.client.protocol.connection.model.Server;
import nl.thijsnederlof.yarup.client.protocol.handler.YarupTimeoutHandler;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

@RequiredArgsConstructor
public class ConnectionManager {

    private final MessageFactory messageFactory;

    private final long timeoutThreshold;

    private final YarupTimeoutHandler timeoutHandler;

    private final AbstractYarupLogger log;

    @Getter
    private Server server;

    @Getter
    private boolean connected = false;

    public void runTimeoutMonitor() {
        if(!connected) {
            return;
        }

        log.trace("Running timeout monitor");
        if (currentTimeMillis() - server.getConnectionDetails().getLastMessageReceivedTimestamp() > timeoutThreshold) {
            this.timeoutHandler.handleTimeout(server);
            this.connected = false;
        }
    }

    public void acceptConnection(final String host, final int port) {
        this.server = new Server(this.messageFactory, host, port);
        this.connected = true;
        log.info(format("Connection established with server at %s", server.getConnectionDetails().getConnectionKey()));
    }

    public void disconnect() {
        this.connected = false;
        log.info(format("Connection with server %s terminated", server.getConnectionDetails().getConnectionKey()));
    }

    public void connect(final String host, final int port, final byte[] message) {
        if(port <= 0) {
            throw new IllegalStateException("Port number must be higher than 0");
        }

        this.messageFactory.sendConnectionRequest(host, port, message);
        log.info(format("Connection request to server at %s:%d send", host, port));
    }

    public void connect(final String host, final int port) {
        if(port <= 0) {
            throw new IllegalStateException("Port number must be higher than 0");
        }

        this.messageFactory.sendConnectionRequest(host, port);
        log.info(format("Connection request to server at %s:%d send", host, port));
    }
}
