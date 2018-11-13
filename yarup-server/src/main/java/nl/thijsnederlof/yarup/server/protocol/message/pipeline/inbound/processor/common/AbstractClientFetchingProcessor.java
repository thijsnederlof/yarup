package nl.thijsnederlof.yarup.server.protocol.message.pipeline.inbound.processor.common;

import nl.thijsnederlof.common.logger.AbstractYarupLogger;
import nl.thijsnederlof.yarup.server.protocol.connection.ClientManager;
import nl.thijsnederlof.yarup.server.protocol.connection.model.Client;
import nl.thijsnederlof.yarup.server.protocol.message.Buffer;

import static nl.thijsnederlof.common.util.Connectionkey.parse;

public abstract class AbstractClientFetchingProcessor extends AbstractProcessor {

    protected final ClientManager clientManager;

    public AbstractClientFetchingProcessor(final AbstractYarupLogger log, final ClientManager clientManager) {
        super(log);
        this.clientManager = clientManager;
    }

    protected abstract void processClientMessage(final Client client, final Buffer buffer);

    @Override
    protected void doProcess(final String host, final int port, final Buffer buffer) {
        if(!clientManager.isConnected(host, port)) {
            log.warn("Unconnected client " + parse(host, port) + " tried to send message that required connecting");
            return;
        }

        final Client client = clientManager.getClientByConnectionkey(parse(host, port));
        client.getConnectionDetails().updatelastMessageReceivedTimestamp();

        this.processClientMessage(client, buffer);
    }
}