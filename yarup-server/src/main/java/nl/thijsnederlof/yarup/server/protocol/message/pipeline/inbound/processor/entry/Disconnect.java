package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.entry;

import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.connection.ClientManager;
import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;
import nl.thijsnederlof.yarup.server.protocol.handler.disconnect.YarupDisconnectHandler;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common.AbstractClientFetchingProcessor;

import static java.lang.String.format;

public class Disconnect extends AbstractClientFetchingProcessor {

    private final YarupDisconnectHandler disconnectHandler;

    public Disconnect(final AbstractYarupLogger log, final YarupDisconnectHandler disconnectHandler, final ClientManager clientManager) {
        super(log, clientManager);
        this.disconnectHandler = disconnectHandler;
    }

    @Override
    protected void processClientMessage(final Client client, final Buffer buffer) {
        disconnectHandler.handleDisconnect(client);
        this.clientManager.disconnectByConnectionId(client.getId());
        log.info(format("Client %s disconnected", client.getConnectionDetails().getConnectionKey()));
    }
}
