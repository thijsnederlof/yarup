package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.entry;

import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.connection.ClientManager;
import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;
import nl.thijsnederlof.yarup.server.protocol.handler.message.YarupMessageHandler;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common.AbstractClientFetchingProcessor;

import static java.lang.String.format;

public class Message extends AbstractClientFetchingProcessor {

    private final YarupMessageHandler messageHandler;

    public Message(final AbstractYarupLogger log, final ClientManager clientManager, final YarupMessageHandler messageHandler) {
        super(log, clientManager);
        this.messageHandler = messageHandler;
    }

    @Override
    protected void processClientMessage(Client client, Buffer buffer) {
        this.messageHandler.handleMessage(client, buffer.getPayload());
        log.debug(format("Message by connection %s processed", client.getConnectionDetails().getConnectionKey()));
    }
}
