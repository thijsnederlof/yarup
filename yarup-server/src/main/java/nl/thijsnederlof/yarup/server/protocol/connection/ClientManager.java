package nl.thijsnederlof.yarup.server.protocol.connection;

import nl.thijsnederlof.common.util.MultiKeyMap;
import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;
import nl.thijsnederlof.yarup.server.protocol.handler.DisconnectHandler;
import nl.thijsnederlof.yarup.server.protocol.handler.TimeoutHandler;
import nl.thijsnederlof.yarup.server.protocol.message.MessageFactory;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static nl.thijsnederlof.common.util.Connectionkey.parseConnectionkey;

public class ClientManager {

    private final ReentrantReadWriteLock clientsLock = new ReentrantReadWriteLock();

    private final MultiKeyMap<String, Long, Client> clients;

    private AtomicLong clientCounter = new AtomicLong();

    private final long maxConnections;

    private final DisconnectHandler disconnectHandler;

    private final MessageFactory messageFactory;

    private final TimeoutHandler timeoutHandler;

    public ClientManager(final long maxConnections, final DisconnectHandler disconnectHandler, final MessageFactory messageFactory, final TimeoutHandler timeoutHandler) {
        this.disconnectHandler = disconnectHandler;
        this.maxConnections = maxConnections;
        this.messageFactory = messageFactory;
        this.timeoutHandler = timeoutHandler;
        clients = new MultiKeyMap<>();
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
            return clients.containsK1(parseConnectionkey(host, port));
        } finally {
            clientsLock.readLock().unlock();
        }
    }

    public boolean addNewConnection(final String host, final int port) {
        if(clients.size() < maxConnections) {
            try {
                clientsLock.writeLock().lock();

                clients.put(parseConnectionkey(host, port), clientCounter.incrementAndGet(), new Client(messageFactory, host, port, clientCounter.incrementAndGet()));
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
            clientsLock.writeLock().unlock();
            disconnectHandler.handleDisconnect(client);
    }

    public void disconnectByConnectionId(final long id) {
        clientsLock.writeLock().lock();
        Client client = clients.removeK2(id);
        clientsLock.writeLock().unlock();
        disconnectHandler.handleDisconnect(client);
    }
}
