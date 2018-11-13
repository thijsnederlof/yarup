package nl.thijsnederlof.yarup.server.protocol.connection;

import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.common.util.MultiKeyMap;
import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;
import nl.thijsnederlof.yarup.server.protocol.handler.disconnect.YarupDisconnectHandler;
import nl.thijsnederlof.yarup.server.protocol.handler.timeout.YarupTimeoutHandler;
import nl.thijsnederlof.yarup.server.protocol.message.MessageFactory;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.String.format;
import static nl.thijsnederlof.common.util.Connectionkey.parse;

public class ClientManager {

    private final ReentrantReadWriteLock clientsLock = new ReentrantReadWriteLock();

    private final MultiKeyMap<String, Long, Client> clients = new MultiKeyMap<>();

    private final AtomicLong clientCounter = new AtomicLong();

    private final long maxConnections;

    private final long timeoutThreshold;

    private final long heartbeatThreshold;

    private final YarupDisconnectHandler disconnectHandler;

    private final MessageFactory messageFactory;

    private final AbstractYarupLogger log;

    private final TimeoutMonitor timeoutMonitor;

    public ClientManager(final long maxConnections, final long timeoutThreshold,
                         final long heartbeatThreshold, final YarupDisconnectHandler disconnectHandler,
                         final MessageFactory messageFactory, final YarupTimeoutHandler timeoutHandler,
                         final AbstractYarupLogger log) {
        this.maxConnections = maxConnections;
        this.timeoutThreshold = timeoutThreshold;
        this.heartbeatThreshold = heartbeatThreshold;
        this.disconnectHandler = disconnectHandler;
        this.messageFactory = messageFactory;
        this.log = log;

        this.timeoutMonitor = new TimeoutMonitor(log, clientsLock, messageFactory, timeoutHandler, this);
    }

    public void runTimeoutMonitor() {
        if(clients.size() > 0) {
            this.timeoutMonitor.run(heartbeatThreshold, timeoutThreshold, clients);
        }
    }

    public Client getClientById(final long id) {
        return this.clients.getK2(id);
    }

    public Client getClientByConnectionkey(final String connectionkey) {
        return this.clients.getK1(connectionkey);
    }

    public boolean isConnected(final String host, final int port) {
        try  {
            clientsLock.readLock().lock();
            return clients.containsK1(parse(host, port));
        } finally {
            clientsLock.readLock().unlock();
        }
    }

    public boolean addNewConnection(final String host, final int port) {
        if(clients.size() < maxConnections) {
            try {
                clientsLock.writeLock().lock();
                long clientId = clientCounter.incrementAndGet();

                final Client client = clients.put(parse(host, port), clientId,
                        new Client(messageFactory, host, port, clientId));
                messageFactory.sendAcceptConnection(client);
                log.debug(format("Connection %s added to client manager", parse(host, port)));
                return true;
            } finally {
                clientsLock.writeLock().unlock();
            }
        }

        return false;
    }

    public void disconnectByConnectionkey(final String connectionkey) {
        clientsLock.writeLock().lock();
        Client client = clients.removeK1(connectionkey);
        log.debug(format("Connection %s removed from client manager", connectionkey));
        clientsLock.writeLock().unlock();
        disconnectHandler.handleDisconnect(client);
    }

    public void disconnectByConnectionId(final long id) {
        clientsLock.writeLock().lock();
        Client client = clients.removeK2(id);
        log.debug(format("Connection with id %s removed from client manager", id));
        clientsLock.writeLock().unlock();
        disconnectHandler.handleDisconnect(client);
    }
}
