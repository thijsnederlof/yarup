package nl.thijsnederlof.yarup.server.protocol.connection;

import lombok.RequiredArgsConstructor;
import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.common.util.MultiKeyMap;
import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;
import nl.thijsnederlof.yarup.server.protocol.handler.timeout.YarupTimeoutHandler;
import nl.thijsnederlof.yarup.server.protocol.message.MessageFactory;

import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

@RequiredArgsConstructor
public class TimeoutMonitor {

    private final AbstractYarupLogger log;

    private final ReentrantReadWriteLock clientLock;

    private final MessageFactory messageFactory;

    private final YarupTimeoutHandler timeoutHandler;

    private final ClientManager clientManager;

    void run(final long heartbeatThreshold, final long timeoutThreshold, final MultiKeyMap<String, Long, Client> clients) {
        log.trace("Running timeout monitor");

        clientLock.readLock().lock();
        final Set<MultiKeyMap.Entry<String, Long, Client>> entries = clients.entrySet();
        clientLock.readLock().unlock();

        for(final MultiKeyMap.Entry<String, Long, Client> entry : entries) {

            if(currentTimeMillis() - entry.getValue().getConnectionDetails().getLastMessageReceivedTimestamp() > heartbeatThreshold) {
                this.messageFactory.sendHeartbeat(entry.getValue());
                log.debug(format("Client %s exceeded heartbeat threshold", entry.getValue().getConnectionDetails().getConnectionKey()));
            }

            if(currentTimeMillis() - entry.getValue().getConnectionDetails().getLastMessageReceivedTimestamp() > timeoutThreshold) {
                this.timeoutHandler.handleTimeout(entry.getValue());
                clientManager.disconnectByConnectionId(entry.getK2());
                log.info(format("Client %s timed out", entry.getValue().getConnectionDetails().getConnectionKey()));
            }
        }
    }
}
