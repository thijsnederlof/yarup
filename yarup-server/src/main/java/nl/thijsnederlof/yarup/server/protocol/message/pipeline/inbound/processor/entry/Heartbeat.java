package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.entry;

import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.connection.ClientManager;
import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;
import nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common.AbstractClientFetchingProcessor;

public class Heartbeat extends AbstractClientFetchingProcessor {

    public Heartbeat(final AbstractYarupLogger log, final ClientManager clientManager) {
        super(log, clientManager);
    }

    @Override
    protected void processClientMessage(Client client, Buffer buffer) {
        // Currently do nothing
    }
}
